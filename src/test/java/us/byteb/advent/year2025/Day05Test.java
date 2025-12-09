package us.byteb.advent.year2025;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.Set;
import org.junit.jupiter.api.*;

class Day05Test {

  static final String INPUT =
      """
      3-5
      10-14
      16-20
      12-18

      1
      5
      8
      11
      17
      32
      """;

  @Test
  void testPart1() {
    assertEquals(
        Set.of(new BigInteger("5"), new BigInteger("11"), new BigInteger("17")),
        Day05.PuzzleInput.parse(INPUT).findFreshIngredients());
  }
}
