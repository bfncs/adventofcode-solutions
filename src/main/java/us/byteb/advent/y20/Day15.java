package us.byteb.advent.y20;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day15 {

  public static void main(String[] args) {
    final List<Integer> input = parseInput("12,1,16,3,11,0");
    System.out.println("Part 1: " + findNumber(input, 2020));
  }

  public static List<Integer> parseInput(final String input) {
    return Arrays.stream(input.split(",")).map(Integer::parseInt).collect(toList());
  }

  public static int findNumber(final List<Integer> startingNumbers, final int maxIterations) {
    List<Integer> history = new ArrayList<>();
    for (int iteration = 0; iteration < maxIterations; iteration++) {
      history.add(findNumberImpl(startingNumbers, history, iteration));
    }

    return history.get(history.size() - 1);
  }

  private static int findNumberImpl(
      final List<Integer> startingNumbers, final List<Integer> history, final int i) {
    if (i < startingNumbers.size()) {
      return startingNumbers.get(i);
    }

    final int lastNumber = history.get(i - 1);
    final List<Integer> indexesOfLastNumber =
        IntStream.range(0, history.size())
            .filter(index -> history.get(index) == lastNumber)
            .boxed()
            .collect(toList());

    return indexesOfLastNumber.size() > 1
        ? indexesOfLastNumber.get(indexesOfLastNumber.size() - 1)
            - indexesOfLastNumber.get(indexesOfLastNumber.size() - 2)
        : 0;
  }
}
