package us.byteb.advent.year2023;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Day04 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2023/day04.txt");

    System.out.println("Part 1: " + totalPoints(parseInput(input)));
  }

  static Set<Card> parseInput(final String input) {
    return input.lines().map(Card::parse).collect(Collectors.toSet());
  }

  static long totalPoints(final Set<Card> cards) {
    return cards.stream().mapToLong(Card::points).sum();
  }

  record Card(Set<Integer> winningNumbers, Set<Integer> ownNumbers) {
    public static Card parse(final String input) {
      final String[] parts = input.split("[:|]");
      if (parts.length != 3) throw new IllegalStateException();
      return new Card(parseNumbers(parts[1]), parseNumbers(parts[2]));
    }

    private static Set<Integer> parseNumbers(final String input) {
      return Arrays.stream(input.trim().split("\s+"))
          .map(Integer::parseInt)
          .collect(Collectors.toSet());
    }

    public long points() {
      final long numMatches = winningNumbers.stream().filter(ownNumbers::contains).count();
      return numMatches == 0 ? 0 : (long) Math.pow(2, numMatches - 1);
    }
  }
}
