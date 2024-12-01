package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day01 {

  public static void main(String[] args) {
    final Comparison input = parseInput(readFileFromResources("year2024/day01.txt"));

    System.out.println("Part 1: " + totalDistance(input));
    System.out.println("Part 2: " + similarityScore(input));
  }

  static Comparison parseInput(final String input) {
    final List<Long> left = new ArrayList<>();
    final List<Long> right = new ArrayList<>();
    final Iterable<String> lines = () -> new Scanner(input).useDelimiter("\n");
    for (final String line : lines) {
      final String[] parts = line.split("\\s+");
      if (parts.length != 2) {
        throw new IllegalStateException("Unexpected line: " + line);
      }
      left.add(Long.valueOf(parts[0]));
      right.add(Long.valueOf(parts[1]));
    }

    return new Comparison(left, right);
  }

  static long totalDistance(final Comparison lists) {
    long total = 0L;

    final List<Long> left = lists.left().stream().sorted().toList();
    final List<Long> right = lists.right().stream().sorted().toList();

    for (int i = 0; i < left.size(); i++) {
      total += Math.abs(left.get(i) - right.get(i));
    }

    return total;
  }

  static long similarityScore(final Comparison lists) {
    long total = 0L;

    for (final long leftNum : lists.left()) {
      final long rightCount = lists.right().stream().filter(num -> num == leftNum).count();
      total += (leftNum * rightCount);
    }

    return total;
  }

  record Comparison(List<Long> left, List<Long> right) {}
}
