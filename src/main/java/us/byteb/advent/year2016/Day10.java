package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2016/day10.txt");
    System.out.println("Part 1: " + solvePart1(input));
    System.out.println("Part 2: " + solvePart2(input));
  }

  private static int solvePart1(final String input) {
    final Factory factory = new Factory(input);
    final List<Action> actions = factory.process();
    return actions.stream()
        .filter(action -> action.lowValue() == 17 && action.highValue() == 61)
        .findFirst()
        .orElseThrow()
        .source();
  }

  private static int solvePart2(final String input) {
    final Factory factory = new Factory(input);
    final List<Action> actions = factory.process();
    Map<Integer, List<Integer>> outputValues = new HashMap<>();
    for (Action action : actions) {
      if (action.isLowOutput()) {
        outputValues
            .computeIfAbsent(action.lowTarget(), _ -> new ArrayList<>())
            .add(action.lowValue());
      }
      if (action.isHighOutput()) {
        outputValues
            .computeIfAbsent(action.highTarget(), k -> new ArrayList<>())
            .add(action.highValue());
      }
    }
    return outputValues.get(0).get(0) * outputValues.get(1).get(0) * outputValues.get(2).get(0);
  }

  static class Factory {
    private static final Pattern VALUE_PATTERN =
        Pattern.compile("value (?<value>\\d+) goes to bot (?<bot>\\d+)");

    private final Map<Integer, Rule> rules = new HashMap<>();
    private Map<Integer, List<Integer>> states = new HashMap<>();

    Factory(final String instructions) {
      for (final String line : instructions.lines().toList()) {
        final Matcher matcher = VALUE_PATTERN.matcher(line);
        if (matcher.matches()) {
          final int value = Integer.parseInt(matcher.group("value"));
          final int bot = Integer.parseInt(matcher.group("bot"));
          states.computeIfAbsent(bot, _ -> new ArrayList<>()).add(value);
        } else {
          final Rule rule = Rule.parse(line);
          rules.put(rule.bot(), rule);
        }
      }
    }

    public List<Action> process() {
      final List<Action> actions = new ArrayList<>();
      boolean executedRule = true;
      while (executedRule) {
        executedRule = false;
        final HashMap<Integer, List<Integer>> nextStates = new HashMap<>(states);
        for (final Integer bot : states.keySet()) {
          final List<Integer> values = states.get(bot);
          if (values.size() < 2) {
            continue;
          } else if (values.size() > 2) {
            throw new IllegalStateException(
                "Wrong number of values for bot %d: %s".formatted(bot, values));
          }

          executedRule = true;
          final Rule rule = rules.get(bot);
          int low = values.get(0) < values.get(1) ? values.get(0) : values.get(1);
          int high = values.get(1) == low ? values.get(0) : values.get(1);
          actions.add(
              new Action(
                  bot,
                  rule.isLowOutput(),
                  rule.isHighOutput(),
                  rule.lowTarget(),
                  rule.highTarget(),
                  low,
                  high));

          if (!rule.isLowOutput()) {
            nextStates.computeIfAbsent(rule.lowTarget(), _ -> new ArrayList<>()).add(low);
          }
          if (!rule.isHighOutput()) {
            nextStates.computeIfAbsent(rule.highTarget(), _ -> new ArrayList<>()).add(high);
          }
          nextStates.get(bot).clear();
          states = nextStates;
        }
      }

      return actions;
    }
  }

  record Rule(int bot, boolean isLowOutput, boolean isHighOutput, int lowTarget, int highTarget) {
    private static final Pattern RULE_PATTERN =
        Pattern.compile(
            "bot (?<bot>\\d+) gives low to (?<lowTargetType>(bot|output)) (?<lowTarget>\\d+) and high to (?<highTargetType>(bot|output)) (?<highTarget>\\d+)");

    static Rule parse(final String line) {
      final Matcher matcher = RULE_PATTERN.matcher(line);
      if (!matcher.matches()) {
        throw new IllegalStateException("Illegal rule: " + line);
      }
      return new Rule(
          Integer.parseInt(matcher.group("bot")),
          "output".equals(matcher.group("lowTargetType")),
          "output".equals(matcher.group("highTargetType")),
          Integer.parseInt(matcher.group("lowTarget")),
          Integer.parseInt(matcher.group("highTarget")));
    }
  }

  record Action(
      int source,
      boolean isLowOutput,
      boolean isHighOutput,
      int lowTarget,
      int highTarget,
      int lowValue,
      int highValue) {}
}
