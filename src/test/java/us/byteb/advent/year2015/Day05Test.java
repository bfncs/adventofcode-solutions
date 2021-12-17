package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2015.Day05.isNice;
import static us.byteb.advent.year2015.Day05.isNiceRevised;

import org.junit.jupiter.api.Test;

class Day05Test {

  @Test
  void example1() {
    assertTrue(isNice("ugknbfddgicrmopn"));
    assertTrue(isNice("aaa"));
    assertFalse(isNice("jchzalrnumimnmhp"));
    assertFalse(isNice("haegwjzuvuyypxyu"));
    assertFalse(isNice("dvszwmarrgswjxmb"));
  }

  @Test
  void example2() {
    assertTrue(isNiceRevised("qjhvhtzxzqqjkmpb"));
    assertTrue(isNiceRevised("xxyxx"));
    assertFalse(isNiceRevised("uurcxstgmygtbstg"));
    assertFalse(isNiceRevised("ieodomkazucvgmuy"));
  }
}
