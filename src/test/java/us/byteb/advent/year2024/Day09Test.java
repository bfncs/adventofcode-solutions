package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day09.*;
import static us.byteb.advent.year2024.Day09.BlockRange.content;
import static us.byteb.advent.year2024.Day09.BlockRange.free;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day09Test {

  private static final String EXAMPLE_DATA = "2333133121414131402";

  @Test
  void parse() {
    final List<BlockRange> input = parseInput(EXAMPLE_DATA);
    assertEquals(
        List.of(
            content(0, 2),
            free(3),
            content(1, 3),
            free(3),
            content(2, 1),
            free(3),
            content(3, 3),
            free(1),
            content(4, 2),
            free(1),
            content(5, 4),
            free(1),
            content(6, 4),
            free(1),
            content(7, 3),
            free(1),
            content(8, 4),
            free(0),
            content(9, 2)),
        input);
  }

  @Test
  void partOneExample() {
    assertEquals(
        List.of(content(0, 1), content(2, 2), content(1, 3), content(2, 3), free(6)),
        compact(parseInput("12345")));

    assertEquals(1928L, checksum(compact(parseInput(EXAMPLE_DATA))));
  }
}
