package com.unina.oobd2324gr22.entity.DTO;

import java.time.LocalDate;
import com.unina.oobd2324gr22.entity.DTO.Deposit.StoredProduct;

public class ShipmentToDeposit extends Shipment {

  /**
   * The veichle transporting the shipment.
   */

  private Transport transport;

  /**
   * The destination deposit.
   */

  private Deposit destinationDeposit;

  /**
   * The constructor of the class.

   * @param sId the id of the shipment.
   * @param shipDate the date the shipment should take off.
   * @param sHasArrived flag to check if the shipment is arrived.
   * @param sOperator the operator that is managing the shipment.
   * @param sTransport the veichle transporting the shipment.
   * @param startDeposit the destination deposit.
   * @param endDeposit the destination deposit.
   */

  public ShipmentToDeposit(
      final int sId, final LocalDate shipDate,
      final boolean sHasArrived, final Operator sOperator,
      final Transport sTransport, final Deposit startDeposit,
      final Deposit endDeposit) {

    super(sId, shipDate, sHasArrived, sOperator, startDeposit);
    if (sTransport.getDepositOwner() != startDeposit) {
      throw new IllegalArgumentException("The transport must be in the same"
          + " deposit of the shipment");
    }
    this.transport = sTransport;
    this.destinationDeposit = endDeposit;
  }

  /**
   * Getter for the veichle transporting the shipment.

   * @return the veichle transporting the shipment.
   */

  public Transport getTransport() {
    return transport;
  }

  /**
   * Setter for the veichle transporting the shipment.

   * @param sTransport the veichle transporting the shipment.
   */

  public void setTransport(final Transport sTransport) {
    this.transport = sTransport;
  }

  /**
   * Getter for the destination deposit.

   * @return the destination deposit.
   */

  public Deposit getDestinationDeposit() {
    return destinationDeposit;
  }

  /**
   * Setter for the destination deposit.

   * @param sDeposit the destination deposit.
   */

  public void setDestinationDeposit(final Deposit sDeposit) {
    this.destinationDeposit = sDeposit;
  }

  /**
   * Implementation of the addOrder method for the ShipmentToDeposit class.
   * {@inheritDoc}
   */

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
      // the quantity is enough to satisfy the order
      if (sp.getProduct().equals(order.getProduct())) {
        // if the product is present move the maximum between the quantity
        // from the starting deposit to the destination deposit
        this.getOrders().add(order);
        // remove the quantity of the product from the starting deposit
        sp.setQuantity(sp.getQuantity() - order.getQuantity());
        // add the quantity of the product to the destination deposit
        this.getDestinationDeposit().addStoredProducts(
            order.getProduct(), order.getQuantity());
        // if the quantity of the product in the starting deposit is 0,
        // remove the product from the starting deposit
        return;
      }
    }

   }



}
