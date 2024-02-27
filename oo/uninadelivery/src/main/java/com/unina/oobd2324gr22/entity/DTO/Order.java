package com.unina.oobd2324gr22.entity.DTO;

import java.time.LocalDate;

public class Order {

  // Attributes

  /** Id of the order. */
  private int id;

  /** Emission date of the order. */
  private LocalDate emissionDate;

  /** Express delivery of the order. */
  private boolean isExpress;

  /** Extra warranty of the order. */
  private int extraWarranty;

  /** Client of the order. */
  private Account account;

  /** Quantity of the order. */
  private int quantity;

  /** Product of the order. */
  private Product product;

  /** Expected delivery date of the order. */
  private LocalDate expectedDeliveryDate;

  /** Total price of the order. */
  private double price;

  /** Express delivery days. */
  private static final int EXPRESSDELIVERY = 3;

  /** Standard delivery days. */
  private static final int STANDARDDELIVERY = 7;

  /** Express delivery fee. */
  private static final double EXPRESS_FEE = 0.2;

  /** Warranty fee. */
  private static final double WARRANTY_FEE = 0.1;

  // Constructors

  /**
   * Constructor with parameters.
   *
   * @param orderid ciao
   * @param emissiondate ciao
   * @param isexpress ciao
   * @param extrawarranty ciao
   * @param client ciao
   * @param qty ciao
   * @param pct ciao
   */
  public Order(
      final int orderid,
      final LocalDate emissiondate,
      final boolean isexpress,
      final int extrawarranty,
      final Account client,
      final int qty,
      final Product pct) {
    id = orderid;
    emissionDate = emissiondate;
    setExpectedDeliveryDate(emissiondate);
    isExpress = isexpress;
    extraWarranty = extrawarranty;
    account = client;
    quantity = qty;
    product = pct;
    price = calculatePrice();
  }

  // Getters and Setters

  /**
   * Getter of the order id.
   *
   * @return order id
   */
  public int getId() {
    return id;
  }

  /**
   * Getter of the emission date.
   *
   * @return emission date
   */
  public LocalDate getEmissionDate() {
    return emissionDate;
  }

  /**
   * Getter of the express delivery.
   *
   * @return express delivery
   */
  public boolean getIsExpress() {
    return isExpress;
  }

  /**
   * Getter of the extra warranty.
   *
   * @return time of extra warranty
   */
  public int getExtraWarranty() {
    return extraWarranty;
  }

  /**
   * Getter of the client.
   *
   * @return client
   */
  public Account getAccount() {
    return account;
  }

  /**
   * Getter of the quantity.
   *
   * @return quantity
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Getter of the product.
   *
   * @return product
   */
  public Product getProduct() {
    return product;
  }

  /**
   * Getter of the Expected Delivery Date.
   *
   * @return expected delivery date
   */
  public LocalDate getExpectedDeliveryDate() {
    return expectedDeliveryDate;
  }

  /**
   * Setter of the emission date.
   *
   * @param emissiondate emission date
   */
  public void setEmissionDate(final LocalDate emissiondate) {
    emissionDate = emissiondate;
    setExpectedDeliveryDate(emissiondate);
  }

  /**
   * Setter of the express delivery.
   *
   * @param isexpress express delivery
   */
  public void setIsExpress(final boolean isexpress) {
    isExpress = isexpress;
  }

  /**
   * Setter of the extra warranty.
   *
   * @param extrawarranty extra warranty
   */
  public void setExtraWarranty(final int extrawarranty) {
    extraWarranty = extrawarranty;
  }

  /**
   * Setter of the client.
   *
   * @param client client
   */
  public void setAccount(final Account client) {
    account = client;
  }

  /**
   * Setter of the quantity.
   *
   * @param qty quantity
   */
  public void setQuantity(final int qty) {
    quantity = qty;
  }

  /**
   * Setter of the product.
   *
   * @param pct product
   */
  public void setProduct(final Product pct) {
    product = pct;
  }

  /**
   * Setter of the expected delivery date.
   *
   * @param emDate expected delivery date
   */
  private void setExpectedDeliveryDate(final LocalDate emDate) {
    expectedDeliveryDate = emDate.plusDays(isExpress ? EXPRESSDELIVERY : STANDARDDELIVERY);
  }

  /**
   * Getter of the total price.
   *
   * @return total price
   */
  public double getPrice() {
    return price;
  }

  /**
   * Setter of the total price.
   *
   * @return total price
   */
  private double calculatePrice() {
    double rowPrice = product.getPrice() * quantity;
    double warrantyPrice = extraWarranty * WARRANTY_FEE * rowPrice;
    double deliveryPrice = isExpress ? EXPRESS_FEE * rowPrice : 0;
    return rowPrice + warrantyPrice + deliveryPrice;
  }

  // Methods

  /** toString method. */
  @Override
  public String toString() {
    return "Order{"
        + "\nid ='"
        + id
        + "',"
        + "\nemissionDate = '"
        + emissionDate
        + "',"
        + "\nisExpress = "
        + isExpress
        + ","
        + "\nextraWarranty = "
        + extraWarranty
        + ","
        + "\naccount = "
        + account.toString().replace("\n", "\n\t")
        + "',"
        + "\nquantity = "
        + quantity
        + ","
        + "\nproduct = "
        + product.toString().replace("\n", "\n\t")
        + "',"
        + "\nexpectedDeliveryDate = '"
        + expectedDeliveryDate
        + "'"
        + "\n}";
  }
}
