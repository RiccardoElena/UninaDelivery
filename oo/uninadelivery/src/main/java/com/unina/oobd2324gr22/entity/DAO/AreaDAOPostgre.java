package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Area;
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

  private Area populateAreaFromResultSet(final ResultSet rs) throws SQLException {
    return new Area(
        rs.getString("zipcode"),
        rs.getString("city"),
        rs.getString("state"),
        rs.getString("country"),
        Area.WorldZone.valueOf(rs.getString("worldzone")));
  }

  /**
   * Create an Address object from the data given.
   *
   * @param addressNo address number
   * @param street street of the address
   * @param zipCode zipCode of the address
   * @param country country of the address
   * @return the Address object created
   * @throws SQLException
   */
  @Override
  public Address extractAddress(
      final String addressNo, final String street, final String zipCode, final String country)
      throws SQLException {
    Area a = new AreaDAOPostgre().getAreaByZipCodeAndCountry(zipCode, country);
    return new Address(
        a.getZipCode(),
        a.getCity(),
        a.getState(),
        a.getCountry(),
        a.getWorldZone(),
        addressNo,
        street);
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
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
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
