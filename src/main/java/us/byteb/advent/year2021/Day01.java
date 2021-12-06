package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 {

  public static void main(String[] args) throws IOException {
    final List<Long> input = parseInput(readFileFromResources("year2021/day01.txt"));

    System.out.println("Part 1: " + countIncreases(input, 1));
    System.out.println("Part 2: " + countIncreases(input, 3));
  }

  static List<Long> parseInput(final String input) {
    return input.lines().map(Long::parseLong).collect(Collectors.toList());
  }

  static long countIncreases(final List<Long> measurements, final int windowSize) {
    long lastSum = Long.MAX_VALUE;
    long numIncreases = 0;

    for (int i = 0; i < measurements.size(); i++) {
      if ((i - windowSize + 1) < 0) {
        continue;
      }

      long sum = 0;
      for (int j = 0; j < windowSize; j++) {
        sum += measurements.get(i - j);
      }

      if (sum > lastSum) {
        numIncreases++;
      }
      lastSum = sum;
    }

    return numIncreases;
  }
}
