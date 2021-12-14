package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;

public class Day10 {

  public static void main(String[] args) throws IOException {
    final List<List<Token>> input = parseInput(readFileFromResources("year2021/day10.txt"));

    System.out.println("Part 1: " + totalSyntaxErrorScore(input));
    System.out.println("Part 2: " + autocompleteScore(input));
  }

  static List<List<Token>> parseInput(final String input) {
    return input.lines().map(Day10::tokenize).toList();
  }

  static long totalSyntaxErrorScore(final List<List<Token>> input) {
    return input.stream()
        .flatMap(line -> findFirstIllegalToken(line).stream())
        .mapToLong(
            token ->
                switch (token) {
                  case RIGHT_PAREN -> 3;
                  case RIGHT_BRACKET -> 57;
                  case RIGHT_BRACE -> 1197;
                  case RIGHT_CHEVRON -> 25137;
                  default -> throw new IllegalStateException();
                })
        .sum();
  }

  static long autocompleteScore(final List<List<Token>> input) {
    final List<Long> scores =
        input.stream()
            .map(Day10::findMissingClosingTokens)
            .filter(missingTokens -> missingTokens.size() > 0)
            .map(
                missingTokens -> {
                  long score = 0;
                  for (final Token token : missingTokens) {
                    long tokenScore =
                        switch (token) {
                          case RIGHT_PAREN -> 1;
                          case RIGHT_BRACKET -> 2;
                          case RIGHT_BRACE -> 3;
                          case RIGHT_CHEVRON -> 4;
                          default -> throw new IllegalStateException();
                        };
                    score = (score * 5) + tokenScore;
                  }

                  return score;
                })
            .sorted()
            .toList();

    return scores.get(scores.size() / 2);
  }

  static List<Token> tokenize(final String line) {
    return line.chars().mapToObj(c -> Token.parse(c).orElseThrow()).toList();
  }

  static Optional<Token> findFirstIllegalToken(final List<Token> input) {
    final Stack<Token> stack = new Stack<>();
    for (final Token token : input) {
      if (token.matchingOpeningToken().isEmpty()) {
        stack.push(token);
        continue;
      }

      final Token last = stack.pop();
      if (last != token.matchingOpeningToken().get()) {
        return Optional.of(token);
      }
    }

    return Optional.empty();
  }

  static List<Token> findMissingClosingTokens(final List<Token> input) {
    final Stack<Token> stack = new Stack<>();
    for (final Token token : input) {
      if (token.matchingOpeningToken().isEmpty()) {
        stack.push(token);
        continue;
      }

      final Token last = stack.pop();
      if (last != token.matchingOpeningToken().get()) {
        return Collections.emptyList();
      }
    }

    final List<Token> missingClosingTokens = new ArrayList<>();
    while (!stack.isEmpty()) {
      missingClosingTokens.add(stack.pop().matchingClosingToken().orElseThrow());
    }

    return missingClosingTokens;
  }

  enum Token {
    LEFT_PAREN('('),
    RIGHT_PAREN(')', LEFT_PAREN),
    LEFT_BRACKET('['),
    RIGHT_BRACKET(']', LEFT_BRACKET),
    LEFT_BRACE('{'),
    RIGHT_BRACE('}', LEFT_BRACE),
    LEFT_CHEVRON('<'),
    RIGHT_CHEVRON('>', LEFT_CHEVRON);

    private final char character;
    private final Token matchingOpeningToken;

    Token(final char character) {
      this.character = character;
      this.matchingOpeningToken = null;
    }

    Token(final char character, final Token matchingOpeningToken) {
      this.character = character;
      this.matchingOpeningToken = matchingOpeningToken;
    }

    private static Optional<Token> parse(final int c) {
      return Arrays.stream(values()).filter(t -> t.character() == c).findFirst();
    }

    public char character() {
      return character;
    }

    public Optional<Token> matchingClosingToken() {
      return Arrays.stream(values())
          .filter(token -> token.matchingOpeningToken().map(t -> this == t).orElse(false))
          .findFirst();
    }

    public Optional<Token> matchingOpeningToken() {
      return Optional.ofNullable(matchingOpeningToken);
    }
  }
}
