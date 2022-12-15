package us.byteb.advent.year2022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2022.Day13.*;
import static us.byteb.advent.year2022.Day13.Data.integer;
import static us.byteb.advent.year2022.Day13.Data.list;

import org.junit.jupiter.api.Test;

class Day13Test {

  private static final String EXAMPLE_DATA =
      """
      [1,1,3,1,1]
      [1,1,5,1,1]

      [[1],[2,3,4]]
      [[1],4]

      [9]
      [[8,7,6]]

      [[4,4],4,4]
      [[4,4],4,4,4]

      [7,7,7,7]
      [7,7,7]

      []
      [3]

      [[[]]]
      [[]]

      [1,[2,[3,[4,[5,6,7]]]],8,9]
      [1,[2,[3,[4,[5,6,0]]]],8,9]
      """;

  @Test
  void parseLine() {
    assertEquals(
        list(list(integer(1)), list(integer(2), integer(3), integer(4))),
        Data.parse("[[1],[2,3,4]]"));
    assertEquals(
        list(
            integer(1),
            list(
                integer(2),
                list(integer(3), list(integer(4), list(integer(5), integer(6), integer(7))))),
            integer(8),
            integer(9)),
        Data.parse("[1,[2,[3,[4,[5,6,7]]]],8,9]"));
  }

  @Test
  void parseInput() {
    assertEquals(8, parse(EXAMPLE_DATA).size());
  }

  @Test
  void partOneExample() {
    assertEquals(13, sumOfIndicesOfCorrectlyOrderedPairs(EXAMPLE_DATA));
  }

  @Test
  void partTwoExample() {
    assertEquals(140L, findDecodeKey(EXAMPLE_DATA, DIVIDER_PACKETS));
  }

  @Test
  void compareData() {
    assertEquals(
        -1, Data.parse("[1,1,3,1,1]").compareTo(Data.parse("[1,[2,[3,[4,[5,6,0]]]],8,9]")));
    assertEquals(1, Data.parse("[[1],[2,3,4]]").compareTo(Data.parse("[1,1,3,1,1]")));
  }
}
