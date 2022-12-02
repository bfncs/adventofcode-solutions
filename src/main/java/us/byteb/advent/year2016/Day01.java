package us.byteb.advent.year2016;

import static java.util.Collections.emptyList;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2016.Day01.CardinalDirection.NORTH;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.apache.commons.lang3.StringUtils;

public class Day01 {

  public static void main(String[] args) {
    final List<Instruction> input = parseInput(readFileFromResources("year2016/day01.txt"));

    System.out.println("Part 1: " + followInstructions(input).position().distanceToOrigin());
    System.out.println(
        "Part 2: " + followInstructionsUntilFirstRevisit(input).position().distanceToOrigin());
  }

  static List<Instruction> parseInput(final String input) {
    return Arrays.stream(input.split(",")).map(StringUtils::strip).map(Instruction::of).toList();
  }

  static Location followInstructions(final List<Instruction> instructions) {
    Location location = new Location(new Position(0, 0), NORTH);

    for (final Instruction instruction : instructions) {
      location = location.apply(instruction);
    }

    return location;
  }

  static Location followInstructionsUntilFirstRevisit(final List<Instruction> instructions) {
    Location location = new Location(new Position(0, 0), NORTH);
    final Set<Position> visited = new HashSet<>();

    for (final Instruction instruction : instructions) {
      final Position lastPosition = location.position();
      location = location.apply(instruction);

      System.out.printf(
          "%d,%d --[%s%d]-->%s%n",
          lastPosition.y(),
          lastPosition.x(),
          instruction.direction(),
          instruction.steps(),
          location);

      final List<Position> intermediatePositions = interpolate(lastPosition, location.position());
      System.out.println(
          intermediatePositions.stream()
              .map(pos -> "%d,%d".formatted(pos.y(), pos.x()))
              .collect(Collectors.joining(" ")));
      for (final Position position : intermediatePositions) {
        if (visited.contains(position)) {
          System.out.println("Revisiting " + position);
          return new Location(position, location.direction());
        }
      }
      visited.addAll(intermediatePositions);
    }

    throw new IllegalStateException("No position was revisited");
  }

  private static List<Position> interpolate(final Position start, final Position end) {
    if (start.equals(end)) {
      return emptyList();
    } else if (start.x() == end.x()) {
      if (start.y() < end.y()) {
        return LongStream.range(start.y(), end.y())
            .mapToObj(y -> new Position(y, start.x()))
            .toList();
      } else {
        return rangeReverse(end.y() + 1, start.y())
            .mapToObj(y -> new Position(y, start.x()))
            .toList();
      }
    } else if (start.y() == end.y()) {
      if (start.x() < end.x()) {
        return LongStream.range(start.x(), end.x())
            .mapToObj(x -> new Position(start.y(), x))
            .toList();
      } else {
        return rangeReverse(end.x() + 1, start.x())
            .mapToObj(x -> new Position(start.y(), x))
            .toList();
      }
    }

    throw new IllegalStateException(
        "Unable to interpolate between %s and %s".formatted(start, end));
  }

  static LongStream rangeReverse(long from, long to) {
    return LongStream.range(from, to).map(i -> to - i + from - 1);
  }

  record Location(Position position, CardinalDirection direction) {
    public Location apply(final Instruction instruction) {
      final CardinalDirection nextDirection = direction.apply(instruction.direction());
      final Position nextPosition = position.apply(nextDirection, instruction.steps());

      return new Location(nextPosition, nextDirection);
    }
  }

  record Position(long y, long x) {
    public long distanceToOrigin() {
      return Math.abs(y) + Math.abs(x);
    }

    public Position apply(final CardinalDirection direction, long steps) {
      return switch (direction) {
        case NORTH -> new Position(y + steps, x);
        case EAST -> new Position(y, x + steps);
        case SOUTH -> new Position(y - steps, x);
        case WEST -> new Position(y, x - steps);
      };
    }
  }

  enum CardinalDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    private CardinalDirection apply(final RelativeDirection direction) {
      final int rotation =
          switch (direction) {
            case LEFT -> -1;
            case RIGHT -> 1;
          };
      final int resultOrdinal = (values().length + ordinal() + rotation) % values().length;

      return values()[resultOrdinal];
    }
  };

  record Instruction(RelativeDirection direction, long steps) {

    public static Instruction of(final String input) {
      return new Instruction(RelativeDirection.of(input), Long.parseLong(input.substring(1)));
    }
  }

  enum RelativeDirection {
    LEFT,
    RIGHT;

    private static RelativeDirection of(final String input) {
      return switch (input.substring(0, 1).toUpperCase()) {
        case "L" -> LEFT;
        case "R" -> RIGHT;
        default -> throw new IllegalStateException(
            "Unable to parse direction from input: " + input);
      };
    }
  }
}
