package us.byteb.advent.y19;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import us.byteb.advent.y19.Day3.WirePath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.y19.Day3.Point;

class Day3Test {

  @Test
  void testDistance() {
    assertEquals(159, new Point(155, 4).manhattanDistanceToCenter());
  }

  @ParameterizedTest
  @CsvSource({
    "'R8,U5,L5,D3','U7,R6,D4,L4',6",
    "'R75,D30,R83,U83,L12,D49,R71,U7,L72','U62,R66,U55,R34,D71,R55,D58,R83',159",
    "'R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51','U98,R91,D20,R16,D67,R40,U7,R15,U6,R7',135"
  })
  void examplePart1(final String path1, final String path2, final long expected) {
    final WirePath wp1 = WirePath.of(path1);
    final WirePath wp2 = WirePath.of(path2);

    final Point point =
        Point.shortestManhattanDistanceToCenter(WirePath.intersectionPoints(wp1, wp2));
    assertEquals(expected, point.manhattanDistanceToCenter());
  }

  @ParameterizedTest
  @CsvSource({
      "'R8,U5,L5,D3','U7,R6,D4,L4',30",
      "'R75,D30,R83,U83,L12,D49,R71,U7,L72','U62,R66,U55,R34,D71,R55,D58,R83',610",
      "'R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51','U98,R91,D20,R16,D67,R40,U7,R15,U6,R7',410"
  })
  void examplePart2(final String path1, final String path2, final long expected) {
    final WirePath wp1 = WirePath.of(path1);
    final WirePath wp2 = WirePath.of(path2);

    assertEquals(expected, WirePath.fewestStepsToIntersection(wp1, wp2));
  }
}
