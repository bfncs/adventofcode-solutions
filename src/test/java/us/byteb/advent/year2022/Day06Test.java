package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day06.findPosAfterFirstMessageStartMarker;
import static us.byteb.advent.year2022.Day06.findPosAfterFirstPacketStartMarker;

import org.junit.jupiter.api.Test;

class Day06Test {

  @Test
  void partOneExample() {
    assertEquals(7, findPosAfterFirstPacketStartMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
    assertEquals(5, findPosAfterFirstPacketStartMarker("bvwbjplbgvbhsrlpgdmjqwftvncz"));
    assertEquals(6, findPosAfterFirstPacketStartMarker("nppdvjthqldpwncqszvftbrmjlhg"));
    assertEquals(10, findPosAfterFirstPacketStartMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
    assertEquals(11, findPosAfterFirstPacketStartMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
  }

  @Test
  void partTwoExample() {
    assertEquals(19, findPosAfterFirstMessageStartMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
    assertEquals(23, findPosAfterFirstMessageStartMarker("bvwbjplbgvbhsrlpgdmjqwftvncz"));
    assertEquals(23, findPosAfterFirstMessageStartMarker("nppdvjthqldpwncqszvftbrmjlhg"));
    assertEquals(29, findPosAfterFirstMessageStartMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
    assertEquals(26, findPosAfterFirstMessageStartMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
  }
}
