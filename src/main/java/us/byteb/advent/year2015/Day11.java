package us.byteb.advent.year2015;

public class Day11 {

  private static final int STRAIGHT_LENGTH = 3;
  private static final int NUM_REQUIRED_PAIRS = 2;

  public static void main(String[] args) {
    final String input = "vzbxkghb";

    System.out.println("Part 1: " + findNextPassword(input));
  }

  public static String findNextPassword(final String input) {
    String candidate = input;

    do {
      candidate = increment(skipInsecure(candidate));
    } while (!isSecure(candidate));

    return candidate;
  }

  private static String skipInsecure(final String input) {
    final char[] chars = input.toCharArray();
    for (int i = chars.length - 1; i >= 0; i--) {
      if (chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'l') {
        chars[i]++;
        for (int j = i + 1; j < chars.length; j++) {
          chars[j] = 'a';
        }
      }
    }

    return new String(chars);
  }

  private static String increment(final String input) {
    final char[] chars = input.toCharArray();
    for (int i = chars.length - 1; i >= 0; i--) {
      if (chars[i] == 'z') {
        chars[i] = 'a';
      } else {
        chars[i]++;
        // directly skip insecure letters
        if (chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'l') {
          chars[i]++;
          for (int j = i + 1; j < chars.length; j++) {
            chars[j] = 'a';
          }
        }
        break;
      }
    }

    return new String(chars);
  }

  static boolean isSecure(final String input) {
    return containsNoInsecureLetter(input) && containsStraight(input) && containsPairs(input);
  }

  static boolean containsNoInsecureLetter(final String input) {
    return !(input.contains("i") || input.contains("o") || input.contains("l"));
  }

  static boolean containsStraight(final String input) {
    final char[] chars = input.toCharArray();

    outer:
    for (int i = 0; i < chars.length - STRAIGHT_LENGTH; i++) {
      for (int j = 1; j <= STRAIGHT_LENGTH; j++) {
        if (chars[i + j] != chars[i] + j) {
          continue outer;
        }
      }
      return true;
    }

    return false;
  }

  static boolean containsPairs(final String input) {
    final char[] chars = input.toCharArray();
    int numPairs = 0;
    char lastCharacter = '!';
    for (final char c : chars) {
      if (c == lastCharacter) {
        numPairs++;
        lastCharacter = '!';
      } else {
        lastCharacter = c;
      }
    }

    return numPairs >= NUM_REQUIRED_PAIRS;
  }
}
