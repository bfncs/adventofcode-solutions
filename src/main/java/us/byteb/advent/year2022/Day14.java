package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.byteb.advent.year2022.Day14.SimResult.FallingThrough;
import us.byteb.advent.year2022.Day14.SimResult.Resting;

public class Day14 {

  public static final Point SOURCE = new Point(500, 0);

  public static void main(final String[] args) {
    final List<Path> input = parse(readFileFromResources("year2022/day14.txt"));

    System.out.println("Part 1: " + findUnitOfSandsBeforeFallthrough(input));
    System.out.println("Part 2: " + findUnitOfSandsBeforeSourceBlocked(input));
  }

  public static List<Path> parse(final String input) {
    return input.lines().map(Path::parse).toList();
  }

  public static long findUnitOfSandsBeforeFallthrough(final List<Path> paths) {
    final Set<Point> blocked =
        paths.stream()
            .flatMap(path -> path.findAllCoveredPoints().stream())
            .collect(Collectors.toSet());

    long numResting = 0;

    while (simulateFallingSand(blocked, false, Integer.MAX_VALUE) instanceof Resting resting) {
      blocked.add(resting.position());
      numResting++;
    }

    return numResting;
  }

  public static long findUnitOfSandsBeforeSourceBlocked(final List<Path> paths) {
    final Set<Point> blocked =
        paths.stream()
            .flatMap(path -> path.findAllCoveredPoints().stream())
            .collect(Collectors.toSet());
    final int floorY = blocked.stream().mapToInt(Point::y).max().orElseThrow() + 1;

    long numResting = 0;

    while (simulateFallingSand(blocked, true, floorY) instanceof Resting resting
        && !resting.position().equals(SOURCE)) {
      blocked.add(resting.position());
      numResting++;
    }

    return numResting + 1;
  }

  sealed interface SimResult {
    record FallingThrough() implements SimResult {}

    record Resting(Point position) implements SimResult {}
  }

  private static SimResult simulateFallingSand(
      final Set<Point> blocked, final boolean assumeFloor, final int floorY) {
    final int maxBlockedY = blocked.stream().mapToInt(Point::y).max().orElseThrow();
    final int maxY = assumeFloor ? floorY - 1 : maxBlockedY;

    Point sand = SOURCE;

    while (sand.y() <= maxY) {

      final Point below = new Point(sand.x(), sand.y() + 1);
      if (!blocked.contains(below)) {
        sand = below;
        continue;
      }

      final Point belowLeft = new Point(sand.x() - 1, sand.y() + 1);
      if (!blocked.contains(belowLeft)) {
        sand = belowLeft;
        continue;
      }

      final Point belowRight = new Point(sand.x() + 1, sand.y() + 1);
      if (!blocked.contains(belowRight)) {
        sand = belowRight;
        continue;
      }

      return new Resting(sand);
    }

    return assumeFloor ? new Resting(new Point(sand.x(), floorY)) : new FallingThrough();
  }

  record Path(List<Point> points) {
    public static Path parse(final String line) {
      final String[] parts = line.split("\\s+->\\s+");
      final List<Point> points = Arrays.stream(parts).map(Point::parse).toList();
      return new Path(points);
    }

    public Set<Point> findAllCoveredPoints() {
      final Set<Point> result = new HashSet<>();

      for (int i = 0; i < points.size() - 1; i++) {
        final Point start = points.get(i);
        final Point end = points.get(i + 1);
        if (start.x() == end.x()) {
          result.addAll(
              IntStream.range(Math.min(start.y(), end.y()), Math.max(start.y(), end.y()) + 1)
                  .mapToObj(y -> new Point(start.x(), y))
                  .collect(Collectors.toSet()));
        } else if (start.y() == end.y()) {
          result.addAll(
              IntStream.range(Math.min(start.x(), end.x()), Math.max(start.x(), end.x()) + 1)
                  .mapToObj(x -> new Point(x, start.y()))
                  .collect(Collectors.toSet()));
        } else {
          throw new IllegalStateException(
              "No straight line possible between %s and %s".formatted(start, end));
        }
      }

      return result;
    }
  }

  record Point(int x, int y) {
    public static Point parse(final String input) {
      final String[] parts = input.split(",");
      if (parts.length != 2) throw new IllegalStateException("Illegal point: " + input);
      return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
  }
}
