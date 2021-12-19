package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2021.Day14.*;

import org.junit.jupiter.api.Test;
import us.byteb.advent.year2021.Day14.PuzzleInput;

class Day14Test {

  private static final String EXAMPLE =
      """
      NNCB

      CH -> B
      HH -> N
      CB -> H
      NH -> C
      HB -> C
      HC -> B
      HN -> C
      NN -> C
      BH -> H
      NC -> B
      NB -> B
      BN -> B
      BB -> N
      BC -> B
      CC -> N
      CN -> C
      """;

  @Test
  void example1() {
    final PuzzleInput input = parseInput(EXAMPLE);
    assertEquals("NCNBCHB", applyRules(input, 1));
    assertEquals("NBCCNBBBCBHCB", applyRules(input, 2));
    assertEquals("NBBBCNCCNBBNBNBBCHBHHBCHB", applyRules(input, 3));
    assertEquals("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB", applyRules(input, 4));

    assertEquals(97, applyRules(input, 5).length());
    assertEquals(3073, applyRules(input, 10).length());

    assertEquals(1588, solvePart1(applyRules(input, 10)));
  }
}
