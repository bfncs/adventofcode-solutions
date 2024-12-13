package us.byteb.advent.year2015;

import static us.byteb.advent.Utils.readFileFromResources;

public class Day12 {

  private static final String RED = "\"red\"";

  public static void main(final String[] args) {
    final String input = readFileFromResources("year2015/day12.txt");

    // System.out.println("Part 1: " + sumAllNumbers(input, false));
    System.out.println("Part 2: " + sumAllNumbers(input, true));
  }

  public static long sumAllNumbers(final String input, final boolean ignoreRed) {
    long sum = 0L;
    int start = -1;
    int pos = 0;
    outerLoop:
    while (pos < input.length()) {
      final char c = input.charAt(pos);
      if (c == '{') {
        int openBrackets = 1;
        boolean containsRed = false;
        for (int endPos = pos + 1; endPos < input.length(); endPos++) {
          if (openBrackets == 1 && input.startsWith(RED, endPos)) {
            System.out.println("contains red " + endPos + ", " + openBrackets);
            containsRed = true;
          } else if (input.charAt(endPos) == '{') {
            openBrackets++;
          } else if (input.charAt(endPos) == '}') {
            openBrackets--;
          }

          if (openBrackets == 0) {
            if (containsRed) System.out.println("contains red " + pos + " - " + endPos);
            if (!ignoreRed || !containsRed) {
              final long sub = sumAllNumbers(input.substring(pos + 1, endPos), ignoreRed);
              System.out.println("+" + sub);
              sum += sub;
            }
            pos = endPos + 1;
            continue outerLoop;
          }
        }
        throw new IllegalStateException();
      }
      if (c == '-' || c >= '0' && c <= '9') {
        if (start == -1) start = pos;
      } else {
        if (start != -1) {
          final long sub = Long.parseLong(input.substring(start, pos));
          System.out.println("+" + sub);
          sum += sub;
          start = -1;
        }
      }
      pos++;
    }
    if (start != -1) {
      final long sub = Long.parseLong(input.substring(start, pos));
      System.out.println("+" + sub);
      sum += sub;
    }
    return sum;
  }
}
