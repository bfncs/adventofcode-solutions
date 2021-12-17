package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2015.Day03.parseInput;
import static us.byteb.advent.year2015.Day03.uniqueVisitedHouses;

import org.junit.jupiter.api.Test;

class Day03Test {

  @Test
  void example1() {
    assertEquals(2, uniqueVisitedHouses(parseInput(">"), 1).size());
    assertEquals(4, uniqueVisitedHouses(parseInput("^>v<"), 1).size());
    assertEquals(2, uniqueVisitedHouses(parseInput("^v^v^v^v^v"), 1).size());
  }

  @Test
  void example2() {
    assertEquals(3, uniqueVisitedHouses(parseInput("^v"), 2).size());
    assertEquals(3, uniqueVisitedHouses(parseInput("^>v<"), 2).size());
    assertEquals(11, uniqueVisitedHouses(parseInput("^v^v^v^v^v"), 2).size());
  }
}
