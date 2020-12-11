package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.y20.Day10.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class Day10Test {

  private static final List<Long> EXAMPLE1 =
      parseInput(
          """
                  16
                  10
                  15
                  5
                  1
                  11
                  7
                  19
                  6
                  12
                  4""");

  private static final List<Long> EXAMPLE2 =
      parseInput(
          """
                  28
                  33
                  18
                  42
                  31
                  14
                  46
                  20
                  48
                  47
                  24
                  23
                  49
                  45
                  19
                  38
                  39
                  11
                  1
                  32
                  25
                  35
                  8
                  17
                  7
                  9
                  4
                  2
                  34
                  10
                  3""");

  @Test
  void examplePart1() {
    assertEquals(Map.of(1, 7, 3, 5), chainVoltageDifferences(EXAMPLE1));
    assertEquals(Map.of(1, 22, 3, 10), chainVoltageDifferences(EXAMPLE2));
  }

  @Test
  void examplePart2() {
    assertEquals(8, countDistinctValidArrangements(EXAMPLE1));
    assertEquals(19208, countDistinctValidArrangements(EXAMPLE2));
  }
}
