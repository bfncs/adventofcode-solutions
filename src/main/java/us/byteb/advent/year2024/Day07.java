package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2024.Day07.Op.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day07 {

  public static void main(String[] args) {
    final List<Equation> input = parseInput(readFileFromResources("year2024/day07.txt"));
    System.out.println("Part 1: " + totalCalibrationResult(input, Set.of(ADD, MULTIPLY)));
    System.out.println("Part 2: " + totalCalibrationResult(input, Set.of(ADD, MULTIPLY, CONCAT)));
  }

  public static List<Equation> parseInput(final String input) {
    return input.lines().map(Equation::parse).toList();
  }

  public static long totalCalibrationResult(final List<Equation> equations, final Set<Op> ops) {
    return equations.stream()
        .filter(equation -> equation.canBeSolved(ops))
        .mapToLong(Equation::expectedResult)
        .sum();
  }

  public record Equation(long expectedResult, List<Long> operands) {
    public static Equation parse(final String input) {
      final String[] parts = input.split(":\\s*");
      if (parts.length != 2) throw new IllegalStateException();
      final List<Long> operands =
          Arrays.stream(parts[1].split("\\s+")).map(Long::parseLong).toList();
      return new Equation(Long.parseLong(parts[0]), operands);
    }

    boolean canBeSolved(final Collection<Op> ops) {
      final Set<List<Op>> opCombinations = combinations(ops, operands().size() - 1);
      for (final List<Op> combination : opCombinations) {
        long result = operands().getFirst();
        for (int i = 0; i < operands.size() - 1; i++) {
          result =
              switch (combination.get(i)) {
                case ADD -> result + operands.get(i + 1);
                case MULTIPLY -> result * operands.get(i + 1);
                case CONCAT -> Long.parseLong(String.valueOf(result) + operands.get(i + 1));
              };
        }
        if (result == expectedResult) {
          return true;
        }
      }
      return false;
    }

    private static <T> Set<List<T>> combinations(final Collection<T> items, int length) {
      if (length == 0) return Set.of(List.of());
      return items.stream()
          .flatMap(
              item -> {
                final Set<List<T>> combinations = combinations(items, length - 1);
                return combinations.stream()
                    .map(
                        tail -> {
                          final List<T> list =
                              Stream.concat(Stream.of(item), tail.stream()).toList();
                          return list;
                        });
              })
          .collect(Collectors.toSet());
    }
  }

  public enum Op {
    ADD,
    MULTIPLY,
    CONCAT
  }
}
