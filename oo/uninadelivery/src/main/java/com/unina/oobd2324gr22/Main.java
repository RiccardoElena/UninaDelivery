package com.unina.oobd2324gr22;

import com.unina.oobd2324gr22.control.App;
import com.unina.oobd2324gr22.utils.UtilityClassInstantiationException;

/** Hello world! */
public final class Main {

  // Constructors

  /** Default constructor. */
  private Main() {
    throw new UtilityClassInstantiationException();
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
