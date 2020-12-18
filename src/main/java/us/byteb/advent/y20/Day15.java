package us.byteb.advent.y20;

import static java.util.stream.Collectors.toList;

import java.util.*;

public class Day15 {

  public static void main(String[] args) {
    final List<Integer> input = parseInput("12,1,16,3,11,0");
    System.out.println("Part 1: " + findNumber(input, 2020));
    System.out.println("Part 2: " + findNumber(input, 30000000));
  }

  public static List<Integer> parseInput(final String input) {
    return Arrays.stream(input.split(",")).map(Integer::parseInt).collect(toList());
  }

  public static int findNumber(final List<Integer> startingNumbers, final int maxIterations) {
    final Map<Integer, Integer> secondToLastIndex = new HashMap<>();
    final Map<Integer, Integer> lastIndex = new HashMap<>();
    int lastNumber = -1;

    for (int iteration = 0; iteration < maxIterations; iteration++) {
      if (iteration < startingNumbers.size()) {
        lastNumber = startingNumbers.get(iteration);
      } else {
        if (secondToLastIndex.containsKey(lastNumber)) {
          lastNumber = lastIndex.get(lastNumber) - secondToLastIndex.get(lastNumber);
        } else {
          lastNumber = 0;
        }

        if (lastIndex.containsKey(lastNumber)) {
          secondToLastIndex.put(lastNumber, lastIndex.get(lastNumber));
        }
      }
      lastIndex.put(lastNumber, iteration);
    }

    return lastNumber;
  }
}
