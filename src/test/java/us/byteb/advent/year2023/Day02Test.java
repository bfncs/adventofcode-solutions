package us.byteb.advent.year2023;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2023.Day02.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day02Test {

  private static final List<Game> part1ExampleDate =
      parseInput(
          """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
            """);

  @Test
  void partOneExample() {
    assertEquals(8L, sumOfIdsOfPossibleGames(part1ExampleDate, 12, 13, 14));
  }
}
