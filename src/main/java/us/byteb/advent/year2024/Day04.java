package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Set;

public class Day04 {
  static final char[] SEARCHED_WORD = "XMAS".toCharArray();

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day04.txt");

    System.out.println("Part 1: " + searchWord(input));
  }

  public static long searchWord(final String input) {

    final char[][] data = input.lines().map(String::toCharArray).toArray(char[][]::new);
    long result = 0;

    record Direction(int dY, int dX) {
      static final Set<Direction> ALL =
          Set.of(
              new Direction(1, 0), // up
              new Direction(-1, 0), // down
              new Direction(0, 1), // right
              new Direction(0, -1), // left
              new Direction(1, 1), // up right
              new Direction(-1, 1), // down right
              new Direction(-1, -1), // down left
              new Direction(1, -1) // up left
              );
    }

    for (int y = 0; y < data.length; y++) {
      for (int x = 0; x < data[y].length; x++) {
        for (Direction direction : Direction.ALL) {
          if (checkWord(data, y, x, direction.dY(), direction.dX())) {
            result++;
          }
        }
      }
    }

    return result;
  }

  private static boolean checkWord(final char[][] data, int posY, int posX, int dY, int dX) {
    for (int i = 0; i < SEARCHED_WORD.length; i++) {
      int checkY = posY + (i * dY);
      int checkX = posX + (i * dX);
      if (checkY < 0
          || checkY >= data.length
          || checkX < 0
          || checkX >= data[0].length
          || data[checkY][checkX] != SEARCHED_WORD[i]) {
        return false;
      }
    }

    return true;
  }
}
