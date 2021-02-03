package us.byteb.advent.year2019;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import us.byteb.advent.Utils;

public class Day03 {

  public static void main(String[] args) {
    final List<String> input =
        Utils.readFileFromResources("year2019/day03.txt").lines().collect(Collectors.toList());
    final WirePath path1 = WirePath.of(input.get(0));
    final WirePath path2 = WirePath.of(input.get(1));

    System.out.println(
        "Part 1: "
            + Point.shortestManhattanDistanceToCenter(WirePath.intersectionPoints(path1, path2))
                .manhattanDistanceToCenter());

    System.out.println("Part 2: " + WirePath.fewestStepsToIntersection(path1, path2));
  }

  static String print(final WirePath path1, final WirePath path2) {
    final Set<Point> allPoints =
        Stream.concat(path1.points().stream(), path2.points.stream()).collect(Collectors.toSet());
    final int xMin = allPoints.stream().mapToInt(Point::x).min().orElseThrow();
    final int xMax = allPoints.stream().mapToInt(Point::x).max().orElseThrow();
    final int yMin = allPoints.stream().mapToInt(Point::y).min().orElseThrow();
    final int yMax = allPoints.stream().mapToInt(Point::y).max().orElseThrow();

    final StringBuilder sb = new StringBuilder();
    for (int y = yMax + 1; y >= yMin - 1; y--) {
      for (int x = xMin - 1; x <= xMax + 1; x++) {

        final Point currentPoint = new Point(x, y);
        final boolean inPath1 = path1.points().contains(currentPoint);
        final boolean inPath2 = path2.points().contains(currentPoint);

        if (x == 0 && y == 0) {
          sb.append('0');
        } else if (inPath1 && inPath2) {
          sb.append('X');
        } else if (inPath1) {
          sb.append('1');
        } else if (inPath2) {
          sb.append('2');
        } else {
          sb.append('.');
        }

        if (x > xMax) {
          sb.append("\n");
        }
      }
    }

    return sb.toString();
  }

  record Point(int x, int y) {
    long manhattanDistanceToCenter() {
      return Math.abs(x) + Math.abs(y);
    }

    static Point shortestManhattanDistanceToCenter(final Collection<Point> points) {
      return points.stream()
          .collect(Collectors.toMap(p -> p, Point::manhattanDistanceToCenter))
          .entrySet()
          .stream()
          .sorted((e1, e2) -> Math.toIntExact((e1.getValue() - e2.getValue())))
          .findFirst()
          .map(Map.Entry::getKey)
          .orElseThrow();
    }
  }

  record WirePath(List<Point> points) {
    static WirePath of(final String input) {

      final List<Point> points = new ArrayList<>();
      Point currentPoint = new Point(0, 0);

      for (final String instruction : input.split(",")) {
        final char op = instruction.charAt(0);
        final int amount = Integer.parseInt(instruction.substring(1));
        for (int i = 0; i < amount; i++) {
          currentPoint =
              switch (op) {
                case 'U':
                  yield new Point(currentPoint.x(), currentPoint.y() + 1);
                case 'D':
                  yield new Point(currentPoint.x(), currentPoint.y() - 1);
                case 'R':
                  yield new Point(currentPoint.x() + 1, currentPoint.y());
                case 'L':
                  yield new Point(currentPoint.x() - 1, currentPoint.y());
                default:
                  throw new IllegalStateException("Illegal op: " + op);
              };
          points.add(currentPoint);
        }
      }

      return new WirePath(points);
    }

    static Set<Point> intersectionPoints(final WirePath path1, final WirePath path2) {
      return Stream.concat(path1.points().stream().distinct(), path2.points().stream().distinct())
          .collect(Collectors.groupingBy(p -> p))
          .entrySet()
          .stream()
          .filter(entry -> entry.getValue().size() > 1)
          .map(Map.Entry::getKey)
          .collect(Collectors.toSet());
    }

    static long fewestStepsToIntersection(final WirePath path1, final WirePath path2) {
      return intersectionPoints(path1, path2).stream()
          .mapToLong(
              point -> path1.shortestNumberOfStepsTo(point) + path2.shortestNumberOfStepsTo(point))
          .min()
          .getAsLong();
    }

    public long shortestNumberOfStepsTo(final Point point) {
      return points.indexOf(point) + 1;
    }
  }
}
