package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2016.Day06.*;

import org.junit.jupiter.api.Test;

class Day06Test {

  private static final String EXAMPLE_DATA =
      """
          eedadn
          drvtee
          eandsr
          raavrd
          atevrs
          tsrnev
          sdttsa
          rasrtv
          nssdts
          ntnada
          svetve
          tesnvt
          vntsnd
          vrdear
          dvrsen
          enarar
          """;

  @Test
  void partOneExample() {
    assertEquals("easter", decode(EXAMPLE_DATA, Day06::mostFrequentCharacterInColumn));
  }

  @Test
  void partTwoExample() {
    assertEquals("advent", decode(EXAMPLE_DATA, Day06::leastFrequentCharacterInColumn));
  }
}
