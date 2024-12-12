package us.byteb.advent.year2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2024.Day12.*;

import java.util.Set;
import org.junit.jupiter.api.Test;

class Day12Test {

  private static final String EXAMPLE1 =
      """
          AAAA
          BBCD
          BBCC
          EEEC
          """;

  @Test
  void partOneExample() {
    final Set<Region> regions = findRegions(EXAMPLE1);
    assertEquals(
        Set.of(
            new Region('A', p(0, 0), p(0, 1), p(0, 2), p(0, 3)),
            new Region('B', p(1, 0), p(1, 1), p(2, 0), p(2, 1)),
            new Region('C', p(1, 2), p(2, 2), p(2, 3), p(3, 3)),
            new Region('D', p(1, 3)),
            new Region('E', p(3, 0), p(3, 1), p(3, 2))),
        regions);
    assertEquals(140L, totalPriceOfFencing(regions));
  }
}
