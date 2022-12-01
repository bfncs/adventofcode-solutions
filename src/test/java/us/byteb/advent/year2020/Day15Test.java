package us.byteb.advent.year2020;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2020.Day15.findNumber;
import static us.byteb.advent.year2020.Day15.parseInput;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day15Test {

  @ParameterizedTest
  @Disabled("expensive")
  @CsvSource({
    "'0,3,6',436,2020",
    "'1,3,2',1,2020",
    "'2,1,3',10,2020",
    "'1,2,3',27,2020",
    "'2,3,1',78,2020",
    "'3,2,1',438,2020",
    "'3,1,2',1836,2020",
    "'0,3,6',175594,30000000",
    "'1,3,2',2578,30000000",
    "'2,1,3',3544142,30000000",
    "'1,2,3',261214,30000000",
    "'2,3,1',6895259,30000000",
    "'3,2,1',18,30000000",
    "'3,1,2',362,30000000",
  })
  void examplePart1(final String input, final int expectedResult, final int iteration) {
    assertEquals(expectedResult, findNumber(parseInput(input), iteration));
  }
}
