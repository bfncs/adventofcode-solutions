package us.byteb.advent.y19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.y19.Day2.execIntCode;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day2Test {

  @Test
  void execExamplePrograms() {
    assertEquals(
        List.of(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50),
        execIntCode(List.of(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50)));
    assertEquals(List.of(2, 0, 0, 0, 99), execIntCode(List.of(1, 0, 0, 0, 99)));
    assertEquals(List.of(2, 3, 0, 6, 99), execIntCode(List.of(2, 3, 0, 3, 99)));
    assertEquals(List.of(2, 4, 4, 5, 99, 9801), execIntCode(List.of(2, 4, 4, 5, 99, 0)));
    assertEquals(
        List.of(30, 1, 1, 4, 2, 5, 6, 0, 99), execIntCode(List.of(1, 1, 1, 4, 99, 5, 6, 0, 99)));
  }
}
