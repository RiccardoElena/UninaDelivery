package com.unina.oobd2324gr22;

import java.sql.SQLException;
import java.util.List;
public interface AreaDAO {

    /**
     * Method to INSERT an area into the DB.

     * @param area Area to insert into the DB
     * @return the number of rows affected by the insert
     * @throws SQLException possible DB related errors
     */

    int insertArea(Area area) throws SQLException;

    /**
     * Method to RETRIEVE all the areas.

     * @return list of areas
     * @throws SQLException possible DB related errors
     */
    List<Area> getAreas() throws SQLException;

    /**
     * Method to RETRIEVE an area by its ZipCode and Country.

    * @param zipCode zipCode of the area to get
    * @param country country of the area to get
    * @return area of the zipCode and country passed
    * @throws SQLException possible DB related errors
    */

    Area getAreaByZipCodeAndCountry(String zipCode, String country)
        throws SQLException;

    /**
     * Method to UPDATE an area.

     * @param area Area to update
     * @return the number of rows affected by the update
     * @throws SQLException possible DB related errors
     */

    int updateArea(Area area) throws SQLException;

    /**
     * Method to DELETE an area.

     * @param area Area to delete
     * @return the number of rows affected by the delete
     * @throws SQLException possible DB related errors
     */

    int deleteArea(Area area) throws SQLException;

}
