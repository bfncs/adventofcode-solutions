package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day17 {

  public static void main(String[] args) throws IOException {
    final TargetArea targetArea = TargetArea.parse(readFileFromResources("year2021/day17.txt"));
    System.out.println("Part 1: " + findInitialVelocityWithMaxYPosition(targetArea));
  }

  public static Result findInitialVelocityWithMaxYPosition(final TargetArea targetArea) {
    Result result = new Result(0, 0, Integer.MIN_VALUE);

    for (int xInitialVelocity = 1; xInitialVelocity <= targetArea.xMax; xInitialVelocity++) {
      int xVelocity = xInitialVelocity;
      int xPos = 0;
      while (xVelocity > 0) {
        xPos += xVelocity;
        xVelocity--;
        if (xPos < targetArea.xMin() || xPos > targetArea.xMax()) continue;

        final int maxYInitialValue =
            Math.max(Math.abs(targetArea.yMin()), Math.abs(targetArea.yMax));
        for (int yInitialVelocity = 1; yInitialVelocity <= maxYInitialValue; yInitialVelocity++) {
          int yVelocity = yInitialVelocity;
          int yPos = 0;

          int yMax = Integer.MIN_VALUE;
          while (yPos >= targetArea.yMin()) {
            yPos = yPos + yVelocity;
            yMax = Math.max(yMax, yPos);
            yVelocity--;
            if (yPos >= targetArea.yMin() && yPos <= targetArea.yMax()) {
              if (yMax > result.maxYPosition) {
                result = new Result(xInitialVelocity, yInitialVelocity, yMax);
              }
            }
          }
        }
      }
    }

    return result;
  }

  record Result(int velocityX, int velocityY, int maxYPosition) {}

  record TargetArea(int xMin, int xMax, int yMin, int yMax) {

    private static final String PREFIX = "target area: ";

    public static TargetArea parse(final String input) {
      if (!input.startsWith(PREFIX)) throw new IllegalStateException();

      final String[] parts = input.substring(PREFIX.length()).split(", ");
      if (parts.length != 2) throw new IllegalStateException();

      final List<Integer> xCoords =
          Arrays.stream(parts[0].substring(2).split("\\.\\."))
              .map(Integer::parseInt)
              .sorted()
              .toList();
      final List<Integer> yCoords =
          Arrays.stream(parts[1].substring(2).split("\\.\\."))
              .map(Integer::parseInt)
              .sorted()
              .toList();

      return new TargetArea(xCoords.get(0), xCoords.get(1), yCoords.get(0), yCoords.get(1));
    }
  }
}
