package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2015.Day12.*;

import org.junit.jupiter.api.Test;

class Day12Test {

  @Test
  void partOneExample() {
    assertEquals(6L, sumAllNumbers("[1,2,3]"));
    assertEquals(6L, sumAllNumbers("{\"a\":2,\"b\":4}"));
  }
}
