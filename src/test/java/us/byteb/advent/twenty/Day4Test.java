package us.byteb.advent.twenty;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.twenty.Day4.*;

class Day4Test {

  @Test
  void testParse() throws IOException {
    final List<List<String>> parsedInput = parseInput(readFileFromResources("day4/example.txt"));
    assertEquals(4, parsedInput.size());
    assertEquals(2, countValid(parsedInput));
  }
}
