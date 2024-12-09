package us.byteb.advent.year2024;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day08 {

  private static final char EMPTY_FIELD = '.';

  public static void main(String[] args) {
    final String input = readFileFromResources("year2024/day08.txt");
    System.out.println("Part 1: " + findUniqueAntinodeLocations(input).size());
    System.out.println("Part 2: " + findUniqueAntinodeLocations(input, true).size());
  }

  public static Set<Position> findUniqueAntinodeLocations(final String input) {
    return findUniqueAntinodeLocations(input, false);
  }

  public static Set<Position> findUniqueAntinodeLocations(final String input, boolean countRepeat) {
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
          if (countRepeat) {
            result.add(firstPos);
            result.add(secondPos);
          }
          final Set<Position> antinodes =
              findAntinodes(firstPos, secondPos, countRepeat, map.length, map[0].length);
          result.addAll(antinodes);
        }
      }
    }

    return result;
  }

  private static Set<Position> findAntinodes(
      final Position a, final Position b, boolean countRepeat, final int maxY, final int maxX) {
    final int dY = Math.abs(b.y() - a.y());
    final int dX = Math.abs(b.x() - a.x());

    final int adY, adX, bdY, bdX;
    if (a.y() <= b.y()) {
      if (a.x() <= b.x()) {
        adY = -dY;
        adX = -dX;
        bdY = dY;
        bdX = dX;
      } else {
        adY = -dY;
        adX = dX;
        bdY = dY;
        bdX = -dX;
      }
    } else {
      if (a.x() <= b.x()) {
        adY = dY;
        adX = -dX;
        bdY = -dY;
        bdX = dX;
      } else {
        adY = dY;
        adX = dX;
        bdY = -dY;
        bdX = -dX;
      }
    }
    final int maxPositions = countRepeat ? Integer.MAX_VALUE : 1;
    return Stream.concat(
            findPositions(a, adY, adX, maxPositions, maxY, maxX).stream(),
            findPositions(b, bdY, bdX, maxPositions, maxY, maxX).stream())
        .collect(Collectors.toSet());
  }

  private static Set<Position> findPositions(
      final Position start,
      final int dY,
      final int dX,
      final int maxPositions,
      final int maxY,
      final int maxX) {
    final Set<Position> result = new HashSet<>();
    Position pos = start;

    while (result.size() < maxPositions) {
      pos = new Position(pos.y() + dY, pos.x() + dX);
      if ((pos.y() < 0 || pos.y() >= maxY) || (pos.x() < 0 || pos.x() >= maxX)) {
        break;
      }
      result.add(pos);
    }

    return result;
  }

  public record Position(int y, int x) {}
}
