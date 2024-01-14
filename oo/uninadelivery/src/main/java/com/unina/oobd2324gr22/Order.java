package com.unina.oobd2324gr22;

import java.time.LocalDate;

public class Order {

  /**
   * Default constructor.
   */
  private int id;

  /**
   * Default constructor.
   */
  private LocalDate emissionDate;

  /**
   * Default constructor.
   */
  private boolean isExpress;

  /**
   * Default constructor.
   */
  private int extraWarranty;

  /**
   * Default constructor.
   */
  private Account account;

  /**
   * Default constructor.
   */
  private int quantity;
  /**
   * Default constructor.
   */
  private Product product;

  /**
   * Default constructor.
   */
  private LocalDate expectedDeliveryDate;

  /**
   * Default constructor.
   */
  private static final int EXPRESSDELIVERY  = 3;
  /**
   * Default constructor.
   */
  private static final int STANDARDDELIVERY = 7;

  /**
   * Default constructor.

   * @param orderid ciao
   * @param emissiondate ciao
   * @param isexpress ciao
   * @param extrawarranty ciao
   * @param client ciao
   * @param qty ciao
   * @param pct ciao
   */
  public Order(final int orderid, final LocalDate emissiondate,
              final boolean isexpress, final int extrawarranty,
              final Account client, final int qty,
              final Product pct) {
    this.id = orderid;
    this.emissionDate = emissiondate;
    this.expectedDeliveryDate = emissiondate
                                  .plusDays(
                                    isexpress
                                    ? EXPRESSDELIVERY
                                    : STANDARDDELIVERY
                                  );
    this.isExpress = isexpress;
    this.extraWarranty = extrawarranty;
    this.account = client;
    this.quantity = qty;
    this.product = pct;
  }

  /**
   * Default constructor.

   * @return ciao
   */
  public int getOrderId() {
    return id;
  }

  /**
   * Default constructor.

   * @return ciao
   */
  public LocalDate getEmissionDate() {
    return emissionDate;
  }

  /**
   * Default constructor.

   * @return ciao
   */
  public boolean getIsExpress() {
    return isExpress;
  }

  /**
   * Default constructor.

   * @return ciao
   */
  public int getExtraWarranty() {
    return extraWarranty;
  }

  /**
   * Default constructor.

   * @return ciao
   */
  public String getAccountEmail() {
    return account.getEmail();
  }

  /**
   * Default constructor.

   * @return ciao
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Default constructor.

   * @return ciao
   */
  public String getProductName() {
    return product.getName();
  }

  /**
   * Default constructor.

   * @return ciao
   */
  public String getProductSupplier() {
    return product.getSupplier();
  }

  /**
   * Default constructor.

   * @return ciao
   */
  public LocalDate getExpectedDeliveryDate() {
    return expectedDeliveryDate;
  }

  /**
   * Default constructor.

   * @param orderid ciao
   */
  public void setOrderId(final int orderid) {
    this.id = orderid;
  }

  /**
   * Default constructor.

   * @param emissiondate ciao
   */
  public void setEmissionDate(final LocalDate emissiondate) {
    this.emissionDate = emissiondate;
  }

  /**
   * Default constructor.

   * @param isexpress ciao
   */
  public void setIsExpress(final boolean isexpress) {
    this.isExpress = isexpress;
  }

  /**
   * Default constructor.

   * @param extrawarranty ciao
   */
  public void setExtraWarranty(final int extrawarranty) {
    this.extraWarranty = extrawarranty;
  }

  /**
   * Default constructor.

   * @param client ciao
   */
  public void setAccount(final Account client) {
    this.account = client;
  }

  /**
   * Default constructor.

   * @param qty ciao
   */
  public void setQuantity(final int qty) {
    this.quantity = qty;
  }

  /**
   * Default constructor.

   * @param pct ciao
   */
  public void setProduct(final Product pct) {
    this.product = pct;
  }

  /**
   * Default constructor.

   * @param expecteddeliverydate ciao
   */
  public void setExpectedDeliveryDate(final LocalDate expecteddeliverydate) {
    this.expectedDeliveryDate = expecteddeliverydate;
  }

  /**
   * toString method.
   */
  @Override
  public String toString() {
    return "Order [id=" + id + ", emissionDate=" + emissionDate
              + ", isExpress=" + isExpress  + ", extraWarranty="
              + extraWarranty + ", account=" + account + ", quantity="
              + quantity + ", product=" + product + ", expectedDeliveryDate="
              + expectedDeliveryDate + "]";
  }
}
