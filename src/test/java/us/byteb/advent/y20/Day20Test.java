package us.byteb.advent.y20;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.y20.Day20.findEdges;
import static us.byteb.advent.y20.Day20.parseTiles;

import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import us.byteb.advent.y20.Day20.Tile;

class Day20Test {

  final String PART1_EXAMPLE_INPUT = readFileFromResources("day20-example.txt");

  @Test
  void parseSingleTile() {
    final String firstTile =
        PART1_EXAMPLE_INPUT.lines().takeWhile(StringUtils::isNotBlank).collect(joining("\n"));

    final Tile actual = Tile.parse(firstTile);

    assertEquals(
        new Tile(
            2311,
            new boolean[][] {
              {false, false, true, true, false, true, false, false, true, false},
              {true, true, false, false, true, false, false, false, false, false},
              {true, false, false, false, true, true, false, false, true, false},
              {true, true, true, true, false, true, false, false, false, true},
              {true, true, false, true, true, false, true, true, true, false},
              {true, true, false, false, false, true, false, true, true, true},
              {false, true, false, true, false, true, false, false, true, true},
              {false, false, true, false, false, false, false, true, false, false},
              {true, true, true, false, false, false, true, false, true, false},
              {false, false, true, true, true, false, false, true, true, true},
            }),
        actual);
  }

  @Test
  void parsePart1ExampleTileSet() {
    assertEquals(9, parseTiles(PART1_EXAMPLE_INPUT).size());
  }

  @Test
  void findPart1ExampleEdges() {
    final Set<Tile> tiles = parseTiles(PART1_EXAMPLE_INPUT);
    final Set<Tile> edgeTiles = findEdges(tiles);
    assertEquals(Set.of(1951, 3079, 2971, 1171), edgeTiles.stream().map(Tile::id).collect(toSet()));
    assertEquals(
        20899048083289L, edgeTiles.stream().mapToLong(Tile::id).reduce(1, (a, b) -> a * b));
  }
}
