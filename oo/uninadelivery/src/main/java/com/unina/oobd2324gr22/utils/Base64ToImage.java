package com.unina.oobd2324gr22.utils;

import java.util.Base64;
import javafx.scene.image.Image;

public final class Base64ToImage {

  /** Length of the JPEG magic number. */
  private static final int JPEG_MAGIC_NUMBER_LENGTH = 3;

  /** First byte of the JPEG magic number. */
  private static final byte JPEG_MAGIC_NUMBER_BYTE_1 = (byte) 0xFF;

  /** Second byte of the JPEG magic number. */
  private static final byte JPEG_MAGIC_NUMBER_BYTE_2 = (byte) 0xD8;

  /** Third byte of the JPEG magic number. */
  private static final byte JPEG_MAGIC_NUMBER_BYTE_3 = (byte) 0xFF;

  /** Private Constructor. */
  private Base64ToImage() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Convert a base64 string to an Image.
   *
   * @param base64 the base64 string
   * @return the Image
   */
  public static Image convert(final String base64) {
    if (!isJpeg(base64)) {
      throw new IllegalArgumentException("The string does not represent a valid JPEG image.");
    }
    return new Image("data:image/jpeg;base64," + base64);
  }

  /**
   * Check if the base64 string represents a JPEG image.
   *
   * @param base64
   * @return true if the base64 string represents a JPEG image, false otherwise
   */
  private static boolean isJpeg(final String base64) {
    try {
      byte[] decodedBytes = Base64.getDecoder().decode(base64);

      if (decodedBytes.length < JPEG_MAGIC_NUMBER_LENGTH
          || decodedBytes[0] != JPEG_MAGIC_NUMBER_BYTE_1
          || decodedBytes[1] != JPEG_MAGIC_NUMBER_BYTE_2
          || decodedBytes[2] != JPEG_MAGIC_NUMBER_BYTE_3) {
        return false;
      }
      return true;

    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
