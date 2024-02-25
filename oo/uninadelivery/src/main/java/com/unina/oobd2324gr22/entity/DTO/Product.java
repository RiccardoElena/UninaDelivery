package com.unina.oobd2324gr22.entity.DTO;

public class Product {

  // Attributes

  /** Default constructor. */
  private String category;

  /** Default constructor. */
  private String name;

  /** Default constructor. */
  private String supplier;

  /** Default constructor. */
  private String description;

  /** Default constructor. */
  private double packageSizeLiters;

  /** Default constructor. */
  private boolean isFragile;

  /** Default constructor. */
  private float price;

  // Constructors

  /**
   * Constructor with parameters.
   *
   * @param pCategory category
   * @param pName name
   * @param pSupplier supplier
   * @param pDescription description
   * @param pPackageSizeLiters packageSizeLiters
   * @param pIsFragile isFragile
   * @param pPrice price
   */
  public Product(
      final String pCategory,
      final String pName,
      final String pSupplier,
      final String pDescription,
      final double pPackageSizeLiters,
      final boolean pIsFragile,
      final float pPrice) {
    category = pCategory;
    name = pName;
    supplier = pSupplier;
    description = pDescription;
    packageSizeLiters = pPackageSizeLiters;
    isFragile = pIsFragile;
    price = pPrice;
  }

  // Getters and Setters

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
    category = pCategory;
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
    name = pName;
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
    supplier = pSupplier;
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
    description = pDescription;
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
    packageSizeLiters = pPackageSizeLiters;
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
    isFragile = pIsFragile;
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
    price = pPrice;
  }

  // Methods

  /**
   * Get the string representation of the product.
   *
   * @return string
   */
  @Override
  public String toString() {
    return "Product {"
        + "\ncategory = '"
        + category
        + "',"
        + "\nname = '"
        + name
        + "',"
        + "\nsupplier = '"
        + supplier
        + "',"
        + "\ndescription = '"
        + description
        + "',"
        + "\npackageSizeLiters = "
        + packageSizeLiters
        + ","
        + "\nisFragile = "
        + isFragile
        + ","
        + "\nprice = "
        + price
        + "\n}";
  }
}
