package com.unina.oobd2324gr22.entity.DTO;

public class AirTransport extends Transport {

  // Constructors

  /**
   * Constructor for the air transport.
   *
   * @param tId id of the air transport.
   * @param tMaxCapacity max capacity of the air transport.
   * @param tOccupiedSpace occupied space of the air transport.
   * @param tIsAvailable availability of the air transport.
   * @param tDepositOwner owner of the air transport.
   */
  public AirTransport(
      final int tId,
      final float tMaxCapacity,
      final float tOccupiedSpace,
      final boolean tIsAvailable,
      final CentralDeposit tDepositOwner) {
    super(tId, tMaxCapacity, tOccupiedSpace, tIsAvailable, tDepositOwner);
    tDepositOwner.addTransport(this);
  }
}
