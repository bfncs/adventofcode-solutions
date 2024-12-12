package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day10.*;

import org.junit.jupiter.api.Test;

class Day10Test {

  @Test
  void partOneExample() {
    assertEquals(
        2L,
        sumOfTrailheadScores(
            """
              ...0...
              ...1...
              ...2...
              6543456
              7.....7
              8.....8
              9.....9
              """));
    assertEquals(
        36L,
        sumOfTrailheadScores(
            """
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732
                """));
  }

  @Test
  void partTwoExample() {
    assertEquals(
        3L,
        sumOfTrailheadRatings(
            """
                .....0.
                ..4321.
                ..5..2.
                ..6543.
                ..7..4.
                ..8765.
                ..9....
                """));
    assertEquals(
        13L,
        sumOfTrailheadRatings(
            """
                ..90..9
                ...1.98
                ...2..7
                6543456
                765.987
                876....
                987....
                """));
  }
}
