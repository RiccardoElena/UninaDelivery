package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Area;
import com.unina.oobd2324gr22.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// TODO @zGenny @RiccardoElena nly signature has been written, no implementation

public class AreaDAOPostgre implements AreaDAO {

  /** Connection to the database. */
  private Connection con;

  private Area populateAreaFromResultSet(final ResultSet rs) throws SQLException {
    return new Area(
        rs.getString("zipcode"),
        rs.getString("city"),
        rs.getString("state"),
        rs.getString("country"),
        rs.getString("worldzone"));
  }

  /** PostgreSQL implementation of the insertArea method. */
  @Override
  public int insertArea(final Area area) throws SQLException {
    return 0;
  }

  /** PostgreSQL implementation of the getAreas method. */
  @Override
  public List<Area> getAreas() throws SQLException {
    return null;
  }

  /** PostgreSQL implementation of the getAreaByZipCodeAndCountry method. */
  @Override
  public Area getAreaByZipCodeAndCountry(final String zipCode, final String country)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Area area = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement("SELECT * FROM area WHERE zipcode = ? AND country = ?");
      psSelect.setString(1, zipCode);
      psSelect.setString(2, country);
      rs = psSelect.executeQuery();
      while (rs.next()) {
        area = populateAreaFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (psSelect != null) {
        psSelect.close();
      }
      if (con != null) {
        con.close();
      }
    }
    return area;
  }

  /** PostgreSQL implementation of the updateArea method. */
  @Override
  public int updateArea(final Area area) throws SQLException {
    return 0;
  }

  /** PostgreSQL implementation of the deleteArea method. */
  @Override
  public int deleteArea(final Area area) throws SQLException {
    return 0;
  }
}
