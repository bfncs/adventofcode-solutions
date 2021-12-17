package us.byteb.advent.year2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Day04 {

  public static void main(String[] args) {
    final String input = "iwrupvqb";
    System.out.println("Part 1: " + solve(input, "00000"));
    System.out.println("Part 2: " + solve(input, "000000"));
  }

  static long solve(final String payloadPrefix, final String requiredMd5Prefix) {
    long i = 0;
    do {
      final String md5Hash = md5Hash(payloadPrefix + i);
      if (md5Hash.startsWith(requiredMd5Prefix)) {
        return i;
      }
    } while (i++ < Long.MAX_VALUE);
    throw new IllegalStateException();
  }

  private static String md5Hash(final String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(input.getBytes());
      byte[] digest = md.digest();

      return HexFormat.of().formatHex(digest);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
