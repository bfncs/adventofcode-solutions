package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;

public class Day11 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day11.txt");
    System.out.println("Part 1: " + blink(parse(input), 25).size());
  }

  public static List<Long> parse(final String input) {
    return Arrays.stream(input.split("\\s+")).map(Long::parseLong).toList();
  }

  public static List<Long> blink(final List<Long> stones, final int times) {
    List<Long> result = stones;
    for (int i = 0; i < times; i++) {
      result = blink(result);
    }

    return result;
  }

  private static List<Long> blink(final List<Long> stones) {
    final List<Long> result = new ArrayList<>(stones);
    int pos = 0;
    while (pos < result.size()) {
      if (result.get(pos) == 0) {
        result.set(pos, 1L);
      } else if (result.get(pos).toString().length() % 2 == 0) {
        final String str = result.get(pos).toString();
        final long firstHalf = Long.parseLong(str.substring(0, str.length() / 2), 10);
        final long secondHalf = Long.parseLong(str.substring(str.length() / 2), 10);
        result.set(pos, secondHalf);
        result.add(pos, firstHalf);
        pos++;
      } else {
        result.set(pos, result.get(pos) * 2024);
      }
      pos++;
    }

    return result;
  }
}
