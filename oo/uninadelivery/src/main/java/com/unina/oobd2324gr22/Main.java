package com.unina.oobd2324gr22;

import com.unina.oobd2324gr22.utils.App;

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
    System.err.println("This is a utility class and cannot be instantiated");
    App.launchApp(args);
  }
}
