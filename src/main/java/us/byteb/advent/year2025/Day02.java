package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Day02 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day02.txt");
    System.out.println("Part 1: " + solvePart1(input));
    System.out.println("Part 2: " + solvePart2(input));
  }

  public static BigInteger solvePart1(final String input) {
    return solve(input, Range::sumInvalidIds1);
  }

  public static BigInteger solvePart2(final String input) {
    return solve(input, Range::sumInvalidIds2);
  }

  private static BigInteger solve(
      final String input, final Function<Range, BigInteger> countStrategy) {
    final List<Range> ranges = parseInput(input);

    BigInteger invalidIdsCount = BigInteger.ZERO;
    for (final Range range : ranges) {
      invalidIdsCount = invalidIdsCount.add(countStrategy.apply(range));
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

    BigInteger sumInvalidIds1() {
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

    BigInteger sumInvalidIds2() {
      BigInteger result = BigInteger.ZERO;
      for (BigInteger i = startInclusive;
          i.compareTo(endInclusive) <= 0;
          i = i.add(BigInteger.ONE)) {
        final String str = i.toString();

        for (int len = 1; len <= (str.length() / 2); len++) {
          final String repeated = str.substring(0, len).repeat((str.length() / len));
          if (str.equals(repeated)) {
            result = result.add(i);
            break;
          }
        }
      }
      return result;
    }
  }
}
