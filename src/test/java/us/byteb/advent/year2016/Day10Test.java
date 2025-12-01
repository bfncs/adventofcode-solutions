package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.*;
import us.byteb.advent.year2016.Day10.Action;
import us.byteb.advent.year2016.Day10.Factory;

class Day10Test {

  private static final String INSTRUCTIONS =
      """
      value 5 goes to bot 2
      bot 2 gives low to bot 1 and high to bot 0
      value 3 goes to bot 1
      bot 1 gives low to output 1 and high to bot 0
      bot 0 gives low to output 2 and high to output 0
      value 2 goes to bot 2
      """;

  @Test
  void testPart1() {
    assertEquals(
        List.of(new Action(2, 2, 5), new Action(1, 2, 3), new Action(0, 3, 5)),
        new Factory(INSTRUCTIONS).process());
  }
}
