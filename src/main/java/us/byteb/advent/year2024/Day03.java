package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {

  private static final Pattern PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day03.txt");

    System.out.println("Part 1: " + sum(parseInput(input)));
  }

  static List<Mul> parseInput(final String input) {
    final List<Mul> result = new ArrayList<>();
    final Matcher matcher = PATTERN.matcher(input);
    while (matcher.find()) {
      final long left = Long.parseLong(matcher.group(1));
      final long right = Long.parseLong(matcher.group(2));
      result.add(new Mul(left, right));
    }

    return result;
  }

  record Mul(long left, long right) {

    public long eval() {
      return left * right;
    }
  }

  static long sum(final List<Mul> instructions) {
    return instructions.stream().mapToLong(Mul::eval).sum();
  }
}
