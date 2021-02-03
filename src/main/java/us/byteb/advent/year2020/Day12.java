package us.byteb.advent.year2020;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2020.Day12.Direction.EAST;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day12 {

  static final BiFunction<DirectionPosition, Instruction, DirectionPosition>
      DIRECTION_POSITION_MOVE = (p, i) -> i.move(p);

  static final BiFunction<WaypointPosition, Instruction, WaypointPosition> WAYPOINT_POSITION_MOVE =
      (p, i) -> i.moveWaypoint(p);

  public static void main(String[] args) {
    final List<Instruction> instructions = parseInput(readFileFromResources("year2020/day12.txt"));

    System.out.println(
        "Part 1:"
            + manhattanDistance(
                move(
                    instructions,
                    DIRECTION_POSITION_MOVE,
                    new DirectionPosition(new Position(0, 0), EAST))));

    System.out.println(
        "Part 2:"
            + manhattanDistance(
                move(
                    instructions,
                    WAYPOINT_POSITION_MOVE,
                    new WaypointPosition(new Position(0, 0), new Position(10, -1)))));
  }

  public static List<Instruction> parseInput(final String input) {
    return input.lines().map(Instruction::parse).collect(Collectors.toList());
  }

  static <P extends ComplexPosition> Position move(
      List<Instruction> instructions,
      final BiFunction<P, Instruction, P> moveStrategy,
      final P initialPosition) {
    P position = initialPosition;

    for (final Instruction instruction : instructions) {
      position = moveStrategy.apply(position, instruction);
    }

    return position.position();
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

    DirectionPosition move(DirectionPosition position);

    WaypointPosition moveWaypoint(WaypointPosition waypointPosition);

    final record North(int value) implements Instruction {
      @Override
      public DirectionPosition move(final DirectionPosition position) {
        return new DirectionPosition(
            new Position(position.position().posX(), position.position().posY() - value),
            position.direction());
      }

      @Override
      public WaypointPosition moveWaypoint(final WaypointPosition position) {
        return new WaypointPosition(
            position.position(),
            new Position(position.waypoint().posX(), position.waypoint().posY() - value));
      }
    }

    final record South(int value) implements Instruction {
      @Override
      public DirectionPosition move(final DirectionPosition position) {
        return new DirectionPosition(
            new Position(position.position().posX(), position.position().posY() + value),
            position.direction());
      }

      @Override
      public WaypointPosition moveWaypoint(final WaypointPosition position) {
        return new WaypointPosition(
            position.position(),
            new Position(position.waypoint().posX(), position.waypoint().posY() + value));
      }
    }

    final record East(int value) implements Instruction {
      @Override
      public DirectionPosition move(final DirectionPosition position) {
        return new DirectionPosition(
            new Position(position.position().posX() + value, position.position().posY()),
            position.direction());
      }

      @Override
      public WaypointPosition moveWaypoint(final WaypointPosition position) {
        return new WaypointPosition(
            position.position(),
            new Position(position.waypoint().posX() + value, position.waypoint().posY()));
      }
    }

    final record West(int value) implements Instruction {
      @Override
      public DirectionPosition move(final DirectionPosition position) {
        return new DirectionPosition(
            new Position(position.position().posX() - value, position.position().posY()),
            position.direction());
      }

      @Override
      public WaypointPosition moveWaypoint(final WaypointPosition position) {
        return new WaypointPosition(
            position.position(),
            new Position(position.waypoint().posX() - value, position.waypoint().posY()));
      }
    }

    final record Left(int value) implements Instruction {
      @Override
      public DirectionPosition move(final DirectionPosition position) {
        return new DirectionPosition(
            new Position(position.position().posX(), position.position().posY()),
            position.direction().turn(-value()));
      }

      @Override
      public WaypointPosition moveWaypoint(final WaypointPosition position) {
        return new Right(-value).moveWaypoint(position);
      }
    }

    final record Right(int value) implements Instruction {
      @Override
      public DirectionPosition move(final DirectionPosition position) {
        return new DirectionPosition(
            new Position(position.position().posX(), position.position().posY()),
            position.direction().turn(value()));
      }

      @Override
      public WaypointPosition moveWaypoint(final WaypointPosition position) {
        final int rotationDeg;
        if (value >= 0) {
          rotationDeg = value % 360;
        } else {
          rotationDeg = (360 + (value % 360)) % 360;
        }
        final Position waypoint =
            switch (rotationDeg) {
              case 0 -> position.waypoint();
              case 90 -> new Position(-1 * position.waypoint().posY(), position.waypoint().posX());
              case 180 -> new Position(
                  -1 * position.waypoint().posX(), -1 * position.waypoint().posY());
              case 270 -> new Position(position.waypoint().posY(), -1 * position.waypoint().posX());
              default -> throw new UnsupportedOperationException();
            };
        return new WaypointPosition(position.position(), waypoint);
      }
    }

    final record Forward(int value) implements Instruction {
      @Override
      public DirectionPosition move(final DirectionPosition position) {
        final Instruction actualInstruction =
            switch (position.direction()) {
              case NORTH -> new North(value);
              case EAST -> new East(value);
              case SOUTH -> new South(value);
              case WEST -> new West(value);
            };
        return actualInstruction.move(position);
      }

      @Override
      public WaypointPosition moveWaypoint(final WaypointPosition position) {
        return new WaypointPosition(
            new Position(
                position.position().posX() + (position.waypoint().posX() * value),
                position.position().posY() + (position.waypoint().posY() * value)),
            position.waypoint());
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

  record Position(int posX, int posY) {}

  interface ComplexPosition {
    Position position();
  }

  record DirectionPosition(Position position, Direction direction) implements ComplexPosition {}

  record WaypointPosition(Position position, Position waypoint) implements ComplexPosition {}
}
