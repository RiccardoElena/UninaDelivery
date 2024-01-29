package com.unina.oobd2324gr22.entity.DTO;

public class WaterTransport extends Transport {

  // Constructors

  /**
   * Constructor for the water transport.
   *
   * @param tId id of the water transport.
   * @param tMaxCapacity max capacity of the water transport.
   * @param tOccupiedSpace occupied space of the water transport.
   * @param tIsAvailable availability of the water transport.
   * @param tDepositOwner owner of the water transport.
   */
  public WaterTransport(
      final int tId,
      final float tMaxCapacity,
      final float tOccupiedSpace,
      final boolean tIsAvailable,
      final CentralDeposit tDepositOwner) {
    super(tId, tMaxCapacity, tOccupiedSpace, tIsAvailable, tDepositOwner);
    tDepositOwner.addTransport(this);
  }
}
