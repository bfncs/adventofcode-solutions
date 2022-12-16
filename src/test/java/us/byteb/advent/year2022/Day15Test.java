package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2022.Day15.*;

import java.math.BigInteger;
import java.util.Set;
import org.junit.jupiter.api.Test;

class Day15Test {

  private static final String EXAMPLE_DATA = readFileFromResources("year2022/day15.txt");

  @Test
  void partOneExample() {
    assertEquals(26L, positionsWithoutBeacon(EXAMPLE_DATA, 10).size());
  }

  @Test
  void partTwoExample() {
    final Position result = findBeacon(EXAMPLE_DATA, 20, 20);
    assertEquals(new Position(14, 11), result);
    assertEquals(BigInteger.valueOf(56000011), result.tuningFrequency());
  }

  @Test
  void rangesCovered() {
    final SensorReading reading = new SensorReading(new Position(10, 10), new Position(8, 10));
    assertEquals(
        Set.of(
            new RangeX(10, 11, 8),
            new RangeX(9, 12, 9),
            new RangeX(8, 13, 10),
            new RangeX(9, 12, 11),
            new RangeX(10, 11, 12)),
        reading.rangesCovered());
  }
}
