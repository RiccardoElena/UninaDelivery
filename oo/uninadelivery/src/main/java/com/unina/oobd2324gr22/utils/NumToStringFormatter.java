package com.unina.oobd2324gr22.utils;

public final class NumToStringFormatter {

  private NumToStringFormatter() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Trunk a number to a certain number of decimal places. If the number has no decimal part, it
   * returns the number as it is. If the number has a decimal part, it returns the number with the
   * specified number of decimal places. If the number has a decimal part with only zeros, it
   * returns the number without the decimal part.
   *
   * @param number the number to trunk
   * @param decimalPlaces the number of decimal places
   * @return the number trunked
   * @throws IllegalArgumentException if decimalPlaces is negative
   */
  public static String trunkDecimal(final Number number, final int decimalPlaces)
      throws IllegalArgumentException {
    if (decimalPlaces < 0) {
      throw new IllegalArgumentException("decimalPlaces must be non-negative");
    }
    int index = String.valueOf(number).indexOf(".");
    String value = String.valueOf(number);
    int offset = decimalPlaces;
    if (index == -1) {
      index = value.length() + 1;
      value += ".";
    } else {
      offset++;
    }

    if (decimalPlaces == 0) {
      return value.substring(0, index);
    }

    if (value.length() < index + offset) {
      for (int i = value.length(); i < index + offset; i++) {
        value += "0";
      }
      return value;
    }

    return value.substring(0, index + offset);
  }
}
