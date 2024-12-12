package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;

public class Day10 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day10.txt");
    System.out.println("Part 1: " + sumOfTrailheadScores(input));
    System.out.println("Part 2: " + sumOfTrailheadRatings(input));
  }

  public static long sumOfTrailheadRatings(final String input) {
    final List<List<Tile>> data = evaluate(input);

    return data.stream()
        .flatMap(List::stream)
        .filter(n -> n.height == 0)
        .mapToLong(tile -> tile.reachableTrailheads().size())
        .sum();
  }

  public static long sumOfTrailheadScores(final String input) {
    final List<List<Tile>> data = evaluate(input);

    return data.stream()
        .flatMap(List::stream)
        .filter(n -> n.height == 0)
        .mapToLong(tile -> Set.copyOf(tile.reachableTrailheads()).size())
        .sum();
  }

  private static List<List<Tile>> evaluate(final String input) {
    final List<List<Tile>> data =
        input
            .lines()
            .map(
                s ->
                    s.chars()
                        .mapToObj(
                            c ->
                                new Tile(
                                    (char) c == '.'
                                        ? -1
                                        : Integer.parseInt(String.valueOf((char) c))))
                        .toList())
            .toList();

    for (int currentHeight = 9; currentHeight >= 0; currentHeight--) {
      for (int y = 0; y < data.size(); y++) {
        for (int x = 0; x < data.getFirst().size(); x++) {
          final Tile currentTile = data.get(y).get(x);
          if (currentTile.height() == currentHeight) {
            if (currentHeight == 9) {
              currentTile.setReachableTrailheads(List.of(new Position(y, x)));
              continue;
            }

            final List<Tile> neighbours = new ArrayList<>();
            if (y > 0) neighbours.add(data.get(y - 1).get(x));
            if (y < data.size() - 1) neighbours.add(data.get(y + 1).get(x));
            if (x > 0) neighbours.add(data.get(y).get(x - 1));
            if (x < data.size() - 1) neighbours.add(data.get(y).get(x + 1));

            final List<Position> reachableTrailheads =
                neighbours.stream()
                    .filter(n -> n.height() == currentTile.height() + 1)
                    .flatMap(n -> n.reachableTrailheads().stream())
                    .toList();
            currentTile.setReachableTrailheads(reachableTrailheads);
          }
        }
      }
    }
    return data;
  }

  static class Tile {
    private final int height;
    private List<Position> reachableTrailheads = Collections.emptyList();

    Tile(final int height) {
      this.height = height;
    }

    int height() {
      return height;
    }

    List<Position> reachableTrailheads() {
      return reachableTrailheads;
    }

    void setReachableTrailheads(final List<Position> reachableTrailheads) {
      this.reachableTrailheads = reachableTrailheads;
    }

    @Override
    public String toString() {
      return "Position{"
          + "height="
          + height
          + ", reachableTrailHeads="
          + reachableTrailheads
          + '}';
    }
  }

  record Position(int y, int x) {}
}
