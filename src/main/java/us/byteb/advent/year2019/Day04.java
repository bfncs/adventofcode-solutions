package us.byteb.advent.year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day04 {

  public static void main(String[] args) {
    System.out.println(
        "Part 1: " + IntStream.rangeClosed(382345, 843167).filter(Day04::isValid1).count());
    System.out.println(
        "Part 2: " + IntStream.rangeClosed(382345, 843167).filter(Day04::isValid2).count());
  }

  static boolean isValid1(final int num) {
    return hasADoubleOrLongerGroup(num) && leftToRightNeverDecrease(num);
  }

  static boolean isValid2(final int num) {
    return hasAnExactlyDoubleGroup(num) && leftToRightNeverDecrease(num);
  }

  private static boolean leftToRightNeverDecrease(final int num) {
    final char[] chars = Integer.toString(num).toCharArray();
    for (int i = 0; i < chars.length - 1; i++) {
      if (chars[i] > chars[i + 1]) {
        return false;
      }
    }
    return true;
  }

  private static boolean hasADoubleOrLongerGroup(final int num) {
    return extractSameCharacterGroups(num).stream().anyMatch(group -> group.getLength() >= 2);
  }

  private static boolean hasAnExactlyDoubleGroup(final int num) {
    return extractSameCharacterGroups(num).stream().anyMatch(group -> group.getLength() == 2);
  }

  private static List<CharacterGroup> extractSameCharacterGroups(final int num) {
    final char[] chars = Integer.toString(num).toCharArray();
    final List<CharacterGroup> characterGroups = new ArrayList<>();

    for (final char currentChar : chars) {
      if (characterGroups.isEmpty()) {
        characterGroups.add(new CharacterGroup(currentChar, 1));
      } else {
        final CharacterGroup currentGroup = characterGroups.get(characterGroups.size() - 1);
        if (currentGroup.getCharacter() != currentChar) {
          characterGroups.add(new CharacterGroup(currentChar, 1));
        } else {
          currentGroup.increaseLength();
        }
      }
    }
    return characterGroups;
  }

  static class CharacterGroup {
    private final char character;
    private int length;

    CharacterGroup(final char character, final int length) {
      this.character = character;
      this.length = length;
    }

    public char getCharacter() {
      return character;
    }

    public int getLength() {
      return length;
    }

    void increaseLength() {
      length += 1;
    }
  }
}
