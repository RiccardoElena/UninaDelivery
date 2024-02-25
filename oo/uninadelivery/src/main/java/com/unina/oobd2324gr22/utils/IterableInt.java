package com.unina.oobd2324gr22.utils;

public class IterableInt {

  // Attributes

  /** Value of the iterable int. */
  private int value = 0;

  // Constructors

  /**
   * Constructor with parameters.
   *
   * @param startingValue starting value of the iterable int
   */
  public IterableInt(final int startingValue) {
    value = startingValue;
  }

  /** Default constructor. */
  public IterableInt() {
    this(0);
  }

  // Methods

  /**
   * Returns the next value of the iterable int.
   *
   * @return next value of the iterable int
   */
  public int next() {
    return value++;
  }

  /**
   * Returns the current value of the iterable int.
   *
   * @return current value of the iterable int
   */
  public int current() {
    return value;
  }
}
