package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day05.numberOfPointsWhereAtLeastTwoHorizontalAndVerticalLinesOverlap;
import static us.byteb.advent.year2021.Day05.parseInput;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.year2021.Day05.Line;

class Day05Test {

  static final String exampleInput =
      """
      0,9 -> 5,9
      8,0 -> 0,8
      9,4 -> 3,4
      2,2 -> 2,1
      7,0 -> 7,4
      6,4 -> 2,0
      0,9 -> 2,9
      3,4 -> 1,4
      0,0 -> 8,8
      5,5 -> 8,2
      """;

  @Test
  void part1Example() {
    final List<Line> lines = parseInput(exampleInput);
    assertEquals(5L, numberOfPointsWhereAtLeastTwoHorizontalAndVerticalLinesOverlap(lines));
  }
}
