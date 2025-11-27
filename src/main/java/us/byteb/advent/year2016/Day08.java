package us.byteb.advent.year2016;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08 {
  public static void main(final String[] args) {
    final String input = readFileFromResources("year2016/day08.txt");
    final Screen screen = solvePart1(input);
    System.out.println("Part 1: " + screen.activePixels());
    System.out.println("Part 2:\n" + screen.prettyPrint());
  }

  private static Screen solvePart1(final String input) {
    final Screen screen = new Screen(6, 50);
    final List<String> ops = input.lines().toList();
    for (final String op : ops) {
      screen.exec(op);
    }
    return screen;
  }

  static class Screen {

    private static final Pattern PATTERN_RECT =
        Pattern.compile("rect (?<width>\\d+)x(?<height>\\d+)");
    private static final Pattern PATTERN_ROTATE_ROW =
        Pattern.compile("rotate row y=(?<row>\\d+) by (?<delta>\\d+)");
    private static final Pattern PATTERN_ROTATE_COLUMN =
        Pattern.compile("rotate column x=(?<column>\\d+) by (?<delta>\\d+)");

    private final boolean[][] pixels;

    Screen(final int height, final int width) {
      final boolean[][] pixels = new boolean[height][width];
      for (int y = 0; y < height; y++) {
        pixels[y] = new boolean[width];
      }

      this.pixels = pixels;
    }

    public Screen exec(final String op) {
      final Matcher rectMatcher = PATTERN_RECT.matcher(op);
      if (rectMatcher.matches()) {
        final int width = Integer.parseInt(rectMatcher.group("width"));
        final int height = Integer.parseInt(rectMatcher.group("height"));
        rect(height, width);
        return this;
      }

      final Matcher rotateRowMatcher = PATTERN_ROTATE_ROW.matcher(op);
      if (rotateRowMatcher.matches()) {
        final int row = Integer.parseInt(rotateRowMatcher.group("row"));
        final int delta = Integer.parseInt(rotateRowMatcher.group("delta"));
        rotateRow(row, delta);
        return this;
      }

      final Matcher rotateColumnMatcher = PATTERN_ROTATE_COLUMN.matcher(op);
      if (rotateColumnMatcher.matches()) {
        final int column = Integer.parseInt(rotateColumnMatcher.group("column"));
        final int delta = Integer.parseInt(rotateColumnMatcher.group("delta"));
        rotateColumn(column, delta);
        return this;
      }

      throw new UnsupportedOperationException();
    }

    private void rect(final int height, final int width) {
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          pixels[y][x] = true;
        }
      }
    }

    private void rotateRow(final int row, final int delta) {
      final boolean[] rowValues = pixels[row].clone();
      for (int x = 0; x < pixels[row].length; x++) {
        pixels[row][(x + delta) % pixels[row].length] = rowValues[x];
      }
    }

    private void rotateColumn(final int column, final int delta) {
      final boolean[] columnValues = new boolean[pixels.length];
      for (int y = 0; y < pixels.length; y++) {
        columnValues[y] = pixels[y][column];
      }
      for (int i = 0; i < columnValues.length; i++) {
        pixels[(i + delta) % pixels.length][column] = columnValues[i];
      }
    }

    public int activePixels() {
      int count = 0;
      for (final boolean[] row : pixels) {
        for (final boolean pixel : row) {
          if (pixel) {
            count++;
          }
        }
      }

      return count;
    }

    public String prettyPrint() {
      final StringBuilder sb = new StringBuilder();
      for (final boolean[] row : pixels) {
        for (final boolean pixel : row) {
          sb.append(pixel ? "#" : ".");
        }
        sb.append("\n");
      }

      return sb.toString();
    }
  }
}
