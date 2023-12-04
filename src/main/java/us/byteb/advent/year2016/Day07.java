package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day07 {
  public static void main(final String[] args) {
    final String input = readFileFromResources("year2016/day07.txt");
    System.out.println("Part 1: " + countLines(input, Day07::isSupportingTls));
    System.out.println("Part 2: " + countLines(input, Day07::isSupportingSsl));
  }

  static long countLines(final String input, Predicate<String> predicate) {
    return input.lines().filter(predicate).count();
  }

  static boolean isSupportingTls(final String line) {
    final Set<Sequence> sequences = parseSequences(line);

    return sequences.stream().anyMatch(seq -> !seq.isHypernet() && seq.containsAbba())
        && sequences.stream().noneMatch(seq -> seq.isHypernet && seq.containsAbba());
  }

  static boolean isSupportingSsl(final String line) {
    final Set<Sequence> sequences = parseSequences(line);

    final Set<String> sequenceAbas =
        sequences.stream()
            .flatMap(seq -> seq.isHypernet() ? Stream.empty() : seq.findAbas().stream())
            .collect(Collectors.toSet());
    final Set<String> hyperSequenceAbas =
        sequences.stream()
            .flatMap(seq -> !seq.isHypernet() ? Stream.empty() : seq.findAbas().stream())
            .collect(Collectors.toSet());

    return sequenceAbas.stream()
        .anyMatch(
            aba ->
                hyperSequenceAbas.stream()
                    .anyMatch(
                        candidate ->
                            aba.charAt(0) == candidate.charAt(1)
                                && aba.charAt(1) == candidate.charAt(0)));
  }

  private static Set<Sequence> parseSequences(final String line) {
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
    return sequences;
  }

  record Sequence(String content, boolean isHypernet) {
    public boolean containsAbba() {
      if (content.length() < 4) return false;

      for (int i = 0; i <= content.length() - 4; i++) {
        if (isMirrored(content.substring(i, i + 4))) return true;
      }

      return false;
    }

    public Set<String> findAbas() {
      if (content.length() < 3) return Collections.emptySet();

      final Set<String> result = new HashSet<>();
      for (int i = 0; i <= content.length() - 3; i++) {
        final String current = content.substring(i, i + 3);
        if (isMirrored(current)) result.add(current);
      }

      return result;
    }

    private static boolean isMirrored(final String input) {
      for (int i = 0; i < (input.length() / 2); i++) {
        if (input.charAt(i) != input.charAt(input.length() - i - 1)) {
          return false;
        }
      }

      return input.charAt(0) != input.charAt(1);
    }
  }
}
