package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2022.Day10.*;

import java.util.Map;
import org.junit.jupiter.api.Test;

class Day10Test {

  final String PART_ONE_EXAMPLE_DATA = readFileFromResources("year2022/day10.txt");

  @Test
  void partOneExample() {
    final Map<Integer, Integer> result = signalStrengthsDuringCycles(PART_ONE_EXAMPLE_DATA);
    assertEquals(
        13140,
        result.get(20)
            + result.get(60)
            + result.get(100)
            + result.get(140)
            + result.get(180)
            + result.get(220));
  }
}
