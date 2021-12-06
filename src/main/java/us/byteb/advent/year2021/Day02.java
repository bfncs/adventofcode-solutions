package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day02 {

  public static void main(String[] args) throws IOException {
    final List<Command> input = parseInput(readFileFromResources("year2021/day02.txt"));

    final Position result1 = applyCommandsStrategy1(input);
    System.out.println("Part 1: " + result1.horizontalPos() * result1.depth());

    final Position result2 = applyCommandsStrategy2(input);
    System.out.println("Part 2: " + result2.horizontalPos() * result2.depth());
  }

  static List<Command> parseInput(final String input) {
    return input.lines().map(Command::parse).collect(Collectors.toList());
  }

  static Position applyCommandsStrategy1(final List<Command> commands) {
    return applyCommands(commands, (pos, cmd) -> cmd.applyStrategy1(pos));
  }

  static Position applyCommandsStrategy2(final List<Command> commands) {
    return applyCommands(commands, (pos, cmd) -> cmd.applyStrategy2(pos));
  }

  private static Position applyCommands(final List<Command> commands, final BiFunction<Position, Command, Position> strategy) {
    Position position = new Position(0, 0, 0);

    for (final Command command : commands) {
      position = strategy.apply(position, command);
    }

    return position;
  }

  record Position(long horizontalPos, long depth, long aim) {}

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

    Position applyStrategy1(Position position);
    Position applyStrategy2(Position position);

    record Forward(long movement) implements Command {
      @Override
      public Position applyStrategy1(final Position position) {
        return new Position(position.horizontalPos() + movement, position.depth(), position.aim());
      }
      @Override
      public Position applyStrategy2(final Position position) {
        return new Position(position.horizontalPos() + movement, position.depth() + (position.aim() * movement), position.aim());
      }
    }

    record Down(long movement) implements Command {
      @Override
      public Position applyStrategy1(final Position position) {
        return new Position(position.horizontalPos(), position.depth() + movement, position.aim());
      }
      @Override
      public Position applyStrategy2(final Position position) {
        return new Position(position.horizontalPos(), position.depth(), position.aim() + movement);
      }
    }

    record Up(long movement) implements Command {
      @Override
      public Position applyStrategy1(final Position position) {
        return new Position(position.horizontalPos(), position.depth() - movement, position.aim());
      }
      @Override
      public Position applyStrategy2(final Position position) {
        return new Position(position.horizontalPos(), position.depth(), position.aim() - movement);
      }
    }
  }
}
