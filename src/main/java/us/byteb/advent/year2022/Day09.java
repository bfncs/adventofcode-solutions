package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2022.Day09.Direction.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day09 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day09.txt");

    System.out.println("Part 1: " + countPositionsVisitedByTail(input));
  }

  public static long countPositionsVisitedByTail(final String input) {
    final List<Motion> motions = parseInput(input);
    State state = new State(new Position(0, 0), new Position(0, 0));
    final Set<Position> tailPositions = new HashSet<>(Set.of(state.tail()));

    for (final Motion motion : motions) {
      for (int i = 0; i < motion.steps(); i++) {
        state = state.moveHead(motion.direction());
        tailPositions.add(state.tail());
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

  record State(Position head, Position tail) {
    private State moveHead(final Direction direction) {
      final Position nextHead = head.move(direction);

      if (nextHead.isTouching(tail)) {
        return new State(nextHead, tail);
      }

      return new State(
          nextHead,
          switch (direction) {
            case UP, DOWN -> {
              if (head.x() < tail.x()) {
                yield tail.move(direction).move(LEFT);
              } else if (head.x() > tail.x()) {
                yield tail.move(direction).move(RIGHT);
              }
              yield tail.move(direction);
            }
            case LEFT, RIGHT -> {
              if (head.y() < tail.y()) {
                yield tail.move(direction).move(UP);
              } else if (head.y() > tail.y()) {
                yield tail.move(direction).move(DOWN);
              }
              yield tail.move(direction);
            }
          });
    }
  }
}
