package us.byteb.advent.y19;

import static java.util.Collections.emptyList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

  static List<Instruction> parseProgram(final String input) {
    final List<Integer> codes =
        Arrays.stream(input.split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toUnmodifiableList());

    int pos = 0;

    // ...

    return emptyList();
  }

  interface Instruction {}

  record Add(Parameter addend1, Parameter addend2, Parameter store) implements Instruction {}

  record Mult(Parameter factor1, Parameter factor2, Parameter store) implements Instruction {}

  record Input(Parameter param) implements Instruction {}

  record Output(Parameter param) implements Instruction {}

  record Halt() implements Instruction {}

  interface Parameter {
    static PositionParameter position(int value) {
      return new PositionParameter(value);
    }

    static ImmediateParameter immediate(int value) {
      return new ImmediateParameter(value);
    }
  }

  record PositionParameter(int value) implements Parameter {}

  record ImmediateParameter(int value) implements Parameter {}
}
