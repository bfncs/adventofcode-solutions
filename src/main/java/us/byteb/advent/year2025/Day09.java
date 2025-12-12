package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day09 {

  private static final int PADDING = 1;

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day09.txt");
    final List<Point> points = parse(input);
    System.out.println("Part 1: " + largestArea(points));
    System.out.println("Part 2: " + largestAreaRedGreen(points));
  }

  static List<Point> parse(final String input) {
    return input
        .lines()
        .map(
            line -> {
              final String[] parts = line.split(",");
              return new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
            })
        .toList();
  }

  static long largestArea(final List<Point> points) {
    long largest = 0L;
    for (int i = 0; i < points.size(); i++) {
      final Point candidate = points.get(i);
      for (int j = i + 1; j < points.size(); j++) {
        final long area = area(candidate, points.get(j));
        if (area > largest) {
          largest = area;
        }
      }
    }

    return largest;
  }

  static long largestAreaRedGreen(final List<Point> points) {
    final List<Point> compressed = compress(points);
    final boolean[][] grid = areaBoundary(compressed).grid();
    applyPath(compressed, grid);
    fillPath(grid);

    long largest = 0L;
    for (int i = 0; i < points.size(); i++) {
      for (int j = i + 1; j < points.size(); j++) {
        if (isOnRedGreenTiles(compressed.get(i), compressed.get(j), grid)) {
          final long area = area(points.get(i), points.get(j));
          if (area > largest) {
            largest = area;
          }
        }
      }
    }

    return largest;
  }

  private static boolean isOnRedGreenTiles(final Point a, final Point b, final boolean[][] grid) {
    final AreaBoundary boundary = areaBoundary(List.of(a, b));
    for (int y = boundary.minY(); y <= boundary.maxY(); y++) {
      for (int x = boundary.minX(); x <= boundary.maxX(); x++) {
        if (!grid[y][x]) {
          return false;
        }
      }
    }
    return true;
  }

  private static void fillPath(final boolean[][] grid) {
    final int height = grid.length;
    final int width = grid[0].length;
    int[] dy = {-1, 1, 0, 0};
    int[] dx = {0, 0, -1, 1};

    boolean[][] flooded = new boolean[height][width];
    flooded[0][0] = true;
    final Queue<Point> queue = new LinkedList<>(Set.of(new Point(0, 0)));

    while (!queue.isEmpty()) {
      final Point p = queue.remove();
      for (int i = 0; i < dy.length; i++) {
        final Point neighbour = new Point(p.y() + dy[i], p.x() + dx[i]);
        if (neighbour.y() >= 0
            && neighbour.y() < height
            && neighbour.x() >= 0
            && neighbour.x() < width
            && !flooded[neighbour.y()][neighbour.x()]
            && !grid[neighbour.y()][neighbour.x()]) {
          flooded[neighbour.y()][neighbour.x()] = true;
          queue.add(neighbour);
        }
      }
    }

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (!flooded[y][x]) {
          grid[y][x] = true;
        }
      }
    }
  }

  private static void applyPath(final List<Point> points, final boolean[][] grid) {
    for (int i = 0; i < points.size(); i++) {
      final Point from = points.get(i);
      final Point to = points.get((i + 1) % points.size());
      if (from.x() == to.x()) {
        for (int y = Math.min(from.y(), to.y()); y <= Math.max(from.y(), to.y()); y++) {
          grid[y][from.x()] = true;
        }
      } else if (from.y() == to.y()) {
        for (int x = Math.min(from.x(), to.x()); x <= Math.max(from.x(), to.x()); x++) {
          grid[from.y()][x] = true;
        }
      } else {
        throw new IllegalStateException();
      }
    }
  }

  private static List<Point> compress(final List<Point> points) {
    final List<Integer> byX = points.stream().map(Point::x).distinct().sorted().toList();
    final List<Integer> byY = points.stream().map(Point::y).distinct().sorted().toList();
    return points.stream()
        .map(
            p -> {
              final int y = PADDING + (byY.indexOf(p.y()) * 2);
              final int x = PADDING + (byX.indexOf(p.x()) * 2);
              return new Point(y, x);
            })
        .toList();
  }

  private static AreaBoundary areaBoundary(final List<Point> points) {
    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;
    for (final Point point : points) {
      if (point.x < minX) {
        minX = point.x;
      }
      if (point.x > maxX) {
        maxX = point.x;
      }
      if (point.y < minY) {
        minY = point.y;
      }
      if (point.y > maxY) {
        maxY = point.y;
      }
    }
    return new AreaBoundary(minY, minX, maxY, maxX);
  }

  private static long area(final Point a, final Point b) {
    final long width = Math.abs(a.x - b.x) + 1;
    final long height = Math.abs(a.y - b.y) + 1;
    return width * height;
  }

  record Point(int y, int x) {}

  record AreaBoundary(int minY, int minX, int maxY, int maxX) {
    boolean[][] grid() {
      return new boolean[maxY - minY + 1 + (2 * PADDING)][maxX - minX + 1 + 2 * (2 * PADDING)];
    }
  }
}
