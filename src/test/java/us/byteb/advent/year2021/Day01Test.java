package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2021.Day01.countIncreases;
import static us.byteb.advent.year2021.Day01.parseInput;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day01Test {

  private static final List<Long> part1ExampleDate =
      parseInput("""
			199
			200
			208
			210
			200
			207
			240
			269
			260
			263
			""");

  @Test
  void partOneExample() {
    assertEquals(7L, countIncreases(part1ExampleDate));
  }
}
