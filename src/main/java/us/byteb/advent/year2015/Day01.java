package us.byteb.advent.year2015;

import static us.byteb.advent.Utils.readFileFromResources;

public class Day01 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2015/day01.txt");

    System.out.println("Part 1: " + resolveBasement(input));
    System.out.println("Part 2: " + positionFirstEnterBasement(input));
  }

  static long resolveBasement(final String instructions) {
    return instructions
        .codePoints()
        .mapToLong(
            c ->
                switch ((char) c) {
                  case '(' -> 1;
                  case ')' -> -1;
                  default -> throw new IllegalStateException();
                })
        .sum();
  }

  static long positionFirstEnterBasement(final String instructions) {
    long basement = 0;
    for (int i = 0; i < instructions.toCharArray().length; i++) {
      basement +=
          switch (instructions.charAt(i)) {
            case '(' -> 1;
            case ')' -> -1;
            default -> throw new IllegalStateException();
          };
      if (basement < 0) {
        return i + 1;
      }
    }

    throw new IllegalStateException();
  }
}
