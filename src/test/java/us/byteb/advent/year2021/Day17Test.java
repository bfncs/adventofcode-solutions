package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2021.Day17.findInitialVelocityWithMaxYPosition;

import org.junit.jupiter.api.Test;
import us.byteb.advent.year2021.Day17.*;

class Day17Test {

  @Test
  void example1() {
    final TargetArea targetArea = TargetArea.parse("target area: x=20..30, y=-10..-5");
    assertEquals(new Result(6, 9, 45), findInitialVelocityWithMaxYPosition(targetArea));
  }
}
