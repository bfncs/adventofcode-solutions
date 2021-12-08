package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;

public class Day07 {

  public static void main(String[] args) throws IOException {
    final List<Integer> input = parseInput(readFileFromResources("year2021/day07.txt"));

    System.out.println("Part 1: " + findCheapestAlignment(input));
  }

  static List<Integer> parseInput(final String input) {
    return Arrays.stream(input.split(",")).map(Integer::parseInt).toList();
  }

  static long findCheapestAlignment(final List<Integer> positions) {
    final int minAttempt = positions.stream().mapToInt(Integer::intValue).min().orElseThrow();
    final int maxAttempt = positions.stream().mapToInt(Integer::intValue).max().orElseThrow();

    SetDescription lower = new SetDescription(0, 0);
    long numAligned = numMatching(positions, minAttempt);
    SetDescription higher =
        new SetDescription(
            positions.size() - numAligned,
            positions.stream().mapToInt(Integer::intValue).map(n -> n - minAttempt).sum());
    long cheapestAttempt = higher.sumDelta();

    for (int attempt = minAttempt + 1; attempt <= maxAttempt; attempt++) {
      lower =
          new SetDescription(
              lower.numNumbers() + numAligned, lower.sumDelta() + lower.numNumbers() + numAligned);
      numAligned = numMatching(positions, attempt);
      higher =
          new SetDescription(
              higher.numNumbers() - numAligned, higher.sumDelta() - higher.numNumbers());

      final long currentAttempt = lower.sumDelta() + higher.sumDelta();
      cheapestAttempt = Math.min(cheapestAttempt, currentAttempt);
    }

    return cheapestAttempt;
  }

  private static long numMatching(final List<Integer> haystack, final int needle) {
    return haystack.stream().filter(n -> n == needle).count();
  }

  record SetDescription(long numNumbers, long sumDelta) {}
}
