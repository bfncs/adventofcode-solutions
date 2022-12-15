package us.byteb.advent.year2022;

import static java.lang.Integer.parseInt;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2022.Day13.Data.OrderCheckResult.*;

import java.util.*;
import java.util.stream.Collectors;

public class Day13 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day13.txt");

    System.out.println("Part 1:" + sumOfIndicesOfCorrectlyOrderedPairs(input));
  }

  public static int sumOfIndicesOfCorrectlyOrderedPairs(final String input) {
    final List<Pair> pairs = parse(input);

    int sum = 0;
    for (int i = 0; i < pairs.size(); i++) {
      System.out.println("=== PAIR " + (i + 1) + " ==");
      if (pairs.get(i).checkOrder()) {
        sum += i + 1;
      }
    }

    return sum;
  }

  public static List<Pair> parse(final String input) {
    final List<Pair> result = new ArrayList<>();

    try (Scanner scanner = new Scanner(input)) {
      while (scanner.hasNextLine()) {
        final Data left = Data.parse(scanner.nextLine());
        final Data right = Data.parse(scanner.nextLine());
        result.add(new Pair(left, right));

        if (scanner.hasNextLine()) scanner.skip("\\s*\n");
      }
    }

    return Collections.unmodifiableList(result);
  }

  record Pair(Data left, Data right) {
    public boolean checkOrder() {
      return Data.checkOrder(left, right) == CORRECT;
    }
  }

  sealed interface Data {
    enum OrderCheckResult {
      CORRECT,
      INCORRECT,
      INDIFFERENT
    }

    static OrderCheckResult checkOrder(final Data left, final Data right) {
      System.out.println("Compare " + left + " vs " + right);
      if (left instanceof DataInteger leftInt && right instanceof DataInteger rightInt) {
        if (leftInt.value() > rightInt.value()) {
          System.out.println("Right side is smaller, so incorrect");
          return INCORRECT;
        }
        if (leftInt.value() < rightInt.value()) {
          System.out.println("Left side is smaller, so correct");
          return CORRECT;
        }
        return INDIFFERENT;
      }

      if ((left instanceof DataList leftList) && (right instanceof DataList rightList)) {
        for (int i = 0; i < leftList.data().size(); i++) {
          if (i >= rightList.data().size()) {
            System.out.println("Right side is smaller, so " + INCORRECT);
            return INCORRECT;
          }
          final Data leftItem = leftList.data().get(i);
          final Data rightItem = rightList.data().get(i);
          final OrderCheckResult itemResult = checkOrder(leftItem, rightItem);
          if (itemResult != INDIFFERENT) {
            return itemResult;
          }
        }

        System.out.println("Left side is smaller, so " + CORRECT);
        return CORRECT;
      }

      if (left instanceof DataInteger) {
        System.out.println("Mixed types, convert " + left + " to List and retry");
        return checkOrder(Data.list(left), right);
      } else {
        System.out.println("Mixed types, convert " + right + " to List and retry");
        return checkOrder(left, Data.list(right));
      }
    }

    static Data parse(final String input) {
      int current = 0;
      if (input.charAt(current) == '[') {
        current++;
        final List<Data> items = new ArrayList<>();
        int depth = 0;
        int start = current;
        while (current < input.length()) {
          switch (input.charAt(current)) {
            case '[' -> depth++;
            case ']' -> {
              if (depth-- == 0) {
                if (start != current) {
                  items.add(Data.parse(input.substring(start, current)));
                }
                return new Data.DataList(items);
              }
            }
            case ',' -> {
              if (depth == 0) {
                items.add(Data.parse(input.substring(start, current)));
                start = current + 1;
              }
            }
          }
          current++;
        }
      } else {
        return new DataInteger(parseInt(input));
      }

      throw new IllegalStateException("Unexpected input: " + input);
    }

    static DataList list(final Data... data) {
      return new DataList(Arrays.stream(data).toList());
    }

    static DataInteger integer(final int value) {
      return new DataInteger(value);
    }

    record DataList(List<Data> data) implements Data {
      @Override
      public String toString() {
        return "[%s]"
            .formatted(data.stream().map(Object::toString).collect(Collectors.joining(",")));
      }
    }

    record DataInteger(int value) implements Data {
      @Override
      public String toString() {
        return String.valueOf(value);
      }
    }
  }
}
