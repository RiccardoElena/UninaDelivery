package com.unina.oobd2324gr22;

import com.unina.oobd2324gr22.control.App;

/** Hello world! */
public final class Main {

  // Constructors

  /** Default constructor. */
  private Main() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  // Methods
  /**
   * Main method.
   *
   * @param args Command line arguments.
   */
  public static void main(final String[] args) {
    App.launchApp(args);
  }
}
