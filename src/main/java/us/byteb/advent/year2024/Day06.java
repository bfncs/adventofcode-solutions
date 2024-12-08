package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day06 {

  private static final char CHAR_EMPTY = '.';
  private static final char CHAR_GUARD = '^';

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day06.txt");
    System.out.println("Part 1: " + positionBeforeLeavingMap(input));
    System.out.println("Part 2: " + positionsForLoop(input).size());
  }

  public static long positionBeforeLeavingMap(final String input) {
    final PuzzleInput puzzle = PuzzleInput.parse(input);

    final Set<Position> distinctPositions = new HashSet<>();
    GuardState guardState = puzzle.guard();
    while (puzzle.map().isInside(guardState.y(), guardState.x())) {
      distinctPositions.add(new Position(guardState.y(), guardState.x()));
      guardState = guardState.step(puzzle.map());
    }

    return distinctPositions.size();
  }

  public static Set<Position> positionsForLoop(final String input) {
    final PuzzleInput puzzle = PuzzleInput.parse(input);
    final Set<Position> distinctObstructionPositions = new HashSet<>();
    for (int y = 0; y < puzzle.map().height(); y++) {
      for (int x = 0; x < puzzle.map().width(); x++) {
        if (puzzle.map().data()[y][x] == CHAR_EMPTY
            && willItLoop(puzzle.map().withObstruction(y, x), puzzle.guard())) {
          distinctObstructionPositions.add(new Position(y, x));
        }
      }
    }

    return distinctObstructionPositions;
  }

  private static boolean willItLoop(final Map map, final GuardState initialState) {
    final Set<GuardState> guardStates = new HashSet<>();
    GuardState guardState = initialState;
    while (map.isInside(guardState.y(), guardState.x())) {
      guardStates.add(guardState);
      guardState = guardState.step(map);
      if (guardStates.contains(guardState)) {
        return true;
      }
    }

    return false;
  }

  record PuzzleInput(Map map, GuardState guard) {
    public static PuzzleInput parse(final String input) {
      final char[][] map = input.lines().map(String::toCharArray).toArray(char[][]::new);
      GuardState guard = GuardState.findPosition(map);
      map[guard.y()][guard.x()] = CHAR_EMPTY;

      return new PuzzleInput(new Map(map), guard);
    }
  }

  record Map(char[][] data) {
    public int height() {
      return data.length;
    }

    public int width() {
      return data[0].length;
    }

    public boolean isInside(final int y, final int x) {
      return y >= 0 && y < height() && x >= 0 && x < width();
    }

    public Map withObstruction(int posY, int posX) {
      char[][] copy = Arrays.stream(data).map(char[]::clone).toArray(char[][]::new);
      copy[posY][posX] = 'O';
      return new Map(copy);
    }
  }

  public record Position(int y, int x) {}

  record GuardState(int y, int x, int dY, int dX) {
    public static GuardState findPosition(final char[][] map) {
      for (int y = 0; y < map.length; y++) {
        for (int x = 0; x < map[y].length; x++) {
          if (map[y][x] == CHAR_GUARD) {
            return new GuardState(y, x, -1, 0);
          }
        }
      }
      throw new IllegalStateException();
    }

    GuardState step(final Map map) {
      int nextDy = dY;
      int nextDx = dX;
      while (true) {
        final int nextY = y + nextDy;
        final int nextX = x + nextDx;
        if (!map.isInside(nextY, nextX) || map.data()[nextY][nextX] == CHAR_EMPTY) {
          return new GuardState(nextY, nextX, nextDy, nextDx);
        }

        if (nextDy == 1 && nextDx == 0) {
          nextDy = 0;
          nextDx = -1;
        } else if (nextDy == 0 && nextDx == 1) {
          nextDy = 1;
          nextDx = 0;
        } else if (nextDy == -1 && nextDx == 0) {
          nextDy = 0;
          nextDx = 1;
        } else if (nextDy == 0 && nextDx == -1) {
          nextDy = -1;
          nextDx = 0;
        } else {
          throw new IllegalStateException();
        }
      }
    }
  }
}
