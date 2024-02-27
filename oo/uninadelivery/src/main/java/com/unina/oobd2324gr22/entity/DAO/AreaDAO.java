package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Area;
import java.sql.SQLException;

public interface AreaDAO extends BasicDAO<Area> {

  /**
   * Create an address from the data given.
   *
   * @param addressNo address number
   * @param street street of the address
   * @param zipCode zip code of the address
   * @param country country of the address
   * @return the Address object created
   * @throws SQLException possible DB related errors
   */
  Address extractAddress(String addressNo, String street, String zipCode, String country)
      throws SQLException;

  /**
   * Retrieve an area by its ZipCode and Country.
   *
   * @param zipCode zipCode of the area to get
   * @param country country of the area to get
   * @return area of the zipCode and country passed
   * @throws SQLException possible DB related errors
   */
  Area getAreaByZipCodeAndCountry(String zipCode, String country) throws SQLException;
}
