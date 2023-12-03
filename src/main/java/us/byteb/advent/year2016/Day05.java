package us.byteb.advent.year2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Day05 {

  public static void main(String[] args) {
    System.out.println("Part 1: " + findPassword("ffykfhsq"));
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

  static String md5(final String input) {
    final MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
    byte[] digest = md.digest(input.getBytes());

    final StringBuilder sb = new StringBuilder();
    for (byte b : digest) {
      sb.append(String.format("%02x", b));
    }

    return sb.toString();
  }
}
