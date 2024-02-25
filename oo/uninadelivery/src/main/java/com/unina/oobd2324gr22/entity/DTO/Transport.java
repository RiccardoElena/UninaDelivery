package com.unina.oobd2324gr22.entity.DTO;

public class Transport {

  // Attributes

  /** Id of the transport. */
  private int id;

  /** Max capacity of the transport. */
  private float maxCapacity;

  /** Occupied space of the transport. */
  private float occupiedSpace;

  /** Availability of the transport. */
  private boolean isAvailable;

  /** Owner of the transport. */
  private Deposit depositOwner;

  // Constants

  /** Default max capacity of the transport. */
  private static final float DEFAULT_MAX_CAPACITY = 100000;

  // Constructors

  /**
   * Constructor for the transport.
   *
   * @param tId id of the transport.
   * @param tMaxCapacity max capacity of the transport.
   * @param tIsAvailable availability of the transport.
   * @param tDepositOwner owner of the transport.
   */
  public Transport(
      final int tId,
      final float tMaxCapacity,
      final boolean tIsAvailable,
      final Deposit tDepositOwner) {
    if (tDepositOwner == null) {
      throw new IllegalArgumentException("Deposit owner cannot be null");
    }
    id = tId;
    maxCapacity = tMaxCapacity < 0 ? DEFAULT_MAX_CAPACITY : tMaxCapacity;
    // occupiedSpace = tOccupiedSpace < 0 ? 0 : tOccupiedSpace;
    isAvailable = tIsAvailable;
    depositOwner = tDepositOwner;
    tDepositOwner.addTransport(this);
  }

  // Getters

  /**
   * Getter for the id of the transport.
   *
   * @return id of the transport.
   */
  public int getId() {
    return id;
  }

  /**
   * Getter for the max capacity of the transport.
   *
   * @return max capacity of the transport.
   */
  public float getMaxCapacity() {
    return maxCapacity;
  }

  /**
   * Getter for the occupied space of the transport.
   *
   * @return occupied space of the transport.
   */
  public float getOccupiedSpace() {
    return occupiedSpace;
  }

  /**
   * Getter for the availability of the transport.
   *
   * @return availability of the transport.
   */
  public boolean isAvailable() {
    return isAvailable;
  }

  /**
   * Getter for the owner of the transport.
   *
   * @return owner of the transport.
   */
  public Deposit getDepositOwner() {
    return depositOwner;
  }

  // Setters

  /**
   * Setter for the max capacity of the transport.
   *
   * @param tMaxCapacity max capacity of the transport.
   */
  public void setMaxCapacity(final float tMaxCapacity) {
    maxCapacity = tMaxCapacity;
  }

  /**
   * Setter for the occupied space of the transport.
   *
   * @param tOccupiedSpace occupied space of the transport.
   */
  public void setOccupiedSpace(final float tOccupiedSpace) {
    occupiedSpace = tOccupiedSpace;
  }

  /**
   * Setter for the availability of the transport.
   *
   * @param tAvailable availability of the transport.
   */
  public void setAvailable(final boolean tAvailable) {
    isAvailable = tAvailable;
  }

  /**
   * Setter for the owner of the transport.
   *
   * @param tDepositOwner owner of the transport.
   */
  public void setDepositOwner(final Deposit tDepositOwner) {
    depositOwner = tDepositOwner;
  }

  // Methods

  /** To string. */
  @Override
  public String toString() {
    return "Veicolo " + id + " - (" + maxCapacity + "kg)";
  }
}
