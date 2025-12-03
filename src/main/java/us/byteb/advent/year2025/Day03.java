package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public class Day03 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day03.txt");
    System.out.println("Part 1: " + totalOutputJoltage(input));
  }

  public static BigInteger totalOutputJoltage(final String input) {
    return input
        .lines()
        .map(
            bank -> {
              final List<Integer> batteries =
                  bank.chars().map(Character::getNumericValue).boxed().toList();
              return findLargestPossibleJoltage(batteries);
            })
        .reduce(BigInteger.ZERO, BigInteger::add);
  }

  private static BigInteger findLargestPossibleJoltage(final List<Integer> batteries) {
    final List<Integer> leftCandidates = batteries.subList(0, batteries.size() - 1);
    final int leftValue = maxInt(leftCandidates);
    final int leftPos = leftCandidates.indexOf(leftValue);
    final List<Integer> rightCandidates = batteries.subList(leftPos + 1, batteries.size());

    return BigInteger.valueOf((leftValue * 10L) + maxInt(rightCandidates));
  }

  private static int maxInt(final Collection<Integer> candidates) {
    return candidates.stream().mapToInt(Integer::intValue).max().orElseThrow();
  }
}
