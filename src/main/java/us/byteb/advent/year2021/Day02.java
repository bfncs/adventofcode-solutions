package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 {

  public static void main(String[] args) throws IOException {
    final List<Command> input = parseInput(readFileFromResources("year2021/day02.txt"));

    final Position result = applyCommands(input);
    System.out.println("Part 1: " + result.horizontalPos() * result.depth());
  }

  static List<Command> parseInput(final String input) {
    return input.lines().map(Command::parse).collect(Collectors.toList());
  }

  static Position applyCommands(final List<Command> commands) {
    Position position = new Position(0, 0);

    for (final Command command : commands) {
      position = command.apply(position);
    }

    return position;
  }

  record Position(long horizontalPos, long depth) {}

  interface Command {
    static Command parse(final String input) {
      final String[] parts = input.split(" ");
      if (parts.length != 2) throw new IllegalStateException("Illegal command: " + input);

      final String commandType = parts[0].toLowerCase();
      final long movement = Long.parseLong(parts[1]);

      return switch (commandType) {
        case "forward" -> new Forward(movement);
        case "down" -> new Down(movement);
        case "up" -> new Up(movement);
        default -> throw new IllegalStateException("Illegal command type: " + commandType);
      };
    }

    Position apply(Position position);

    record Forward(long movement) implements Command {
      @Override
      public Position apply(final Position position) {
        return new Position(position.horizontalPos() + movement, position.depth());
      }
    }

    record Down(long movement) implements Command {
      @Override
      public Position apply(final Position position) {
        return new Position(position.horizontalPos(), position.depth() + movement);
      }
    }

    record Up(long movement) implements Command {
      @Override
      public Position apply(final Position position) {
        return new Position(position.horizontalPos(), position.depth() - movement);
      }
    }
  }
}
