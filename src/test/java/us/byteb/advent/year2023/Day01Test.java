package us.byteb.advent.year2023;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2023.Day01.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day01Test {

  private static final List<String> part1ExampleDate =
      parseInput(
          """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
            """);

  private static final List<String> part2ExampleDate =
      parseInput(
          """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
            """);

  @Test
  void partOneExample() {
    assertEquals(142L, sumCalibrationValues(part1ExampleDate, Day01::parseDigits));
  }

  @Test
  void partTwoExample() {
    assertEquals(281L, sumCalibrationValues(part2ExampleDate, Day01::parseDigitsAndWords));
  }

  @Test
  void parseDigitsAndWords() {
    assertEquals(13L, Day01.parseDigitsAndWords("onetwothree"));
    assertEquals(12L, Day01.parseDigitsAndWords("jhctmxconelfkgmprnfourseven8twofkjvlvnjgd"));
    assertEquals(28L, Day01.parseDigitsAndWords("2fourseven1oneights"));
  }
}
