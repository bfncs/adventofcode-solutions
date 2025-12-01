package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

public class Day01 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day01.txt");
    System.out.println("Part 1: " + solvePart1(input));
    System.out.println("Part 2: " + solvePart2(input));
  }

  public static int solvePart1(final String document) {
    final Dial dial = new Dial();
    int result = 0;

    for (final String line : document.lines().toList()) {
      dial.rotate(line);
      if (dial.getValue() == 0) {
        result++;
      }
    }

    return result;
  }

  public static int solvePart2(final String document) {
    final Dial dial = new Dial();
    for (final String line : document.lines().toList()) {
      dial.rotate(line);
    }

    return dial.getVisitedZero();
  }

  public static class Dial {
    private static final int NUM_VALUES = 100;
    private int value = 50;
    private int visitedZero = 0;

    public Dial() {}

    public Dial(final int value) {
      this.value = value;
    }

    public Dial rotate(final String rotation) {
      final int distance = Integer.parseInt(rotation.substring(1));

      value =
          switch (rotation.charAt(0)) {
            case 'L' -> {
              if (value > 0 && distance >= value) {
                visitedZero += ((distance - value) / 100) + 1;
              } else if (value == 0 && distance > 0) {
                visitedZero += distance / 100;
              }
              int candidate = (value - distance) % NUM_VALUES;
              while (candidate < 0) {
                candidate += NUM_VALUES;
              }
              yield candidate;
            }
            case 'R' -> {
              visitedZero += (value + distance) / NUM_VALUES;
              yield (value + distance) % NUM_VALUES;
            }
            default -> throw new IllegalArgumentException("Illegal rotation: " + rotation);
          };

      return this;
    }

    public int getValue() {
      return value;
    }

    public int getVisitedZero() {
      return visitedZero;
    }
  }
}
