package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Day15 {

  public static void main(String[] args) throws IOException {
    final List<List<Integer>> input = parseInput(readFileFromResources("year2021/day15.txt"));

    System.out.println("Part 1: " + findPathWithLowestTotalRisk(input).totalRisk());
    System.out.println("Part 2: " + findPathWithLowestTotalRisk(repeatGrid(input, 5)).totalRisk());
  }

  static Path findPathWithLowestTotalRisk(final List<List<Integer>> grid) {
    final Point start = new Point(0, 0);
    final Point destination = new Point(grid.size() - 1, grid.size() - 1);

    final Map<Point, Path> visitedPoints = new HashMap<>();
    final Map<Point, Path> unvisitedPoints = new HashMap<>();
    unvisitedPoints.put(start, new Path(List.of(start), 0));

    while (!visitedPoints.containsKey(destination)) {
      final Point currentPoint =
          getPointWithLowestRisk(unvisitedPoints);
      final Path currentPath = unvisitedPoints.get(currentPoint);
      final Set<Point> neighbours = currentPoint.neighbours();

      for (final Point point : neighbours) {
        if (visitedPoints.containsKey(point)
            || point.row() < 0
            || point.row() > grid.size() - 1
            || point.col() < 0
            || point.col() > grid.size() - 1) {
          continue;
        }

        final long currentRisk;
        final Path unvisitedPath = unvisitedPoints.get(point);
        if (unvisitedPath == null) {
          currentRisk = Long.MAX_VALUE;
        } else {
          currentRisk = unvisitedPath.totalRisk();
        }

        final int pointRisk = grid.get(point.row()).get(point.col());
        final long totalRiskThroughCurrentPoint = pointRisk + currentPath.totalRisk();

        if (totalRiskThroughCurrentPoint < currentRisk) {
          unvisitedPoints.put(
              point,
              new Path(
                  Stream.concat(currentPath.points().stream(), Stream.of(point)).toList(),
                  totalRiskThroughCurrentPoint));
        }
      }

      visitedPoints.put(currentPoint, currentPath);
      unvisitedPoints.remove(currentPoint);
    }

    return visitedPoints.get(destination);
  }

  static List<List<Integer>> repeatGrid(final List<List<Integer>> grid, final int repeatFactor) {
    final int edgeLength = grid.size();
    final int nextEdgeLength = edgeLength * repeatFactor;
    final List<List<Integer>> nextGrid = new ArrayList<>(nextEdgeLength);

    for (int row = 0; row < nextEdgeLength; row++) {
      final List<Integer> currentRow = new ArrayList<>(nextEdgeLength);
      for (int col = 0; col < nextEdgeLength; col++) {
        final int gridValue = grid.get(row % grid.size()).get(col % grid.size());
        final int shiftValue = (row / edgeLength) + (col / edgeLength);
        final int nextValue = (gridValue + shiftValue) % 10 + ((gridValue + shiftValue) / 10);
        currentRow.add(nextValue);
      }

      nextGrid.add(currentRow);
    }

    return nextGrid;
  }

  static List<List<Integer>> parseInput(final String input) {
    return input
        .lines()
        .map(line -> line.chars().mapToObj(Character::getNumericValue).toList())
        .toList();
  }

  private static Point getPointWithLowestRisk(final Map<Point, Path> points) {
    return points.entrySet().stream()
        .min(Comparator.comparing(entry -> entry.getValue().totalRisk()))
        .map(Map.Entry::getKey)
        .orElseThrow();
  }

  record Point(int row, int col) {
    public Set<Point> neighbours() {
      final Set<Point> neighbours = new HashSet<>();
      neighbours.add(new Point(row - 1, col));
      neighbours.add(new Point(row + 1, col));
      neighbours.add(new Point(row, col - 1));
      neighbours.add(new Point(row, col + 1));

      return neighbours;
    }

    @Override
    public String toString() {
      return "{" + row + ", " + col + "}";
    }
  }

  record Path(List<Point> points, long totalRisk) {}
}
