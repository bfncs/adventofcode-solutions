package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day05.*;
import static us.byteb.advent.year2022.Day06.findPosAfterFirstStartMarker;

import org.junit.jupiter.api.Test;

class Day06Test {

  @Test
  void partOneExample() {
    assertEquals(7, findPosAfterFirstStartMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
    assertEquals(5, findPosAfterFirstStartMarker("bvwbjplbgvbhsrlpgdmjqwftvncz"));
    assertEquals(6, findPosAfterFirstStartMarker("nppdvjthqldpwncqszvftbrmjlhg"));
    assertEquals(10, findPosAfterFirstStartMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
    assertEquals(11, findPosAfterFirstStartMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
  }
}
