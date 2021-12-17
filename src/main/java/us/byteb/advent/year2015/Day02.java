package us.byteb.advent.year2015;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.List;
import java.util.stream.Stream;

public class Day02 {

  public static void main(String[] args) {
    final List<Box> boxes = parseInput(readFileFromResources("year2015/day02.txt"));

    System.out.println("Part 1: " + totalRequiredWrappingPaper(boxes));
    System.out.println("Part 2: " + totalRequiredWrappingRibbon(boxes));
  }

  static List<Box> parseInput(final String input) {
    return input.lines().map(Box::of).toList();
  }

  static long totalRequiredWrappingPaper(final List<Box> boxes) {
    return boxes.stream().mapToLong(Box::requiredWrappingPaper).sum();
  }

  static long totalRequiredWrappingRibbon(final List<Box> boxes) {
    return boxes.stream().mapToLong(Box::requiredRibbonLength).sum();
  }

  record Box(long length, long width, long height) {
    static Box of(final String input) {
      final String[] parts = input.split("x");
      if (parts.length != 3) throw new IllegalStateException();

      return new Box(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
    }

    long requiredWrappingPaper() {
      final List<Long> sideAreas = List.of(length * width, width * height, height * length);
      final long boxArea = 2 * sideAreas.stream().mapToLong(Long::longValue).sum();
      final long smallestSideArea =
          sideAreas.stream().mapToLong(Long::longValue).min().orElseThrow();

      return boxArea + smallestSideArea;
    }

    long requiredRibbonLength() {
      final List<Long> shortestTwoSides =
          Stream.of(length, width, height).sorted().toList().subList(0, 2);
      final long shortestPerimeter = 2 * (shortestTwoSides.get(0) + shortestTwoSides.get(1));
      final long volume = length * width * height;

      return shortestPerimeter + volume;
    }
  }
}
