package us.byteb.advent.year2021;

import static us.byteb.advent.Utils.readFileFromResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day11 {

  public static void main(String[] args) throws IOException {
    final List<List<Octopus>> input = parseInput(readFileFromResources("year2021/day11.txt"));

    System.out.println("Part 1: " + steps(input, 100).numFlashes());
    System.out.println("Part 2: " + findFirstSynchronizedStep(input));
  }

  record StepResult(List<List<Octopus>> grid, long numFlashes) {}

  static StepResult steps(final List<List<Octopus>> input, int numSteps) {
    StepResult currentStep = new StepResult(resetFlashes(input), 0);
    for (int i = 0; i < numSteps; i++) {
      final StepResult stepResult = step(currentStep.grid());
      currentStep =
          new StepResult(
              resetFlashes(stepResult.grid()), currentStep.numFlashes() + stepResult.numFlashes());
    }

    return currentStep;
  }

  static long findFirstSynchronizedStep(final List<List<Octopus>> input) {
    List<List<Octopus>> grid = resetFlashes(input);
    long steps = 0;
    while (true) {
      steps++;
      final StepResult nextGrid = step(grid);

      if (nextGrid.grid().stream()
              .flatMapToInt(row -> row.stream().mapToInt(Octopus::energyLevel))
              .sum()
          == 0) {
        return steps;
      }

      grid = resetFlashes(nextGrid.grid());
    }
  }

  static List<List<Octopus>> resetFlashes(final List<List<Octopus>> grid) {
    return grid.stream()
        .map(row -> (List<Octopus>) new ArrayList<>(row.stream().map(Octopus::resetFlash).toList()))
        .toList();
  }

  static StepResult step(final List<List<Octopus>> input) {

    final List<List<Octopus>> grid =
        new ArrayList<>(
            input.stream()
                .map(
                    row ->
                        (List<Octopus>)
                            new ArrayList<>(row.stream().map(Octopus::levelUp).toList()))
                .toList());

    long lastNumFlashes = 0;
    long numFlashes = 0;
    do {
      lastNumFlashes = numFlashes;

      for (int row = 0; row < grid.size(); row++) {
        final List<Octopus> currentRow = grid.get(row);
        for (int col = 0; col < currentRow.size(); col++) {
          if (currentRow.get(col).energyLevel() <= 9) continue;

          numFlashes++;
          flash(grid, row, col);

          // row center
          if (col > 0) {
            levelUp(grid, row, col - 1); // left
          }
          if (col + 1 < currentRow.size()) {
            levelUp(grid, row, col + 1); // right
          }

          // row above
          if (row > 0) {
            levelUp(grid, row - 1, col); // middle
            if (col > 0) {
              levelUp(grid, row - 1, col - 1); // left
            }
            if (col + 1 < currentRow.size()) {
              levelUp(grid, row - 1, col + 1); // right
            }
          }

          // row below
          if (row + 1 < grid.size()) {
            levelUp(grid, row + 1, col); // middle
            if (col > 0) {
              levelUp(grid, row + 1, col - 1); // left
            }
            if (col + 1 < currentRow.size()) {
              levelUp(grid, row + 1, col + 1); // right
            }
          }
        }
      }

    } while (lastNumFlashes != numFlashes);

    return new StepResult(grid, numFlashes);
  }

  private static void flash(final List<List<Octopus>> octopusses, final int row, final int col) {
    octopusses.get(row).set(col, new Octopus(0, true));
  }

  private static void levelUp(final List<List<Octopus>> grid, final int row, final int col) {
    grid.get(row).set(col, grid.get(row).get(col).levelUp());
  }

  static List<List<Octopus>> parseInput(final String input) {
    return input
        .lines()
        .map(line -> line.chars().mapToObj(Character::getNumericValue).map(Octopus::new).toList())
        .toList();
  }

  record Octopus(int energyLevel, boolean flashedLastStep) {
    Octopus(final int energyLevel) {
      this(energyLevel, false);
    }

    Octopus levelUp() {
      return flashedLastStep ? this : new Octopus(Math.min(energyLevel + 1, 10));
    }

    Octopus resetFlash() {
      return new Octopus(energyLevel, false);
    }
  }
}
