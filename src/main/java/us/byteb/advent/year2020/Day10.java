package us.byteb.advent.year2020;

import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10 {

  static List<Integer> TRIBONACCI_SEQUENCE = List.of(1, 1, 2, 4, 7);

  public static void main(String[] args) {
    final List<Long> input = parseInput(readFileFromResources("year2020/day10.txt"));

    final Map<Long, Long> resultPart1 = chainVoltageDifferences(input);
    System.out.println("Part 1: " + resultPart1.get(1L) * resultPart1.get(3L));
    System.out.println("Part 2: " + countDistinctValidArrangements(input));
  }

  static List<Long> parseInput(final String input) {
    return input.lines().map(Long::parseLong).collect(Collectors.toList());
  }

  static Map<Long, Long> chainVoltageDifferences(final List<Long> input) {
    final List<Long> sorted = input.stream().sorted().collect(Collectors.toList());

    final HashMap<Long, Long> result = new HashMap<>();
    long lastItem = 0;
    for (final long item : sorted) {
      final long delta = item - lastItem;
      result.compute(delta, (k, v) -> (v == null) ? 1 : v + 1);
      lastItem = item;
    }

    result.compute(3L, (k, v) -> (v == null) ? 1 : v + 1);

    return result;
  }

  static BigInteger countDistinctValidArrangements(final List<Long> remainingItems) {
    final LinkedList<Long> sorted =
        remainingItems.stream().sorted().collect(Collectors.toCollection(LinkedList::new));
    sorted.addFirst(0L);
    sorted.addLast(sorted.stream().mapToLong(i -> i).max().getAsLong() + 3);

    return countDistinctValidArrangementsRec(sorted, 0, BigInteger.ONE);
  }

  static BigInteger countDistinctValidArrangementsRec(
      final List<Long> items, final int streak, final BigInteger combos) {

    if (items.isEmpty()) {
      return combos;
    }

    final List<Long> remainingInput = items.subList(1, items.size());
    return items.contains(items.get(0) + 1)
        ? countDistinctValidArrangementsRec(remainingInput, streak + 1, combos)
        : countDistinctValidArrangementsRec(
            remainingInput,
            0,
            combos.multiply(BigInteger.valueOf(TRIBONACCI_SEQUENCE.get(streak))));
  }
}
