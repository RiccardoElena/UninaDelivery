package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Area;
import com.unina.oobd2324gr22.utils.DBConnection;
import com.unina.oobd2324gr22.utils.UnimplementedMethodException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class implements the AreaDAO interface and provides the PostgreSQL implementation for the
 * shipment data access operations.
 */
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

  /**
   * PostgreSQL implementation of the insert method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int insert(final Area area) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the getAll method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Area> getAll() throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the getAreaByZipCodeAndCountry method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Area getAreaByZipCodeAndCountry(final String zipCode, final String country)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Area area = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;

    psSelect = con.prepareStatement("SELECT * FROM area WHERE zipcode = ? AND country = ?");
    psSelect.setString(1, zipCode);
    psSelect.setString(2, country);
    rs = psSelect.executeQuery();
    while (rs.next()) {
      area = populateAreaFromResultSet(rs);
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }
    if (con != null) {
      con.close();
    }

    return area;
  }

  /**
   * PostgreSQL implementation of the update method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int update(final Area area) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the delete method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int delete(final Area area) throws SQLException {
    throw new UnimplementedMethodException();
  }
}
