package us.byteb.advent.year2022;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Day01 {

  public static void main(String[] args) {
    final List<Elf> input = parseInput(readFileFromResources("year2022/day01.txt"));

    System.out.println("Part 1: " + findMaxCalories(input));
    System.out.println("Part 2: " + sumMaxNCalories(input, 3));
  }

  static List<Elf> parseInput(final String input) {
    final List<String> lines = input.lines().toList();
    final List<Elf> result = new ArrayList<>();
    List<Long> currentCalories = new ArrayList<>();
    for (int i = 0; i <= lines.size(); i++) {
      if (i == (lines.size()) || isBlank(lines.get(i))) {
        result.add(new Elf(currentCalories));
        currentCalories = new ArrayList<>();
      } else {
        currentCalories.add(Long.parseLong(lines.get(i)));
      }
    }

    return result;
  }

  static long findMaxCalories(final Collection<Elf> elves) {
    return elves.stream().mapToLong(Elf::totalCalories).max().getAsLong();
  }

  static long sumMaxNCalories(final Collection<Elf> elves, final int n) {
    return elves.stream()
        .map(Elf::totalCalories)
        .sorted(Comparator.reverseOrder())
        .limit(n)
        .mapToLong(x -> x)
        .sum();
  }

  record Elf(List<Long> calories) {
    long totalCalories() {
      return calories.stream().mapToLong(x -> x).sum();
    }
  }
}
