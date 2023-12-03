package us.byteb.advent.year2016;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.year2016.Day04.*;

import java.util.Set;
import org.junit.jupiter.api.Test;

class Day04Test {

  private static final String PART1_DATA =
      """
          aaaaa-bbb-z-y-x-123[abxyz]
          a-b-c-d-e-f-g-h-987[abcde]
          not-a-real-room-404[oarel]
          totally-real-room-200[decoy]
          """;

  @Test
  void partOneExample() {
    final Set<Room> rooms = parseInput(PART1_DATA);
    assertEquals(1514L, sumOfSectorIdsOfRealRooms(rooms));
  }
}
