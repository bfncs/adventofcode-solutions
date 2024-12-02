package us.byteb.advent.year2015;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day09 {

  public static void main(String[] args) {
    final List<DistanceDef> input = parseInput(readFileFromResources("year2015/day09.txt"));

    System.out.println("Part 1: " + shortestPath(input));
    System.out.println("Part 2: " + longestPath(input));
  }

  static List<DistanceDef> parseInput(final String input) {
    return input.lines().map(DistanceDef::parse).toList();
  }

  static Path shortestPath(final List<DistanceDef> distances) {
    return findPath(distances, true);
  }

  static Path longestPath(final List<DistanceDef> distances) {
    return findPath(distances, false);
  }

  private static Path findPath(
      final List<DistanceDef> distances, final boolean selectShortestPath) {
    final Set<String> locations =
        distances.stream().flatMap(d -> Stream.of(d.start(), d.end())).collect(Collectors.toSet());
    final Set<List<String>> permutations = permutations(locations);

    Path result =
        new Path(Collections.emptyList(), selectShortestPath ? Long.MAX_VALUE : Long.MIN_VALUE);
    for (final List<String> path : permutations) {
      final long distance = distance(path, distances);
      if (selectShortestPath
          ? distance < result.totalDistance()
          : distance > result.totalDistance()) {
        result = new Path(path, distance);
      }
    }

    return result;
  }

  private static long distance(final List<String> locations, final List<DistanceDef> distances) {
    long result = 0;
    for (int i = 1; i < locations.size(); i++) {
      final String start = locations.get(i - 1);
      final String end = locations.get(i);

      final long distance =
          distances.stream()
              .filter(
                  distanceDef ->
                      (distanceDef.start().equals(start) && distanceDef.end().equals(end))
                          || (distanceDef.start().equals(end) && distanceDef.end().equals(start)))
              .findFirst()
              .orElseThrow()
              .distance();
      result += distance;
    }

    return result;
  }

  private static <T> Set<List<T>> permutations(final Collection<T> items) {
    if (items.size() == 1) {
      return Set.of(items.stream().toList());
    }

    final Set<List<T>> permutations = new HashSet<>();
    for (final T item : items) {
      permutations.addAll(
          permutations(items.stream().filter(i -> !i.equals(item)).toList()).stream()
              .map(p -> concat(List.of(item), p))
              .toList());
    }

    return permutations;
  }

  record Path(List<String> locations, long totalDistance) {}

  record DistanceDef(String start, String end, long distance) {

    private static final Pattern PATTERN =
        Pattern.compile("(\\w+) to (\\w+) = (\\d+)", Pattern.CASE_INSENSITIVE);

    public static DistanceDef parse(final String input) {
      final Matcher matcher = PATTERN.matcher(input);
      if (!matcher.find()) {
        throw new IllegalArgumentException("Invalid input: " + input);
      }

      return new DistanceDef(matcher.group(1), matcher.group(2), Long.parseLong(matcher.group(3)));
    }
  }

  private static <T> List<T> concat(final Collection<T> left, final Collection<T> right) {
    return Stream.concat(left.stream(), right.stream()).toList();
  }
}
