package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.List;

public class Day04 {

  public static void main(String[] args) {
    final List<Pair> input = parseInput(readFileFromResources("year2022/day04.txt"));

    System.out.println("Part 1: " + numberOfPairsWhereOneContainsTheOther(input));
  }

  static List<Pair> parseInput(final String input) {
    return input.lines().map(Pair::of).toList();
  }

  static long numberOfPairsWhereOneContainsTheOther(final List<Pair> pairs) {
    return pairs.stream().filter(Pair::oneAssigmentContainsOther).count();
  }

  record Pair(Assignment first, Assignment second) {
    static Pair of(final String input) {
      final String[] parts = input.split(",");
      return new Pair(Assignment.of(parts[0]), Assignment.of(parts[1]));
    }

    boolean oneAssigmentContainsOther() {
      final long lower = Math.min(first.lower(), second.lower());
      final long upper = Math.max(first.upper(), second.upper());
      final Assignment maxAssignment = new Assignment(lower, upper);
      return maxAssignment.equals(first) || maxAssignment.equals(second);
    }
  }

  record Assignment(long lower, long upper) {
    static Assignment of(final String input) {
      final String[] parts = input.split("-");
      return new Assignment(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }
  }
}
