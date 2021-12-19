package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day14 {

  public static void main(String[] args) throws IOException {
    final PuzzleInput input = parseInput(readFileFromResources("year2021/day14.txt"));

    System.out.println("Part 1: " + solvePart1(applyRules(input, 10)));
  }

  static long solvePart1(final String input) {
    final Map<Character, Long> histogram = charCountHistogram(input);
    final Long mostCommonCount =
        histogram.entrySet().stream()
            .max((o1, o2) -> (int) (o1.getValue() - o2.getValue()))
            .map(Map.Entry::getValue)
            .orElseThrow();
    final Long leastCommonCount =
        histogram.entrySet().stream()
            .min((o1, o2) -> (int) (o1.getValue() - o2.getValue()))
            .map(Map.Entry::getValue)
            .orElseThrow();

    return mostCommonCount - leastCommonCount;
  }

  private static Map<Character, Long> charCountHistogram(final String input) {
    return input
        .chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  static String applyRules(final PuzzleInput input, final int steps) {
    return applyRules(input.polymerTemplate(), input.pairInsertionRules(), steps);
  }

  static String applyRules(
      final String polymerTemplate,
      final Set<PairInsertionRule> pairInsertionRules,
      final int steps) {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < (polymerTemplate.length() - 1); i++) {
      sb.append(polymerTemplate.charAt(i));

      final String pair = polymerTemplate.substring(i, i + 2);
      pairInsertionRules.stream()
          .filter(rule -> rule.pair().equals(pair))
          .findAny()
          .ifPresent(
              pairInsertionRule -> {
                sb.append(pairInsertionRule.elementToBeInserted());
              });
    }
    sb.append(polymerTemplate.charAt(polymerTemplate.length() - 1));

    final String result = sb.toString();
    return steps > 1 ? applyRules(result, pairInsertionRules, steps - 1) : result;
  }

  static PuzzleInput parseInput(final String input) {
    final List<String> lines = input.lines().toList();
    final String polymerTemplate = lines.get(0);

    final Set<PairInsertionRule> pairInsertionRules =
        lines.subList(2, lines.size()).stream()
            .map(PairInsertionRule::parse)
            .collect(Collectors.toSet());

    return new PuzzleInput(polymerTemplate, pairInsertionRules);
  }

  record PuzzleInput(String polymerTemplate, Set<PairInsertionRule> pairInsertionRules) {}

  record PairInsertionRule(String pair, char elementToBeInserted) {
    public static PairInsertionRule parse(final String input) {
      final String[] parts = input.split("->");
      if (parts.length != 2) throw new IllegalStateException();

      return new PairInsertionRule(parts[0].trim(), parts[1].trim().charAt(0));
    }
  }
}
