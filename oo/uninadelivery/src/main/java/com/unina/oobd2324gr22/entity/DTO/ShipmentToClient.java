package com.unina.oobd2324gr22.entity.DTO;

import com.unina.oobd2324gr22.entity.DTO.Deposit.StoredProduct;
import java.time.LocalDate;

public class ShipmentToClient extends Shipment {

  /** The veichle transporting the shipment. */
  private WheeledSmall transport;

  /**
   * The constructor of the class.
   *
   * @param sId the id of the shipment.
   * @param shipDate the date the shipment should take off.
   * @param sHasArrived flag to check if the shipment is arrived.
   * @param sOperator the operator that is managing the shipment.
   * @param sTransport the veichle transporting the shipment.
   * @param startDeposit the destination deposit.
   */
  public ShipmentToClient(
      final int sId,
      final LocalDate shipDate,
      final boolean sHasArrived,
      final Operator sOperator,
      final WheeledSmall sTransport,
      final Deposit startDeposit) {

    super(sId, shipDate, sHasArrived, sOperator, startDeposit);
    if (sTransport.getDepositOwner() != startDeposit) {
      throw new IllegalArgumentException(
          "The transport must be in the same deposit of the shipment");
    } else {
      this.transport = sTransport;
    }
  }

  /**
   * Constructor of the class for user input.
   *
   * @param shipDate the date the shipment should take off.
   * @param sOperator the operator that is managing the shipment.
   * @param sTransport the veichle transporting the shipment.
   * @param startDeposit the destination deposit.
   */
  public ShipmentToClient(
      final LocalDate shipDate,
      final Operator sOperator,
      final WheeledSmall sTransport,
      final Deposit startDeposit) {

    super(shipDate, sOperator, startDeposit);
    if (sTransport.getDepositOwner() != startDeposit) {
      throw new IllegalArgumentException(
          "The transport must be in the same deposit of the shipment");
    } else {
      this.transport = sTransport;
    }
  }

  /**
   * Getter for the veichle transporting the shipment.
   *
   * @return the veichle transporting the shipment.
   */
  public WheeledSmall getTransport() {
    return transport;
  }

  /**
   * Setter for the veichle transporting the shipment.
   *
   * @param sTransport the veichle transporting the shipment.
   */
  public void setTransport(final WheeledSmall sTransport) {
    this.transport = sTransport;
  }

  /** Implementation of the addOrder method for the ShipmentToClient class. {@inheritDoc} */
  @Override
  public void addOrder(final Order order) {
    if (order == null) {
      throw new IllegalArgumentException("The order cannot be null");
    }

    if (this.getOrders().contains(order)) {
      return;
    }

    for (StoredProduct sp : this.getStartingDeposit().getStoredProducts()) {
      // check if the product is present in the starting deposit and if
      // there are enough products
      if (sp.getProduct().equals(order.getProduct()) && sp.getQuantity() >= order.getQuantity()) {
        // check if the product is destined to another area
        // being this the only way to add orders is enough to check the first
        // order because the others will have the same area
        if (order
            .getAccount()
            .getAddress()
            .getArea()
            .equals(this.getOrders().get(1).getAccount().getAddress().getArea())) {
          this.getOrders().add(order);
          sp.setQuantity(sp.getQuantity() - order.getQuantity());
          return;
        } else {
          throw new IllegalArgumentException("The order is destined to another area");
        }
      }
    }
  }
}
