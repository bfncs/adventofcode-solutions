package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 {

  public static void main(String[] args) throws IOException {
    final List<Integer> input = parseInput(readFileFromResources("year2021/day03.txt"));

    System.out.println("Part 1: " + (gammaRate(input, 12) * epsilonRate(input, 12)));
  }

  static List<Integer> parseInput(final String input) {
    return input.lines().map(s -> Integer.parseInt(s, 2)).collect(Collectors.toList());
  }

  static int gammaRate(final List<Integer> input, int maxBits) {
    int result = 0;

    for (int bit = 0; bit < maxBits; bit++) {
      long setBitCount = 0;
      final int mask = 1 << bit;
      for (final Integer i : input) {
        if ((i & mask) > 0) {
          setBitCount++;
        }
      }

      if (setBitCount > (input.size() / 2)) {
        result = result | mask;
      }
    }

    return result;
  }

  static int epsilonRate(final List<Integer> input, final int maxBits) {
    final int gammaRate = gammaRate(input, maxBits);
    final int mask = createLeastSignificantBitsMask(maxBits);
    return ~gammaRate & mask;
  }

  private static int createLeastSignificantBitsMask(final int numBits) {
    int mask = 0;
    for (int i = 0; i < numBits; i++) {
      mask = mask | (1 << i);
    }
    return mask;
  }
}
