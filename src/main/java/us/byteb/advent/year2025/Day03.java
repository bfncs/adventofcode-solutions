package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day03.txt");
    System.out.println("Part 1: " + totalOutputJoltage(input, 2));
    System.out.println("Part 2: " + totalOutputJoltage(input, 12));
  }

  public static BigInteger totalOutputJoltage(final String input, final int numBatteries) {
    return input
        .lines()
        .map(
            bank -> {
              final List<Integer> batteries =
                  bank.chars().map(Character::getNumericValue).boxed().toList();
              return findLargestPossibleJoltage(batteries, numBatteries);
            })
        .reduce(BigInteger.ZERO, BigInteger::add);
  }

  private static BigInteger findLargestPossibleJoltage(
      final List<Integer> batteries, final int numBatteries) {
    final List<Integer> selectedPos = new ArrayList<>();

    for (int i = 0; i < numBatteries; i++) {
      int leftPos = selectedPos.isEmpty() ? 0 : selectedPos.getLast() + 1;
      int rightPos = batteries.size() - (numBatteries - i) + 1;
      final List<Integer> candidates = batteries.subList(leftPos, rightPos);
      selectedPos.add(leftPos + maxIntPos(candidates));
    }

    return new BigInteger(
        selectedPos.stream()
            .map(batteries::get)
            .map(String::valueOf)
            .collect(Collectors.joining()));
  }

  private static int maxIntPos(final List<Integer> candidates) {
    int max = Integer.MIN_VALUE;
    int maxPos = -1;
    for (int pos = 0; pos < candidates.size(); pos++) {
      final int value = candidates.get(pos);
      if (value > max) {
        max = value;
        maxPos = pos;
      }
    }

    return maxPos;
  }
}
