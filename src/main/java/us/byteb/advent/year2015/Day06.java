package us.byteb.advent.year2015;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2015.Day06.Command.*;

import java.util.List;

public class Day06 {

  public static void main(String[] args) {
    final List<Instruction> input = parseInput(readFileFromResources("year2015/day06.txt"));
    System.out.println("Part 1: " + countLit(runInstructions(input)));
    System.out.println("Part 2: " + measureTotalBrightness(runInstructionsBrightness(input)));
  }

  static int countLit(boolean[][] grid) {
    int lit = 0;

    for (final boolean[] row : grid) {
      for (final boolean light : row) {
        if (light) lit++;
      }
    }

    return lit;
  }

  static boolean[][] runInstructions(final List<Instruction> instructions) {
    boolean[][] grid = new boolean[1000][1000];

    for (final Instruction instruction : instructions) {
      for (int x = instruction.a().x(); x <= instruction.b().x(); x++) {
        for (int y = instruction.a().y(); y <= instruction.b().y(); y++) {
          grid[x][y] =
              switch (instruction.command()) {
                case TURN_ON -> true;
                case TURN_OFF -> false;
                case TOGGLE -> !grid[x][y];
              };
        }
      }
    }

    return grid;
  }

  static long measureTotalBrightness(int[][] grid) {
    long totalBrightness = 0;

    for (final int[] row : grid) {
      for (final int light : row) {
        totalBrightness += light;
      }
    }

    return totalBrightness;
  }

  static int[][] runInstructionsBrightness(final List<Instruction> instructions) {
    int[][] grid = new int[1000][1000];

    for (final Instruction instruction : instructions) {
      for (int x = instruction.a().x(); x <= instruction.b().x(); x++) {
        for (int y = instruction.a().y(); y <= instruction.b().y(); y++) {
          grid[x][y] =
              switch (instruction.command()) {
                case TURN_ON -> grid[x][y] + 1;
                case TURN_OFF -> Math.max(grid[x][y] - 1, 0);
                case TOGGLE -> grid[x][y] + 2;
              };
        }
      }
    }

    return grid;
  }

  static List<Instruction> parseInput(final String input) {
    return input.lines().map(Instruction::parse).toList();
  }

  record Instruction(Command command, Point a, Point b) {
    static Instruction parse(final String input) {
      final Command command;
      if (input.startsWith("turn on")) {
        command = TURN_ON;
      } else if (input.startsWith("turn off")) {
        command = TURN_OFF;
      } else if (input.startsWith("toggle")) {
        command = TOGGLE;
      } else {
        throw new IllegalStateException();
      }

      final String[] coordinates =
          input.substring(command.name().length()).trim().split(" through ");
      if (coordinates.length != 2) throw new IllegalStateException();

      return new Instruction(command, Point.parse(coordinates[0]), Point.parse(coordinates[1]));
    }
  }

  enum Command {
    TURN_ON,
    TURN_OFF,
    TOGGLE;
  }

  record Point(int x, int y) {
    static Point parse(final String input) {
      final String[] parts = input.split(",");
      if (parts.length != 2) throw new IllegalStateException();

      return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
  }
}
