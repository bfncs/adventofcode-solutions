package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;

public class Day04 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day04.txt");

    System.out.println("Part 1: " + searchWord(input, "XMAS").size());
    System.out.println("Part 2: " + searchXShapeWord(input, "MAS").size());
  }

  public static Set<Occurrence> searchWord(final String input, final String needle) {
    final char[][] data = input.lines().map(String::toCharArray).toArray(char[][]::new);
    final Set<Occurrence> result = new HashSet<>();

    record Direction(int dY, int dX) {
      static final Set<Direction> ALL =
          Set.of(
              new Direction(1, 0), // up
              new Direction(-1, 0), // down
              new Direction(0, 1), // right
              new Direction(0, -1), // left
              new Direction(1, 1), // up right
              new Direction(-1, 1), // down right
              new Direction(-1, -1), // down left
              new Direction(1, -1) // up left
              );
    }

    for (int y = 0; y < data.length; y++) {
      for (int x = 0; x < data[y].length; x++) {
        for (Direction direction : Direction.ALL) {
          final Optional<Occurrence> positions =
              checkWord(data, y, x, direction.dY(), direction.dX(), needle.toCharArray());
          positions.ifPresent(result::add);
        }
      }
    }

    return result;
  }

  public static Set<Set<Position>> searchXShapeWord(final String input, final String needle) {
    final int centerPos = (needle.length() / 2);
    final Set<Occurrence> candidates = searchWord(input, needle);

    final Map<Position, List<Occurrence>> diagonalsGroupedByCenterPosition =
        candidates.stream()
            .filter(Occurrence::isDiagonal)
            .collect(Collectors.groupingBy(o -> o.positions().get(centerPos)));

    return diagonalsGroupedByCenterPosition.values().stream()
        .filter(occurrences -> occurrences.size() > 1)
        .map(
            occurrences ->
                occurrences.stream()
                    .flatMap(o -> o.positions().stream())
                    .collect(Collectors.toSet()))
        .collect(Collectors.toSet());
  }

  private static Optional<Occurrence> checkWord(
      final char[][] data, int posY, int posX, int dY, int dX, final char[] searchedWord) {
    final List<Position> positions = new ArrayList<>();
    for (int i = 0; i < searchedWord.length; i++) {
      int checkY = posY + (i * dY);
      int checkX = posX + (i * dX);
      if (checkY < 0
          || checkY >= data.length
          || checkX < 0
          || checkX >= data[0].length
          || data[checkY][checkX] != searchedWord[i]) {
        return Optional.empty();
      }
      positions.add(new Position(checkY, checkX));
    }

    return Optional.of(new Occurrence(positions, dY, dX));
  }

  public record Occurrence(List<Position> positions, int dY, int dX) {
    boolean isDiagonal() {
      return dY != 0 && dX != 0;
    }
  }

  public record Position(int y, int x) {}
}
