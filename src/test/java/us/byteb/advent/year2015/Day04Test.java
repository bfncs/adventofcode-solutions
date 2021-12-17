package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2015.Day04.solve;

import org.junit.jupiter.api.Test;

class Day04Test {

  @Test
  void example1() {
    assertEquals(609043L, solve("abcdef", "00000"));
    assertEquals(1048970L, solve("pqrstuv", "00000"));
  }
}
