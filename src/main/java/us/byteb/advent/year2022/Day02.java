package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.List;

public class Day02 {

  public static void main(String[] args) throws IOException {
    final List<Round> input = parseInput(readFileFromResources("year2022/day02.txt"));

    System.out.println("Part 1: " + totalScore(input));
  }

  static List<Round> parseInput(final String input) {
    return input
        .lines()
        .map(
            line -> {
              final String[] split = line.split("\\s+");
              if (split.length != 2) {
                throw new IllegalStateException("Unexpected input: " + line);
              }

              return new Round(Hand.of(split[0]), Hand.of(split[1]));
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
      public Hand defeatsWhich() {
        return SCISSOR;
      }
    },
    PAPER(2L) {
      @Override
      public Hand defeatsWhich() {
        return ROCK;
      }
    },
    SCISSOR(3L) {
      @Override
      public Hand defeatsWhich() {
        return PAPER;
      }
    };

    private final long shapeScore;

    Hand(final long shapeScore) {
      this.shapeScore = shapeScore;
    }

    public abstract Hand defeatsWhich();

    public long shapeScore() {
      return shapeScore;
    }

    public long scoreAgainst(final Hand opponent) {
      if (this == opponent) {
        return 3L;
      } else if (this.defeatsWhich() == opponent) {
        return 6L;
      } else {
        return 0L;
      }
    }

    public static Hand of(final String s) {
      return switch (s.toUpperCase()) {
        case "A", "X" -> ROCK;
        case "B", "Y" -> PAPER;
        case "C", "Z" -> SCISSOR;
        default -> throw new IllegalStateException("Unexpected hand: " + s);
      };
    }
  }
}
