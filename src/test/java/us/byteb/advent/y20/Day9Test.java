package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class Day9Test {

  @Test
  void part1Example() {
    final List<Long> input =
        """
        5
        20
        15
        25
        47
        40
        62
        55
        65
        95
        102
        117
        150
        182
        127
        219
        299
        277
        309
        576
        """
            .lines()
            .map(Long::parseLong)
            .collect(Collectors.toList());
    assertEquals(127, Day9.findFirstInvalid(input, 5));
  }
}
