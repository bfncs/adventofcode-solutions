package us.byteb.advent.y20;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.y20.Day11.PositionState.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day11 {

  public static void main(String[] args) {
    final List<List<PositionState>> initialState =
        parseInput(readFileFromResources("y20/day11.txt"));

    System.out.println(
        "Part 1: " + countOccupiedSeats(findStableState(initialState, Day11::part1Strategy)));
  }

  static List<List<PositionState>> parseInput(final String input) {
    return input
        .lines()
        .map(
            line ->
                line.chars().mapToObj(c -> PositionState.of((char) c)).collect(Collectors.toList()))
        .collect(Collectors.toList());
  }

  static List<List<PositionState>> nextState(
      final List<List<PositionState>> curState,
      final BiFunction<List<List<PositionState>>, Position, PositionState>
          nextPositionStateStrategy) {
    final List<List<PositionState>> nextState = new ArrayList<>();

    for (int row = 0; row < curState.size(); row++) {
      final List<PositionState> nextRow = new ArrayList<>();
      for (int col = 0; col < curState.get(row).size(); col++) {
        nextRow.add(nextPositionStateStrategy.apply(curState, new Position(row, col)));
      }
      nextState.add(nextRow);
    }

    return nextState;
  }

  static PositionState part1Strategy(
      final List<List<PositionState>> state, final Position position) {
    final List<PositionState> curRow = state.get(position.row());
    return switch (curRow.get(position.col())) {
      case FLOOR -> FLOOR;
      case EMPTY_SEAT -> countOccupiedSeatsInRow(
                  adjacentSeats(state, position.row(), position.col()))
              == 0
          ? OCCUPIED_SEAT
          : EMPTY_SEAT;
      case OCCUPIED_SEAT -> countOccupiedSeatsInRow(
                  adjacentSeats(state, position.row(), position.col()))
              >= 4
          ? EMPTY_SEAT
          : OCCUPIED_SEAT;
    };
  }

  static long countOccupiedSeats(final List<List<PositionState>> seats) {
    return countOccupiedSeatsInRow(
        seats.stream().flatMap(Collection::stream).collect(Collectors.toList()));
  }

  static long countOccupiedSeatsInRow(final List<PositionState> seats) {
    return seats.stream().filter(p -> p == OCCUPIED_SEAT).count();
  }

  private static List<PositionState> adjacentSeats(
      final List<List<PositionState>> curState, final int curRow, final int curCol) {
    final List<PositionState> adjacentSeats = new ArrayList<>();

    for (int row = Math.max(0, curRow - 1);
        row <= Math.min(curRow + 1, curState.size() - 1);
        row++) {
      for (int col = Math.max(0, curCol - 1);
          col <= Math.min(curCol + 1, curState.get(row).size() - 1);
          col++) {
        final PositionState positionState = curState.get(row).get(col);
        if (!((row == curRow) && (col == curCol))) {
          adjacentSeats.add(positionState);
        }
      }
    }
    return adjacentSeats;
  }

  public static List<List<PositionState>> findStableState(
      final List<List<PositionState>> initialState,
      final BiFunction<List<List<PositionState>>, Position, PositionState>
          nextPositionStateStrategy) {
    List<List<PositionState>> state = initialState;
    while (true) {
      final List<List<PositionState>> nextState = nextState(state, nextPositionStateStrategy);
      if (state.equals(nextState)) {
        return state;
      }
      state = nextState;
    }
  }

  final record Position(int row, int col) {}

  enum PositionState {
    FLOOR('.'),
    EMPTY_SEAT('L'),
    OCCUPIED_SEAT('#');

    private final char character;

    PositionState(final char character) {
      this.character = character;
    }

    static PositionState of(final char character) {
      return Arrays.stream(values())
          .filter(p -> p.character == character)
          .findFirst()
          .orElseThrow();
    }
  }
}
