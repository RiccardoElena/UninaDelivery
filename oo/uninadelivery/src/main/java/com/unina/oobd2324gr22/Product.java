package com.unina.oobd2324gr22;

public class Product {

  /**
   * Default constructor.
   */
  private String category;

  /**
   * Default constructor.
   */
  private String name;

  /**
   * Default constructor.
   */
  private String supplier;

  /**
   * Default constructor.
   */
  private String description;

  /**
   * Default constructor.
   */
  private double packageSizeLiters;

  /**
   * Default constructor.
   */
  private boolean isFragile;

  /**
   * Default constructor.
   */
  private float price;

  /**
   * @param pCategory category
   * @param pName name
   * @param pSupplier supplier
   * @param pDescription description
   * @param pPackageSizeLiters packageSizeLiters
   * @param pIsFragile isFragile
   * @param pPrice price
   */
  public Product(final String pCategory, final String pName,
                  final String pSupplier, final String pDescription,
                  final double pPackageSizeLiters, final boolean pIsFragile,
                  final float pPrice) {
    this.category = pCategory;
    this.name = pName;
    this.supplier = pSupplier;
    this.description = pDescription;
    this.packageSizeLiters = pPackageSizeLiters;
    this.isFragile = pIsFragile;
    this.price = pPrice;
  }

  /**
   * @return category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @param pCategory category
   */
  public void setCategory(final String pCategory) {
    this.category = pCategory;
  }

  /**
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * @param pName name
   */
  public void setName(final String pName) {
    this.name = pName;
  }

  /**
   * @return supplier
   */
  public String getSupplier() {
    return supplier;
  }

  /**
   * @param pSupplier supplier
   */
  public void setSupplier(final String pSupplier) {
    this.supplier = pSupplier;
  }

  /**
   * @return description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param pDescription description
   */
  public void setDescription(final String pDescription) {
    this.description = pDescription;
  }

  /**
   * @return packageSizeLiters
   */
  public double getPackageSizeLiters() {
    return packageSizeLiters;
  }

  /**
   * @param pPackageSizeLiters packageSizeLiters
   */
  public void setPackageSizeLiters(final double pPackageSizeLiters) {
    this.packageSizeLiters = pPackageSizeLiters;
  }

  /**
   * @return isFragile
   */
  public boolean getIsFragile() {
    return isFragile;
  }

  /**
   * @param pIsFragile isFragile
   */
  public void setIsFragile(final boolean pIsFragile) {
    this.isFragile = pIsFragile;
  }

  /**
   * @return price
   */
  public float getPrice() {
    return price;
  }

  /**
   * @param pPrice price
   */
  public void setPrice(final float pPrice) {
    this.price = pPrice;
  }

  /**
   * Method to get the string representation of the product.
   *
   * @return string
   */
  @Override
  public String toString() {
    return "Product ["
            + "category='" + category + '\''
            + ", name='" + name + '\''
            + ", supplier='" + supplier + '\''
            + ", description='" + description + '\''
            + ", packageSizeLiters=" + packageSizeLiters
            + ", isFragile=" + isFragile
            + ", price=" + price
            + ']';
  }
}
