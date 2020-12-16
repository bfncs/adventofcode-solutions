package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.y20.Day13.*;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import us.byteb.advent.y20.Day13.BusIdCandidate.BusId;

class Day13Test {

  final String INPUT = """
      939
      7,13,x,x,59,x,31,19
      """;

  @Test
  void part1ExampleFindDeparture() {
    assertEquals(
        new Day13.Departure(new BusId(59), 944), findEarliestPossibleDeparture(parseInput(INPUT)));
  }

  @ParameterizedTest
  @CsvSource({
    "3417,'17,x,13,19'",
    "754018,'67,7,59,61'",
    "779210,'67,x,7,59,61'",
    "1261476,'67,7,x,59,61'",
    "1202161486,'1789,37,47,1889'"
  })
  void part2ExampleP(final long expectedResult, final String input) {
    assertEquals(
        BigInteger.valueOf(expectedResult),
        findEarliestBusIdWithOffsetMatchingPosition(parseBusIdCandidates(input)));
  }
}
