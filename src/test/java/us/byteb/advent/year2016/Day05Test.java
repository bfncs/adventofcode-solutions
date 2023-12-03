package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2016.Day05.*;

import org.junit.jupiter.api.Test;

class Day05Test {
  @Test
  void partOneExample() {
    assertEquals("18f47a30", findPassword("abc"));
  }
}
