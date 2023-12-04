package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static us.byteb.advent.year2016.Day07.*;

import org.junit.jupiter.api.Test;

class Day07Test {

  @Test
  void partOneExample() {
    assertTrue(isSupportingTls("abba[mnop]qrst"));
    assertFalse(isSupportingTls("abcd[bddb]xyyx"));
    assertFalse(isSupportingTls("aaaa[qwer]tyui"));
    assertTrue(isSupportingTls("ioxxoj[asdfgh]zxcvbn"));
  }

  @Test
  void partTwoExample() {
    assertTrue(isSupportingSsl("aba[bab]xyz"));
    assertFalse(isSupportingSsl("xyx[xyx]xyx"));
    assertTrue(isSupportingSsl("aaa[kek]eke"));
    assertTrue(isSupportingSsl("zazbz[bzb]cdb"));
  }
}
