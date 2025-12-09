package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day06 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day06.txt");
    System.out.println("Part 1: " + grandTotal(parse(input, Problem::fromInputByRow)));
    System.out.println("Part 2: " + grandTotal(parse(input, Problem::fromInputByColumn)));
  }

  static List<Problem> parse(
      final String input, TriFunction<List<String>, Integer, Integer, Problem> parseStrategy) {
    final List<String> lines = input.lines().toList();

    int startColumn = 0;
    final List<Problem> problems = new ArrayList<>();
    for (int column = 0; column < lines.getFirst().length(); column++) {
      if (column == startColumn) {
        continue;
      }

      if (isIsFullColumnOfOnlySpaces(lines, column)) {
        problems.add(parseStrategy.apply(lines, startColumn, column));
        startColumn = column + 1;
      }
    }
    problems.add(parseStrategy.apply(lines, startColumn, lines.getFirst().length()));

    return problems;
  }

  static BigInteger grandTotal(final List<Problem> problems) {
    return problems.stream().map(Problem::solve).reduce(BigInteger.ZERO, BigInteger::add);
  }

  private static boolean isIsFullColumnOfOnlySpaces(final List<String> lines, final int column) {
    boolean isFullColumnOfOnlySpaces = true;
    for (String line : lines) {
      if (line.charAt(column) != ' ') {
        isFullColumnOfOnlySpaces = false;
        break;
      }
    }
    return isFullColumnOfOnlySpaces;
  }

  record Problem(List<BigInteger> numbers, Operator op) {

    static Problem fromInputByRow(
        final List<String> lines, final int startColumn, final int endColumn) {
      final List<BigInteger> numbers = new ArrayList<>();
      for (int row = 0; row < lines.size() - 1; row++) {
        numbers.add(new BigInteger(lines.get(row).substring(startColumn, endColumn).trim()));
      }

      return new Problem(
          numbers,
          Operator.parse(
              lines
                  .getLast()
                  .substring(startColumn, Math.min(endColumn, lines.getLast().length()))
                  .trim()));
    }

    static Problem fromInputByColumn(
        final List<String> lines, final int startColumn, final int endColumn) {
      final List<BigInteger> numbers = new ArrayList<>();
      for (int column = endColumn; column >= startColumn; column--) {
        final StringBuilder sb = new StringBuilder();
        for (int row = 0; row < lines.size() - 1; row++) {
          if (lines.get(row).length() > column) {
            sb.append(lines.get(row).charAt(column));
          }
        }
        final String value = sb.toString().trim();
        if (!value.isBlank()) {
          numbers.add(new BigInteger(value));
        }
      }

      return new Problem(
          numbers,
          Operator.parse(
              lines
                  .getLast()
                  .substring(startColumn, Math.min(endColumn, lines.getLast().length()))
                  .trim()));
    }

    BigInteger solve() {
      return switch (op) {
        case ADD -> numbers.stream().reduce(BigInteger.ZERO, BigInteger::add);
        case MULTIPLY -> numbers.stream().reduce(BigInteger.ONE, BigInteger::multiply);
      };
    }
  }

  enum Operator {
    ADD,
    MULTIPLY;

    static Operator parse(String s) {
      return switch (s.trim()) {
        case "+" -> ADD;
        case "*" -> MULTIPLY;
        default -> throw new IllegalStateException("Unexpected value: " + s);
      };
    }
  }

  @FunctionalInterface
  interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
  }
}
