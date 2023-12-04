package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2016.Day06.*;

import org.junit.jupiter.api.Test;

class Day06Test {

  private static final String PART1_DATA =
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
    assertEquals("easter", decodeByMostFrequentPerColumn(PART1_DATA));
  }
}
