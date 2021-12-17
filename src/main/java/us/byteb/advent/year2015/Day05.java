package us.byteb.advent.year2015;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Set;

public class Day05 {

  private static final Set<String> FORBIDDEN_STRINGS = Set.of("ab", "cd", "pq", "xy");

  public static void main(String[] args) {
    final String input = readFileFromResources("year2015/day05.txt");
    System.out.println("Part 1: " + input.lines().filter(Day05::isNice).count());
    System.out.println("Part 2: " + input.lines().filter(Day05::isNiceRevised).count());
  }

  static boolean isNice(final String str) {
    return countVowels(str) >= 3
        && containsLetterTwiceInARow(str)
        && !containsForbiddenStrings(str);
  }

  static boolean isNiceRevised(final String str) {
    return containsRepeatedPairOfTwoLetters(str)
        && containsRepeatingLetterWithOneLetterBetween(str);
  }

  private static int countVowels(final String str) {
    return str.codePoints()
        .mapToObj(c -> (char) c)
        .filter(
            c ->
                switch (c) {
                  case 'a', 'e', 'i', 'o', 'u' -> true;
                  default -> false;
                })
        .toList()
        .size();
  }

  private static boolean containsLetterTwiceInARow(final String str) {
    int last = -1;
    for (int i = 0; i < str.length(); i++) {
      final int current = str.codePointAt(i);
      if (current == last) {
        return true;
      }
      last = current;
    }
    return false;
  }

  private static boolean containsForbiddenStrings(final String str) {
    for (final String forbidden : FORBIDDEN_STRINGS) {
      if (str.contains(forbidden)) {
        return true;
      }
    }
    return false;
  }

  private static boolean containsRepeatedPairOfTwoLetters(final String str) {
    for (int haystackStart = 0; haystackStart < (str.length() - 1); haystackStart++) {
      final String pair = str.substring(haystackStart, haystackStart + 2);
      for (int needleStart = 0; needleStart < (str.length() - 1); needleStart++) {
        if (needleStart >= haystackStart - 1 && needleStart <= haystackStart + 1) {
          continue;
        }

        final String candidate = str.substring(needleStart, needleStart + 2);
        if (candidate.equals(pair)) {
          return true;
        }
      }
    }

    return false;
  }

  private static boolean containsRepeatingLetterWithOneLetterBetween(final String str) {
    return containsLetterTwiceInARow(removeEverySecondLetter(str))
        || containsLetterTwiceInARow(removeEverySecondLetter(str.substring(1)));
  }

  private static String removeEverySecondLetter(final String str) {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < str.length(); i += 2) {
      sb.append(str.charAt(i));
    }

    return sb.toString();
  }
}
