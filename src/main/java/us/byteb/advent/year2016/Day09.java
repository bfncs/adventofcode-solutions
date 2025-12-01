package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 {

  private static final Pattern MARKER_PATTERN =
      Pattern.compile("\\((?<length>\\d+)x(?<repeat>\\d+)\\)");

  public static void main(String[] args) {
    final String input = readFileFromResources("year2016/day09.txt");
    System.out.println("Part 1: " + decompress(input).length());
    System.out.println("Part 2: " + decompress2(input));
  }

  public static String decompress(final String input) {
    final StringBuilder buffer = new StringBuilder();
    int pos = 0;

    while (pos < input.length()) {
      if (input.charAt(pos) != '(') {
        buffer.append(input.charAt(pos));
        pos++;
        continue;
      }

      final Matcher matcher = MARKER_PATTERN.matcher(input.substring(pos));
      if (!matcher.find()) {
        throw new IllegalStateException("Unable to parse marker at pos " + pos);
      }
      final int length = Integer.parseInt(matcher.group("length"));
      final int repeat = Integer.parseInt(matcher.group("repeat"));

      final int repeatStart = pos + matcher.end();
      for (int i = 0; i < repeat; i++) {
        buffer.append(input, repeatStart, repeatStart + length);
      }
      pos += matcher.end() + length;
    }

    return buffer.toString();
  }

  public static long decompress2(final String input) {
    int pos = 0;
    long resultLength = 0;

    while (pos < input.length()) {
      if (input.charAt(pos) != '(') {
        pos++;
        resultLength++;
        continue;
      }

      final Matcher matcher = MARKER_PATTERN.matcher(input.substring(pos));
      if (!matcher.find()) {
        throw new IllegalStateException("Unable to parse marker at pos " + pos);
      }
      final int length = Integer.parseInt(matcher.group("length"));
      final int repeat = Integer.parseInt(matcher.group("repeat"));

      final int repeatStart = pos + matcher.end();
      resultLength += repeat * decompress2(input.substring(repeatStart, repeatStart + length));
      pos = repeatStart + length;
    }

    return resultLength;
  }
}
