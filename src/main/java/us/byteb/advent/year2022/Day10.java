package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;
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
  }

  public static Map<Integer, Integer> signalStrengthsDuringCycles(final String input) {
    final List<Instruction> instructions = parseInput(input);
    final Map<Integer, Integer> cyclesToSignalStrength = new HashMap<>();

    int registerX = 1;
    int cycle = 0;

    for (final Instruction instruction : instructions) {
      for (int c = 0; c < instruction.cycles(); c++) {
        cycle++;
        cyclesToSignalStrength.put(cycle, cycle * registerX);
      }

      registerX =
          switch (instruction) {
            case NoOp ignored -> registerX;
            case AddX addX -> registerX + addX.value();
          };
    }

    return cyclesToSignalStrength;
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
