package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day11.*;

import org.junit.jupiter.api.Test;

class Day11Test {

  @Test
  void partOneExample() {
    assertEquals(parse("1 2024 1 0 9 9 2021976"), breadthFirstBlink(parse("0 1 10 99 999"), 1));
    assertEquals(
        parse("2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2"),
        breadthFirstBlink(parse("125 17"), 6));
  }

  @Test
  void partTwoExample() {
    assertEquals(
        parse("1 2024 1 0 9 9 2021976").size(), depthFirstBlink(parse("0 1 10 99 999"), 1));
    assertEquals(
        parse("2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2").size(),
        depthFirstBlink(parse("125 17"), 6));
  }
}
