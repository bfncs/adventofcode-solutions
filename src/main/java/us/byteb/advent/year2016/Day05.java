package us.byteb.advent.year2016;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class Day05 {

  private static final MessageDigest DIGEST = digest();
  private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);
  private static final String INPUT = "ffykfhsq";

  public static void main(String[] args) {
    System.out.println("Part 1: " + findPassword(INPUT));
    System.out.println("Part 2: " + findPasswordPosition(INPUT));
  }

  static String findPassword(final String input) {
    final StringBuilder password = new StringBuilder();
    long index = 0;
    while (password.length() < 8) {
      final String candidate = md5(input + index);
      if (candidate.startsWith("00000")) {
        password.append(candidate.charAt(5));
      }
      index++;
    }

    return password.toString();
  }

  static String findPasswordPosition(final String input) {
    final Character[] password = new Character[8];
    long index = 0;
    while (containsNull(password)) {
      final String candidate = md5(input + index);
      index++;

      if (candidate.startsWith("00000")) {
        final int position;
        try {
          position = Integer.parseInt(candidate.substring(5, 6));
        } catch (NumberFormatException e) {
          continue;
        }

        if (position < 0 || position > 7 || password[position] != null) {
          continue;
        }
        password[position] = candidate.charAt(6);
      }
    }

    return Arrays.stream(password).map(String::valueOf).collect(Collectors.joining());
  }

  private static <T> boolean containsNull(final T[] items) {
    for (final T item : items) {
      if (item == null) return true;
    }
    return false;
  }

  static String md5(final String input) {
    return bytesToHex(DIGEST.digest(input.getBytes()));
  }

  private static MessageDigest digest() {
    try {
      return MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  private static String bytesToHex(byte[] bytes) {
    byte[] hexChars = new byte[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }

    return new String(hexChars, StandardCharsets.UTF_8);
  }
}
