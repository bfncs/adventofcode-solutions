package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2016.Day01.CardinalDirection.NORTH;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Day01 {

  public static void main(String[] args) throws IOException {
    final List<Instruction> input = parseInput(readFileFromResources("year2016/day01.txt"));

    System.out.println("Part 1: " + followInstructions(input).distanceToOrigin());
  }

  static List<Instruction> parseInput(final String input) {
    return Arrays.stream(input.split(",")).map(StringUtils::strip).map(Instruction::of).toList();
  }

  static Position followInstructions(final List<Instruction> instructions) {
    Position position = new Position(0, 0, NORTH);

    for (final Instruction instruction : instructions) {
      position = position.apply(instruction);
    }

    return position;
  }

  record Position(long y, long x, CardinalDirection direction) {
    public Position apply(final Instruction instruction) {
      final CardinalDirection nextDirection = direction.apply(instruction.direction());

      return switch (nextDirection) {
        case NORTH -> new Position(y + instruction.steps(), x, nextDirection);
        case EAST -> new Position(y, x + instruction.steps(), nextDirection);
        case SOUTH -> new Position(y - instruction.steps(), x, nextDirection);
        case WEST -> new Position(y, x - instruction.steps(), nextDirection);
      };
    }

    public long distanceToOrigin() {
      return Math.abs(y) + Math.abs(x);
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
