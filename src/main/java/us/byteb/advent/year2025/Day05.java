package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day05 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day05.txt");
    System.out.println("Part 1: " + PuzzleInput.parse(input).findFreshIngredients().size());
  }

  record PuzzleInput(Set<Range> ranges, Set<BigInteger> availableIngredients) {
    static PuzzleInput parse(final String input) {
      final Set<Range> ranges = new HashSet<>();
      final Set<BigInteger> availableIngredients = new HashSet<>();

      try (final Scanner scanner = new Scanner(input)) {
        while (scanner.hasNextLine()) {
          final String line = scanner.nextLine();
          if (line.isBlank()) {
            continue;
          }

          if (line.contains("-")) {
            final String[] parts = line.split("-");
            ranges.add(new Range(new BigInteger(parts[0]), new BigInteger(parts[1])));
          } else {
            availableIngredients.add(new BigInteger(line));
          }
        }
      }

      return new PuzzleInput(ranges, availableIngredients);
    }

    Set<BigInteger> findFreshIngredients() {
      final Set<BigInteger> result = new HashSet<>();

      for (final BigInteger ingredient : availableIngredients) {
        for (final Range range : ranges) {
          if (range.startInclusive().compareTo(ingredient) <= 0
              && range.endInclusive().compareTo(ingredient) >= 0) {
            result.add(ingredient);
            break;
          }
        }
      }

      return result;
    }
  }

  record Range(BigInteger startInclusive, BigInteger endInclusive) {}
}
