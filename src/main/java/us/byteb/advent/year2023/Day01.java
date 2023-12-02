package us.byteb.advent.year2023;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Collection;
import java.util.List;

public class Day01 {

  public static void main(String[] args) {
    final List<String> input = parseInput(readFileFromResources("year2023/day01.txt"));

    System.out.println("Part 1: " + sumCalibrationValues(input));
  }

  static List<String> parseInput(final String input) {
    return input.lines().toList();
  }

  static long sumCalibrationValues(final Collection<String> lines) {
    return lines.stream().mapToLong(Day01::parseCalibrationValue).sum();
  }

  private static long parseCalibrationValue(final String s) {
    Long first = null;
    Long last = null;
    for (final char c : s.toCharArray()) {
      if (c < '0' || c > '9') {
        continue;
      }
      final Long asLong = Long.valueOf(String.valueOf(c));
      if (first == null) {
        first = asLong;
      }
      last = asLong;
    }

    return (first * 10) + last;
  }
}
