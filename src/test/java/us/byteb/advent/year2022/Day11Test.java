package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2022.Day11.*;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class Day11Test {

  final String EXAMPLE_DATA = readFileFromResources("year2022/day11.txt");

  @Test
  void partOneExample() {
    assertEquals(BigInteger.valueOf(10605), monkeyBusiness(EXAMPLE_DATA, 20, true));
  }

  @Test
  void partTwoExample() {
    assertEquals(new BigInteger("2713310158"), monkeyBusiness(EXAMPLE_DATA, 10000, false));
  }
}
