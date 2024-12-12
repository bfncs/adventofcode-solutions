package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Day11 {
  private static final Map<String, Long> cache = new ConcurrentHashMap<>();

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day11.txt");
    System.out.println("Part 1: " + depthFirstBlink(parse(input), 25));
    System.out.println("Part 2: " + depthFirstBlink(parse(input), 75));
  }

  public static List<Long> parse(final String input) {
    return Arrays.stream(input.split("\\s+")).map(Long::parseLong).toList();
  }

  public static long depthFirstBlink(final List<Long> stones, final int times) {
    return stones.stream().mapToLong(stone -> depthFirstBlink(stone, times)).sum();
  }

  public static long depthFirstBlink(final long stone, final int times) {
    final String key = stone + "-" + times;
    if (cache.containsKey(key)) return cache.get(key);
    final long result = depthFirstBlinkImpl(stone, times);
    cache.put(key, result);
    return result;
  }

  public static long depthFirstBlinkImpl(final long stone, final int times) {
    if (times == 0) {
      return 1;
    }

    if (stone == 0) {
      return depthFirstBlink(1, times - 1);
    }

    final String str = String.valueOf(stone);
    if (str.length() % 2 == 0) {
      final long firstHalf = Long.parseLong(str.substring(0, str.length() / 2), 10);
      final long secondHalf = Long.parseLong(str.substring(str.length() / 2), 10);

      return depthFirstBlink(firstHalf, times - 1) + depthFirstBlink(secondHalf, times - 1);
    } else {
      return depthFirstBlink(stone * 2024, times - 1);
    }
  }

  public static List<Long> breadthFirstBlink(final List<Long> stones, final int times) {
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
