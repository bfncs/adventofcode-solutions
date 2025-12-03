package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Day02 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day02.txt");
    System.out.println("Part 1: " + solvePart1(input));
  }

  public static BigInteger solvePart1(final String input) {
    final List<Range> ranges = parseInput(input);

    BigInteger invalidIdsCount = BigInteger.ZERO;
    for (final Range range : ranges) {
      invalidIdsCount = invalidIdsCount.add(range.countInvalidIds());
    }

    return invalidIdsCount;
  }

  public static List<Range> parseInput(final String input) {
    return Arrays.stream(input.split(","))
        .map(
            range -> {
              final String[] parts = range.split("-");
              if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid input: " + range);
              }
              return new Range(new BigInteger(parts[0], 10), new BigInteger(parts[1], 10));
            })
        .toList();
  }

  public record Range(BigInteger startInclusive, BigInteger endInclusive) {
    BigInteger countInvalidIds() {
      BigInteger result = BigInteger.ZERO;
      for (BigInteger i = startInclusive;
          i.compareTo(endInclusive) <= 0;
          i = i.add(BigInteger.ONE)) {
        final String str = i.toString();
        if ((str.length() % 2) != 0) {
          continue;
        }

        if (str.substring(0, str.length() / 2).equals(str.substring(str.length() / 2))) {
          result = result.add(i);
        }
      }
      return result;
    }
  }
}
