package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2021.Day14.*;

import java.util.Map;
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
    assertEquals(1588, mostCommonsMinusLeastCommonCharacter(applyRules(input, 10)));
  }

  @Test
  void example2() {
    final PuzzleInput input = parseInput(EXAMPLE);
    assertEquals(
        Map.of(
            'N', 2L,
            'C', 2L,
            'B', 2L,
            'H', 1L),
        applyRules(input, 1));

    assertEquals(1588L, mostCommonsMinusLeastCommonCharacter(applyRules(input, 10)));
    assertEquals(2188189693529L, mostCommonsMinusLeastCommonCharacter(applyRules(input, 40)));
  }
}
