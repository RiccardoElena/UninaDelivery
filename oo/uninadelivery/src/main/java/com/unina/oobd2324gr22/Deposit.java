package com.unina.oobd2324gr22;


public class Deposit {

  // Attributes

  /**
   * Id of the deposit.
   */

  private int id;

  /**
   * Occupied space of the deposit.
   */

  private float occupiedSpace;

  /**
   * Total space of the deposit.
   */

  private float maxCapacity;

  /**
   * Address of the deposit.
   */

  private Address address;

  // Constructors

  /**
   * Constructor with parameters.
   *
   * @param dId id of the deposit
   * @param dOccupiedSpace occupied space of the deposit
   * @param dMaxCapacity total space of the deposit
   * @param dAddress address of the deposit
   */

  public Deposit(final int dId, final float dOccupiedSpace,
                 final float dMaxCapacity, final Address dAddress) {
    this.id = dId;
    this.occupiedSpace = dOccupiedSpace;
    this.maxCapacity = dMaxCapacity;
    this.address = dAddress;
  }

  // Getters and Setters

  /**
   * Getter of the id.
   *
   * @return id
   */

  public int getId() {
    return this.id;
  }

  /**
   * Setter of the id.
   *
   * @param dId id
   */

  public void setId(final int dId) {
    this.id = dId;
  }

  /**
   * Getter of the occupied space.
   *
   * @return occupied space
   */

  public float getOccupiedSpace() {
    return this.occupiedSpace;
  }

  /**
   * Setter of the occupied space.
   *
   * @param dOccupiedSpace occupied space
   */

  public void setOccupiedSpace(final float dOccupiedSpace) {
    this.occupiedSpace = dOccupiedSpace;
  }

  /**
   * Getter of the total space.
   *
   * @return total space
   */

  public float getMaxCapacity() {
    return this.maxCapacity;
  }

  /**
   * Setter of the total space.
   *
   * @param dMaxCapacity total space
   */

  public void setMaxCapacity(final float dMaxCapacity) {
    this.maxCapacity = dMaxCapacity;
  }

  /**
   * Getter of the address.
   *
   * @return address
   */

  public Address getAddress() {
    return this.address;
  }

  /**
   * Setter of the address.
   *
   * @param dAddress address
   */

  public void setAddress(final Address dAddress) {
    this.address = dAddress;
  }

}
