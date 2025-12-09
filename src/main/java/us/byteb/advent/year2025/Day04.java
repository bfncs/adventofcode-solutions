package us.byteb.advent.year2025;

import static us.byteb.advent.Utils.readFileFromResources;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day04 {

  public static void main(String[] args) {
    final String input = readFileFromResources("year2025/day04.txt");
    System.out.println("Part 1: " + Grid.parse(input).findAccessiblePoints().size());
  }

  record Grid(boolean[][] data) {
    public static Grid parse(final String input) {
      final List<String> lines = input.lines().toList();
      final boolean[][] data = new boolean[lines.size()][lines.getFirst().length()];
      for (int y = 0; y < lines.size(); y++) {
        final String line = lines.get(y);
        char[] charArray = line.toCharArray();
        for (int x = 0; x < charArray.length; x++) {
          if (charArray[x] == '@') {
            data[y][x] = true;
          }
        }
      }
      return new Grid(data);
    }

    Set<Point> findAccessiblePoints() {
      final Set<Point> points = new HashSet<>();
      for (int y = 0; y < data.length; y++) {
        for (int x = 0; x < data[y].length; x++) {
          if (data[y][x] && pointIsAccessible(y, x)) {
            points.add(new Point(y, x));
          }
        }
      }
      return points;
    }

    private boolean pointIsAccessible(final int y, final int x) {
      int numAdjacentRolls = 0;
      final int startY = Math.max(0, y - 1);
      final int endY = Math.min(data.length - 1, y + 1);
      final int startX = Math.max(0, x - 1);
      final int endX = Math.min(data[0].length - 1, x + 1);
      for (int dY = startY; dY <= endY; dY++) {
        for (int dX = startX; dX <= endX; dX++) {
          if (!(dY == y && dX == x) && data[dY][dX]) {
            numAdjacentRolls++;
            if (numAdjacentRolls >= 4) {
              return false;
            }
          }
        }
      }
      return true;
    }
  }

  record Point(int y, int x) {}
}
