package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 {

  public static void main(String[] args) throws IOException {
    final List<Long> input = parseInput(readFileFromResources("year2021/day01.txt"));

    System.out.println("Part 1: " + countIncreases(input));
  }

  static List<Long> parseInput(final String input) {
    return input.lines().map(Long::parseLong).collect(Collectors.toList());
  }

  static long countIncreases(final List<Long> measurements) {
    long lastMeasurement = Long.MAX_VALUE;
    long numIncreases = 0;
    for (final long measurement : measurements) {
      if (measurement > lastMeasurement) {
        numIncreases++;
      }
      lastMeasurement = measurement;
    }

    return numIncreases;
  }
}
