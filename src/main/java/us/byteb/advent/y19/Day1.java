package us.byteb.advent.y19;

import us.byteb.advent.Utils;

public class Day1 {

  public static void main(String[] args) {
    final String input = Utils.readFileFromResources("y19/day1.txt");

    System.out.println(
        "Part 1: " + input.lines().mapToLong(line -> calcRequiredFuel(Long.parseLong(line))).sum());
  }

  static long calcRequiredFuel(long moduleMass) {
    return (moduleMass / 3) - 2;
  }
}
