package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;

public class Day08 {

  private static final char EMPTY_FIELD = '.';

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day08.txt");
    System.out.println("Part 1: " + findUniqueAntinodeLocations(input).size());
  }

  public static Set<Position> findUniqueAntinodeLocations(final String input) {
    final char[][] map = input.lines().map(String::toCharArray).toArray(char[][]::new);

    final Map<Character, List<Position>> frequenciesByPosition = new HashMap<>();
    for (int y = 0; y < map.length; y++) {
      for (int x = 0; x < map[y].length; x++) {
        final char currentFrequency = map[y][x];
        if (currentFrequency == EMPTY_FIELD) {
          continue;
        }
        frequenciesByPosition.putIfAbsent(currentFrequency, new ArrayList<>());
        frequenciesByPosition.get(currentFrequency).add(new Position(y, x));
      }
    }

    final Set<Position> result = new HashSet<>();

    for (final List<Position> positions : frequenciesByPosition.values()) {
      if (positions.size() <= 1) {
        continue;
      }
      for (int i = 0; i < positions.size(); i++) {
        for (int j = i + 1; j < positions.size(); j++) {
          final Position firstPos = positions.get(i);
          final Position secondPos = positions.get(j);
          final Set<Position> antinodes = findAntinodes(firstPos, secondPos);
          result.addAll(antinodes);
        }
      }
    }

    return result.stream()
        .filter(
            pos -> pos.y() >= 0 && pos.y() < map.length && pos.x() >= 0 && pos.x() < map[0].length)
        .collect(Collectors.toSet());
  }

  private static Set<Position> findAntinodes(final Position a, final Position b) {
    final int dY = Math.abs(b.y() - a.y());
    final int dX = Math.abs(b.x() - a.x());

    if (a.y() <= b.y()) {
      if (a.x() <= b.x()) {
        return Set.of(new Position(a.y() - dY, a.x() - dX), new Position(b.y() + dY, b.x() + dX));
      } else {
        return Set.of(new Position(a.y() - dY, a.x() + dX), new Position(b.y() + dY, b.x() - dX));
      }
    } else {
      if (a.x() <= b.x()) {
        return Set.of(new Position(a.y() + dY, a.x() - dX), new Position(b.y() - dY, b.x() + dX));
      } else {
        return Set.of(new Position(a.y() + dY, a.x() + dX), new Position(b.y() - dY, b.x() - dX));
      }
    }
  }

  public record Position(int y, int x) {}
}
