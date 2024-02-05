package com.unina.oobd2324gr22.entity.DTO;

public class Address extends Area {

  // Attributes

  /** Address number of the address. */
  private String addressNumber;

  /** Name of the street of the address. */
  private String street;

  // Constructors

  /**
   * Constructor with parameters.
   *
   * @param zip zip code of the area
   * @param cityN city name of the area
   * @param stateN state name of the area
   * @param countryN country name of the area
   * @param wz world zone of the area
   * @param number address number of the address
   * @param streetName name of the street of the address
   */
  public Address(
      final String zip,
      final String cityN,
      final String stateN,
      final String countryN,
      final String wz,
      final String number,
      final String streetName) {
    super(zip, cityN, stateN, countryN, wz);
    this.addressNumber = number;
    this.street = streetName;
  }

  // Getters and Setters

  /**
   * Getter of the address number.
   *
   * @return address number
   */
  public String getAddressNumber() {
    return this.addressNumber;
  }

  /**
   * Setter of the address number.
   *
   * @param number address number
   */
  public void setAddressNumber(final String number) {
    this.addressNumber = number;
  }

  /**
   * Getter of the street name.
   *
   * @return street name
   */
  public String getStreet() {
    return this.street;
  }

  /**
   * Setter of the street name.
   *
   * @param streetName street name
   */
  public void setStreet(final String streetName) {
    this.street = streetName;
  }

  /**
   * Get the area the addres is in.
   *
   * @return area the address is in
   */
  public Area getArea() {
    return new Area(
        this.getZipCode(), this.getCity(), this.getState(), this.getCountry(), this.getWorldZone());
  }

  /**
   * Get the string representation of the address.
   *
   * @return string representation of the address
   */
  @Override
  public String toString() {
    return this.getStreet()
        + " "
        + this.getAddressNumber()
        + ", "
        + this.getZipCode()
        + " "
        + this.getCity()
        + "("
        + this.getState()
        + "), "
        + this.getCountry();
  }
}
