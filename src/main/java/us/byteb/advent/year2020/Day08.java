package us.byteb.advent.year2020;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day08 {

  public static void main(String[] args) {
    final List<Instruction> program = parseProgram(readFileFromResources("year2020/day08.txt"));

    System.out.println("Part 1: " + executeUntilFirstInstructionWouldBeExecutedTwice(program));
    System.out.println("Part 2: " + executeUntilTerminatesWithFix(program));
  }

  static List<Instruction> parseProgram(final String input) {
    return input
        .lines()
        .map(
            line -> {
              final String[] parts = line.split("\s+");
              return Instruction.of(parts[0], Integer.parseInt(parts[1]));
            })
        .collect(Collectors.toList());
  }

  static int executeUntilFirstInstructionWouldBeExecutedTwice(final List<Instruction> program) {
    int pointer = 0;
    int acc = 0;
    final Set<Integer> visitedInstructions = new HashSet<>();

    while (!visitedInstructions.contains(pointer)) {
      visitedInstructions.add(pointer);
      final Instruction currentInstruction = program.get(pointer);
      final Instruction.Result result = currentInstruction.execute(pointer, acc);
      pointer = result.pointer();
      acc = result.acc();
    }

    return acc;
  }

  public static int executeUntilTerminatesWithFix(final List<Instruction> program) {
    int lastFixAttemptPosition = 0;

    while (true) {
      int pointer = 0;
      int acc = 0;
      boolean fixAttempted = false;
      final List<Integer> visitedInstructions = new ArrayList<>();

      while (!visitedInstructions.contains(pointer)) {
        if (pointer >= program.size()) {
          return acc;
        }

        visitedInstructions.add(pointer);
        Instruction currentInstruction = program.get(pointer);

        final boolean shouldFix =
            !fixAttempted
                && visitedInstructions.size() > lastFixAttemptPosition + 1
                && (currentInstruction instanceof Nop || currentInstruction instanceof Jmp);
        if (shouldFix) {
          if (currentInstruction instanceof Nop nop) {
            currentInstruction = new Jmp(nop.num());
          } else if (currentInstruction instanceof Jmp jmp) {
            currentInstruction = new Nop(jmp.target());
          }
          lastFixAttemptPosition = visitedInstructions.size() - 1;
          fixAttempted = true;
        }

        if (currentInstruction.equals(new Jmp(0))) {
          break;
        }

        final Instruction.Result result = currentInstruction.execute(pointer, acc);
        pointer = result.pointer();
        acc = result.acc();
      }
    }
  }

  interface Instruction {
    static Instruction of(final String opType, final int num) {
      return switch (opType) {
        case "nop":
          yield new Nop(num);
        case "acc":
          yield new Acc(num);
        case "jmp":
          yield new Jmp(num);
        default:
          throw new IllegalStateException("Unknown op type: " + opType);
      };
    }

    Result execute(final int pointer, final int acc);

    record Result(int pointer, int acc) {}
  }

  record Nop(int num) implements Instruction {
    @Override
    public Result execute(final int pointer, final int acc) {
      return new Result(pointer + 1, acc);
    }
  }

  record Acc(int adder) implements Instruction {
    @Override
    public Result execute(final int pointer, final int acc) {
      return new Result(pointer + 1, acc + adder);
    }
  }

  record Jmp(int target) implements Instruction {
    @Override
    public Result execute(final int pointer, final int acc) {
      return new Result(pointer + target, acc);
    }
  }
}
