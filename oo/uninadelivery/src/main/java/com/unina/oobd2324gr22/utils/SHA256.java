package com.unina.oobd2324gr22.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

  /** Mask to get the last byte of an integer. */
  private static final int LAST_BYTE_MASK = 0xff;

  private static byte[] getSHA(final String input) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    return digest.digest(input.getBytes());
  }

  private static String toHexString(final byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (byte b : hash) {
      String hex = Integer.toHexString(LAST_BYTE_MASK & b);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

  /**
   * Returns a SHA-256 hash of the given string.
   *
   * @param originalString the string to hash
   * @return the SHA-256 hash of the given string
   */
  public final String toSHA256(final String originalString) throws RuntimeException {
    try {
      return toHexString(getSHA(originalString));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Internal error", e);
    }
  }
}
