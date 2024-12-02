package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2015.Day10.*;

import org.junit.jupiter.api.Test;

class Day10Test {

  @Test
  void partOneExample() {
    assertEquals("11", lookAndSay("1", 1));
    assertEquals("21", lookAndSay("1", 2));
    assertEquals("1211", lookAndSay("1", 3));
    assertEquals("111221", lookAndSay("1", 4));
    assertEquals("312211", lookAndSay("1", 5));
  }
}
