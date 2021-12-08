package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;

public class Day07 {

  public static final BiFunction<Integer, Integer, Integer> PART1_STRATEGY =
      (position, attempt) -> Math.abs(position - attempt);
  public static final BiFunction<Integer, Integer, Integer> PART2_STRATEGY =
      (position, attempt) -> {
        final int distance = Math.abs(position - attempt);

        int result = 0;
        for (int i = 0; i <= distance; i++) {
          result += i;
        }

        return result;
      };

  public static void main(String[] args) throws IOException {
    final List<Integer> input = parseInput(readFileFromResources("year2021/day07.txt"));

    System.out.println("Part 1: " + findCheapestAlignment(input, PART1_STRATEGY));
    System.out.println("Part 2: " + findCheapestAlignment(input, PART2_STRATEGY));
  }

  static List<Integer> parseInput(final String input) {
    return Arrays.stream(input.split(",")).map(Integer::parseInt).toList();
  }

  static long findCheapestAlignment(
      final List<Integer> positions,
      final BiFunction<Integer, Integer, Integer> calculateNeededFuel) {
    final int minAttempt = positions.stream().mapToInt(Integer::intValue).min().orElseThrow();
    final int maxAttempt = positions.stream().mapToInt(Integer::intValue).max().orElseThrow();
    long cheapestAttempt = Long.MAX_VALUE;

    for (int attempt = minAttempt + 1; attempt <= maxAttempt; attempt++) {
      long totalFuel = 0;
      for (final int position : positions) {
        totalFuel += calculateNeededFuel.apply(position, attempt);
      }
      cheapestAttempt = Math.min(cheapestAttempt, totalFuel);
    }

    return cheapestAttempt;
  }
}
