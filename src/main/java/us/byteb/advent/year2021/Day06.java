package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Day06 {

  public static void main(String[] args) throws IOException {
    final List<Integer> input = parseInput(readFileFromResources("year2021/day06.txt"));

    System.out.println("Part 1: " + evolveDays(input, 80).size());
  }

  static List<Integer> parseInput(final String input) {
    return Arrays.stream(input.split(",")).map(Integer::parseInt).toList();
  }

  static List<Integer> evolveDays(final List<Integer> startState, final int numDays) {
    List<Integer> state = startState;

    for (int i = 0; i < numDays; i++) {
      state = evolveNextDay(state);
    }

    return state;
  }

  private static List<Integer> evolveNextDay(final List<Integer> state) {
    final List<Integer> existing = new ArrayList<>();
    final List<Integer> fresh = new ArrayList<>();

    for (final int fish : state) {
      if (fish == 0) {
        existing.add(6);
        fresh.add(8);
      } else {
        existing.add(fish - 1);
      }
    }

    return Stream.concat(existing.stream(), fresh.stream()).toList();
  }
}
