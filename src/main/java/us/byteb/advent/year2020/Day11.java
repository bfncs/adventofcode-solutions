package us.byteb.advent.year2020;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2020.Day11.PositionState.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day11 {

  public static void main(String[] args) {
    final List<List<PositionState>> initialState =
        parseInput(readFileFromResources("year2020/day11.txt"));

    System.out.println(
        "Part 1: " + countOccupiedSeats(findStableState(initialState, Day11::part1Strategy)));

    System.out.println(
        "Part 2: " + countOccupiedSeats(findStableState(initialState, Day11::part2Strategy)));
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
    return switch (state.get(position.row()).get(position.col())) {
      case FLOOR -> FLOOR;
      case EMPTY_SEAT ->
          countOccupiedAdjacentSeats(state, position) == 0 ? OCCUPIED_SEAT : EMPTY_SEAT;
      case OCCUPIED_SEAT ->
          countOccupiedAdjacentSeats(state, position) >= 4 ? EMPTY_SEAT : OCCUPIED_SEAT;
    };
  }

  static PositionState part2Strategy(
      final List<List<PositionState>> state, final Position position) {
    return switch (state.get(position.row()).get(position.col())) {
      case FLOOR -> FLOOR;
      case EMPTY_SEAT ->
          countOccupiedSeatsFirstInEachDirection(state, position) == 0 ? OCCUPIED_SEAT : EMPTY_SEAT;
      case OCCUPIED_SEAT ->
          countOccupiedSeatsFirstInEachDirection(state, position) >= 5 ? EMPTY_SEAT : OCCUPIED_SEAT;
    };
  }

  static long countOccupiedSeats(final List<List<PositionState>> seats) {
    return seats.stream().flatMap(Collection::stream).collect(Collectors.toList()).stream()
        .filter(p -> p == OCCUPIED_SEAT)
        .count();
  }

  private static long countOccupiedAdjacentSeats(
      final List<List<PositionState>> state, final Position position) {
    return adjacentSeats(state, position).stream().filter(p -> p == OCCUPIED_SEAT).count();
  }

  private static List<PositionState> adjacentSeats(
      final List<List<PositionState>> curState, final Position position) {
    final List<PositionState> adjacentSeats = new ArrayList<>();

    for (int row = Math.max(0, position.row() - 1);
        row <= Math.min(position.row() + 1, curState.size() - 1);
        row++) {
      for (int col = Math.max(0, position.col() - 1);
          col <= Math.min(position.col() + 1, curState.get(row).size() - 1);
          col++) {
        final PositionState positionState = curState.get(row).get(col);
        if (!((row == position.row()) && (col == position.col()))) {
          adjacentSeats.add(positionState);
        }
      }
    }

    return adjacentSeats;
  }

  private static int countOccupiedSeatsFirstInEachDirection(
      final List<List<PositionState>> state, final Position position) {
    final Set<Movement> movements =
        Set.of(
            new Movement(-1, -1),
            new Movement(-1, 0),
            new Movement(-1, 1),
            new Movement(0, -1),
            new Movement(0, 1),
            new Movement(1, -1),
            new Movement(1, 0),
            new Movement(1, 1));

    int occupiedSeats = 0;
    for (final Movement movement : movements) {
      if (getFirstSeatInMovementDirection(state, position, movement)
          .map(p -> p == OCCUPIED_SEAT)
          .orElse(false)) {
        occupiedSeats++;
      }
    }

    return occupiedSeats;
  }

  private static Optional<PositionState> getFirstSeatInMovementDirection(
      final List<List<PositionState>> state, final Position position, final Movement movement) {
    Position curPos = position;
    while (true) {
      curPos = new Position(curPos.row() + movement.deltaRow(), curPos.col() + movement.deltaCol());
      final boolean positionIsOutOfBounds =
          curPos.row() < 0
              || curPos.row() >= state.size()
              || curPos.col() < 0
              || curPos.col() >= state.get(curPos.row()).size();

      if (positionIsOutOfBounds) {
        return Optional.empty();
      }

      final PositionState nextPositionState = state.get(curPos.row()).get(curPos.col());
      if (nextPositionState != FLOOR) {
        return Optional.of(nextPositionState);
      }
    }
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

  final record Position(int row, int col) {}

  final record Movement(int deltaRow, int deltaCol) {}
}
