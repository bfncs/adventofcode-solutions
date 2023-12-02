package us.byteb.advent.year2023;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Collection;
import java.util.List;
import java.util.function.ToLongFunction;

public class Day01 {

  public static void main(String[] args) {
    final List<String> input = parseInput(readFileFromResources("year2023/day01.txt"));

    System.out.println("Part 1: " + sumCalibrationValues(input, Day01::parseDigits));
    System.out.println("Part 2: " + sumCalibrationValues(input, Day01::parseDigitsAndWords));
  }

  static List<String> parseInput(final String input) {
    return input.lines().toList();
  }

  static long sumCalibrationValues(
      final Collection<String> lines, final ToLongFunction<String> parseCalibrationValue) {
    return lines.stream().mapToLong(parseCalibrationValue).sum();
  }

  static long parseDigits(final String s) {
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

  static long parseDigitsAndWords(final String s) {
    return (findFirstDigit(s) * 10) + findLastDigit(s);
  }

  private static long findFirstDigit(final String s) {
    String unparsed = s;

    while (!unparsed.isEmpty()) {
      final char c = unparsed.charAt(0);
      if (c >= '0' && c <= '9') {
        return Long.valueOf(String.valueOf(c));
      }

      for (final Digits digit : Digits.values()) {
        if (unparsed.startsWith(digit.name().toLowerCase())) {
          return digit.value;
        }
      }

      unparsed = unparsed.substring(1);
    }

    throw new IllegalStateException("No digit found in " + s);
  }

  private static long findLastDigit(final String s) {
    String unparsed = s;

    while (!unparsed.isEmpty()) {
      final char c = unparsed.charAt(unparsed.length() - 1);
      if (c >= '0' && c <= '9') {
        return Long.valueOf(String.valueOf(c));
      }

      for (final Digits digit : Digits.values()) {
        if (unparsed.endsWith(digit.name().toLowerCase())) {
          return digit.value;
        }
      }

      unparsed = unparsed.substring(0, unparsed.length() - 1);
    }

    throw new IllegalStateException("No digit found in " + s);
  }

  enum Digits {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);
    private final long value;

    Digits(final long value) {
      this.value = value;
    }
  }
}
