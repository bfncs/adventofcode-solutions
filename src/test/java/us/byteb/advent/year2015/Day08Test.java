package us.byteb.advent.year2015;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2015.Day08.evaluateString;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day08Test {

  @Test
  void example1() {
    assertEquals("", evaluateString("\"\""));
    assertEquals("abc", evaluateString("\"abc\""));
    assertEquals("aaa\"aaa", evaluateString("\"aaa\\\"aaa\""));
    assertEquals("'", evaluateString("\"\\x27\""));

    final String input = readFileFromResources("year2015/day08.txt");
    final List<String> inputLines = input.lines().toList();
    assertEquals(List.of("\"\"", "\"abc\"", "\"aaa\\\"aaa\"", "\"\\x27\""), inputLines);
    final List<String> evaluated = inputLines.stream().map(Day08::evaluateString).toList();
    assertEquals(List.of("", "abc", "aaa\"aaa", "'"), evaluated);

    assertEquals(23, inputLines.stream().mapToInt(String::length).sum());
    assertEquals(11, evaluated.stream().mapToInt(String::length).sum());
  }
}
