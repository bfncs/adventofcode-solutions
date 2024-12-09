package us.byteb.advent.year2019;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2019.Day05.ParameterMode.IMMEDIATE;
import static us.byteb.advent.year2019.Day05.ParameterMode.POSITION;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day05 {

  public static void main(String[] args) {
    final List<Integer> program = parseInput(readFileFromResources("year2019/day05.txt"));
    // System.out.println("Part 1: " + executeProgram(program, 1));
    System.out.println("Part 2: " + executeProgram(program, 5));
  }

  static List<Integer> parseInput(final String input) {
    return Arrays.stream(input.split(",")).map(Integer::parseInt).collect(Collectors.toList());
  }

  static int executeProgram(final List<Integer> program, final int input) {
    int pos = 0;
    int output = Integer.MIN_VALUE;

    while (pos < program.size()) {
      System.out.println(programSnippetAroundPosition(program, pos));
      final OpCandidate op = OpCandidate.parse(program.get(pos++));
      System.out.printf("%d: %s%n", pos, op);
      switch (op.op()) {
        case 1 -> {
          final int addend1 = op.pm1().parameter(program.get(pos++)).resolve(program);
          final int addend2 = op.pm2().parameter(program.get(pos++)).resolve(program);
          final int result = addend1 + addend2;
          final Integer writePos = program.get(pos++);
          System.out.println(
              "  ADD: " + addend1 + " + " + addend2 + " = " + result + " >> " + writePos);
          program.set(writePos, result);
        }
        case 2 -> {
          final int factor1 = op.pm1().parameter(program.get(pos++)).resolve(program);
          final int factor2 = op.pm2().parameter(program.get(pos++)).resolve(program);
          final int result = factor1 * factor2;
          final int writePos = program.get(pos++);
          System.out.println(
              "  MULT: " + factor1 + " * " + factor2 + " = " + result + " >> " + writePos);
          program.set(writePos, result);
        }
        case 3 -> {
          final int writePos = program.get(pos++);
          System.out.printf("  INPUT: Set pos %d to input %d%n", writePos, input);
          program.set(writePos, input);
        }
        case 4 -> {
          final int readPos = program.get(pos++);
          final int readValue = program.get(readPos);
          System.out.printf("  OUTPUT: Set output to value %d from pos %d%n", readValue, readPos);
          output = readValue;
        }
        case 5 -> {
          final boolean shouldJump = op.pm1().parameter(program.get(pos++)).resolve(program) > 0;
          final int jumpPos = op.pm2().parameter(program.get(pos++)).resolve(program);
          System.out.println("  JUMP-IF-TRUE: " + (shouldJump ? " jump to " + jumpPos : " nop"));
          if (shouldJump) {
            pos = jumpPos;
          }
        }
        case 6 -> {
          final boolean shouldJump = op.pm1().parameter(program.get(pos++)).resolve(program) == 0;
          final int jumpPos = op.pm2().parameter(program.get(pos++)).resolve(program);
          System.out.println("  JUMP-IF-FALSE: " + (shouldJump ? " jump to " + jumpPos : " nop"));
          if (shouldJump) {
            pos = jumpPos;
          }
        }
        case 7 -> {
          final int param1 = op.pm1().parameter(program.get(pos++)).resolve(program);
          final int param2 = op.pm2().parameter(program.get(pos++)).resolve(program);
          final int result = (param1 < param2) ? 1 : 0;
          final int writePos = program.get(pos++);
          System.out.printf("  LESS THAN: %d < %d ? %d >> %d%n", param1, param2, result, writePos);
          program.set(writePos, result);
        }
        case 8 -> {
          final int param1 = op.pm1().parameter(program.get(pos++)).resolve(program);
          final int param2 = op.pm2().parameter(program.get(pos++)).resolve(program);
          final int result = (param1 == param2) ? 1 : 0;
          final int writePos = program.get(pos++);
          System.out.printf("  EQUALS: %d == %d ? %d >> %d%n", param1, param2, result, writePos);
          program.set(writePos, result);
        }
        case 99 -> {
          System.out.println("HALT");
          return output;
        }
        default ->
            throw new UnsupportedOperationException(
                "Unknown op candidate %s at pos %d before end".formatted(op, pos));
      }
    }

    throw new IllegalStateException("Reached end but program did not halt");
  }

  private static String programSnippetAroundPosition(final List<Integer> program, final int pos) {
    return IntStream.range(Math.max(0, pos - 10), Math.min(program.size() - 1, pos + 10))
        .mapToObj(
            p ->
                p == pos
                    ? ">>>%s<<<".formatted(program.get(p).toString())
                    : program.get(p).toString())
        .collect(Collectors.joining(","));
  }

  record OpCandidate(int op, ParameterMode pm1, ParameterMode pm2, ParameterMode pm3) {
    static OpCandidate parse(final int candidate) {
      final int op = candidate % 100;
      final ParameterMode pm1 = (((candidate / 100) % 10) == 0) ? POSITION : IMMEDIATE;
      final ParameterMode pm2 = (((candidate / 1000) % 10) == 0) ? POSITION : IMMEDIATE;
      final ParameterMode pm3 = (((candidate / 10000) % 10) == 0) ? POSITION : IMMEDIATE;
      return new OpCandidate(op, pm1, pm2, pm3);
    }
  }

  enum ParameterMode {
    POSITION,
    IMMEDIATE;

    Parameter parameter(final int value) {
      return new Parameter(this, value);
    }
  }

  record Parameter(ParameterMode mode, int value) {
    int resolve(final List<Integer> program) {
      return switch (mode) {
        case IMMEDIATE:
          yield value;
        case POSITION:
          yield program.get(value);
      };
    }
  }
}
