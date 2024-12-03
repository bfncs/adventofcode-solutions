package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {

  private static final Pattern PATTERN =
      Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)");

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day03.txt");

    System.out.println("Part 1: " + evaluate(parseInput(input), true));
    System.out.println("Part 2: " + evaluate(parseInput(input), false));
  }

  static List<Op> parseInput(final String input) {
    final List<Op> result = new ArrayList<>();
    final Matcher matcher = PATTERN.matcher(input);
    while (matcher.find()) {
      switch (matcher.group(0)) {
        case "do()" -> result.add(new Op.Do());
        case "don't()" -> result.add(new Op.Dont());
        default -> {
          final long left = Long.parseLong(matcher.group(1));
          final long right = Long.parseLong(matcher.group(2));
          result.add(new Op.Mul(left, right));
        }
      }
    }

    return result;
  }

  sealed interface Op {
    record Mul(long left, long right) implements Op {
      public long eval() {
        return left * right;
      }
    }

    record Do() implements Op {}

    record Dont() implements Op {}
  }

  static long evaluate(final List<Op> instructions, final boolean ignoreToggle) {
    long sum = 0;
    boolean instructionsEnabled = true;

    for (final Op instruction : instructions) {
      switch (instruction) {
        case Op.Do ignored -> instructionsEnabled = true;
        case Op.Dont ignored -> instructionsEnabled = false;
        case Op.Mul mul -> {
          if (ignoreToggle || instructionsEnabled) {
            sum += mul.eval();
          }
        }
      }
    }

    return sum;
  }
}
