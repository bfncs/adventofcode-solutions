package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day05 {

  public static void main(String[] args) throws IOException {
    final List<Line> input = parseInput(readFileFromResources("year2021/day05.txt"));

    System.out.println(
        "Part 1: " + numberOfPointsWhereAtLeastTwoHorizontalAndVerticalLinesOverlap(input));

    System.out.println("Part 2: " + numberOfPointsWhereAtLeastTwoLinesOverlap(input));
  }

  public static List<Line> parseInput(final String input) {
    return input.lines().map(Line::parse).toList();
  }

  public static long numberOfPointsWhereAtLeastTwoHorizontalAndVerticalLinesOverlap(
      final List<Line> lines) {
    final List<Line> horizontalOrVerticalLines =
        lines.stream().filter(line -> line.isHorizontal() || line.isVertical()).toList();
    final Field field = Field.fromLines(horizontalOrVerticalLines);

    return field.numberOfPointsWithMinLines(2);
  }

  public static long numberOfPointsWhereAtLeastTwoLinesOverlap(final List<Line> lines) {
    final Field field = Field.fromLines(lines);
    return field.numberOfPointsWithMinLines(2);
  }

  record Point(int x, int y) {
    public static Point parse(final String input) {
      final String[] coords = input.split(",");
      if (coords.length != 2) {
        throw new IllegalStateException("Unable to parse point: " + input);
      }

      return new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
    }
  }

  record Line(Point start, Point end) {
    public static Line parse(final String input) {
      final String[] points = input.split("\s+->\s+");
      if (points.length != 2) {
        throw new IllegalStateException("Unable to parse line: " + input);
      }

      return new Line(Point.parse(points[0]), Point.parse(points[1]));
    }

    boolean isHorizontal() {
      return start.x() == end.x();
    }

    boolean isVertical() {
      return start.y() == end.y();
    }

    boolean isDiagonal45Degrees() {
      return Math.abs(start.x() - end.x()) == Math.abs(start.y() - end.y());
    }

    List<Point> resolvePoints() {
      if (isHorizontal()) {
        final int lower = Math.min(start.y(), end.y());
        final int upper = Math.max(start.y(), end.y());
        return IntStream.rangeClosed(lower, upper).mapToObj(y -> new Point(start.x(), y)).toList();
      }

      if (isVertical()) {
        final int lower = Math.min(start.x(), end.x());
        final int upper = Math.max(start.x(), end.x());
        return IntStream.rangeClosed(lower, upper).mapToObj(x -> new Point(x, start.y())).toList();
      }

      if (isDiagonal45Degrees()) {
        final int firstY = Math.min(start.y(), end.y());
        final int secondY = Math.max(start.y(), end.y());
        final int firstX = firstY == start.y() ? start.x() : end.x();
        final int secondX = firstY == start.y() ? end.x() : start.x();
        final int stepX = firstX < secondX ? 1 : -1;

        final List<Point> points = new ArrayList<>();
        int x = firstX;
        for (int y = firstY; y <= secondY; y++) {
          points.add(new Point(x, y));
          x += stepX;
        }

        return points;
      }

      throw new UnsupportedOperationException(
          "Resolving points of line from %s to %s not implemented yet!".formatted(start, end));
    }
  }

  static class Field {
    private final Map<Point, Integer> linesPerPoint;

    public static Field fromLines(List<Line> lines) {
      final Map<Point, Integer> data = new HashMap<>();

      for (final Line line : lines) {
        final List<Point> points = line.resolvePoints();
        for (final Point point : points) {
          data.put(point, data.getOrDefault(point, 0) + 1);
        }
      }

      return new Field(data);
    }

    private Field(final Map<Point, Integer> linesPerPoint) {
      this.linesPerPoint = linesPerPoint;
    }

    public long numberOfPointsWithMinLines(final int minLinesInclusive) {
      return linesPerPoint.values().stream().filter(n -> n >= minLinesInclusive).count();
    }
  }
}
