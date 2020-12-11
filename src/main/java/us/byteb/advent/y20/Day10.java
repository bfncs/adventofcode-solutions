package us.byteb.advent.y20;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10 {

  public static void main(String[] args) {
    final List<Integer> input = parseInput(readFileFromResources("y20/day10.txt"));

    final Map<Integer, Integer> resultPart1 = chainVoltageDifferences(input);
    System.out.println("Part 1: " + resultPart1.get(1) * resultPart1.get(3));
  }

  static List<Integer> parseInput(final String input) {
    return input.lines().map(Integer::parseInt).collect(Collectors.toList());
  }

  static Map<Integer, Integer> chainVoltageDifferences(final List<Integer> input) {
    final List<Integer> sorted = input.stream().sorted().collect(Collectors.toList());

    final HashMap<Integer, Integer> result = new HashMap<>();
    int lastItem = 0;
    for (final int item : sorted) {
      final int delta = item - lastItem;
      result.compute(delta, (k, v) -> (v == null) ? 1 : v + 1);
      lastItem = item;
    }

    result.compute(3, (k, v) -> (v == null) ? 1 : v + 1);

    return result;
  }
}
