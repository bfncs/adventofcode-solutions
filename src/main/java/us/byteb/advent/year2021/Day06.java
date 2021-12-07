package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;

public class Day06 {

  public static void main(String[] args) throws IOException {
    final List<Integer> input = parseInput(readFileFromResources("year2021/day06.txt"));

    System.out.println("Part 1: " + totalFishAfterDays(input, 80));
    System.out.println("Part 2: " + totalFishAfterDays(input, 256));
  }

  static List<Integer> parseInput(final String input) {
    return Arrays.stream(input.split(",")).map(Integer::parseInt).toList();
  }

  static long totalFishAfterDays(final List<Integer> fishes, final int numDays) {
    Map<Integer, Long> currentHistogram = createHistogram(fishes);

    for (int i = 0; i < numDays; i++) {
      currentHistogram = evolveNextDay(currentHistogram);
    }

    return currentHistogram.values().stream().mapToLong(l -> l).sum();
  }

  private static Map<Integer, Long> createHistogram(final List<Integer> fishes) {
    final Map<Integer, Long> histogram = new HashMap<>();

    for (final Integer fish : fishes) {
      histogram.put(fish, histogram.getOrDefault(fish, 0L) + 1);
    }

    return histogram;
  }

  private static Map<Integer, Long> evolveNextDay(final Map<Integer, Long> histogram) {
    final Map<Integer, Long> result = new HashMap<>();

    for (final Map.Entry<Integer, Long> entry : histogram.entrySet()) {
      final int age = entry.getKey();
      final long count = entry.getValue();

      if (age == 0) {
        result.put(6, result.getOrDefault(6, 0L) + count);
        result.put(8, count);
      } else {
        result.put(age - 1, result.getOrDefault(age - 1, 0L) + count);
      }
    }

    return result;
  }
}
