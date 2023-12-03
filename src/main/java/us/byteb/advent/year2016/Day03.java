package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day03 {

  private static final int NUM_COLUMNS = 3;

  public static void main(String[] args) {
    final String input = readFileFromResources("year2016/day03.txt");

    System.out.println(
        "Part 1: " + parseInputHorizontally(input).stream().filter(Triangle::isValid).count());

    System.out.println(
        "Part 2: " + parseInputVertically(input).stream().filter(Triangle::isValid).count());
  }

  private static Set<Triangle> parseInputHorizontally(final String input) {
    return input
        .lines()
        .map(
            line -> {
              final String[] parts = line.trim().split("\\s+");
              if (parts.length != NUM_COLUMNS) throw new IllegalStateException();
              return new Triangle(
                  Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
            })
        .collect(Collectors.toSet());
  }

  private static Set<Triangle> parseInputVertically(final String input) {
    final List<List<Long>> columns =
        IntStream.rangeClosed(0, NUM_COLUMNS)
            .mapToObj(i -> new ArrayList<Long>())
            .collect(Collectors.toList());
    input
        .lines()
        .forEach(
            line -> {
              final String[] parts = line.trim().split("\\s+");
              if (parts.length != NUM_COLUMNS) throw new IllegalStateException();
              for (int i = 0; i < NUM_COLUMNS; i++) {
                columns.get(i).add(Long.parseLong(parts[i]));
              }
            });
    final List<Long> flattenedColumns = columns.stream().flatMap(Collection::stream).toList();

    final Set<Triangle> triangles = new HashSet<>();
    for (int i = 0; i < flattenedColumns.size(); i += NUM_COLUMNS) {
      triangles.add(
          new Triangle(
              flattenedColumns.get(i), flattenedColumns.get(i + 1), flattenedColumns.get(i + 2)));
    }

    return triangles;
  }

  static boolean isValidTriangle(long a, long b, long c) {
    return ((a + b) > c && (b + c) > a && (c + a) > b);
  }

  record Triangle(long a, long b, long c) {

    boolean isValid() {
      return ((a + b) > c && (b + c) > a && (c + a) > b);
    }
  }
}
