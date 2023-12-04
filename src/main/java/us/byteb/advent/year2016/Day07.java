package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.HashSet;
import java.util.Set;

public class Day07 {
  public static void main(final String[] args) {
    final String input = readFileFromResources("year2016/day07.txt");
    System.out.println("Part 1: " + countSupportingTls(input));
  }

  static long countSupportingTls(final String input) {
    return input.lines().filter(Day07::isSupportingTls).count();
  }

  static boolean isSupportingTls(final String line) {
    final Set<Sequence> sequences = new HashSet<>();
    for (int i = 0; i < line.toCharArray().length; ) {
      final char c = line.charAt(i);
      boolean isInsideBrackets = c == '[';
      int start = isInsideBrackets ? i + 1 : i;
      int end = start;
      while (end < line.length() && line.charAt(end) != '[' && line.charAt(end) != ']') {
        end++;
      }
      final String content = line.substring(start, end);
      sequences.add(new Sequence(content, isInsideBrackets));
      i = isInsideBrackets ? end + 1 : end;
    }

    return sequences.stream().anyMatch(seq -> !seq.isHypernet() && seq.containsAbba())
        && sequences.stream().noneMatch(seq -> seq.isHypernet && seq.containsAbba());
  }

  record Sequence(String content, boolean isHypernet) {
    public boolean containsAbba() {
      if (content.length() < 4) return false;

      for (int i = 0; i <= content.length() - 4; i++) {
        if (isAbba(content.substring(i, i + 4))) return true;
      }

      return false;
    }

    private static boolean isAbba(final String input) {
      for (int i = 0; i < (input.length() / 2); i++) {
        if (input.charAt(i) != input.charAt(input.length() - i - 1)) {
          return false;
        }
      }

      return input.charAt(0) != input.charAt(1);
    }
  }
}
