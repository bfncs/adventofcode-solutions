package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public class Day12 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day12.txt");
    final Set<Region> regions = findRegions(input);
    System.out.println("Part 1: " + totalPrice(regions, Region::priceOfFencing));
    System.out.println("Part 2: " + totalPrice(regions, Region::discountedPriceOfFencing));
  }

  public static Set<Region> findRegions(final String input) {
    final char[][] data = input.lines().map(String::toCharArray).toArray(char[][]::new);
    final Queue<Position> startCandidates = new LinkedList<>();
    final Set<Region> regions = new HashSet<>();
    for (int y = 0; y < data.length; y++) {
      for (int x = 0; x < data[0].length; x++) {
        startCandidates.add(new Position(y, x));
      }
    }

    while (!startCandidates.isEmpty()) {
      final Position start = startCandidates.remove();
      final char type = data[start.y][start.x];
      final Set<Position> unvisited = new HashSet<>();
      final Set<Position> plots = new HashSet<>();
      final Set<Position> visited = new HashSet<>();
      unvisited.add(new Position(start.y, start.x));

      while (!unvisited.isEmpty()) {
        final Position candidate = unvisited.stream().findFirst().orElseThrow();
        unvisited.remove(candidate);
        visited.add(candidate);

        if (data[candidate.y][candidate.x] == type) {
          plots.add(candidate);
          final Set<Position> unvisitedNeighbours =
              potentialNeighbours(candidate).stream()
                  .filter(
                      p ->
                          p.y() >= 0 && p.y() < data.length && p.x() >= 0 && p.x() < data[0].length)
                  .filter(p -> !visited.contains(p))
                  .collect(Collectors.toSet());
          unvisited.addAll(unvisitedNeighbours);
        }
      }

      startCandidates.removeAll(plots);
      regions.add(new Region(type, plots));
    }

    return regions;
  }

  private static Set<Position> potentialNeighbours(final Position candidate) {
    return Set.of(
        new Position(candidate.y - 1, candidate.x), new Position(candidate.y + 1, candidate.x),
        new Position(candidate.y, candidate.x - 1), new Position(candidate.y, candidate.x + 1));
  }

  public static long totalPrice(final Set<Region> regions, final ToLongFunction<Region> strategy) {
    return regions.stream().mapToLong(strategy).sum();
  }

  public record Region(char type, Set<Position> plots) {
    public Region(final char type, final Position... plots) {
      this(type, Arrays.stream(plots).collect(Collectors.toSet()));
    }

    long area() {
      return plots.size();
    }

    long perimeter() {
      return plots.stream()
          .mapToLong(
              plot -> potentialNeighbours(plot).stream().filter(p -> !plots.contains(p)).count())
          .sum();
    }

    long priceOfFencing() {
      return area() * perimeter();
    }

    long numberOfSides() {
      int minY = Integer.MAX_VALUE, minX = Integer.MAX_VALUE;
      int maxY = Integer.MIN_VALUE, maxX = Integer.MIN_VALUE;
      for (final Position plot : plots) {
        minY = Math.min(minY, plot.y());
        minX = Math.min(minX, plot.x());
        maxY = Math.max(maxY, plot.y());
        maxX = Math.max(maxX, plot.x());
      }

      long sides = 0;
      for (int y = minY; y <= maxY; y++) {
        int lastMatchingTopX = Integer.MIN_VALUE, lastMatchingBottomX = Integer.MIN_VALUE;
        for (int x = minX; x <= maxX; x++) {
          if (!plots.contains(new Position(y, x))) {
            continue;
          }
          if (!plots.contains(new Position(y - 1, x))) {
            if (lastMatchingTopX < x - 1) {
              sides++;
            }
            lastMatchingTopX = x;
          }
          if (!plots.contains(new Position(y + 1, x))) {
            if (lastMatchingBottomX < x - 1) {
              sides++;
            }
            lastMatchingBottomX = x;
          }
        }
      }

      for (int x = minX; x <= maxX; x++) {
        int lastMatchingLeftY = Integer.MIN_VALUE, lastMatchingRightY = Integer.MIN_VALUE;
        for (int y = minY; y <= maxY; y++) {
          if (!plots.contains(new Position(y, x))) {
            continue;
          }
          if (!plots.contains(new Position(y, x - 1))) {
            if (lastMatchingLeftY < y - 1) {
              sides++;
            }
            lastMatchingLeftY = y;
          }
          if (!plots.contains(new Position(y, x + 1))) {
            if (lastMatchingRightY < y - 1) {
              sides++;
            }
            lastMatchingRightY = y;
          }
        }
      }

      return sides;
    }

    long discountedPriceOfFencing() {
      return area() * numberOfSides();
    }
  }

  public record Position(int y, int x) {}

  static Position p(final int y, final int x) {
    return new Position(y, x);
  }
}
