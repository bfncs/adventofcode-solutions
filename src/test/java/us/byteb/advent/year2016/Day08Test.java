package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2016.Day07.*;

import org.junit.jupiter.api.Test;
import us.byteb.advent.year2016.Day08.Screen;

class Day08Test {

  @Test
  void partOneExample() {
    Screen screen = new Screen(3, 7);
    assertEquals(
        """
        ###....
        ###....
        .......
        """,
        screen.exec("rect 3x2").prettyPrint());
    assertEquals(
        """
          #.#....
          ###....
          .#.....
          """,
        screen.exec("rotate column x=1 by 1").prettyPrint());
    assertEquals(
        """
          ....#.#
          ###....
          .#.....
          """,
        screen.exec("rotate row y=0 by 4").prettyPrint());
    assertEquals(
        """
          .#..#.#
          #.#....
          .#.....
          """,
        screen.exec("rotate column x=1 by 1").prettyPrint());
  }
}
