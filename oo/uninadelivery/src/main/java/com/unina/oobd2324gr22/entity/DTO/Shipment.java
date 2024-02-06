package com.unina.oobd2324gr22.entity.DTO;

import com.unina.oobd2324gr22.entity.DTO.Deposit.StoredProduct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shipment {

  /** The id of the shipment. */
  private int id;

  /** The date the shimpent should take off. */
  private LocalDate shippingDate;

  /** Flag to check if the shipment is arrived. */
  private boolean hasArrived;

  /** The operator that is managing the shipment. */
  private Operator operator;

  /** Starting deposit of the shipment. */
  private Deposit startingDeposit;

  /** The orders shipped. */
  private List<Order> orders = new ArrayList<>();

  /** The veichle transporting the shipment. */
  private Transport transport;

  /** The space of the transport occupied by the orders. */
  private float occupiedSpace;

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
  public Shipment(
      final int sId,
      final LocalDate shipDate,
      final boolean sHasArrived,
      final Operator sOperator,
      final Transport sTransport,
      final Deposit startDeposit) {

    this.id = sId;
    this.shippingDate = shipDate;
    this.hasArrived = sHasArrived;
    this.operator = sOperator;
    this.startingDeposit = startDeposit;
    if (sTransport.getDepositOwner().equals(startDeposit)) {
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
  public Shipment(
      final LocalDate shipDate,
      final Operator sOperator,
      final Transport sTransport,
      final Deposit startDeposit) {

    this.id = -1;
    this.shippingDate = shipDate;
    this.hasArrived = false;
    this.operator = sOperator;
    this.startingDeposit = startDeposit;
    if (sTransport.getDepositOwner().equals(startDeposit)) {
      throw new IllegalArgumentException(
          "The transport must be in the same deposit of the shipment");
    } else {
      this.transport = sTransport;
    }
  }

  /**
   * Constructor of the class for quick lookup.
   *
   * @param sId the id of the shipment.
   * @param shipDate the date the shipment should take off.
   * @param sTransport the veichle transporting the shipment.
   * @param startDeposit the destination deposit.
   * @param oSpace the space of the transport occupied by the orders.
   */
  public Shipment(
      final int sId,
      final LocalDate shipDate,
      final Transport sTransport,
      final Deposit startDeposit,
      final float oSpace) {

    this.id = sId;
    this.shippingDate = shipDate;
    this.hasArrived = false;
    this.startingDeposit = startDeposit;
    this.occupiedSpace = oSpace;

    if (sTransport.getDepositOwner().equals(startDeposit)) {
      throw new IllegalArgumentException(
          "The transport must be in the same deposit of the shipment");
    } else {
      this.transport = sTransport;
    }
  }

  /**
   * Getter for the id of the shipment.
   *
   * @return the id of the shipment.
   */
  public int getId() {
    return id;
  }

  /**
   * Getter for the shipping date of the shipment.
   *
   * @return the shipping date of the shipment.
   */
  public LocalDate getShippingDate() {
    return shippingDate;
  }

  /**
   * Setter for the shipping date of the shipment.
   *
   * @param shipDate the shipping date of the shipment.
   */
  public void setShippingDate(final LocalDate shipDate) {
    this.shippingDate = shipDate;
  }

  /**
   * Getter for the hasArrived flag of the shipment.
   *
   * @return true if the shipment has arrived, false otherwise.
   */
  public boolean hasArrived() {
    return hasArrived;
  }

  /**
   * Setter for the hasArrived flag of the shipment.
   *
   * @param sHasArrived true if the shipment has arrived, false otherwise.
   */
  public void setHasArrived(final boolean sHasArrived) {
    this.hasArrived = sHasArrived;
  }

  /**
   * Getter for the operator of the shipment.
   *
   * @return the operator of the shipment.
   */
  public Operator getOperator() {
    return operator;
  }

  /**
   * Setter for the operator of the shipment.
   *
   * @param sOperator the operator of the shipment.
   */
  public void setOperator(final Operator sOperator) {
    this.operator = sOperator;
  }

  /**
   * Getter for the starting deposit of the shipment.
   *
   * @return the starting deposit of the shipment.
   */
  public Deposit getStartingDeposit() {
    return startingDeposit;
  }

  /**
   * Setter for the starting deposit of the shipment.
   *
   * @param sStartingDeposit the starting deposit of the shipment.
   */
  public void setStartingDeposit(final Deposit sStartingDeposit) {
    this.startingDeposit = sStartingDeposit;
  }

  /**
   * Getter for the veichle transporting the shipment.
   *
   * @return the veichle transporting the shipment.
   */
  public Transport getTransport() {
    return transport;
  }

  /**
   * Setter for the veichle transporting the shipment.
   *
   * @param sTransport the veichle transporting the shipment.
   */
  public void setTransport(final Transport sTransport) {
    this.transport = sTransport;
  }

  /**
   * Getter for the orders shipped.
   *
   * @return the orders shipped.
   */
  public List<Order> getOrders() {
    return orders;
  }

  /**
   * Add an order to the orders shipped.
   *
   * @param order the order to add.
   */
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

  /**
   * Get the space occupied by the orders.
   *
   * @return the space occupied by the orders.
   */
  public float getOccupiedSpace() {
    return occupiedSpace;
  }

  /**
   * Set the space occupied by the orders.
   *
   * @param oSpace the space occupied by the orders.
   */
  public void setOccupiedSpace(final float oSpace) {
    this.occupiedSpace = oSpace;
  }

  /**
   * Get the remaining space in the transport.
   *
   * @return the remaining space in the transport.
   */
  public float getRemainingSpace() {
    return this.getTransport().getMaxCapacity() - this.getOccupiedSpace();
  }

  /** To strig method. */
  @Override
  public String toString() {
    String op = this.getOperator() == null ? "null" : this.getOperator().toString();
    return "Shipment{"
        + "\n\tid="
        + getId()
        + "\n\thasArrived="
        + hasArrived()
        + ","
        + "\n\toperator="
        + op
        + ","
        + "\n\tstartingDeposit="
        + getStartingDeposit().toString().replace("\n", "\n\t")
        + ","
        + "\n\ttransport="
        + getTransport().toString().replace("\n", "\n\t")
        + ","
        + "\n\torders="
        + getOrders().toString().replace("\n", "\n\t")
        + ","
        + "\n\toccupiedSpace="
        + getOccupiedSpace()
        + ","
        + "\n\tremainingSpace="
        + getRemainingSpace()
        + "\n}";
  }
}
