package us.byteb.advent.year2020;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2020.Day18.*;
import static us.byteb.advent.year2020.Day18.Tokenizer.tokenize;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.year2020.Day18.Token.ClosingParenthesis;
import us.byteb.advent.year2020.Day18.Token.Num;
import us.byteb.advent.year2020.Day18.Token.OpeningParenthesis;
import us.byteb.advent.year2020.Day18.Token.Operator.Multiply;
import us.byteb.advent.year2020.Day18.Token.Operator.Plus;

class Day18Test {

  @Test
  void tokenizeInput() {
    assertEquals(
        List.of(
            new Num(1),
            new Plus(),
            new OpeningParenthesis(),
            new Num(2),
            new Multiply(),
            new Num(3),
            new ClosingParenthesis(),
            new Plus(),
            new OpeningParenthesis(),
            new Num(4),
            new Multiply(),
            new OpeningParenthesis(),
            new Num(5),
            new Plus(),
            new Num(6),
            new ClosingParenthesis(),
            new ClosingParenthesis()),
        tokenize("1 + (2 * 3) + (4 * (5 + 6))"));
  }

  @Test
  void part1Examples() {
    assertEquals(26, evaluate("2 * 3 + (4 * 5)", LEFT_BEFORE_RIGHT));
    assertEquals(437, evaluate("5 + (8 * 3 + 9 + 3 * 4 * 3)", LEFT_BEFORE_RIGHT));
    assertEquals(12240, evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", LEFT_BEFORE_RIGHT));
    assertEquals(
        13632, evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", LEFT_BEFORE_RIGHT));
  }

  @Test
  void part2Examples() {
    assertEquals(231, evaluate("1 + 2 * 3 + 4 * 5 + 6", PLUS_BEFORE_MULTIPLY));
    assertEquals(46, evaluate("2 * 3 + (4 * 5)", PLUS_BEFORE_MULTIPLY));
    assertEquals(1445, evaluate("5 + (8 * 3 + 9 + 3 * 4 * 3)", PLUS_BEFORE_MULTIPLY));
    assertEquals(
        669060, evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", PLUS_BEFORE_MULTIPLY));
    assertEquals(
        23340, evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", PLUS_BEFORE_MULTIPLY));
  }
}
