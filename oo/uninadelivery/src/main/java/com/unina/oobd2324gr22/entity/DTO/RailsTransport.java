package com.unina.oobd2324gr22.entity.DTO;

public class RailsTransport extends Transport {

  // Constructors

  /**
   * Constructor for the rails transport.
   *
   * @param tId            id of the rails transport.
   * @param tMaxCapacity   max capacity of the rails transport.
   * @param tOccupiedSpace occupied space of the rails transport.
   * @param tIsAvailable   availability of the rails transport.
   * @param tDepositOwner  owner of the rails transport.
   */
  public RailsTransport(final int tId, final float tMaxCapacity,
                  final float tOccupiedSpace, final boolean tIsAvailable,
                  final CountryDeposit tDepositOwner) {
    super(tId, tMaxCapacity, tOccupiedSpace, tIsAvailable, tDepositOwner);
    tDepositOwner.addTransport(this);
  }
}
