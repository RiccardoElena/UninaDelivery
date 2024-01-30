package com.unina.oobd2324gr22.entity.DTO;

import java.time.LocalDate;
import java.util.List;

public abstract class Shipment {
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
  private List<Order> orders;

  /**
   * The constructor of the class.
   *
   * @param sId the id of the shipment.
   * @param shipDate the date the shipment should take off.
   * @param sHasArrived flag to check if the shipment is arrived.
   * @param sOperator the operator that is managing the shipment.
   * @param sStartingDeposit the starting deposit of the shipment.
   */
  public Shipment(
      final int sId,
      final LocalDate shipDate,
      final boolean sHasArrived,
      final Operator sOperator,
      final Deposit sStartingDeposit) {
    this.id = sId;
    this.shippingDate = shipDate;
    this.hasArrived = sHasArrived;
    this.operator = sOperator;
    this.startingDeposit = sStartingDeposit;
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
  public abstract void addOrder(Order order);
}
