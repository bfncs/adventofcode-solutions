package us.byteb.advent.year2022;

import static java.lang.Integer.parseInt;
import static us.byteb.advent.Utils.readFileFromResources;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 {
  public static void main(final String[] args) {
    final String input = readFileFromResources("year2022/day15.txt");

    System.out.println("Part 1: " + positionsWithoutBeacon(input, 2000000).size());
  }

  public static Set<Position> positionsWithoutBeacon(final String input, final int line) {
    final List<SensorReading> readings = parse(input);

    return readings.stream()
        .flatMap(reading -> reading.positionsWithoutBeacon(line).stream())
        .collect(Collectors.toSet());
  }

  public static List<SensorReading> parse(final String input) {
    return input.lines().map(SensorReading::parse).toList();
  }

  record SensorReading(Position position, Position closestBeacon) {
    private static final Pattern PATTERN =
        Pattern.compile(
            "^Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)$");

    private static SensorReading parse(final String line) {
      final Matcher matcher = PATTERN.matcher(line);
      if (!matcher.matches()) throw new IllegalStateException("Illegal input: " + line);
      return new SensorReading(
          new Position(parseInt(matcher.group(1)), parseInt(matcher.group(2))),
          new Position(parseInt(matcher.group(3)), parseInt(matcher.group(4))));
    }

    public Set<Position> positionsWithoutBeacon(final int y) {
      final int distance = position.distance(closestBeacon);
      final int minX = position.x() - distance;
      final int maxX = position.x() + distance;

      final Set<Position> result = new HashSet<>();
      for (int x = minX; x < maxX; x++) {
        final Position currentPosition = new Position(x, y);
        if (!currentPosition.equals(closestBeacon)
            && position.distance(currentPosition) <= distance) {
          result.add(currentPosition);
        }
      }

      return result;
    }
  }

  record Position(int x, int y) {
    public int distance(final Position other) {
      return Math.abs(x - other.x()) + Math.abs(y - other.y());
    }
  }
}
