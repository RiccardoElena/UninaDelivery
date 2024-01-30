package com.unina.oobd2324gr22.entity.DTO;

public class CentralDeposit extends CountryDeposit {

  // Attributes

  // Constructors

  /**
   * Constructor with parameters.
   *
   * @param dId id of the deposit
   * @param dOccupiedSpace occupied space of the deposit
   * @param dMaxCapacity total space of the deposit
   * @param dAddress address of the deposit
   */
  public CentralDeposit(
      final int dId, final float dOccupiedSpace, final float dMaxCapacity, final Address dAddress) {
    super(dId, dOccupiedSpace, dMaxCapacity, dAddress);
  }

  // Getters and Setters

  // Methods

  /**
   * toString method.
   *
   * @return state of the deposit
   */
  @Override
  public String toString() {
    return "Account{"
        + "\nid = '"
        + this.getId()
        + "',"
        + "\nsurname = "
        + this.getOccupiedSpace()
        + ","
        + "\nemail = "
        + this.getMaxCapacity()
        + ","
        + "\naddress = "
        + this.getAddress().toString().replace("\n", "\n\t")
        + "\n}";
  }

  /**
   * Add a transport to the deposit.
   *
   * @param transport the transport to add.
   */
  public void addTransport(final Transport transport) {
    if (transport == null) {
      throw new IllegalArgumentException("Transport cannot be null");
    }

    if (this.getTransports().contains(transport)) {
      return;
    }

    this.getTransports().add(transport);
  }
}
