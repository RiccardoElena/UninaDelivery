package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Area;
import java.sql.SQLException;

public interface AreaDAO extends BasicDAO<Area> {

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
