package us.byteb.advent.year2023;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day04 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2023/day04.txt");

    System.out.println("Part 1: " + totalPoints(parseInput(input)));
    System.out.println("Part 2: " + totalScratchCards(parseInput(input)));
  }

  static List<Card> parseInput(final String input) {
    return input.lines().map(Card::parse).collect(Collectors.toList());
  }

  static long totalPoints(final List<Card> cards) {
    return cards.stream().mapToLong(Card::points).sum();
  }

  static long totalScratchCards(final List<Card> cards) {
    final Map<Card, Long> countByCard =
        cards.stream().collect(Collectors.toMap(Function.identity(), ignored -> 1L));

    for (int i = 0; i < cards.size(); i++) {
      final Card currentCard = cards.get(i);
      final long matches = (int) currentCard.matchingCards();
      for (int l = 0; l < matches; l++) {
        final Long countOfCurrentCard = countByCard.get(currentCard);
        final Card target = cards.get(i + l + 1);
        countByCard.compute(target, (c, v) -> v + countOfCurrentCard);
      }
    }

    return countByCard.values().stream().mapToLong(Long::longValue).sum();
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
      final long numMatches = matchingCards();
      return numMatches == 0 ? 0 : (long) Math.pow(2, numMatches - 1);
    }

    public long matchingCards() {
      return winningNumbers.stream().filter(ownNumbers::contains).count();
    }
  }
}
