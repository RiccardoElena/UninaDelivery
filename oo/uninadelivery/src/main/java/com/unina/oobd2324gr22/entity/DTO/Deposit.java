package com.unina.oobd2324gr22.entity.DTO;

import java.util.ArrayList;
import java.util.List;

public class Deposit {

  // Attributes

  /** Id of the deposit. */
  private int id;

  /** Occupied space of the deposit. */
  private float occupiedSpace;

  /** Total space of the deposit. */
  private float maxCapacity;

  /** Address of the deposit. */
  private Address address;

  /** Transports owned by the deposit. */
  private List<Transport> transports = new ArrayList<>();

  /** Stored products of the deposit. */
  private List<StoredProduct> storedProducts = new ArrayList<>();

  /** Inner util class needed to store quantity along with the product. */
  protected final class StoredProduct {

    // Attributes

    /** Product stored. */
    private Product product;

    /** Quantity of the product stored. */
    private int quantity;

    // Constructors

    /**
     * Constructor with parameters.
     *
     * @param sProduct product stored
     * @param sQuantity quantity of the product stored
     */
    protected StoredProduct(final Product sProduct, final int sQuantity) {
      product = sProduct;
      quantity = sQuantity;
    }

    // Getters and Setters

    /**
     * Getter of the product stored.
     *
     * @return product stored
     */
    public Product getProduct() {
      return product;
    }

    /**
     * Setter of the product stored.
     *
     * @param sProduct product stored
     */
    public void setProduct(final Product sProduct) {
      product = sProduct;
    }

    /**
     * Getter of the quantity of the product stored.
     *
     * @return quantity of the product stored
     */
    public int getQuantity() {
      return quantity;
    }

    /**
     * Setter of the quantity of the product stored.
     *
     * @param sQuantity quantity of the product stored
     */
    public void setQuantity(final int sQuantity) {
      if (sQuantity <= 0) {
        storedProducts.remove(this);
        return;
      }
      quantity = sQuantity;
    }
  }

  // Constructors

  /**
   * Constructor with parameters.
   *
   * @param dId id of the deposit
   * @param dOccupiedSpace occupied space of the deposit
   * @param dMaxCapacity total space of the deposit
   * @param dAddress address of the deposit
   */
  public Deposit(
      final int dId, final float dOccupiedSpace, final float dMaxCapacity, final Address dAddress) {
    id = dId;
    occupiedSpace = dOccupiedSpace;
    maxCapacity = dMaxCapacity;
    address = dAddress;
  }

  // Getters and Setters

  /**
   * Getter of the id.
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * Getter of the occupied space.
   *
   * @return occupied space
   */
  public float getOccupiedSpace() {
    return occupiedSpace;
  }

  /**
   * Setter of the occupied space.
   *
   * @param dOccupiedSpace occupied space
   */
  public void setOccupiedSpace(final float dOccupiedSpace) {
    occupiedSpace = dOccupiedSpace;
  }

  /**
   * Getter of the total space.
   *
   * @return total space
   */
  public float getMaxCapacity() {
    return maxCapacity;
  }

  /**
   * Setter of the total space.
   *
   * @param dMaxCapacity total space
   */
  public void setMaxCapacity(final float dMaxCapacity) {
    maxCapacity = dMaxCapacity;
  }

  /**
   * Getter of the address.
   *
   * @return address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Setter of the address.
   *
   * @param dAddress address
   */
  public void setAddress(final Address dAddress) {
    address = dAddress;
  }

  /**
   * Getter of the stored products.
   *
   * @return stored products
   */
  public List<StoredProduct> getStoredProducts() {
    return storedProducts;
  }

  /**
   * Setter of the stored products.
   *
   * @param sProduct product to store
   * @param sQuantity quantity of the product to store
   */
  public void addStoredProducts(final Product sProduct, final int sQuantity) {
    if (sProduct == null || sQuantity <= 0) {
      return;
    }

    StoredProduct sP = new StoredProduct(sProduct, sQuantity);
    if (storedProducts.contains(sP)) {

      storedProducts
          .get(storedProducts.indexOf(sP))
          .setQuantity(storedProducts.get(storedProducts.indexOf(sP)).getQuantity() + sQuantity);
    } else {
      storedProducts.add(sP);
    }
  }

  /**
   * Getter of the transports.
   *
   * @return transports
   */
  public List<Transport> getTransports() {
    return transports;
  }

  /**
   * Add a wheeled small transport to the deposit.
   *
   * @param transport wheeled small transport to add
   */
  public void addTransport(final Transport transport) {
    if (transport == null) {
      throw new IllegalArgumentException("Transport cannot be null");
    }

    if (transports.contains(transport)) {
      return;
    }

    transports.add(transport);
  }

  /** To string method. */
  @Override
  public String toString() {
    return "Deposito di " + getAddress().getCity() + ", CAP: " + getAddress().getZipCode();
  }

  /** Equals method to comparing deposit's id. */
  @Override
  public boolean equals(final Object dep) {
    if (this == dep) {
      return true;
    }
    if (dep == null || getClass() != dep.getClass()) {
      return false;
    }
    Deposit deposit = (Deposit) dep;
    return (id == deposit.id
        && occupiedSpace == deposit.occupiedSpace
        && maxCapacity == deposit.maxCapacity
        && address.equals(deposit.address));
  }

  /** Hash code method. */
  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
