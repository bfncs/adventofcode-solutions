package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day06 {

  public static final int START_MARKER_LENGHT = 4;

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day06.txt");

    System.out.println("Part 1: " + findPosAfterFirstStartMarker(input));
  }

  static int findPosAfterFirstStartMarker(final String input) {
    for (int i = START_MARKER_LENGHT; i < input.length(); i++) {
      final int uniqChars =
          IntStream.range(i - START_MARKER_LENGHT, i)
              .mapToObj(input::charAt)
              .collect(Collectors.toSet())
              .size();
      if (uniqChars == START_MARKER_LENGHT) {
        return i;
      }
    }

    throw new IllegalStateException("No start marker found");
  }
}
