package us.byteb.advent.year2025;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2025.Day08.*;

import java.math.BigInteger;
import org.junit.jupiter.api.*;

class Day08Test {

  static final String INPUT =
      """
      162,817,812
      57,618,57
      906,360,560
      592,479,940
      352,342,300
      466,668,158
      542,29,236
      431,825,988
      739,650,466
      52,470,668
      216,146,977
      819,987,18
      117,168,530
      805,96,715
      346,949,466
      970,615,88
      941,993,340
      862,61,35
      984,92,344
      425,690,689
      """;

  @Test
  void testPart1() {
    assertEquals(BigInteger.valueOf(40L), solvePart1(10, parse(INPUT)));
  }

  @Test
  void testPart2() {
    assertEquals(25272L, solvePart2(parse(INPUT)));
  }
}
