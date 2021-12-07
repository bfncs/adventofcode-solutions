package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;

public class Day03 {

  public static void main(String[] args) throws IOException {
    final PuzzleInput input = parseInput(readFileFromResources("year2021/day03.txt"));

    System.out.println("Part 1: " + (gammaRate(input) * epsilonRate(input)));
    System.out.println("Part 2: " + (oxygenGeneratorRating(input) * co2ScrubberRating(input)));
  }

  public static PuzzleInput parseInput(final String input) {
    final List<Integer> numbers = input.lines().map(s -> Integer.parseInt(s, 2)).toList();
    final int maxBits = input.lines().findFirst().orElseThrow().length();
    return new PuzzleInput(numbers, maxBits);
  }

  record PuzzleInput(List<Integer> numbers, int maxBits) {}

  public static int gammaRate(final PuzzleInput puzzleInput) {
    int result = 0;

    for (int bit = 0; bit < puzzleInput.maxBits(); bit++) {
      if (mostlySetBitValue(puzzleInput.numbers(), bit)) {
        result = result | 1 << bit;
      }
    }

    return result;
  }

  public static int epsilonRate(final PuzzleInput puzzleInput) {
    final int gammaRate = gammaRate(puzzleInput);
    final int mask = createLeastSignificantBitsMask(puzzleInput.maxBits());
    return ~gammaRate & mask;
  }

  public static int oxygenGeneratorRating(final PuzzleInput puzzleInput) {
    return filterRating(
        puzzleInput,
        (currentNumbers, bitPosition) -> {
          final boolean mostlySetBitValue = mostlySetBitValue(currentNumbers, bitPosition, true);
          return onlyNumbersWithBitValueAtPosition(currentNumbers, mostlySetBitValue, bitPosition);
        });
  }

  public static int co2ScrubberRating(final PuzzleInput puzzleInput) {
    return filterRating(
        puzzleInput,
        (currentNumbers, bitPosition) -> {
          final boolean mostlySetBitValue = !mostlySetBitValue(currentNumbers, bitPosition, true);
          return onlyNumbersWithBitValueAtPosition(currentNumbers, mostlySetBitValue, bitPosition);
        });
  }

  private static Integer filterRating(
      final PuzzleInput puzzleInput,
      BiFunction<List<Integer>, Integer, List<Integer>> filterStrategy) {
    List<Integer> currentNumbers = puzzleInput.numbers();

    for (int bitPosition = puzzleInput.maxBits() - 1; bitPosition >= 0; bitPosition--) {
      currentNumbers = filterStrategy.apply(currentNumbers, bitPosition);

      if (currentNumbers.size() == 1) {
        return currentNumbers.get(0);
      }
    }

    throw new IllegalStateException("Unable to find oxygen generator rating");
  }

  private static boolean mostlySetBitValue(
      final List<Integer> input, final int bit, final boolean positiveBias) {
    long setBitCount = 0;
    for (final Integer i : input) {
      if ((i & 1 << bit) > 0) {
        setBitCount++;
      }
    }

    final long unsetBitCount = input.size() - setBitCount;
    if (setBitCount == unsetBitCount) {
      return positiveBias;
    }
    return setBitCount > unsetBitCount;
  }

  private static List<Integer> onlyNumbersWithBitValueAtPosition(
      List<Integer> input, final boolean wantedBitValue, final int bitPosition) {
    return input.stream()
        .filter(n -> bitValueAtPosition(bitPosition, n) == wantedBitValue)
        .toList();
  }

  private static boolean bitValueAtPosition(final int bitPosition, final Integer n) {
    return (n & (1 << bitPosition)) > 0;
  }

  private static boolean mostlySetBitValue(final List<Integer> input, final int bit) {
    return mostlySetBitValue(input, bit, false);
  }

  private static int createLeastSignificantBitsMask(final int numBits) {
    int mask = 0;
    for (int i = 0; i < numBits; i++) {
      mask = mask | (1 << i);
    }
    return mask;
  }
}
