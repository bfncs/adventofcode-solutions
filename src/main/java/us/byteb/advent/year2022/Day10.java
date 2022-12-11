package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
import java.util.stream.Collectors;
import us.byteb.advent.year2022.Day10.Instruction.AddX;
import us.byteb.advent.year2022.Day10.Instruction.NoOp;

public class Day10 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day10.txt");

    final Map<Integer, Integer> result = signalStrengthsDuringCycles(input);
    System.out.println(
        "Part 1: "
            + (result.get(20)
                + result.get(60)
                + result.get(100)
                + result.get(140)
                + result.get(180)
                + result.get(220)));

    System.out.println("Part 2:\n" + renderCrt(input));
  }

  private static Map<Integer, Integer> registerValueDuringCycles(final String input) {
    final List<Instruction> instructions = parseInput(input);
    final Map<Integer, Integer> cyclesToRegisterXValue = new HashMap<>();

    int registerX = 1;
    int cycle = 0;

    for (final Instruction instruction : instructions) {
      for (int c = 0; c < instruction.cycles(); c++) {
        cycle++;
        cyclesToRegisterXValue.put(cycle, registerX);
      }

      registerX =
          switch (instruction) {
            case NoOp ignored -> registerX;
            case AddX addX -> registerX + addX.value();
          };
    }

    return cyclesToRegisterXValue;
  }

  public static Map<Integer, Integer> signalStrengthsDuringCycles(final String input) {
    return registerValueDuringCycles(input).entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getKey() * e.getValue()));
  }

  public static List<Instruction> parseInput(final String input) {
    return input
        .lines()
        .<Instruction>map(
            line -> {
              if (line.equals("noop")) {
                return new NoOp();
              } else {
                return new AddX(Integer.parseInt(line.substring(5)));
              }
            })
        .toList();
  }

  public static String renderCrt(final String input) {
    final Map<Integer, Integer> cyclesToRegisterXValue = registerValueDuringCycles(input);
    final int numRows = 6;
    final int numColumns = 40;

    final StringBuilder result = new StringBuilder();
    for (int position = 0; position < (numRows * numColumns); position++) {
      final int registerX = cyclesToRegisterXValue.get(position + 1);
      final int positionInRow = position % numColumns;
      result.append((positionInRow >= registerX - 1 && positionInRow <= registerX + 1) ? '#' : '.');

      if (position % numColumns == (numColumns - 1)) {
        result.append("\n");
      }
    }

    return result.toString();
  }

  sealed interface Instruction {
    int cycles();

    record AddX(int value) implements Instruction {

      @Override
      public int cycles() {
        return 2;
      }
    }

    record NoOp() implements Instruction {
      @Override
      public int cycles() {
        return 1;
      }
    }
  }
}
