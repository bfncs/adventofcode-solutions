package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day02 {

  private static final int MIN_DIFFERENCE = 1;
  private static final int MAX_DIFFERENCE = 3;

  public static void main(String[] args) {
    final List<List<Long>> input = parseInput(readFileFromResources("year2024/day02.txt"));

    System.out.println("Part 1: " + input.stream().filter(Day02::isSafe).count());
  }

  static List<List<Long>> parseInput(final String input) {
    return input
        .lines()
        .map(line -> Arrays.stream(line.split("\\s+")).map(Long::valueOf).toList())
        .toList();
  }

  static boolean isSafe(final List<Long> report) {
    final boolean isIncreasing = report.get(0) < report.get(MIN_DIFFERENCE);
    Long lastItem = null;
    for (final Long item : report) {
      if (lastItem != null) {
        if (isIncreasing && item < lastItem) {
          return false;
        }

        if (!isIncreasing && item > lastItem) {
          return false;
        }

        final long difference = Math.abs(item - lastItem);
        if (difference < MIN_DIFFERENCE || difference > MAX_DIFFERENCE) {
          return false;
        }
      }
      lastItem = item;
    }

    return true;
  }
}
