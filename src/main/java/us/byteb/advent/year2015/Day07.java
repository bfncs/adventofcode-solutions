package us.byteb.advent.year2015;

import static java.lang.Integer.parseInt;
import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import us.byteb.advent.year2015.Day07.Operand.Wire;

public class Day07 {

  public static final int INTEGER_MAX_VALUE_16BIT = 65535;
  private static Map<Operation, Integer> OPERATION_RESULT_CACHE = new HashMap<>();

  public static void main(String[] args) {
    final List<Instruction> instructions = parseInput(readFileFromResources("year2015/day07.txt"));

    final Wire wireA = new Wire("a");
    final int wireAResult = evaluateWire(instructions, wireA);
    System.out.println("Part 1: " + wireAResult);

    OPERATION_RESULT_CACHE = new HashMap<>();
    final Wire wireB = new Wire("b");
    final List<Instruction> modifiedInstructions =
        replaceInstruction(instructions, wireB, wireAResult);

    System.out.println("Part 2: " + evaluateWire(modifiedInstructions, wireA));
  }

  private static List<Instruction> replaceInstruction(
      final List<Instruction> instructions, final Wire wire, final int value) {
    final List<Instruction> modifiedInstructions =
        new ArrayList<>(instructions.stream().filter(i -> !i.target().equals(wire)).toList());
    modifiedInstructions.add(new Instruction(new Operation.Signal(new Operand.Value(value)), wire));

    return modifiedInstructions;
  }

  static List<Instruction> parseInput(final String input) {
    return input.lines().map(Instruction::parse).toList();
  }

  static int evaluateWire(final List<Instruction> instructions, final Wire wire) {
    final Instruction instruction = instructionForWire(instructions, wire);
    return instruction.op().eval(instructions);
  }

  private static Instruction instructionForWire(
      final List<Instruction> instructions, final Wire wire) {
    return instructions.stream()
        .filter(i -> i.target().equals(wire))
        .findFirst()
        .orElseThrow(
            () -> new IllegalStateException("No instruction found for wire " + wire.name()));
  }

  private static int memoize(final Operation name, IntSupplier fn) {
    if (!OPERATION_RESULT_CACHE.containsKey(name)) {
      final int result = fn.getAsInt();
      OPERATION_RESULT_CACHE.put(name, result);
    }

    return OPERATION_RESULT_CACHE.get(name);
  }

  record Instruction(Operation op, Wire target) {
    static Instruction parse(final String input) {
      final String[] parts = input.split(" -> ");
      if (parts.length != 2) throw new IllegalStateException();
      return new Instruction(Operation.parse(parts[0]), new Wire(parts[1]));
    }
  }

  interface Operation {
    static Operation parse(String input) {
      final String[] parts = input.split("\s+");
      return switch (parts.length) {
        case 2 -> {
          if (!parts[0].equals("NOT")) throw new IllegalStateException();
          yield new Not(Operand.parse(parts[1]));
        }
        case 3 -> {
          final Operand operand1 = Operand.parse(parts[0]);
          yield switch (parts[1]) {
            case "AND" -> new And(operand1, Operand.parse(parts[2]));
            case "OR" -> new Or(operand1, Operand.parse(parts[2]));
            case "LSHIFT" -> new LShift(operand1, parseInt(parts[2]));
            case "RSHIFT" -> new RShift(operand1, parseInt(parts[2]));
            default -> throw new IllegalStateException();
          };
        }
        default -> new Signal(Operand.parse(input));
      };
    }

    int eval(List<Instruction> instructions);

    record Signal(Operand input) implements Operation {
      @Override
      public int eval(final List<Instruction> instructions) {
        final int result = memoize(this, () -> input.eval(instructions));
        return result;
      }
    }

    record And(Operand op1, Operand op2) implements Operation {
      @Override
      public int eval(final List<Instruction> instructions) {
        final int result = memoize(this, () -> op1.eval(instructions) & op2.eval(instructions));
        return result;
      }
    }

    record Or(Operand op1, Operand op2) implements Operation {
      @Override
      public int eval(final List<Instruction> instructions) {
        final int result = memoize(this, () -> op1.eval(instructions) | op2.eval(instructions));
        return result;
      }
    }

    record LShift(Operand input, int positions) implements Operation {
      @Override
      public int eval(final List<Instruction> instructions) {
        final int result =
            memoize(this, () -> input.eval(instructions) << positions & INTEGER_MAX_VALUE_16BIT);
        return result;
      }
    }

    record RShift(Operand input, int positions) implements Operation {
      @Override
      public int eval(final List<Instruction> instructions) {
        final int result =
            memoize(this, () -> input.eval(instructions) >> positions & INTEGER_MAX_VALUE_16BIT);
        return result;
      }
    }

    record Not(Operand input) implements Operation {
      @Override
      public int eval(final List<Instruction> instructions) {
        final int result = memoize(this, () -> ~input.eval(instructions) & INTEGER_MAX_VALUE_16BIT);
        return result;
      }
    }
  }

  interface Operand {
    static Operand parse(final String input) {
      try {
        return new Value(Integer.parseInt(input));
      } catch (NumberFormatException e) {
        return new Wire(input);
      }
    }

    int eval(List<Instruction> instructions);

    record Value(int value) implements Operand {
      @Override
      public int eval(final List<Instruction> instructions) {
        return value;
      }
    }

    record Wire(String name) implements Operand {
      @Override
      public int eval(final List<Instruction> instructions) {
        return instructionForWire(instructions, this).op().eval(instructions);
      }
    }
  }
}
