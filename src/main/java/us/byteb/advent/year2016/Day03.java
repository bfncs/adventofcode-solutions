package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

public class Day03 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2016/day03.txt");

    System.out.println("Part 1: " + countPossibleTriangles(input));
  }

  private static long countPossibleTriangles(final String input) {
    return input
        .lines()
        .filter(
            line -> {
              final String[] parts = line.trim().split("\\s+");
              if (parts.length != 3) throw new IllegalStateException();
              return isValidTriangle(
                  Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
            })
        .count();
  }

  static boolean isValidTriangle(long a, long b, long c) {
    return ((a + b) > c && (b + c) > a && (c + a) > b);
  }
}
