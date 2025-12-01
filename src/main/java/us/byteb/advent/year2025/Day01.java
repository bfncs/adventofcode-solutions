package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

public class Day01 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day01.txt");
    System.out.println("Part 1: " + solvePart1(input));
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

  public static class Dial {
    private static final int NUM_VALUES = 100;
    private int value = 50;

    public Dial() {}

    public Dial(final int value) {
      this.value = value;
    }

    public Dial rotate(final String rotation) {
      int distance = Integer.parseInt(rotation.substring(1));
      if (rotation.startsWith("L")) {
        distance *= -1;
      }

      int nextDistance = value + distance;
      while (nextDistance < 0) {
        nextDistance += NUM_VALUES;
      }
      value = nextDistance % NUM_VALUES;

      return this;
    }

    public int getValue() {
      return value;
    }
  }
}
