package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day05 {

  public static void main(String[] args) {
    final PuzzleInput input = parseInput(readFileFromResources("year2024/day05.txt"));
    System.out.println("Part 1: " + sumOfMiddleNums(filterCorrectlyOrdered(input)));
  }

  public static PuzzleInput parseInput(final String input) {
    final List<String> lines = input.lines().toList();
    final int firstEmptyLine =
        lines.indexOf(lines.stream().filter(String::isEmpty).findFirst().orElseThrow());

    final Set<PageOrderingRule> rules =
        lines.subList(0, firstEmptyLine).stream()
            .map(
                line -> {
                  final String[] parts = line.split("\\|");
                  if (parts.length != 2) throw new IllegalStateException();
                  return new PageOrderingRule(
                      Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                })
            .collect(Collectors.toSet());

    final Set<List<Integer>> updates =
        lines.subList(firstEmptyLine + 1, lines.size()).stream()
            .map(line -> Arrays.stream(line.split(",")).map(Integer::parseInt).toList())
            .collect(Collectors.toSet());

    return new PuzzleInput(updates, rules);
  }

  public static Set<List<Integer>> filterCorrectlyOrdered(final PuzzleInput input) {
    return input.updates().stream()
        .filter(update -> isUpdateRuleCompliant(update, input.rules()))
        .collect(Collectors.toSet());
  }

  private static boolean isUpdateRuleCompliant(
      final List<Integer> update, final Set<PageOrderingRule> rules) {
    for (final PageOrderingRule rule : rules) {
      final int posBefore = update.indexOf(rule.before());
      final int posAfter = update.indexOf(rule.after());
      if (posBefore != -1 && posAfter != -1 && posBefore > posAfter) {
        return false;
      }
    }
    return true;
  }

  private static Predicate<List<Integer>> getListPredicate() {
    return update -> {
      return true;
    };
  }

  public static long sumOfMiddleNums(final Set<List<Integer>> updates) {
    return updates.stream().mapToLong(update -> update.get(update.size() / 2)).sum();
  }

  public record PuzzleInput(Set<List<Integer>> updates, Set<PageOrderingRule> rules) {}

  public record PageOrderingRule(int before, int after) {}
}
