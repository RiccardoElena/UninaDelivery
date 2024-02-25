package com.unina.oobd2324gr22.entity.DTO;

public class Area {

  // Attributes

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

  // Constructors

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
    zipCode = zip;
    city = cityN;
    state = stateN;
    country = countryN;
    worldZone = wz;
  }

  // Getters and Setters

  /**
   * Getter of the zip code.
   *
   * @return zip code
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * Setter of the zip code.
   *
   * @param zip zip code
   */
  public void setZipCode(final String zip) {
    zipCode = zip;
  }

  /**
   * Getter of the city name.
   *
   * @return city name
   */
  public String getCity() {
    return city;
  }

  /**
   * Setter of the city name.
   *
   * @param cityN city name
   */
  public void setCity(final String cityN) {
    city = cityN;
  }

  /**
   * Getter of the state name.
   *
   * @return state name
   */
  public String getState() {
    return state;
  }

  /**
   * Setter of the state name.
   *
   * @param stateN state name
   */
  public void setState(final String stateN) {
    state = stateN;
  }

  /**
   * Getter of the country name.
   *
   * @return country name
   */
  public String getCountry() {
    return country;
  }

  /**
   * Setter of the country name.
   *
   * @param countryN country name
   */
  public void setCountry(final String countryN) {
    country = countryN;
  }

  /**
   * Getter of the world zone.
   *
   * @return world zone
   */
  public String getWorldZone() {
    return worldZone;
  }

  /**
   * Setter of the world zone.
   *
   * @param wz world zone
   */
  public void setWorldZone(final String wz) {
    worldZone = wz;
  }

  // Methods

  /**
   * Get the string representation of the area.
   *
   * @return string representation of the area
   */
  @Override
  public String toString() {
    return "Area{"
        + "\nzipCode = '"
        + zipCode
        + "',"
        + "\ncity = '"
        + city
        + "',"
        + "\nstate = '"
        + state
        + "',"
        + "\ncountry = '"
        + country
        + "',"
        + '}';
  }

  /**
   * Check if two areas are equal.
   *
   * @param obj object to compare
   * @return true if the areas are equal, false otherwise
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Area area = (Area) obj;
    return (zipCode.equals(area.zipCode)
        && city.equals(area.city)
        && state.equals(area.state)
        && country.equals(area.country)
        && worldZone.equals(area.worldZone));
  }

  /**
   * Get the hash code of the area.
   *
   * @return hash code of the area
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
