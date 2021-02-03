package us.byteb.advent.year2020;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;
import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Day19 {

  public static void main(String[] args) {
    final PuzzleInput puzzleInput = PuzzleInput.parse(readFileFromResources("year2020/day19.txt"));

    System.out.println("Part 1: " + puzzleInput.findValidMessages().size());
    System.out.println("Part 2: " + puzzleInput.withModifiedRuleSet().findValidMessages().size());
  }

  record PuzzleInput(RuleSet ruleSet, List<String> messages) {
    static PuzzleInput parse(final String input) {
      final List<String> lines = input.lines().collect(toList());

      final int firstEmptyLine =
          lines.indexOf(lines.stream().filter(String::isEmpty).findFirst().orElseThrow());
      final String rulesInput = String.join("\n", lines.subList(0, firstEmptyLine));
      final RuleSet ruleSet = RuleSet.parse(rulesInput);

      return new PuzzleInput(ruleSet, lines.subList(firstEmptyLine + 1, lines.size()));
    }

    Set<String> findValidMessages() {
      return messages.stream().filter(ruleSet::isValid).collect(toSet());
    }

    PuzzleInput withModifiedRuleSet() {
      return new PuzzleInput(ruleSet.withModifiedRules(), messages);
    }
  }

  record RuleSet(Map<Integer, Rule> rules) {
    public static RuleSet parse(final String input) {
      final List<String> lines = input.lines().collect(toList());

      final Map<Integer, Rule> rules = new HashMap<>();
      for (final String line : lines) {
        final String[] split = line.split(":");
        final int pos = parseInt(split[0]);
        final String value = split[1].trim();

        final Rule rule = Rule.parseRuleValue(pos, value);
        rules.put(pos, rule);
      }

      return new RuleSet(rules);
    }

    private RuleSet withModifiedRules() {
      final Map<Integer, Rule> modifiedRules = new HashMap<>(rules);
      modifiedRules.put(8, Rule.parseRuleValue(8, "42 | 42 8"));
      modifiedRules.put(11, Rule.parseRuleValue(11, "42 31 | 42 11 31"));

      return new RuleSet(modifiedRules);
    }

    boolean isValid(final String input) {
      if (StringUtils.isBlank(input)) {
        return false;
      }

      final List<String> result = rules.get(0).matches(input, this);
      return result.stream().anyMatch(m -> m.length() == 0);
    }

    public Rule rule(final int pos) {
      return rules.get(pos);
    }
  }

  interface Rule {
    List<String> matches(String input, RuleSet ruleSet);

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

    final record Literal(int pos, char value) implements Rule {
      @Override
      public List<String> matches(final String input, final RuleSet ruleSet) {
        if (input.isBlank()) {
          return emptyList();
        }

        final boolean isMatching = input.charAt(0) == value;
        final String remainder = input.substring(1);
        if (isMatching) {
          return List.of(remainder);
        } else {
          return emptyList();
        }
      }
    }

    final record Or(int pos, List<Rule> rules) implements Rule {
      @Override
      public List<String> matches(final String input, final RuleSet ruleSet) {
        final ArrayList<String> result = new ArrayList<>();
        for (final Rule rule : rules) {
          result.addAll(rule.matches(input, ruleSet));
        }

        return result;
      }
    }

    final record Seq(int pos, List<Rule> rules) implements Rule {
      @Override
      public List<String> matches(final String input, final RuleSet ruleSet) {
        List<String> curMatches = List.of(input);

        for (final Rule rule : rules) {
          final List<String> matches = new ArrayList<>();
          for (final String curInput : curMatches) {
            matches.addAll(rule.matches(curInput, ruleSet));
          }

          if (matches.isEmpty()) {
            return matches;
          }

          curMatches = matches;
        }

        return curMatches;
      }
    }

    record Ref(int rulePos) implements Rule {
      @Override
      public List<String> matches(final String input, final RuleSet ruleSet) {
        return ruleSet.rule(rulePos).matches(input, ruleSet);
      }
    }
  }
}
