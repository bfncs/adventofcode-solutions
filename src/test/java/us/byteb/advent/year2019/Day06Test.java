package us.byteb.advent.year2019;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2019.Day06.*;

import org.junit.jupiter.api.Test;

class Day06Test {

  @Test
  void part1Example() {
    final String input =
        """
        COM)B
        B)C
        C)D
        D)E
        E)F
        B)G
        G)H
        D)I
        E)J
        J)K
        K)L
        """;
    assertEquals(42, countOrbits(parse(input)));
  }

  @Test
  void part2Example() {
    final String input =
        """
        COM)B
        B)C
        C)D
        D)E
        E)F
        B)G
        G)H
        D)I
        E)J
        J)K
        K)L
        K)YOU
        I)SAN
        """;
    assertEquals(4, findMinNumberOfRequiredTransfers(parse(input)));
  }
}
