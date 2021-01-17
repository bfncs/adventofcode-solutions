package us.byteb.advent.y20;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class Day19 {

  public static void main(String[] args) {
    final PuzzleInput puzzleInput = parsePuzzleInput(readFileFromResources("y20/day19.txt"));

    final long numberOfValidMessages =
        puzzleInput.messages().stream()
            .filter(message -> Rule.isValid(puzzleInput.rules(), message))
            .count();
    System.out.println("Part 1: " + numberOfValidMessages);
  }

  record PuzzleInput(Map<Integer, Rule> rules, List<String> messages) {}
  ;

  private static PuzzleInput parsePuzzleInput(final String input) {
    final List<String> lines = input.lines().collect(toList());

    final int firstEmptyLine =
        lines.indexOf(lines.stream().filter(l -> l.isEmpty()).findFirst().orElseThrow());
    final String rulesInput = lines.subList(0, firstEmptyLine).stream().collect(joining("\n"));
    final Map<Integer, Rule> rules = Rule.parse(rulesInput);

    return new PuzzleInput(rules, lines.subList(firstEmptyLine + 1, lines.size()));
  }

  interface Rule {
    private static void logMatch(
        final String input, final Rule rule, final boolean isMatching, final String remainder) {
      if (isMatching) {
        System.out.printf("✔ '%s' %s %s%n", input, rule, remainder);
      } else {
        System.out.printf("⭕ '%s' %s %s%n", input, rule, remainder);
      }
    }

    static Map<Integer, Rule> parse(final String input) {
      final List<String> lines = input.lines().collect(toList());

      final Map<Integer, Rule> rules = parseRules(lines);
      System.out.println(rules);

      return rules;
    }

    static boolean isValid(final Map<Integer, Rule> ruleSet, final String input) {
      if (StringUtils.isBlank(input)) {
        return false;
      }

      final ValidationResult result = ruleSet.get(0).isValid(input, ruleSet);
      return result.isValid() && result.remainder().isEmpty();
    }

    ValidationResult isValid(String input, Map<Integer, Rule> rules);

    private static Map<Integer, Rule> parseRules(final List<String> lines) {
      final Map<Integer, Rule> rules = new HashMap<>();
      for (final String line : lines) {
        final String[] split = line.split(":");
        final int pos = parseInt(split[0]);
        final String value = split[1].trim();

        final Rule rule = parseRuleValue(pos, value);
        rules.put(pos, rule);
      }

      return rules;
    }

    private static Rule parseRuleValue(final int pos, final String value) {
      if (value.charAt(0) == '"') {
        return literal(pos, value.charAt(1));
      } else {
        if (value.contains("|")) {
          final List<Rule> rules =
              Arrays.stream(value.split("\\|"))
                  .map(String::trim)
                  .map(v -> parseRuleValue(pos, v))
                  .collect(toList());
          return or(pos, rules);
        } else {
          final List<Rule> rules =
              Arrays.stream(value.split("\\s+")).map(ref -> ref(parseInt(ref))).collect(toList());
          return seq(pos, rules);
        }
      }
    }

    static Literal literal(int pos, final char value) {
      return new Literal(pos, value);
    }

    static Or or(int pos, final Rule... rules) {
      return or(pos, asList(rules));
    }

    static Or or(final int pos, final List<Rule> rules) {
      return new Or(pos, rules);
    }

    static Seq seq(int pos, final Rule... rules) {
      return seq(pos, asList(rules));
    }

    static Seq seq(final int pos, final List<Rule> rules) {
      return new Seq(pos, rules);
    }

    static Ref ref(int refPos) {
      return new Ref(refPos);
    }

    final record ValidationResult(boolean isValid, String remainder) {}

    final record Literal(int pos, char value) implements Rule {
      @Override
      public ValidationResult isValid(final String input, final Map<Integer, Rule> ruleSet) {
        final ValidationResult result =
            new ValidationResult(input.charAt(0) == value, input.substring(1));
        logMatch(input, this, result.isValid(), result.remainder());
        return result;
      }

      @Override
      public String toString() {
        return "lit[" + pos + "](" + value + ")";
      }
    }

    final record Or(int pos, List<Rule> rules) implements Rule {
      @Override
      public ValidationResult isValid(final String input, final Map<Integer, Rule> ruleSet) {
        for (final Rule rule : rules) {
          final ValidationResult result = rule.isValid(input, ruleSet);
          if (result.isValid()) {
            logMatch(input, this, true, result.remainder());
            return result;
          }
        }
        logMatch(input, this, false, null);
        return new ValidationResult(false, null);
      }

      @Override
      public String toString() {
        return "or["
            + pos
            + "]("
            + rules.stream().map(Object::toString).collect(joining(", "))
            + ")";
      }
    }

    final record Seq(int pos, List<Rule> rules) implements Rule {
      @Override
      public ValidationResult isValid(final String input, final Map<Integer, Rule> ruleSet) {
        String curInput = input;

        for (Rule rule : rules) {
          if (curInput.isEmpty()) {
            logMatch(input, this, false, "");
            return new ValidationResult(false, "");
          }

          final ValidationResult result = rule.isValid(curInput, ruleSet);
          if (!result.isValid()) {
            logMatch(input, this, false, result.remainder());
            return result;
          }

          curInput = result.remainder();
        }

        logMatch(input, this, true, curInput);
        return new ValidationResult(true, curInput);
      }

      @Override
      public String toString() {
        return "seq["
            + pos
            + "]("
            + rules.stream().map(Object::toString).collect(joining(", "))
            + ")";
      }
    }

    record Ref(int rulePos) implements Rule {
      @Override
      public ValidationResult isValid(final String input, final Map<Integer, Rule> ruleSet) {
        final ValidationResult result = ruleSet.get(rulePos).isValid(input, ruleSet);
        // logMatch(input, this, result.isValid(), result.remainder());
        return result;
      }

      @Override
      public String toString() {
        return "ref(" + rulePos + ")";
      }
    }
  }
}
