package us.byteb.advent.year2015;

import static us.byteb.advent.Utils.readFileFromResources;

public class Day12 {

  public static void main(final String[] args) {
    final String input = readFileFromResources("year2015/day12.txt");

    System.out.println("Part 1: " + sumAllNumbers(input));
  }

  public static long sumAllNumbers(final String input) {
    long sum = 0L;
    int start = -1;
    for (int pos = 0; pos < input.length(); pos++) {
      final char c = input.charAt(pos);
      if (c == '-' || c >= '0' && c <= '9') {
        if (start == -1) start = pos;
      } else {
        if (start != -1) {
          final long asLong = Long.parseLong(input.substring(start, pos));
          sum += asLong;
          start = -1;
        }
      }
    }
    return sum;
  }
}
