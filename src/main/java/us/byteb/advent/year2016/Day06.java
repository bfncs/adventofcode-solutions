package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day06 {

  public static void main(final String[] args) {
    final String input = readFileFromResources("year2016/day06.txt");
    System.out.println("Part 1: " + decodeByMostFrequentPerColumn(input));
  }

  static String decodeByMostFrequentPerColumn(final String input) {
    final int numChars = input.lines().findFirst().orElseThrow().length();

    final StringBuilder result = new StringBuilder();
    for (int column = 0; column < numChars; column++) {
      result.append(mostFrequentCharacterInColumn(input, column));
    }

    return result.toString();
  }

  private static char mostFrequentCharacterInColumn(final String input, final int column) {
    final Map<Character, Long> characterFrequencies =
        input
            .lines()
            .map(line -> line.charAt(column))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    return characterFrequencies.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .orElseThrow()
        .getKey();
  }
}
