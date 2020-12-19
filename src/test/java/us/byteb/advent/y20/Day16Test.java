package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.y20.Day16.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import us.byteb.advent.y20.Day16.Input;
import us.byteb.advent.y20.Day16.Range;
import us.byteb.advent.y20.Day16.Ticket;
import us.byteb.advent.y20.Day16.ValidationResult;

class Day16Test {

  public static final String INPUT =
      """
          class: 1-3 or 5-7
          row: 6-11 or 33-44
          seat: 13-40 or 45-50

          your ticket:
          7,1,14

          nearby tickets:
          7,3,47
          40,4,50
          55,2,20
          38,6,12
          """;

  @Test
  void parseExample1() {
    assertEquals(
        new Input(
            Map.of(
                "class", List.of(new Range(1, 3), new Range(5, 7)),
                "row", List.of(new Range(6, 11), new Range(33, 44)),
                "seat", List.of(new Range(13, 40), new Range(45, 50))),
            Ticket.of(7, 1, 14),
            List.of(
                Ticket.of(7, 3, 47),
                Ticket.of(40, 4, 50),
                Ticket.of(55, 2, 20),
                Ticket.of(38, 6, 12))),
        parseInput(INPUT));
  }

  @Test
  void part1Example() {
    assertEquals(
        List.of(
            new ValidationResult(Ticket.of(40, 4, 50), Optional.of(4)),
            new ValidationResult(Ticket.of(55, 2, 20), Optional.of(55)),
            new ValidationResult(Ticket.of(38, 6, 12), Optional.of(12))),
        findTicketsWithValuesNotValidForAnyField(parseInput(INPUT)));
  }

  @Test
  void part2Example() {
    assertEquals(
        Map.of(
            "row", 11,
            "class", 12,
            "seat", 13),
        resolveOwnTicket(
            parseInput(
                """
                    class: 0-1 or 4-19
                    row: 0-5 or 8-19
                    seat: 0-13 or 16-19

                    your ticket:
                    11,12,13

                    nearby tickets:
                    3,9,18
                    15,1,5
                    5,14,9""")));
  }
}
