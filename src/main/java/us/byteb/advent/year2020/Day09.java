package us.byteb.advent.year2020;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day09 {
  public static void main(String[] args) {
    final List<Long> input = parse(readFileFromResources("year2020/day09.txt"));

    final long firstInvalid = findFirstInvalid(input, 25);
    System.out.println("Part 1: " + firstInvalid);

    final List<Long> contiguousSet = findContiguousSetWithSum(input, firstInvalid);
    final long sum = contiguousSet.stream().mapToLong(i -> i).sum();
    assert sum == firstInvalid;
    final long resultPart2 = reduceToSolution(contiguousSet);
    System.out.println("Part 2: " + resultPart2); // 17929808 is not the right answer 🤔
  }

  static List<Long> parse(final String input) {
    return input.lines().map(Long::parseLong).collect(Collectors.toList());
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

  static List<Long> findContiguousSetWithSum(final List<Long> input, final long target) {
    final int minSetSize = 2;
    for (int setSize = minSetSize; setSize <= input.size() - minSetSize; setSize++) {
      for (int j = 0; j < input.size() - setSize; j++) {
        long sum = 0;
        for (int i = 0; i < setSize; i++) {
          sum += input.get(j + i);
        }
        if (sum == target) {
          return IntStream.range(j, j + setSize - 1)
              .mapToObj(input::get)
              .collect(Collectors.toList());
        }
      }
    }

    throw new IllegalStateException("No result found");
  }

  static long reduceToSolution(final List<Long> contiguousSet) {
    final long min = contiguousSet.stream().mapToLong(i -> i).min().getAsLong();
    final long max = contiguousSet.stream().mapToLong(i -> i).max().getAsLong();
    final long result = min + max;
    return result;
  }
}
