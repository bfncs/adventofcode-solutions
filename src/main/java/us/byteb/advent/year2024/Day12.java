package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;

public class Day12 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day12.txt");
    System.out.println("Part 1: " + totalPriceOfFencing(findRegions(input)));
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

  public static long totalPriceOfFencing(final Set<Region> regions) {
    return regions.stream().mapToLong(Region::priceOfFencing).sum();
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
  }

  public record Position(int y, int x) {}

  static Position p(final int y, final int x) {
    return new Position(y, x);
  }
}
