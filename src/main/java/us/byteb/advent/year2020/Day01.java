package us.byteb.advent.year2020;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 {

  public static void main(String[] args) throws IOException {
    final String input = readFileFromResources("year2020/day01.txt");

    System.out.println(partOne(input));
    System.out.println(partTwo(input));
  }

  private static String partOne(final String input) {
    final List<Integer> sortedItems =
        input.lines().mapToInt(Integer::parseInt).sorted().boxed().collect(Collectors.toList());

    for (int i = 0; i < sortedItems.size(); i++) {
      final Integer first = sortedItems.get(i);
      for (int j = i + 1; j < sortedItems.size(); j++) {
        final Integer second = sortedItems.get(j);
        final int sum = first + second;
        if (sum == 2020) {
          return String.format(
              "Found it: %d + %d = %d, => %d * %d = %d%n",
              first, second, first + second, first, second, first * second);
        }
        if (sum > 2020) {
          break;
        }
      }
    }

    throw new IllegalStateException("Solution not found");
  }

  private static String partTwo(final String input) {
    final List<Integer> sortedItems =
        input.lines().mapToInt(Integer::parseInt).sorted().boxed().collect(Collectors.toList());

    for (int i = 0; i < sortedItems.size(); i++) {
      final Integer first = sortedItems.get(i);
      for (int j = i + 1; j < sortedItems.size(); j++) {
        final Integer second = sortedItems.get(j);
        for (int k = j + 1; k < sortedItems.size(); k++) {
          final Integer third = sortedItems.get(k);
          final int sum = first + second + third;
          if (sum == 2020) {
            return String.format(
                "Found it: %d + %d + % d = %d, => %d * %d * %d = %d%n",
                first,
                second,
                third,
                first + second + third,
                first,
                second,
                third,
                first * second * third);
          }
          if (sum > 2020) {
            break;
          }
        }
      }
    }

    throw new IllegalStateException("Solution not found");
  }
}
