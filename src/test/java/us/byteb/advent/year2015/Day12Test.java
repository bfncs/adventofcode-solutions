package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2015.Day12.*;

import org.junit.jupiter.api.Test;

class Day12Test {

  @Test
  void partOneExample() {
    assertEquals(6L, sumAllNumbers("[1,2,3]", false));
    assertEquals(6L, sumAllNumbers("{\"a\":2,\"b\":4}", false));
  }

  @Test
  void partTwoExample() {
    assertEquals(6L, sumAllNumbers("[1,2,3]", true));
    assertEquals(4L, sumAllNumbers("[1,{\"c\":\"red\",\"b\":2},3]", true));
    assertEquals(0L, sumAllNumbers("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}", true));
    assertEquals(6L, sumAllNumbers("[1,\"red\",5]", true));
    assertEquals(0L, sumAllNumbers("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}", true));
    assertEquals(6L, sumAllNumbers("[1,\"red\",5]", true));
  }
}
