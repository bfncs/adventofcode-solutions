package us.byteb.advent.y19;

import java.util.stream.IntStream;

public class Day4 {

  public static void main(String[] args) {
    final long result1 = IntStream.rangeClosed(382345, 843167).filter(Day4::isValid).count();
    System.out.println("Part 1: " + result1);
  }

  static boolean isValid(final int num) {
    return hasDoubles(num) && leftToRightNeverDecrease(num);
  }

  private static boolean hasDoubles(final int num) {
    final char[] chars = Integer.toString(num).toCharArray();
    for (int i = 0; i < chars.length - 1; i++) {
      if (chars[i] == chars[i + 1]) {
        return true;
      }
    }
    return false;
  }

  private static boolean leftToRightNeverDecrease(final int num) {
    final char[] chars = Integer.toString(num).toCharArray();
    for (int i = 0; i < chars.length - 1; i++) {
      if (chars[i] > chars[i + 1]) {
        return false;
      }
    }
    return true;
  }
}
