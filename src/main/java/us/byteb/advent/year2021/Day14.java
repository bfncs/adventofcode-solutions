package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day14 {

  public static void main(String[] args) throws IOException {
    final PuzzleInput input = parseInput(readFileFromResources("year2021/day14.txt"));

    System.out.println("Part 1: " + mostCommonsMinusLeastCommonCharacter(applyRules(input, 10)));
    System.out.println("Part 2: " + mostCommonsMinusLeastCommonCharacter(applyRules(input, 40)));
  }

  static long mostCommonsMinusLeastCommonCharacter(final Map<Character, Long> histogram) {
    final Comparator<Map.Entry<Character, Long>> comparator =
        (o1, o2) ->
            o1.getValue().equals(o2.getValue()) ? 0 : o1.getValue() > o2.getValue() ? 1 : -1;

    final long mostCommonCount =
        histogram.entrySet().stream().max(comparator).map(Map.Entry::getValue).orElseThrow();
    final long leastCommonCount =
        histogram.entrySet().stream().min(comparator).map(Map.Entry::getValue).orElseThrow();

    return mostCommonCount - leastCommonCount;
  }

  static Map<Character, Long> applyRules(final PuzzleInput input, final int steps) {
    final Map<String, Long> pairCountHistogram =
        applyRules(
            toPairCountHistogram(input.polymerTemplate()), input.pairInsertionRules(), steps);
    return toCharCountHistogram(pairCountHistogram);
  }

  private static Map<String, Long> applyRules(
      final Map<String, Long> input,
      final Set<PairInsertionRule> pairInsertionRules,
      final int steps) {
    final Map<String, Long> result = new HashMap<>();

    input.forEach(
        (pair, pairCount) -> {
          final Optional<PairInsertionRule> maybeMatchingRule =
              pairInsertionRules.stream().filter(rule -> rule.pair().equals(pair)).findAny();
          if (maybeMatchingRule.isPresent()) {
            final PairInsertionRule rule = maybeMatchingRule.get();

            final String pair1 = rule.pair().substring(0, 1) + rule.elementToBeInserted();
            result.put(pair1, result.getOrDefault(pair1, 0L) + pairCount);
            final String pair2 = rule.elementToBeInserted() + rule.pair().substring(1, 2);
            result.put(pair2, result.getOrDefault(pair2, 0L) + pairCount);
          } else {
            result.put(pair, result.getOrDefault(pair, 0L) + pairCount);
          }
        });

    return steps > 1 ? applyRules(result, pairInsertionRules, steps - 1) : result;
  }

  private static Map<Character, Long> toCharCountHistogram(
      final Map<String, Long> pairCountHistogram) {
    final Map<Character, Long> result = new HashMap<>();
    pairCountHistogram.forEach(
        (pair, count) -> {
          final char countingChar = pair.charAt(0);
          result.put(countingChar, result.getOrDefault(countingChar, 0L) + count);
        });

    return result;
  }

  private static Map<String, Long> toPairCountHistogram(final String input) {
    final Map<String, Long> result = new HashMap<>();

    for (int i = 0; i < (input.length() - 1); i++) {
      final String pair = input.substring(i, i + 2);
      result.put(pair, result.getOrDefault(pair, 0L) + 1);
    }
    result.put(input.substring(input.length() - 1), 1L);

    return result;
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
