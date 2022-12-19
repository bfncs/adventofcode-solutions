package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day17.*;

import org.junit.jupiter.api.Test;

class Day17Test {

  private static final String EXAMPLE_DATA = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";

  @Test
  void partOneExample() {
    assertEquals(3068L, towerHeight(EXAMPLE_DATA, 2022));
  }
}
