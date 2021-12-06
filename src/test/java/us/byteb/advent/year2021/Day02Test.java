package us.byteb.advent.year2021;

import static org.junit.jupiter.api.Assertions.*;
import static us.byteb.advent.year2021.Day02.applyCommandsStrategy2;
import static us.byteb.advent.year2021.Day02.parseInput;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.year2021.Day02.Position;

class Day02Test {

  private static final List<Day02.Command> example1Input =
      parseInput("""
			forward 5
			down 5
			forward 8
			up 3
			down 8
			forward 2
			""");

  @Test
  void part1Example() {
    final Position finalPosition = Day02.applyCommandsStrategy1(example1Input);
    assertEquals(150L, finalPosition.horizontalPos() * finalPosition.depth());
  }

	@Test
	void part2Example() {
		final Position finalPosition = applyCommandsStrategy2(example1Input);
		assertEquals(900L, finalPosition.horizontalPos() * finalPosition.depth());
	}
}
