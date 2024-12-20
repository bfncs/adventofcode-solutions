package us.byteb.advent.year2015;

import static us.byteb.advent.Utils.readFileFromResources;

public class Day08 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2015/day08.txt");
    final long resultPart1 =
        totalNumberOfCharactersRaw(input) - totalNumberOfCharactersEscaped(input);
    final long resultPart2 =
        totalNumberOfCharactersEncoded(input) - totalNumberOfCharactersRaw(input);
    System.out.println("Part 1: " + resultPart1);
    System.out.println("Part 2: " + resultPart2);
  }

  static long totalNumberOfCharactersRaw(final String input) {
    return input.lines().mapToLong(String::length).sum();
  }

  static long totalNumberOfCharactersEscaped(final String input) {
    return input.lines().map(Day08::evaluateString).mapToLong(String::length).sum();
  }

  static long totalNumberOfCharactersEncoded(final String input) {
    return input.lines().map(Day08::encode).mapToLong(String::length).sum();
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

  public static String encode(final String input) {
    return "\"%s\"".formatted(escapeString(input));
  }

  private static String escapeString(final String input) {
    final StringBuilder sb = new StringBuilder();

    int i = 0;
    while (i < input.length()) {
      final char currentChar = input.charAt(i);
      if (currentChar == '\"') {
        sb.append("\\\"");
      } else if (currentChar == '\\') {
        sb.append("\\\\");
      } else {
        sb.append(currentChar);
      }
      i++;
    }

    return sb.toString();
  }
}
