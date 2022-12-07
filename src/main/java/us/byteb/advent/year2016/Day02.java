package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Arrays;
import java.util.List;

public class Day02 {

  static final char[][] KEYPAD1 = {
    new char[] {'1', '2', '3'},
    new char[] {'4', '5', '6'},
    new char[] {'7', '8', '9'}
  };

  static final char[][] KEYPAD2 = {
    new char[] {' ', ' ', '1', ' ', ' '},
    new char[] {' ', '2', '3', '4', ' '},
    new char[] {'5', '6', '7', '8', '9'},
    new char[] {' ', 'A', 'B', 'C', ' '},
    new char[] {' ', ' ', 'D', ' ', ' '}
  };

  public static void main(String[] args) {
    final String input = readFileFromResources("year2016/day02.txt");

    System.out.println("Part 1: " + findCode(input, KEYPAD1));
    System.out.println("Part 2: " + findCode(input, KEYPAD2));
  }

  static String findCode(final String input, final char[][] keypad) {
    final StringBuilder code = new StringBuilder();

    Position position = findInitialPosition(keypad);
    for (final String line : input.lines().toList()) {
      final List<Character> characters = line.chars().mapToObj(c -> (char) c).toList();

      for (final Character c : characters) {
        final Position nextPosition =
            switch (c) {
              case 'U' -> position.withY(Math.max(position.y() - 1, 0));
              case 'D' -> position.withY(Math.min(position.y() + 1, keypad.length - 1));
              case 'L' -> position.withX(Math.max(position.x() - 1, 0));
              case 'R' -> position.withX(Math.min(position.x() + 1, keypad.length - 1));
              default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        if (keypad[nextPosition.y()][nextPosition.x()] != ' ') {
          position = nextPosition;
        }
      }

      code.append(keypad[position.y()][position.x()]);
    }

    return code.toString();
  }

  private static Position findInitialPosition(final char[][] keypad) {
    for (int y = 0; y < keypad.length; y++) {
      for (int x = 0; x < keypad[y].length; x++) {
        if (keypad[y][x] == '5') {
          return new Position(x, y);
        }
      }
    }

    throw new IllegalStateException("Illegal keypad: " + Arrays.deepToString(keypad));
  }

  record Position(int x, int y) {
    public Position withX(int x) {
      return new Position(x, y);
    }

    public Position withY(int y) {
      return new Position(x, y);
    }
  }
}
