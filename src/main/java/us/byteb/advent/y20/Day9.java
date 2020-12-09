package us.byteb.advent.y20;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.List;
import java.util.stream.Collectors;

public class Day9 {

  public static void main(String[] args) {
    final List<Long> input =
        readFileFromResources("y20/day9.txt")
            .lines()
            .map(Long::parseLong)
            .collect(Collectors.toList());

    System.out.println("Part 1: " + findFirstInvalid(input, 25));
  }

  static long findFirstInvalid(final List<Long> input, final int preambleSize) {
    for (int i = preambleSize; i < input.size(); i++) {
      final List<Long> availableNumbers = input.subList(i - preambleSize, i);
      if (!isPossibleToFindSumOfTwo(availableNumbers, input.get(i))) {
        return input.get(i);
      }
    }

    throw new IllegalStateException("No result found");
  }

  private static boolean isPossibleToFindSumOfTwo(
      final List<Long> availableNumbers, final long target) {
    for (int i = 0; i < availableNumbers.size() - 1; i++) {
      for (int j = i + 1; j < availableNumbers.size(); j++) {
        if (availableNumbers.get(i) + availableNumbers.get(j) == target) {
          return true;
        }
      }
    }

    return false;
  }
}
