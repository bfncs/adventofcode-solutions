package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day06 {

  public static final int PACKET_START_MARKER_LENGTH = 4;
  public static final int MESSAGE_START_MARKER_LENGTH = 14;

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day06.txt");

    System.out.println("Part 1: " + findPosAfterFirstPacketStartMarker(input));
    System.out.println("Part 2: " + findPosAfterFirstMessageStartMarker(input));
  }

  static int findPosAfterFirstPacketStartMarker(final String input) {
    return findPosAfterMarker(input, PACKET_START_MARKER_LENGTH);
  }

  static int findPosAfterFirstMessageStartMarker(final String input) {
    return findPosAfterMarker(input, MESSAGE_START_MARKER_LENGTH);
  }

  private static int findPosAfterMarker(final String input, final int distinctCharactersLength) {
    for (int i = distinctCharactersLength; i < input.length(); i++) {
      final int uniqChars =
          IntStream.range(i - distinctCharactersLength, i)
              .mapToObj(input::charAt)
              .collect(Collectors.toSet())
              .size();
      if (uniqChars == distinctCharactersLength) {
        return i;
      }
    }

    throw new IllegalStateException("No start marker found");
  }
}
