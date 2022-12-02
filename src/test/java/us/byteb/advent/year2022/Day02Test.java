package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day02.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day02Test {

  private static final List<Round> part1ExampleDate =
      parseInput("""
            A Y
            B X
            C Z
            """);

  @Test
  void partOneExample() {
    assertEquals(15L, totalScore(part1ExampleDate));
  }
}
