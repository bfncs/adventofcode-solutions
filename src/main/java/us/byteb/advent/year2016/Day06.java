package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day06 {

  public static void main(final String[] args) {
    final String input = readFileFromResources("year2016/day06.txt");
    System.out.println("Part 1: " + decode(input, Day06::mostFrequentCharacterInColumn));
    System.out.println("Part 2: " + decode(input, Day06::leastFrequentCharacterInColumn));
  }

  static String decode(
      final String input, final BiFunction<String, Integer, Character> decodeColumn) {
    final int numChars = input.lines().findFirst().orElseThrow().length();

    final StringBuilder result = new StringBuilder();
    for (int column = 0; column < numChars; column++) {
      result.append(decodeColumn.apply(input, column));
    }

    return result.toString();
  }

  static char mostFrequentCharacterInColumn(final String input, final int column) {
    return characterFrequencies(input, column).entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .orElseThrow()
        .getKey();
  }

  static char leastFrequentCharacterInColumn(final String input, final int column) {
    return characterFrequencies(input, column).entrySet().stream()
        .min(Map.Entry.comparingByValue())
        .orElseThrow()
        .getKey();
  }

  private static Map<Character, Long> characterFrequencies(final String input, final int column) {
    return input
        .lines()
        .map(line -> line.charAt(column))
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }
}
