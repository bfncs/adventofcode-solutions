package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.y20.Day18.evaluate;
import static us.byteb.advent.y20.Day18.tokenize;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.y20.Day18.Token.ClosingParenthesis;
import us.byteb.advent.y20.Day18.Token.Num;
import us.byteb.advent.y20.Day18.Token.OpeningParenthesis;
import us.byteb.advent.y20.Day18.Token.Operator;
import us.byteb.advent.y20.Day18.Token.Operator.Multiply;
import us.byteb.advent.y20.Day18.Token.Operator.Plus;

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
    assertEquals(26, evaluate("2 * 3 + (4 * 5)"));
    assertEquals(437, evaluate("5 + (8 * 3 + 9 + 3 * 4 * 3)"));
    assertEquals(12240, evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"));
    assertEquals(13632, evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"));
  }

  @Test
  void part2Examples() {
    final List<Class<? extends Operator>> operatorPrecedence = List.of(Plus.class, Multiply.class);

    assertEquals(231, evaluate("1 + 2 * 3 + 4 * 5 + 6", operatorPrecedence));
    assertEquals(46, evaluate("2 * 3 + (4 * 5)", operatorPrecedence));
    assertEquals(1445, evaluate("5 + (8 * 3 + 9 + 3 * 4 * 3)", operatorPrecedence));
    assertEquals(669060, evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", operatorPrecedence));
    assertEquals(
        23340, evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", operatorPrecedence));
  }
}
