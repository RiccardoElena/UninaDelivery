package com.unina.oobd2324gr22.entity.DTO;

public abstract class WheeledTransport extends Transport {

  // Constructors

  /**
   * Constructor for the wheeled transport.
   *
   * @param tId            id of the wheeled transport.
   * @param tMaxCapacity   max capacity of the wheeled transport.
   * @param tOccupiedSpace occupied space of the wheeled transport.
   * @param tIsAvailable   availability of the wheeled transport.
   * @param tDepositOwner  owner of the wheeled transport.
   */
  public WheeledTransport(final int tId, final float tMaxCapacity,
                  final float tOccupiedSpace, final boolean tIsAvailable,
                  final Deposit tDepositOwner) {
    super(tId, tMaxCapacity, tOccupiedSpace, tIsAvailable, tDepositOwner);
  }
}