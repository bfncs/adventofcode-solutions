package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Day02 {

  static BiFunction<String, String, Round> STRATEGY_PART1 =
      (opponent, own) -> new Round(Hand.of(opponent), Hand.of(own));
  static BiFunction<String, String, Round> STRATEGY_PART2 =
      (opponent, own) -> {
        final Hand opponentHand = Hand.of(opponent);
        final Reaction reaction = Reaction.of(own);
        return new Round(opponentHand, reaction.apply(opponentHand));
      };

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day02.txt");

    System.out.println("Part 1: " + totalScore(parseInput(input, STRATEGY_PART1)));
    System.out.println("Part 2: " + totalScore(parseInput(input, STRATEGY_PART2)));
  }

  static List<Round> parseInput(
      final String input, final BiFunction<String, String, Round> handCreatorStrategy) {
    return input
        .lines()
        .map(
            line -> {
              final String[] split = line.split("\\s+");
              if (split.length != 2) {
                throw new IllegalStateException("Unexpected input: " + line);
              }

              return handCreatorStrategy.apply(split[0], split[1]);
            })
        .toList();
  }

  static long totalScore(final List<Round> rounds) {
    return rounds.stream().mapToLong(Round::score).sum();
  }

  record Round(Hand opponent, Hand own) {
    public long score() {
      return own.shapeScore() + own.scoreAgainst(opponent);
    }
  }

  enum Hand {
    ROCK(1L) {
      @Override
      public Hand defeats() {
        return SCISSORS;
      }
    },
    PAPER(2L) {
      @Override
      public Hand defeats() {
        return ROCK;
      }
    },
    SCISSORS(3L) {
      @Override
      public Hand defeats() {
        return PAPER;
      }
    };

    private final long shapeScore;

    Hand(final long shapeScore) {
      this.shapeScore = shapeScore;
    }

    public abstract Hand defeats();

    public long shapeScore() {
      return shapeScore;
    }

    public long scoreAgainst(final Hand opponent) {
      if (this == opponent) {
        return 3L;
      } else if (this.defeats() == opponent) {
        return 6L;
      } else {
        return 0L;
      }
    }

    public static Hand of(final String s) {
      return switch (s.toUpperCase()) {
        case "A", "X" -> ROCK;
        case "B", "Y" -> PAPER;
        case "C", "Z" -> SCISSORS;
        default -> throw new IllegalStateException("Unexpected hand: " + s);
      };
    }
  }

  enum Reaction {
    LOOSE,
    DRAW,
    WIN;

    private static Reaction of(final String own) {
      return switch (own) {
        case "X" -> LOOSE;
        case "Y" -> DRAW;
        case "Z" -> WIN;
        default -> throw new IllegalStateException("Unexpected reaction: " + own);
      };
    }

    private Hand apply(final Hand opponent) {
      return switch (this) {
        case LOOSE -> opponent.defeats();
        case DRAW -> opponent;
        case WIN ->
            Arrays.stream(Hand.values())
                .filter(hand -> hand != opponent && hand != opponent.defeats())
                .findFirst()
                .orElseThrow();
      };
    }
  }
}
