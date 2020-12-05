package us.byteb.advent.twenty;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Day5Test {

  @Test
  void parseSeatCode() {
    assertEquals(new Day5.Seat(44, 5), Day5.Seat.of("FBFBBFFRLR"));
    assertEquals(new Day5.Seat(70, 7), Day5.Seat.of("BFFFBBFRRR"));
    assertEquals(new Day5.Seat(14, 7), Day5.Seat.of("FFFBBBFRRR"));
    assertEquals(new Day5.Seat(102, 4), Day5.Seat.of("BBFFBBFRLL"));
  }

  @Test
  void calculateSeatId() {
    assertEquals(357, Day5.Seat.of("FBFBBFFRLR").calcSeatId());
    assertEquals(567, Day5.Seat.of("BFFFBBFRRR").calcSeatId());
    assertEquals(119, Day5.Seat.of("FFFBBBFRRR").calcSeatId());
    assertEquals(820, Day5.Seat.of("BBFFBBFRLL").calcSeatId());
  }
}
