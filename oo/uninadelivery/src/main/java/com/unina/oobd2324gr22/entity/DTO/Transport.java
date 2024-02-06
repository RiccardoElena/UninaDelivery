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
    this.id = tId;
    this.maxCapacity = tMaxCapacity < 0 ? DEFAULT_MAX_CAPACITY : tMaxCapacity;
    // this.occupiedSpace = tOccupiedSpace < 0 ? 0 : tOccupiedSpace;
    this.isAvailable = tIsAvailable;
    this.depositOwner = tDepositOwner;
    tDepositOwner.addTransport(this);
  }

  // Getters

  /**
   * Getter for the id of the transport.
   *
   * @return id of the transport.
   */
  public int getId() {
    return this.id;
  }

  /**
   * Getter for the max capacity of the transport.
   *
   * @return max capacity of the transport.
   */
  public float getMaxCapacity() {
    return this.maxCapacity;
  }

  /**
   * Getter for the occupied space of the transport.
   *
   * @return occupied space of the transport.
   */
  public float getOccupiedSpace() {
    return this.occupiedSpace;
  }

  /**
   * Getter for the availability of the transport.
   *
   * @return availability of the transport.
   */
  public boolean isAvailable() {
    return this.isAvailable;
  }

  /**
   * Getter for the owner of the transport.
   *
   * @return owner of the transport.
   */
  public Deposit getDepositOwner() {
    return this.depositOwner;
  }

  // Setters

  // /**
  //  * Setter for the id of the transport.
  //  *
  //  * @param tId id of the transport.
  //  */
  // public void setId(final int tId) {
  //   this.id = tId;
  // }

  /**
   * Setter for the max capacity of the transport.
   *
   * @param tMaxCapacity max capacity of the transport.
   */
  public void setMaxCapacity(final float tMaxCapacity) {
    this.maxCapacity = tMaxCapacity;
  }

  /**
   * Setter for the occupied space of the transport.
   *
   * @param tOccupiedSpace occupied space of the transport.
   */
  public void setOccupiedSpace(final float tOccupiedSpace) {
    this.occupiedSpace = tOccupiedSpace;
  }

  /**
   * Setter for the availability of the transport.
   *
   * @param tAvailable availability of the transport.
   */
  public void setAvailable(final boolean tAvailable) {
    this.isAvailable = tAvailable;
  }

  /**
   * Setter for the owner of the transport.
   *
   * @param tDepositOwner owner of the transport.
   */
  public void setDepositOwner(final Deposit tDepositOwner) {
    this.depositOwner = tDepositOwner;
  }

  /** To string. */
  @Override
  public String toString() {
    return "Transport{"
        + "\n\tid = "
        + id
        + ","
        + "\n\tmaxCapacity = "
        + maxCapacity
        + ","
        + "\n\toccupiedSpace = "
        + occupiedSpace
        + ","
        + "\n\tisAvailable = "
        + isAvailable
        + ","
        + "\n\tdepositOwner = "
        + depositOwner.toString().replace("\n", "\n\t")
        + "\n}";
  }
}
