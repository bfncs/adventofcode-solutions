package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day14.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day14Test {

  private static final String EXAMPLE_DATA =
      """
      498,4 -> 498,6 -> 496,6
      503,4 -> 502,4 -> 502,9 -> 494,9
      """;

  @Test
  void parsePaths() {
    assertEquals(
        List.of(
            new Path(List.of(new Point(498, 4), new Point(498, 6), new Point(496, 6))),
            new Path(
                List.of(
                    new Point(503, 4), new Point(502, 4), new Point(502, 9), new Point(494, 9)))),
        parse(EXAMPLE_DATA));
  }

  @Test
  void partOneExample() {
    assertEquals(24, findUnitOfSandsBeforeFallthrough(parse(EXAMPLE_DATA)));
  }
}
