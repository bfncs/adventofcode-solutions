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

  @Test
  void partOneExample() {
    assertEquals(142L, sumCalibrationValues(part1ExampleDate));
  }

}
