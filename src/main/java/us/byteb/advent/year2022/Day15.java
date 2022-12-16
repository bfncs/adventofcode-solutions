package us.byteb.advent.year2022;

import static java.lang.Integer.parseInt;
import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 {
  public static void main(final String[] args) {
    final String input = readFileFromResources("year2022/day15.txt");

    System.out.println("Part 1: " + positionsWithoutBeacon(input, 2000000).size());
    System.out.println("Part 1: " + findBeacon(input, 4000000, 4000000).tuningFrequency());
  }

  public static Set<Position> positionsWithoutBeacon(final String input, final int line) {
    final List<SensorReading> readings = parse(input);

    return readings.stream()
        .flatMap(reading -> reading.positionsWithoutBeacon(line).stream())
        .collect(Collectors.toSet());
  }

  public static Position findBeacon(final String input, final int maxX, final int maxY) {
    final List<SensorReading> readings = parse(input);

    final Map<Integer, List<RangeX>> rangesByY =
        readings.stream()
            .flatMap(reading -> reading.rangesCovered().stream())
            .collect(Collectors.groupingBy(RangeX::y));

    final Set<Position> beaconPositions =
        rangesByY.entrySet().parallelStream()
            .filter(entry -> entry.getKey() > 0 && entry.getKey() <= maxY)
            .flatMap(entry -> findGaps(entry.getKey(), entry.getValue(), maxX).stream())
            .collect(Collectors.toSet());

    if (beaconPositions.size() != 1)
      throw new IllegalStateException("No unique beacon position found");

    return beaconPositions.stream().findFirst().orElseThrow();
  }

  private static Collection<Position> findGaps(
      final int y, final Collection<RangeX> ranges, final int maxX) {
    final List<RangeX> compacted = RangeX.compact(ranges);

    final Set<Position> gaps = new HashSet<>();
    for (int x = 0; x < compacted.get(0).xMinInclusive(); x++) {
      gaps.add(new Position(x, y));
    }
    for (int i = 0; i < compacted.size() - 1; i++) {
      for (int x = compacted.get(i).xMaxExclusive();
          x < compacted.get(i + 1).xMinInclusive();
          x++) {
        gaps.add(new Position(x, y));
      }
    }
    for (int x = compacted.get(compacted.size() - 1).xMaxExclusive(); x <= maxX; x++) {
      gaps.add(new Position(x, y));
    }

    return gaps;
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

    public Set<RangeX> rangesCovered() {
      final int distance = position.distance(closestBeacon);
      final int minY = position.y() - distance;
      final int maxY = position.y() + distance;

      final Set<RangeX> result = new HashSet<>();
      for (int y = minY; y <= maxY; y++) {
        final int range = Math.abs(position.y() - minY) - Math.abs(position.y() - y);
        result.add(new RangeX(position.x() - range, position.x() + range + 1, y));
      }

      return result;
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

    public BigInteger tuningFrequency() {
      return BigInteger.valueOf(x).multiply(BigInteger.valueOf(4000000)).add(BigInteger.valueOf(y));
    }
  }

  record RangeX(int xMinInclusive, int xMaxExclusive, int y) {
    private static List<RangeX> compact(final Collection<RangeX> ranges) {
      final List<RangeX> sorted =
          ranges.stream().sorted(Comparator.comparing(RangeX::xMinInclusive)).toList();

      final List<RangeX> compacted = new ArrayList<>();
      for (final RangeX range : sorted) {
        if (compacted.isEmpty()) {
          compacted.add(range);
          continue;
        }

        final RangeX lastInserted = compacted.get(compacted.size() - 1);
        if (lastInserted.xMaxExclusive() >= range.xMinInclusive()) {
          compacted.remove(lastInserted);
          compacted.add(
              new RangeX(
                  lastInserted.xMinInclusive(),
                  Math.max(lastInserted.xMaxExclusive(), range.xMaxExclusive()),
                  range.y()));
        } else {
          compacted.add(range);
        }
      }

      return compacted;
    }
  }
}
