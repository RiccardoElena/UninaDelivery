package demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

  private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    return digest.digest(input.getBytes());
  }

  private static String toHexString(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (byte b : hash) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) {
        hexString.append('0'); 
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

  public String toSHA256(String originalString) {
    try {
      return toHexString(getSHA(originalString));
    } catch (NoSuchAlgorithmException e) {
      System.out.println("Errore durante la generazione dell'hash SHA-256: " + e.getMessage());
      return null;
    }
  }

}
