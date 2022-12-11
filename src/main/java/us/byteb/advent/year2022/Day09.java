package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Day09 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day09.txt");

    System.out.println("Part 1: " + countPositionsVisitedByTail(input, 2));
    System.out.println("Part 2: " + countPositionsVisitedByTail(input, 10));
  }

  public static long countPositionsVisitedByTail(final String input, final int numKnots) {
    final List<Motion> motions = parseInput(input);
    Rope rope = Rope.ofSize(numKnots);
    final Set<Position> tailPositions = new HashSet<>(Set.of(rope.tail()));

    for (final Motion motion : motions) {
      for (int i = 0; i < motion.steps(); i++) {
        rope = rope.moveHead(motion.direction());
        tailPositions.add(rope.tail());
      }
    }

    return tailPositions.size();
  }

  public static List<Motion> parseInput(final String input) {
    return input
        .lines()
        .map(
            line -> {
              final String[] parts = line.split("\\s");
              if (parts.length != 2) throw new IllegalStateException("Invalid line: " + line);
              return new Motion(Direction.of(parts[0]), Integer.parseInt(parts[1]));
            })
        .toList();
  }

  record Motion(Direction direction, int steps) {}

  enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    private static Direction of(final String input) {
      return switch (input) {
        case "U" -> UP;
        case "R" -> RIGHT;
        case "D" -> DOWN;
        case "L" -> LEFT;
        default -> throw new IllegalStateException("Unexpected value: " + input);
      };
    }
  }

  record Position(int x, int y) {
    public Position move(final Direction direction) {
      return switch (direction) {
        case UP -> new Position(x, y - 1);
        case RIGHT -> new Position(x + 1, y);
        case DOWN -> new Position(x, y + 1);
        case LEFT -> new Position(x - 1, y);
      };
    }

    private boolean isTouching(final Position other) {
      return Math.abs(y - other.y()) <= 1 && Math.abs(x - other.x()) <= 1;
    }
  }

  record Rope(List<Position> knots) {
    public static Rope ofSize(int numKnots) {
      return new Rope(
          IntStream.range(0, numKnots).mapToObj(ignored -> new Position(0, 0)).toList());
    }

    public Position tail() {
      return knots.get(knots().size() - 1);
    }

    public Rope moveHead(final Direction direction) {
      final List<Position> nextKnots = new ArrayList<>();
      nextKnots.add(knots.get(0).move(direction));

      for (int i = 1; i < knots.size(); i++) {
        final Position current = knots.get(i);
        final Position predecessor = nextKnots.get(i - 1);

        if (predecessor.isTouching(current)) {
          nextKnots.add(current);
        } else {
          final int nextY;
          if (predecessor.y() == current.y()) {
            nextY = current.y();
          } else if (predecessor.y() < current.y()) {
            nextY = current.y() - 1;
          } else {
            nextY = current.y() + 1;
          }

          final int nextX;
          if (predecessor.x() == current.x()) {
            nextX = current.x();
          } else if (predecessor.x() < current.x()) {
            nextX = current.x() - 1;
          } else {
            nextX = current.x() + 1;
          }

          nextKnots.add(new Position(nextX, nextY));
        }
      }

      return new Rope(nextKnots);
    }
  }
}
