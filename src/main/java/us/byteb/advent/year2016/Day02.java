package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.List;

public class Day02 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2016/day02.txt");

    System.out.println("Part 1: " + findCode(input));
  }

  static String findCode(final String input) {
    final StringBuilder code = new StringBuilder();

    final char[][] keypad =
        new char[][] {
          new char[] {'1', '2', '3'},
          new char[] {'4', '5', '6'},
          new char[] {'7', '8', '9'}
        };

    Position position = new Position(1, 1);
    for (final String line : input.lines().toList()) {
      final List<Character> characters = line.chars().mapToObj(c -> (char) c).toList();

      for (final Character c : characters) {
        position =
            switch (c) {
              case 'U' -> position.withY(Math.max(position.y() - 1, 0));
              case 'D' -> position.withY(Math.min(position.y() + 1, keypad.length - 1));
              case 'L' -> position.withX(Math.max(position.x() - 1, 0));
              case 'R' -> position.withX(Math.min(position.x() + 1, keypad.length - 1));
              default -> throw new IllegalStateException("Unexpected value: " + c);
            };
      }

      code.append(keypad[position.y()][position.x()]);
    }

    return code.toString();
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
