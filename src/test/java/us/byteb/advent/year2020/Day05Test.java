package us.byteb.advent.year2020;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Day05Test {

  @Test
  void parseSeatCode() {
    assertEquals(new Day05.Seat(44, 5), Day05.Seat.of("FBFBBFFRLR"));
    assertEquals(new Day05.Seat(70, 7), Day05.Seat.of("BFFFBBFRRR"));
    assertEquals(new Day05.Seat(14, 7), Day05.Seat.of("FFFBBBFRRR"));
    assertEquals(new Day05.Seat(102, 4), Day05.Seat.of("BBFFBBFRLL"));
  }

  @Test
  void calculateSeatId() {
    assertEquals(357, Day05.Seat.of("FBFBBFFRLR").calcSeatId());
    assertEquals(567, Day05.Seat.of("BFFFBBFRRR").calcSeatId());
    assertEquals(119, Day05.Seat.of("FFFBBBFRRR").calcSeatId());
    assertEquals(820, Day05.Seat.of("BBFFBBFRLL").calcSeatId());
  }
}
