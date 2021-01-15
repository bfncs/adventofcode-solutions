package us.byteb.advent.y20;

import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;
import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import us.byteb.advent.y20.Day18.Token.ClosingParenthesis;
import us.byteb.advent.y20.Day18.Token.Num;
import us.byteb.advent.y20.Day18.Token.OpeningParenthesis;
import us.byteb.advent.y20.Day18.Token.Operator;
import us.byteb.advent.y20.Day18.Token.Operator.Multiply;
import us.byteb.advent.y20.Day18.Token.Operator.Plus;

public final class Day18 {

  static final List<Class<? extends Operator>> LEFT_BEFORE_RIGHT = List.of(Operator.class);
  static final List<Class<? extends Operator>> PLUS_BEFORE_MULTIPLY =
      List.of(Plus.class, Multiply.class);

  public static void main(String[] args) {
    final String input = readFileFromResources("y20/day18.txt");

    System.out.println(
        "Part 1: " + input.lines().mapToLong(input1 -> evaluate(input1, LEFT_BEFORE_RIGHT)).sum());
    System.out.println(
        "Part 2: " + input.lines().mapToLong(line -> evaluate(line, PLUS_BEFORE_MULTIPLY)).sum());
  }

  public static long evaluate(
      final String input, final List<Class<? extends Operator>> operatorPrecedence) {
    final List<Token> result = Evaluator.evaluate(Tokenizer.tokenize(input), operatorPrecedence);
    return ((Num) result.get(0)).value();
  }

  interface Token {
    interface Operator extends Token {
      Num evaluate(Num a, Num b);

      record Plus() implements Operator {

        @Override
        public Num evaluate(final Num a, final Num b) {
          return new Num(a.value() + b.value());
        }
      }

      record Multiply() implements Operator {
        @Override
        public Num evaluate(final Num a, final Num b) {
          return new Num(a.value() * b.value());
        }
      }
    }

    record Num(long value) implements Token {}

    record OpeningParenthesis() implements Token {}

    record ClosingParenthesis() implements Token {}
  }

  static class Evaluator {
    private static List<Token> evaluate(
        final List<Token> tokens, final List<Class<? extends Operator>> operatorPrecedence) {
      return resolveOperators(resolveParentheses(tokens, operatorPrecedence), operatorPrecedence);
    }

    private static List<Token> resolveParentheses(
        List<Token> tokens, final List<Class<? extends Operator>> operatorPrecedence) {
      List<Token> curTokens = List.copyOf(tokens);

      while (true) {
        final Optional<Token> openingParenthesis =
            curTokens.stream().filter(t -> t instanceof OpeningParenthesis).findFirst();

        if (openingParenthesis.isEmpty()) {
          return curTokens;
        }

        curTokens =
            resolveSingleParenthesis(
                curTokens, curTokens.indexOf(openingParenthesis.get()), operatorPrecedence);
      }
    }

    private static List<Token> resolveSingleParenthesis(
        final List<Token> tokens,
        final int openingBracketPosition,
        final List<Class<? extends Operator>> operatorPrecedence) {
      List<Token> curTokens = List.copyOf(tokens);
      int pos = openingBracketPosition;
      int innerOpenedBrackets = 0;

      while (++pos < curTokens.size()) {
        if (curTokens.get(pos) instanceof OpeningParenthesis) {
          innerOpenedBrackets++;
        } else if (curTokens.get(pos) instanceof ClosingParenthesis) {
          if (innerOpenedBrackets == 0) {
            final List<Token> bracketResult =
                evaluate(curTokens.subList(openingBracketPosition + 1, pos), operatorPrecedence);
            curTokens =
                splice(
                    curTokens,
                    openingBracketPosition,
                    pos - openingBracketPosition + 1,
                    bracketResult);
            break;
          } else {
            innerOpenedBrackets--;
          }
        }
      }

      return curTokens;
    }

    private static List<Token> resolveOperators(
        final List<Token> tokens, final List<Class<? extends Operator>> operatorPrecedence) {
      List<Token> curTokens = List.copyOf(tokens);

      for (final Class<? extends Operator> operatorType : operatorPrecedence) {
        curTokens = resolveOperator(curTokens, operatorType);
      }

      return curTokens;
    }

    private static List<Token> resolveOperator(
        final List<Token> tokens, final Class<? extends Operator> operatorType) {
      List<Token> curTokens = List.copyOf(tokens);

      while (true) {
        final Optional<Operator> operator =
            curTokens.stream().filter(operatorType::isInstance).map(t -> (Operator) t).findFirst();

        if (operator.isEmpty()) {
          return curTokens;
        }

        final int index = curTokens.indexOf(operator.get());
        final Num arg1 = (Num) curTokens.get(index - 1);
        final Num arg2 = (Num) curTokens.get(index + 1);
        final Num result = operator.get().evaluate(arg1, arg2);

        curTokens = splice(curTokens, index - 1, 3, List.of(result));
      }
    }

    private static <T> List<T> splice(
        final List<T> tokens, final int start, final int deleteCount, final List<T> addItems) {
      final List<T> nextResult = new ArrayList<>(tokens.subList(0, start));
      nextResult.addAll(addItems);
      nextResult.addAll(tokens.subList(start + deleteCount, tokens.size()));

      return nextResult;
    }
  }

  static class Tokenizer {

    static List<Token> tokenize(final String input) {
      final List<Token> tokens = new ArrayList<>();
      int pos = 0;

      while (pos < input.length()) {

        final TokenizeNextResult result = tokenizeNext(input, pos);

        if (result.token() != null) {
          tokens.add(result.token());
        }
        pos = result.nextPos();
      }

      return tokens;
    }

    private static TokenizeNextResult tokenizeNext(final String input, final int pos) {
      final char curChar = input.charAt(pos);
      return switch (curChar) {
        case ' ' -> new TokenizeNextResult(pos + 1, null);
        case '+' -> new TokenizeNextResult(pos + 1, new Plus());
        case '*' -> new TokenizeNextResult(pos + 1, new Multiply());
        case '(' -> new TokenizeNextResult(pos + 1, new OpeningParenthesis());
        case ')' -> new TokenizeNextResult(pos + 1, new ClosingParenthesis());
        default -> {
          if (isDigit(curChar)) {
            yield tokenizeNextDigit(input, pos);
          }

          throw new IllegalStateException("Illegal character %s at pos %d".formatted(curChar, pos));
        }
      };
    }

    private static TokenizeNextResult tokenizeNextDigit(final String input, final int pos) {
      int i = pos;
      while (i < input.length()) {
        if (!isDigit(input.charAt(i))) {
          break;
        }
        i++;
      }

      return new TokenizeNextResult(i, new Num(parseInt(input.substring(pos, i))));
    }

    record TokenizeNextResult(int nextPos, Token token) {}
  }
}
