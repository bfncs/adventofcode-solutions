package us.byteb.advent.year2022;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.*;

public class Day08 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2022/day08.txt");

    System.out.println("Part 1: " + findVisibleTrees(input));
  }

  public static long findVisibleTrees(final String input) {
    final List<List<Integer>> grid = input.lines().map(line -> line.chars().mapToObj(c -> Integer.parseInt(String.valueOf((char) c))).toList()).toList();
    final int gridHeight = grid.size();
    final int gridWidth = grid.get(0).size();

    long visibleTrees = (2 * gridHeight) + (2 * gridWidth) - 4;

    for (int y = 1; y < gridHeight - 1; y++) {
      final List<Integer> currentRow = grid.get(y);
      for (int x = 1; x < currentRow.size() - 1; x++) {

        final Integer currentTree = grid.get(y).get(x);

        boolean isVisibleLeft = isVisibleX(grid, y, 0, x, currentTree);
        boolean isVisibleRight = isVisibleX(grid, y, x + 1, gridWidth, currentTree);
        boolean isVisibleUp = isVisibleY(grid, x, 0, y, currentTree);
        boolean isVisibleDown = isVisibleY(grid, x, y + 1 , gridHeight, currentTree);

        boolean isVisible = isVisibleLeft || isVisibleRight || isVisibleUp || isVisibleDown;
        if (isVisible) {
          visibleTrees++;
        }

      }
    }

    return visibleTrees;
  }

  private static boolean isVisibleX(final List<List<Integer>> grid, final int y, final int minX, final int maxX, final Integer currentValue) {
    for (int lookUp = minX; lookUp < maxX; lookUp++) {
      final int lookTree = grid.get(y).get(lookUp);
      if (lookTree >= currentValue) {
        return false;
      }
    }

    return true;
  }

  private static boolean isVisibleY(final List<List<Integer>> grid, final int x, final int minY, final int maxY, final Integer currentValue) {
    for (int lookUp = minY; lookUp < maxY; lookUp++) {
      final int lookTree = grid.get(lookUp).get(x);
      if (lookTree >= currentValue) {
        return false;
      }
    }

    return true;
  }
}
