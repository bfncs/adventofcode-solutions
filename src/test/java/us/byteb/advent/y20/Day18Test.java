package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y20.Day18.Expression.*;

import org.junit.jupiter.api.Test;

class Day18Test {

  @Test
  void parseExpression() {
    assertEquals(
        add(add(num(1), sub(mult(num(2), num(3)))), sub(mult(num(4), sub(add(num(5), num(6)))))),
        parse("1 + (2 * 3) + (4 * (5 + 6))"));
  }

  @Test
  void evaluateExpression() {
    assertEquals(3, add(num(1), num(2)).eval().value());
    assertEquals(6, add(num(1), add(num(2), num(3))).eval().value());
    assertEquals(
        51,
        add(add(num(1), sub(mult(num(2), num(3)))), sub(mult(num(4), sub(add(num(5), num(6))))))
            .eval()
            .value());
  }

  @Test
  void part1Examples() {
    assertEquals(26, parse("2 * 3 + (4 * 5)").eval().value());
    assertEquals(437, parse("5 + (8 * 3 + 9 + 3 * 4 * 3)").eval().value());
    assertEquals(12240, parse("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))").eval().value());
    assertEquals(13632, parse("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2").eval().value());
  }
}
