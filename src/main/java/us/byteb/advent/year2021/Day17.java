package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.*;

public class Day17 {

  public static void main(String[] args) throws IOException {
    final TargetArea targetArea = TargetArea.parse(readFileFromResources("year2021/day17.txt"));
    System.out.println("Part 1: " + findInitialVelocityWithMaxYPosition(targetArea).maxYPosition());
    System.out.println("Part 2: " + findResults(targetArea).size());
  }

  public static Result findInitialVelocityWithMaxYPosition(final TargetArea targetArea) {
    return findResults(targetArea).entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(entry -> new Result(entry.getKey(), entry.getValue()))
        .orElseThrow();
  }

  record Result(Velocity velocity, int maxYPosition) {}

  public static Map<Velocity, Integer> findResults(final TargetArea targetArea) {
    final Map<Velocity, Integer> results = new HashMap<>();

    for (int initVelocityX = 1; initVelocityX <= targetArea.xMax(); initVelocityX++) {
      final int maxInitialValueY = Math.max(Math.abs(targetArea.yMin()), Math.abs(targetArea.yMax));
      for (int initVelocityY = -maxInitialValueY;
          initVelocityY <= maxInitialValueY;
          initVelocityY++) {

        int posX = 0;
        int posY = 0;
        int maxPosY = Integer.MIN_VALUE;
        int velocityX = initVelocityX;
        int velocityY = initVelocityY;

        while (posY >= targetArea.yMin()) {
          posX += velocityX;
          posY += velocityY;
          maxPosY = Math.max(maxPosY, posY);
          velocityX = velocityX == 0 ? 0 : velocityX - 1;
          velocityY--;

          if (posX >= targetArea.xMin()
              && posX <= targetArea.xMax()
              && posY >= targetArea.yMin()
              && posY <= targetArea.yMax()) {
            results.put(new Velocity(initVelocityX, initVelocityY), maxPosY);
          }
        }
      }
    }

    return results;
  }

  record Velocity(int velocityX, int velocityY) {
    @Override
    public String toString() {
      return "(" + velocityX + "," + velocityY + ')';
    }
  }

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
