package us.byteb.advent.y20;

import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.y20.Day18.Expression.*;

import java.util.Optional;
import java.util.function.BiFunction;

public class Day18 {

  public static void main(String[] args) {
    final String input = readFileFromResources("y20/day18.txt");

    final long resultPart1 = input.lines().mapToLong(line -> parse(line).eval().value()).sum();
    System.out.println("Part 1: " + resultPart1);
  }

  interface Expression {

    static Expression parse(final String input) {
      return ExpressionParser.parse(input);
    }

    static Number num(final long i) {
      return new Number(i);
    }

    static Addition add(final Expression addend1, final Expression addend2) {
      return new Addition(addend1, addend2);
    }

    static Multiplication mult(final Expression factor1, final Expression factor2) {
      return new Multiplication(factor1, factor2);
    }

    static SubTerm sub(final Expression expression) {
      return new SubTerm(expression);
    }

    Number eval();

    record Number(long value) implements Expression {

      @Override
      public Number eval() {
        return this;
      }

      @Override
      public String toString() {
        return String.valueOf(value);
      }
    }

    record Addition(Expression addend1, Expression addend2) implements Expression {

      @Override
      public Number eval() {
        return new Number(addend1.eval().value() + addend2().eval().value());
      }

      @Override
      public String toString() {
        return addend1 + " + " + addend2;
      }
    }

    record Multiplication(Expression factor1, Expression factor2) implements Expression {

      @Override
      public Number eval() {
        return new Number(factor1.eval().value() * factor2().eval().value());
      }

      @Override
      public String toString() {
        return factor1 + " * " + factor2;
      }
    }

    record SubTerm(Expression expression) implements Expression {

      @Override
      public Number eval() {
        return expression.eval();
      }

      @Override
      public String toString() {
        return "(" + expression + ")";
      }
    }
  }

  private static class ExpressionParser {
    static Expression parse(final String input) {
      Expression lastExpression = null;
      int pos = 0;

      while (pos < input.length()) {
        final ParseNextResult result = parseNext(input, pos, lastExpression);
        if (result.expression().isPresent()) {
          lastExpression = result.expression().get();
        }
        pos = result.nextPos();
      }

      if (lastExpression == null) {
        throw new IllegalStateException("Expression incomplete");
      }
      return lastExpression;
    }

    record ParseNextResult(Optional<Expression> expression, int nextPos) {}

    private static ParseNextResult parseNext(
        final String input, int pos, final Expression lastExpression) {
      final char curChar = input.charAt(pos);
      if (curChar == ' ') {
        return new ParseNextResult(Optional.empty(), pos + 1);
      } else if (isDigit(curChar)) {
        int i = pos;
        while (i < input.length()) {
          if (!isDigit(input.charAt(i))) {
            break;
          }
          i++;
        }
        return new ParseNextResult(Optional.of(num(parseInt(input.substring(pos, i)))), i);
      } else if (curChar == '+') {
        return parseNextInfixOperator(input, pos, lastExpression, curChar, Expression::add);
      } else if (curChar == '*') {
        return parseNextInfixOperator(input, pos, lastExpression, curChar, Expression::mult);
      } else if (curChar == '(') {
        int i = pos;
        int innerOpenedBrackets = 0;
        while (++i < input.length()) {
          if (input.charAt(i) == '(') {
            innerOpenedBrackets++;
          } else if (input.charAt(i) == ')') {
            if (innerOpenedBrackets == 0) {
              final String term = input.substring(pos + 1, i);
              return new ParseNextResult(Optional.of(sub(parse(term))), i + 1);
            } else {
              innerOpenedBrackets--;
            }
          }
        }

        throw new IllegalStateException(
            "No closing bracket for opening bracket at position " + pos);
      } else {
        throw new IllegalStateException(
            "Illegal character '%s' at position %d".formatted(curChar, pos));
      }
    }

    private static ParseNextResult parseNextInfixOperator(
        final String input,
        final int pos,
        final Expression lastExpression,
        final char curChar,
        final BiFunction<Expression, Expression, Expression> expressionCreator) {
      if (lastExpression == null) {
        throw new IllegalStateException(
            "Illegal character '%s' at position %d".formatted(curChar, pos));
      }
      ParseNextResult addend2 = null;
      int i = pos + 1;
      while (i < input.length()) {
        addend2 = parseNext(input, i++, lastExpression);
        if (addend2.expression().isPresent()) {
          break;
        }
      }
      if (addend2 == null || addend2.expression().isEmpty()) {
        throw new IllegalStateException("Missing addend after position %d".formatted(pos));
      }
      return new ParseNextResult(
          Optional.of(expressionCreator.apply(lastExpression, addend2.expression().get())),
          addend2.nextPos());
    }
  }
}
