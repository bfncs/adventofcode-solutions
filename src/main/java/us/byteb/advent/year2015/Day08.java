package us.byteb.advent.year2015;

import static us.byteb.advent.Utils.readFileFromResources;

public class Day08 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2015/day08.txt");
    System.out.println(
        "Part 1: " + (totalNumberOfCharactersRaw(input) - totalNumberOfCharactersEscaped(input)));
  }

  static long totalNumberOfCharactersRaw(final String input) {
    return input.lines().mapToLong(String::length).sum();
  }

  static long totalNumberOfCharactersEscaped(final String input) {
    return input.lines().map(Day08::evaluateString).mapToLong(String::length).sum();
  }

  static String evaluateString(final String input) {
    if (!(input.startsWith("\"") && input.endsWith("\""))) throw new IllegalStateException();

    return resolveEscapeSequences(input.substring(1, input.length() - 1));
  }

  private static String resolveEscapeSequences(final String input) {
    final StringBuilder sb = new StringBuilder();
    int i = 0;
    while (i < input.length()) {
      final char currentChar = input.charAt(i);
      if (currentChar != '\\') {
        sb.append(currentChar);
        i++;
        continue;
      }

      final char nextChar = input.charAt(i + 1);
      sb.append(
          switch (nextChar) {
            case 'x' -> {
              final char result = (char) Integer.parseInt(input.substring(i + 2, i + 4), 16);
              i += 4;
              yield result;
            }
            case '"', '\\' -> {
              i += 2;
              yield nextChar;
            }
            default -> throw new IllegalStateException();
          });
    }

    return sb.toString();
  }
}
