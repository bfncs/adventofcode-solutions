package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Day15 {

  public static void main(String[] args) throws IOException {
    final List<List<Integer>> input = parseInput(readFileFromResources("year2021/day15.txt"));

    System.out.println("Part 1: " + findPathWithLowestTotalRisk(input).totalRisk());

    // This is not correct :-/
    System.out.println("Part 2: " + findPathWithLowestTotalRisk(repeatGrid(input, 5)).totalRisk());
  }

  static Path findPathWithLowestTotalRisk(final List<List<Integer>> grid) {
    Set<Path> paths = createInitialPathFromLowerRightPoint(grid);

    do {
      final Set<Path> nextPaths = new HashSet<>();
      for (final Path path : paths) {
        final Point head = path.points().get(0);
        if (head.row() > 0) {
          final Point pointLeft = new Point(head.row() - 1, head.col());
          addIfCheapestPathCandidate(nextPaths, prependPath(grid, pointLeft, path));
        }
        if (head.col() > 0) {
          final Point pointTop = new Point(head.row(), head.col() - 1);
          addIfCheapestPathCandidate(nextPaths, prependPath(grid, pointTop, path));
        }
      }
      paths = nextPaths;
    } while (paths.size() > 1);

    final Path shortestPath = paths.stream().findFirst().orElseThrow();
    return removeFirstPoint(grid, shortestPath);
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

  private static Set<Path> createInitialPathFromLowerRightPoint(final List<List<Integer>> grid) {
    Set<Path> paths = new HashSet<>();
    paths.add(
        new Path(
            List.of(new Point(grid.size() - 1, grid.size() - 1)),
            grid.get(grid.size() - 1).get(grid.size() - 1)));
    return paths;
  }

  private static Path removeFirstPoint(final List<List<Integer>> grid, final Path path) {
    final Point firstPoint = path.points().get(0);
    return new Path(
        path.points().subList(1, path.points().size()),
        path.totalRisk() - grid.get(firstPoint.row()).get(firstPoint.col()));
  }

  private static void addIfCheapestPathCandidate(final Set<Path> paths, final Path candidate) {
    final Point candidateHead = candidate.points().get(0);
    final Optional<Path> existingPath =
        paths.stream()
            .filter(
                p -> {
                  final Point currentHead = p.points().get(0);
                  return currentHead.row() == candidateHead.row()
                      && currentHead.col() == candidateHead.col();
                })
            .findAny();

    if (existingPath.isEmpty()) {
      paths.add(candidate);
      return;
    }

    if (candidate.totalRisk() < existingPath.get().totalRisk()) {
      paths.remove(existingPath.get());
      paths.add(candidate);
    }
  }

  private static Path prependPath(
      final List<List<Integer>> grid, final Point pointToBePrepended, final Path existingPath) {
    final List<Point> points =
        Stream.concat(Stream.of(pointToBePrepended), existingPath.points().stream()).toList();
    final long totalRisk =
        existingPath.totalRisk() + grid.get(pointToBePrepended.row()).get(pointToBePrepended.col());

    return new Path(points, totalRisk);
  }

  static List<List<Integer>> parseInput(final String input) {
    return input
        .lines()
        .map(line -> line.chars().mapToObj(Character::getNumericValue).toList())
        .toList();
  }

  static String formatPath(final List<List<Integer>> grid, final Path path) {
    final StringBuilder sb = new StringBuilder();
    for (int row = 0; row < grid.size(); row++) {
      for (int col = 0; col < grid.size(); col++) {
        final String value =
            (row == 0 && col == 0) || path.points().contains(new Point(row, col))
                ? "X"
                : grid.get(row).get(col).toString();
        sb.append(value);
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  record Point(int row, int col) {
    @Override
    public String toString() {
      return "{" + row + ", " + col + "}";
    }
  }

  record Path(List<Point> points, long totalRisk) {}
}
