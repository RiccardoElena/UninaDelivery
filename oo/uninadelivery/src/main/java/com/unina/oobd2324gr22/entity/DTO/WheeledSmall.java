package com.unina.oobd2324gr22.entity.DTO;

public class WheeledSmall extends WheeledTransport {

  // Constructors

  /**
   * Constructor for the wheeled small transport.
   *
   * @param tId id of the wheeled small transport.
   * @param tMaxCapacity max capacity of the wheeled small transport.
   * @param tOccupiedSpace occupied space of the wheeled small transport.
   * @param tIsAvailable availability of the wheeled small transport.
   * @param tDepositOwner owner of the wheeled small transport.
   */
  public WheeledSmall(
      final int tId,
      final float tMaxCapacity,
      final float tOccupiedSpace,
      final boolean tIsAvailable,
      final Deposit tDepositOwner) {
    super(tId, tMaxCapacity, tOccupiedSpace, tIsAvailable, tDepositOwner);
    tDepositOwner.addTransport(this);
  }
}
