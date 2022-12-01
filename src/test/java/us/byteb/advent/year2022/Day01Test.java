package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day01.findMaxCalories;
import static us.byteb.advent.year2022.Day01.parseInput;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.year2022.Day01.Elf;

class Day01Test {

  private static final List<Elf> part1ExampleDate =
      parseInput(
          """
           1000
           2000
           3000

           4000

           5000
           6000

           7000
           8000
           9000

           10000
            """);

  @Test
  void partOneExample() {
    assertEquals(24000L, findMaxCalories(part1ExampleDate));
  }
}
