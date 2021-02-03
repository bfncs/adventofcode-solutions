package us.byteb.advent.year2020;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2020.Day10.*;

import java.math.BigInteger;
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
    assertEquals(Map.of(1L, 7L, 3L, 5L), chainVoltageDifferences(EXAMPLE1));
    assertEquals(Map.of(1L, 22L, 3L, 10L), chainVoltageDifferences(EXAMPLE2));
  }

  @Test
  void examplePart2() {
    assertEquals(BigInteger.valueOf(8), countDistinctValidArrangements(EXAMPLE1));
    assertEquals(BigInteger.valueOf(19208), countDistinctValidArrangements(EXAMPLE2));
  }
}
