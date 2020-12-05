package us.byteb.advent.twenty;

public class Day5 {

  public static void main(String[] args) {
    final String input = Utils.readFileFromResources("day5/input.txt");

    System.out.println(
        "Part 1: " + input.lines().map(Seat::of).mapToLong(Seat::calcSeatId).max().getAsLong());
  }

  record Seat(long row, long column) {
    public static Seat of(final String code) {
      return new Seat(parse(code.substring(0, 7), "B", "F"), parse(code.substring(7), "R", "L"));
    }

    private static int parse(final String input, final String oneChar, final String zeroChar) {
      return Integer.parseInt(input.replaceAll(oneChar, "1").replaceAll(zeroChar, "0"), 2);
    }

    public long calcSeatId() {
      return (row * 8) + column;
    }
  }
}
