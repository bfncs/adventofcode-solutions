package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y20.Day15.findNumber;
import static us.byteb.advent.y20.Day15.parseInput;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day15Test {

  @ParameterizedTest
  @CsvSource({
    "'0,3,6',436",
    "'1,3,2',1",
    "'2,1,3',10",
    "'1,2,3',27",
    "'2,3,1',78",
    "'3,2,1',438",
    "'3,1,2',1836"
  })
  void example1(final String input, final int expectedResult) {
    assertEquals(expectedResult, findNumber(parseInput(input), 2020));
  }
}
