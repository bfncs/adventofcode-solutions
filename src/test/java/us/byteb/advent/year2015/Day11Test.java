package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2015.Day11.*;

import org.junit.jupiter.api.Test;

class Day11Test {

  @Test
  void partOneExample() {
    assertFalse(containsStraight("vzbxkkaa"));
    assertTrue(containsStraight("ghjaabcc"));
    assertEquals("abcdffaa", findNextPassword("abcdefgh"));
    assertEquals("ghjaabcc", findNextPassword("ghijklmn"));
  }
}
