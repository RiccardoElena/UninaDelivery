package com.unina.oobd2324gr22.utils;

import com.unina.oobd2324gr22.control.NonLoginControl;

/** Utility class to represent the absence of a value. */
public final class None extends NonLoginControl {
  private None() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
