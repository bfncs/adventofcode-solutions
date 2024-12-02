package us.byteb.advent.year2015;

import java.util.*;

public class Day10 {

  public static void main(String[] args) {
    final String input = "1321131112";

    System.out.println("Part 1: " + lookAndSay(input, 40).length());
  }

  static String lookAndSay(final String input, final int times) {
    String result = input;
    for (int i = 0; i < times; i++) {
      result = lookAndSay(result);
    }

    return result;
  }

  static String lookAndSay(final String input) {

    final char[] chars = input.toCharArray();
    final StringBuilder sb = new StringBuilder();

    int currentStreak = 0;
    char currentDigit = '0';
    for (int i = 0; i <= chars.length; i++) {
      if (i == chars.length) {
        sb.append(currentStreak).append(currentDigit);
      } else if (currentStreak == 0) {
        currentStreak = 1;
        currentDigit = chars[i];
      } else {
        if (chars[i] != currentDigit) {
          sb.append(currentStreak).append(currentDigit);
          currentStreak = 1;
          currentDigit = chars[i];
        } else {
          currentStreak++;
        }
      }
    }

    return sb.toString();
  }
}
