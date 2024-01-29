package com.unina.oobd2324gr22.entity.DTO;

public class Area {

  /** Zip code of the area. */
  private String zipCode;

  /** City name of the area. */
  private String city;

  /** State name of the area. */
  private String state;

  /** Country name of the area. */
  private String country;

  /** WorldZone of the area. */
  private String worldZone;

  /** Default constructor. */
  public Area() {}

  /**
   * Constructor with parameters.
   *
   * @param zip zip code of the area
   * @param cityN city name of the area
   * @param stateN state name of the area
   * @param countryN country name of the area
   * @param wz world zone of the area
   */
  public Area(
      final String zip,
      final String cityN,
      final String stateN,
      final String countryN,
      final String wz) {
    this.zipCode = zip;
    this.city = cityN;
    this.state = stateN;
    this.country = countryN;
    this.worldZone = wz;
  }

  /**
   * Getter of the zip code.
   *
   * @return zip code
   */
  public String getZipCode() {
    return this.zipCode;
  }

  /**
   * Setter of the zip code.
   *
   * @param zip zip code
   */
  public void setZipCode(final String zip) {
    this.zipCode = zip;
  }

  /**
   * Getter of the city name.
   *
   * @return city name
   */
  public String getCity() {
    return this.city;
  }

  /**
   * Setter of the city name.
   *
   * @param cityN city name
   */
  public void setCity(final String cityN) {
    this.city = cityN;
  }

  /**
   * Getter of the state name.
   *
   * @return state name
   */
  public String getState() {
    return this.state;
  }

  /**
   * Setter of the state name.
   *
   * @param stateN state name
   */
  public void setState(final String stateN) {
    this.state = stateN;
  }

  /**
   * Getter of the country name.
   *
   * @return country name
   */
  public String getCountry() {
    return this.country;
  }

  /**
   * Setter of the country name.
   *
   * @param countryN country name
   */
  public void setCountry(final String countryN) {
    this.country = countryN;
  }

  /**
   * Getter of the world zone.
   *
   * @return world zone
   */
  public String getWorldZone() {
    return this.worldZone;
  }

  /**
   * Setter of the world zone.
   *
   * @param wz world zone
   */
  public void setWorldZone(final String wz) {
    this.worldZone = wz;
  }

  /**
   * Get the string representation of the area.
   *
   * @return string representation of the area
   */
  @Override
  public String toString() {
    return "Area{"
        + "\nzipCode = '"
        + this.zipCode
        + "',"
        + "\ncity = '"
        + this.city
        + "',"
        + "\nstate = '"
        + this.state
        + "',"
        + "\ncountry = '"
        + this.country
        + "',"
        + '}';
  }
}
