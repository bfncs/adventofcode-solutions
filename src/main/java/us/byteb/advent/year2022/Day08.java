package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;
import static us.byteb.advent.year2022.Day08.AXIS.X;
import static us.byteb.advent.year2022.Day08.AXIS.Y;
import static us.byteb.advent.year2022.Day08.DIRECTION.DECREMENT;
import static us.byteb.advent.year2022.Day08.DIRECTION.INCREMENT;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class Day08 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day08.txt");

    System.out.println("Part 1: " + findVisibleTrees(input));
    System.out.println("Part 2: " + findHighestScenicScore(input));
  }

  public static long findVisibleTrees(final String input) {
    final Grid grid = Grid.of(input);

    long visibleTrees = (2L * grid.height()) + (2L * grid.width()) - 4;

    for (int y = 1; y < grid.height() - 1; y++) {
      for (int x = 1; x < grid.width() - 1; x++) {

        final int currentTree = grid.get(y, x);

        boolean left = isVisibleX(grid, y, 0, x, currentTree);
        boolean leftNew = countVisible(grid, X, INCREMENT, y, 0, x + 1, currentTree) == x + 1;
        boolean right = isVisibleX(grid, y, x + 1, grid.width(), currentTree);
        boolean rightNew =
            countVisible(grid, X, DECREMENT, y, x, grid.width() - 1, currentTree)
                == grid.width() - x;
        boolean up = isVisibleY(grid, x, 0, y, currentTree);
        boolean upNew = countVisible(grid, X, INCREMENT, x, 0, y, currentTree) == y + 1;
        boolean down = isVisibleY(grid, x, y + 1, grid.height(), currentTree);
        boolean downNew =
            countVisible(grid, X, DECREMENT, x, y + 1, grid.height() - 1, currentTree)
                == grid.height() - y;
        if (left != leftNew || right != rightNew || up != upNew || down != downNew) {
          throw new IllegalStateException();
        }
        boolean isVisible = leftNew || rightNew || upNew || downNew;

        if (isVisible) {
          visibleTrees++;
        }
      }
    }

    return visibleTrees;
  }

  public static long findHighestScenicScore(final String input) {
    final Grid grid = Grid.of(input);

    long maxScore = 0L;

    for (int y = 1; y < grid.height() - 1; y++) {
      for (int x = 1; x < grid.width() - 1; x++) {

        final int currentTree = grid.get(y, x);

        final int left = countVisible(grid, X, DECREMENT, y, 0, x - 1, currentTree);
        final int right = countVisible(grid, X, INCREMENT, y, x + 1, grid.width(), currentTree);
        final int up = countVisible(grid, Y, DECREMENT, x, 0, y - 1, currentTree);
        final int down = countVisible(grid, Y, INCREMENT, x, y + 1, grid.height(), currentTree);
        final int score = left * right * up * down;

        maxScore = Math.max(maxScore, score);
      }
    }

    return maxScore;
  }

  private static int countVisible(
      final Grid grid,
      final AXIS variableAxis,
      final DIRECTION direction,
      final int fixAxisValue,
      final int variableAxisMin,
      final int variableAxisMax,
      final int referenceTree) {
    int start =
        switch (direction) {
          case INCREMENT -> variableAxisMin;
          case DECREMENT -> variableAxisMax;
        };
    int stop =
        switch (direction) {
          case INCREMENT -> variableAxisMax;
          case DECREMENT -> variableAxisMin;
        };
    int step =
        switch (direction) {
          case INCREMENT -> 1;
          case DECREMENT -> -1;
        };
    final BiPredicate<Integer, Integer> condition =
        switch (direction) {
          case INCREMENT -> (current, limit) -> current < limit;
          case DECREMENT -> (current, limit) -> current >= limit;
        };
    final Function<Integer, Integer> accessTree =
        switch (variableAxis) {
          case Y -> (variableAxisValue) -> grid.get(variableAxisValue, fixAxisValue);
          case X -> (value) -> grid.get(fixAxisValue, value);
        };

    int visibleTrees = 0;
    for (int value = start; condition.test(value, stop); value += step) {
      final int tree = accessTree.apply(value);
      visibleTrees++;
      if (tree >= referenceTree) {
        break;
      }
    }

    return visibleTrees;
  }

  private static boolean isVisibleX(
      final Grid grid, final int y, final int minX, final int maxX, final Integer referenceTree) {
    for (int lookUp = minX; lookUp < maxX; lookUp++) {
      final int lookTree = grid.get(y, lookUp);
      if (lookTree >= referenceTree) {
        return false;
      }
    }

    return true;
  }

  private static boolean isVisibleY(
      final Grid grid, final int x, final int minY, final int maxY, final Integer currentValue) {
    for (int lookUp = minY; lookUp < maxY; lookUp++) {
      final int lookTree = grid.get(lookUp, x);
      if (lookTree >= currentValue) {
        return false;
      }
    }

    return true;
  }

  record Grid(List<List<Integer>> data) {
    public static Grid of(final String input) {
      final List<List<Integer>> data =
          input
              .lines()
              .map(
                  line ->
                      line.chars()
                          .mapToObj(c -> Integer.parseInt(String.valueOf((char) c)))
                          .toList())
              .toList();
      return new Grid(data);
    }

    public int get(final int y, final int x) {
      return data.get(y).get(x);
    }

    private int height() {
      return data.size();
    }

    private int width() {
      return data.get(0).size();
    }
  }

  enum DIRECTION {
    INCREMENT,
    DECREMENT
  };

  enum AXIS {
    Y,
    X
  };
}
