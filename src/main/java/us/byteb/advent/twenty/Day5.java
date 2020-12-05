package us.byteb.advent.twenty;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 {

  public static void main(String[] args) {
    final String input = Utils.readFileFromResources("day5/input.txt");

    final List<Seat> seats = input.lines().map(Seat::of).sorted().collect(Collectors.toList());

    System.out.println("Part 1: " + seats.stream().mapToInt(Seat::calcSeatId).max().getAsInt());

    final List<Integer> seatIds =
        seats.stream().map(seat -> seat.calcSeatId()).collect(Collectors.toList());
    final int minSeatId = seatIds.stream().mapToInt(id -> id).min().getAsInt();
    final int maxSeatId = seatIds.stream().mapToInt(id -> id).max().getAsInt();
    final List<Integer> missingSeatIds =
        IntStream.range(minSeatId, maxSeatId)
            .filter(seatId -> !seatIds.contains(seatId))
            .boxed()
            .collect(Collectors.toList());
    System.out.println("Part 2: " + missingSeatIds);
  }

  record Seat(int row, int column) implements Comparable<Seat> {
    public static Seat of(final String code) {
      return new Seat(parse(code.substring(0, 7), "B", "F"), parse(code.substring(7), "R", "L"));
    }

    private static int parse(final String input, final String oneChar, final String zeroChar) {
      return Integer.parseInt(input.replaceAll(oneChar, "1").replaceAll(zeroChar, "0"), 2);
    }

    public int calcSeatId() {
      return (row * 8) + column;
    }

    @Override
    public int compareTo(final Seat other) {
      final int rowCompare = this.row() - other.row;
      if (rowCompare != 0) {
        return rowCompare;
      }
      return this.column() - other.column();
    }
  }
}
