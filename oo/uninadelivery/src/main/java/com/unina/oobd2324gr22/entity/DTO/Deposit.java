package com.unina.oobd2324gr22.entity.DTO;

import java.util.List;

// TODO we should consider to make an abstract class from this
public class Deposit {

  // Attributes

  /**
   * Id of the deposit.
   */

  private int id;

  /**
   * Occupied space of the deposit.
   */

  private float occupiedSpace;

  /**
   * Total space of the deposit.
   */

  private float maxCapacity;

  /**
   * Address of the deposit.
   */

  private Address address;

  /**
   * Stored products of the deposit.
   */

  private List<StoredProduct> storedProducts;

  /**
   * Inner util class needed to store quantity along with the product.
   */
  // TODO we should consider to move this class in his own file in utils package
  protected final class StoredProduct {

      // Attributes

      /**
       * Product stored.
       */

      private Product product;

      /**
       * Quantity of the product stored.
       */

      private int quantity;

      // Constructors

      /**
       * Constructor with parameters.
       *
       * @param sProduct product stored
       * @param sQuantity quantity of the product stored
       */

      protected StoredProduct(final Product sProduct, final int sQuantity) {
        this.product = sProduct;
        this.quantity = sQuantity;
      }

      // Getters and Setters

      /**
       * Getter of the product stored.
       *
       * @return product stored
       */
      public Product getProduct() {
        return this.product;
      }

      /**
       * Setter of the product stored.
       *
       * @param sProduct product stored
       */

      public void setProduct(final Product sProduct) {
        this.product = sProduct;
      }

      /**
       * Getter of the quantity of the product stored.
       *
       * @return quantity of the product stored
       */

      public int getQuantity() {
        return this.quantity;
      }

      /**
       * Setter of the quantity of the product stored.
       *
       * @param sQuantity quantity of the product stored
       */

      public void setQuantity(final int sQuantity) {
        this.quantity = sQuantity;
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

  public Deposit(final int dId, final float dOccupiedSpace,
                 final float dMaxCapacity, final Address dAddress) {
    this.id = dId;
    this.occupiedSpace = dOccupiedSpace;
    this.maxCapacity = dMaxCapacity;
    this.address = dAddress;
  }

  // Getters and Setters

  /**
   * Getter of the id.
   *
   * @return id
   */

  public int getId() {
    return this.id;
  }

  // /**
  //  * Setter of the id.
  //  *
  //  * @param dId id
  //  */

  // private void setId(final int dId) {
  //   this.id = dId;
  // }

  /**
   * Getter of the occupied space.
   *
   * @return occupied space
   */

  public float getOccupiedSpace() {
    return this.occupiedSpace;
  }

  /**
   * Setter of the occupied space.
   *
   * @param dOccupiedSpace occupied space
   */

  public void setOccupiedSpace(final float dOccupiedSpace) {
    this.occupiedSpace = dOccupiedSpace;
  }

  /**
   * Getter of the total space.
   *
   * @return total space
   */

  public float getMaxCapacity() {
    return this.maxCapacity;
  }

  /**
   * Setter of the total space.
   *
   * @param dMaxCapacity total space
   */

  public void setMaxCapacity(final float dMaxCapacity) {
    this.maxCapacity = dMaxCapacity;
  }

  /**
   * Getter of the address.
   *
   * @return address
   */

  public Address getAddress() {
    return this.address;
  }

  /**
   * Setter of the address.
   *
   * @param dAddress address
   */

  public void setAddress(final Address dAddress) {
    this.address = dAddress;
  }

  /**
   * Getter of the stored products.
   *
   * @return stored products
   */
  public List<StoredProduct> getStoredProducts() {
    return this.storedProducts;
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
    if (this.storedProducts.contains(sP)) {

      storedProducts.get(storedProducts.indexOf(sP))
                      .setQuantity(
                        storedProducts.get(
                          storedProducts.indexOf(sP)
                        ).getQuantity() + sQuantity
                      );
    } else {
      this.storedProducts.add(sP);
    }
  }
}
