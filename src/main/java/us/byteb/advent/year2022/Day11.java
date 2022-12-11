package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import us.byteb.advent.year2022.Day11.Operation.Add;
import us.byteb.advent.year2022.Day11.Operation.Multiply;
import us.byteb.advent.year2022.Day11.Operation.Square;

public class Day11 {

  private static final String ITEMS_LINE_PREFIX = "  Starting items: ";
  private static final String OPERATIONS_LINE_PREFIX = "  Operation: new = old ";
  private static final String TEST_LINE_PREFIX = "  Test: divisible by ";
  private static final String TRUE_LINE_PREFIX = "    If true: throw to monkey ";
  private static final String FALSE_LINE_PREFIX = "    If false: throw to monkey ";

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day11.txt");

    System.out.println("Part 1:" + monkeyBusiness(input, 20, true));
    System.out.println("Part 2:" + monkeyBusiness(input, 10_000, false));
  }

  public static BigInteger monkeyBusiness(
      final String input, final int rounds, final boolean divideAfterInspection) {
    final List<Monkey> monkeys = parse(input);

    final long commonDivisor =
        monkeys.stream()
            .mapToLong(m -> m.test.divisibleBy())
            .reduce(Math::multiplyExact)
            .orElseThrow();

    for (int round = 0; round < rounds; round++) {
      for (final Monkey monkey : monkeys) {
        monkey.turn(
            monkeys::get,
            divideAfterInspection
                ? worryLevel -> worryLevel.divide(BigInteger.valueOf(3))
                : worryLevel -> worryLevel.remainder(BigInteger.valueOf(commonDivisor)));
      }
    }

    return monkeys.stream()
        .mapToInt(Monkey::numInspections)
        .sorted()
        .skip(monkeys.size() - 2)
        .mapToObj(BigInteger::valueOf)
        .reduce(BigInteger::multiply)
        .orElseThrow();
  }

  public static List<Monkey> parse(final String input) {
    final List<Monkey> result = new ArrayList<>();

    try (Scanner scanner = new Scanner(input)) {
      while (scanner.hasNextLine()) {
        scanner.skip(Pattern.compile("Monkey \\d+:\n"));

        final String itemsLine = scanner.nextLine();
        final List<Integer> items =
            Arrays.stream(itemsLine.substring(ITEMS_LINE_PREFIX.length()).split(",\\s+"))
                .map(Integer::parseInt)
                .toList();

        final String operationLine = scanner.nextLine();
        final String operator =
            operationLine.substring(
                OPERATIONS_LINE_PREFIX.length(), OPERATIONS_LINE_PREFIX.length() + 1);
        final String operationValue = operationLine.substring(OPERATIONS_LINE_PREFIX.length() + 2);
        final Operation operation;
        if (operator.equals("*") && operationValue.equals("old")) {
          operation = new Square();
        } else {
          operation =
              switch (operator) {
                case "+" -> new Add(Integer.parseInt(operationValue));
                case "*" -> new Multiply(Integer.parseInt(operationValue));
                default -> throw new IllegalStateException("Illegal operation: " + operationLine);
              };
        }

        final String testLine = scanner.nextLine();
        final int testDivisibleBy = Integer.parseInt(testLine.substring(TEST_LINE_PREFIX.length()));

        final String trueLine = scanner.nextLine();
        final int trueTarget = Integer.parseInt(trueLine.substring(TRUE_LINE_PREFIX.length()));

        final String falseLine = scanner.nextLine();
        final int falseTarget = Integer.parseInt(falseLine.substring(FALSE_LINE_PREFIX.length()));

        if (scanner.hasNextLine()) {
          scanner.skip(".*\n");
        }

        result.add(
            new Monkey(items, operation, new Test(testDivisibleBy, trueTarget, falseTarget)));
      }
    }

    return result;
  }

  static final class Monkey {
    private final Deque<BigInteger> items;
    private final Operation operation;
    private final Test test;

    private int numInspections = 0;

    Monkey(final List<Integer> items, final Operation operation, final Test test) {
      this.items = new LinkedList<>(items.stream().map(BigInteger::valueOf).toList());
      this.operation = operation;
      this.test = test;
    }

    private int numInspections() {
      return numInspections;
    }

    private void turn(
        final Function<Integer, Monkey> getMonkeyById,
        final Function<BigInteger, BigInteger> lowerWorryLevel) {
      while (!items.isEmpty()) {
        final BigInteger item = items.pop();

        numInspections++;
        final BigInteger worryLevel = lowerWorryLevel.apply(operation.apply(item));

        final int targetMonkey =
            worryLevel.mod(BigInteger.valueOf(test.divisibleBy())).equals(BigInteger.ZERO)
                ? test.trueTarget()
                : test.falseTarget();
        getMonkeyById.apply(targetMonkey).addItem(worryLevel);
      }
    }

    private void addItem(final BigInteger worryLevel) {
      items.add(worryLevel);
    }
  }

  sealed interface Operation {

    BigInteger apply(BigInteger value);

    record Add(int addend) implements Operation {
      @Override
      public BigInteger apply(final BigInteger value) {
        return value.add(BigInteger.valueOf(addend));
      }
    }

    record Multiply(int factor) implements Operation {
      @Override
      public BigInteger apply(final BigInteger value) {
        return value.multiply(BigInteger.valueOf(factor));
      }
    }

    record Square() implements Operation {
      @Override
      public BigInteger apply(final BigInteger value) {
        return value.multiply(value);
      }
    }
  }

  record Test(int divisibleBy, int trueTarget, int falseTarget) {}
}
