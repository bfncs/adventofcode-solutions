package us.byteb.advent.year2021;

import static java.lang.Integer.parseInt;
import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import us.byteb.advent.year2021.Day13.Instruction.FoldX;
import us.byteb.advent.year2021.Day13.Instruction.FoldY;

public class Day13 {

  private static final String FOLD_INSTRUCTION_PREFIX = "fold along ";

  public static void main(String[] args) throws IOException {
    final PuzzleInput input = parseInput(readFileFromResources("year2021/day13.txt"));

    System.out.println("Part 1: " + input.instructions().get(0).apply(input.dots()).size());
    System.out.println("Part 2: \n" + visualize(resolve(input)));
  }

  static Set<Dot> resolve(final PuzzleInput input) {
    Set<Dot> dots = input.dots();
    for (final Instruction instruction : input.instructions()) {
      dots = instruction.apply(dots);
    }

    return dots;
  }

  static String visualize(final Set<Dot> dots) {
    final Dot minCoords =
        dots.stream()
            .reduce((a, b) -> new Dot(Math.min(a.x(), b.x()), Math.min(a.y(), b.y())))
            .orElseThrow();
    final Dot maxCoords =
        dots.stream()
            .reduce((a, b) -> new Dot(Math.max(a.x(), b.x()), Math.max(a.y(), b.y())))
            .orElseThrow();

    final StringBuilder sb = new StringBuilder();
    for (int y = minCoords.y(); y <= maxCoords.y(); y++) {
      for (int x = minCoords.x(); x <= maxCoords.x(); x++) {
        sb.append(dots.contains(new Dot(x, y)) ? '#' : '.');
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  static PuzzleInput parseInput(final String input) {
    final Set<Dot> dots = new HashSet<>();
    final List<Instruction> instructions = new ArrayList<>();

    input
        .lines()
        .forEach(
            line -> {
              if (line.startsWith(FOLD_INSTRUCTION_PREFIX)) {
                final String[] parts = line.substring(FOLD_INSTRUCTION_PREFIX.length()).split("=");
                if (parts.length != 2) throw new IllegalStateException();

                final int axis = parseInt(parts[1]);
                instructions.add(
                    switch (parts[0]) {
                      case "y" -> new FoldY(axis);
                      case "x" -> new FoldX(axis);
                      default -> throw new IllegalStateException();
                    });
              } else if (!line.isBlank()) {
                final String[] parts = line.split(",");
                if (parts.length != 2) throw new IllegalStateException();

                dots.add(new Dot(parseInt(parts[0]), parseInt(parts[1])));
              }
            });

    return new PuzzleInput(dots, instructions);
  }

  record PuzzleInput(Set<Dot> dots, List<Instruction> instructions) {}

  record Dot(int x, int y) {
    @Override
    public String toString() {
      return "{%d,%d}".formatted(x, y);
    }
  }

  interface Instruction {
    Set<Dot> apply(Set<Dot> dots);

    record FoldY(int axis) implements Instruction {
      @Override
      public Set<Dot> apply(final Set<Dot> dots) {
        final Set<Dot> upperHalf = subSetMax(dots, Integer.MAX_VALUE, axis);
        final Set<Dot> lowerHalf = subSetMin(dots, 0, axis);
        final Set<Dot> lowerHalfRotated = rotateY(lowerHalf, axis);

        return merge(upperHalf, lowerHalfRotated);
      }
    }

    record FoldX(int axis) implements Instruction {
      @Override
      public Set<Dot> apply(final Set<Dot> dots) {
        final Set<Dot> leftHalf = subSetMax(dots, axis, Integer.MAX_VALUE);
        final Set<Dot> rightHalf = subSetMin(dots, axis, 0);
        final Set<Dot> rightHalfRoated = rotateX(rightHalf, axis);

        return merge(leftHalf, rightHalfRoated);
      }
    }
  }

  private static Set<Dot> merge(final Set<Dot> first, final Set<Dot> second) {
    return Stream.concat(first.stream(), second.stream()).collect(Collectors.toSet());
  }

  private static Set<Dot> rotateY(final Set<Dot> dots, final int yAxis) {
    return dots.stream()
        .map(
            dot -> {
              final int deltaY = dot.y() - yAxis;
              return new Dot(dot.x(), dot.y() - (2 * deltaY));
            })
        .collect(Collectors.toSet());
  }

  private static Set<Dot> rotateX(final Set<Dot> dots, final int xAxis) {
    return dots.stream()
        .map(
            dot -> {
              final int deltaX = dot.x() - xAxis;
              return new Dot(dot.x() - (2 * deltaX), dot.y());
            })
        .collect(Collectors.toSet());
  }

  private static Set<Dot> subSetMax(
      final Set<Dot> dots, final int xMaxExclusive, final int yMaxExclusive) {
    return dots.stream()
        .filter(dot -> dot.x() < xMaxExclusive && dot.y() < yMaxExclusive)
        .collect(Collectors.toSet());
  }

  private static Set<Dot> subSetMin(
      final Set<Dot> dots, final int xMinInclusive, final int yMinInclusive) {
    return dots.stream()
        .filter(dot -> dot.x() >= xMinInclusive && dot.y() >= yMinInclusive)
        .collect(Collectors.toSet());
  }
}
