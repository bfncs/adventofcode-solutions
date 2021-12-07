package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Day04 {

  public static void main(String[] args) throws IOException {
    final BingoInput input = parseInput(readFileFromResources("year2021/day04.txt"));

    System.out.println("Part 1: " + findFirstWinningBoard(input).score());
    System.out.println("Part 2: " + findLastWinningBoard(input).score());
  }

  public static BingoInput parseInput(final String input) {
    final List<String> lines = input.lines().toList();
    final List<Integer> drawnNumbers =
        Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).toList();

    final List<BingoBoard> boards = new ArrayList<>();
    List<String> currentBoard = new ArrayList<>();
    for (int i = 2; i <= lines.size(); i++) {
      if (i == lines.size() || lines.get(i).isEmpty()) {
        boards.add(BingoBoard.parse(currentBoard));
        currentBoard = new ArrayList<>();
        continue;
      }

      currentBoard.add(lines.get(i));
    }

    return new BingoInput(drawnNumbers, boards);
  }

  public static BingoResult findFirstWinningBoard(final BingoInput input) {
    for (int i = 0; i < input.drawnNumbers().size(); i++) {
      final List<Integer> drawnNumbers = input.drawnNumbers().subList(0, i);
      for (final BingoBoard board : input.boards()) {
        if (board.isWinning(drawnNumbers)) {
          return new BingoResult(board, drawnNumbers);
        }
      }
    }

    throw new IllegalStateException("No winning board found!");
  }

  public static BingoResult findLastWinningBoard(final BingoInput input) {
    List<BingoBoard> boards = input.boards();

    for (int i = 0; i < input.drawnNumbers().size(); i++) {
      final List<Integer> drawnNumbers = input.drawnNumbers().subList(0, i);
      final List<BingoBoard> remainingBoards =
          boards.stream().filter(board -> !board.isWinning(drawnNumbers)).toList();

      if (remainingBoards.size() == 0) {
        final BingoBoard lastWonBoard = boards.get(boards.size() - 1);
        return new BingoResult(lastWonBoard, drawnNumbers);
      }

      boards = remainingBoards;
    }

    return null;
  }

  record BingoResult(BingoBoard winningBoard, List<Integer> drawnNumbers) {
    public long score() {
      return winningBoard.score(drawnNumbers);
    }
  }

  record BingoInput(List<Integer> drawnNumbers, List<BingoBoard> boards) {}

  static final class BingoBoard {
    private final List<List<Integer>> numbers;

    private BingoBoard(final List<List<Integer>> numbers) {
      this.numbers = numbers;
    }

    public static BingoBoard parse(final List<String> lines) {
      final List<List<Integer>> data =
          lines.stream()
              .map(str -> Arrays.stream(str.trim().split("\s+")).map(Integer::parseInt).toList())
              .toList();

      return new BingoBoard(data);
    }

    public long score(final List<Integer> drawnNumbers) {
      final long sumOfAllUnmarkedNumbers =
          numbers.stream()
              .flatMap(Collection::stream)
              .filter(n -> !drawnNumbers.contains(n))
              .mapToLong(n -> n)
              .sum();
      final long lastDrawnNumber = drawnNumbers.get(drawnNumbers.size() - 1);

      return sumOfAllUnmarkedNumbers * lastDrawnNumber;
    }

    public boolean isWinning(final List<Integer> drawnNumbers) {
      return containsWinningRow(drawnNumbers) || containsWinningColumn(drawnNumbers);
    }

    private boolean containsWinningRow(final List<Integer> drawnNumbers) {
      for (final List<Integer> row : numbers) {
        if (drawnNumbers.containsAll(row)) {
          return true;
        }
      }

      return false;
    }

    private boolean containsWinningColumn(final List<Integer> drawnNumbers) {
      for (int numColumn = 0; numColumn < numbers.get(0).size(); numColumn++) {
        final List<Integer> column = new ArrayList<>();
        for (final List<Integer> row : numbers) {
          column.add(row.get(numColumn));
        }

        if (drawnNumbers.containsAll(column)) {
          return true;
        }
      }
      return false;
    }
  }
}
