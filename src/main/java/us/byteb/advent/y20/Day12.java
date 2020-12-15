package us.byteb.advent.y20;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.y20.Day12.Direction.EAST;

import java.util.List;
import java.util.stream.Collectors;

public class Day12 {

  public static void main(String[] args) {
    final List<Instruction> instructions = parseInput(readFileFromResources("y20/day12.txt"));

    final Position finalPos = move(instructions);
    System.out.println("Part 1:" + manhattanDistance(finalPos));
  }

  public static List<Instruction> parseInput(final String input) {
    return input.lines().map(Instruction::parse).collect(Collectors.toList());
  }

  static Position move(List<Instruction> instructions) {
    Position position = new Position(0, 0, EAST);

    for (final Instruction instruction : instructions) {
      position = instruction.move(position);
    }

    return position;
  }

  static int manhattanDistance(final Position position) {
    return Math.abs(position.posX()) + Math.abs(position.posY());
  }

  interface Instruction {
    static Instruction parse(final String line) {
      final char action = line.charAt(0);
      final int value = Integer.parseInt(line.substring(1));
      return switch (action) {
        case 'N' -> new North(value);
        case 'S' -> new South(value);
        case 'E' -> new East(value);
        case 'W' -> new West(value);
        case 'L' -> new Left(value);
        case 'R' -> new Right(value);
        case 'F' -> new Forward(value);
        default -> throw new UnsupportedOperationException("Unsupported action: " + line);
      };
    }

    Position move(Position position);

    final record North(int value) implements Instruction {
      @Override
      public Position move(final Position position) {
        return new Position(position.posX(), position.posY() - value, position.direction());
      }
    }

    final record South(int value) implements Instruction {
      @Override
      public Position move(final Position position) {
        return new Position(position.posX(), position.posY() + value, position.direction());
      }
    }

    final record East(int value) implements Instruction {
      @Override
      public Position move(final Position position) {
        return new Position(position.posX() + value, position.posY(), position.direction());
      }
    }

    final record West(int value) implements Instruction {
      @Override
      public Position move(final Position position) {
        return new Position(position.posX() - value, position.posY(), position.direction());
      }
    }

    final record Left(int value) implements Instruction {
      @Override
      public Position move(final Position position) {
        return new Position(position.posX(), position.posY(), position.direction().turn(-value()));
      }
    }

    final record Right(int value) implements Instruction {
      @Override
      public Position move(final Position position) {
        return new Position(position.posX(), position.posY(), position.direction().turn(value()));
      }
    }

    final record Forward(int value) implements Instruction {
      @Override
      public Position move(final Position position) {
        final Instruction actualInstruction =
            switch (position.direction()) {
              case NORTH -> new North(value);
              case EAST -> new East(value);
              case SOUTH -> new South(value);
              case WEST -> new West(value);
            };
        return actualInstruction.move(position);
      }
    }
  }

  enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    Direction turn(final int degrees) {
      final int i = this.ordinal() + (degrees / 90);
      if (i >= 0) {
        return values()[i % values().length];
      } else {
        return values()[(values().length - Math.abs(i % values().length)) % values().length];
      }
    }
  }

  record Position(int posX, int posY, Direction direction) {}
}
