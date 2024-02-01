package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Area;
import com.unina.oobd2324gr22.entity.DTO.CentralDeposit;
import com.unina.oobd2324gr22.entity.DTO.CountryDeposit;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.StateDeposit;
import com.unina.oobd2324gr22.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DepositDAOPostgre implements DepositDAO {

  /** Connection to the database. */
  private Connection con;

  private Deposit populateDepositFromResultSet(final ResultSet rs) throws SQLException {
    String depositType = rs.getString("deposittype");

    switch (depositType) {
      case "City":
        return createDeposit(rs);
      case "State":
        return createStateDeposit(rs);
      case "Country":
        return createCountryDeposit(rs);
      case "Central":
        return createCentralDeposit(rs);
      default:
        throw new SQLException("Invalid deposit type");
    }
  }

  private Deposit createDeposit(final ResultSet rs) throws SQLException {
    return new Deposit(
        rs.getInt("depositid"),
        rs.getInt("occupiedspace"),
        rs.getInt("maxcapacity"),
        createAddress(rs));
  }

  private StateDeposit createStateDeposit(final ResultSet rs) throws SQLException {
    return new StateDeposit(
        rs.getInt("depositid"),
        rs.getInt("occupiedspace"),
        rs.getInt("maxcapacity"),
        createAddress(rs));
  }

  private CountryDeposit createCountryDeposit(final ResultSet rs) throws SQLException {
    return new CountryDeposit(
        rs.getInt("depositid"),
        rs.getInt("occupiedspace"),
        rs.getInt("maxcapacity"),
        createAddress(rs));
  }

  private CentralDeposit createCentralDeposit(final ResultSet rs) throws SQLException {
    return new CentralDeposit(
        rs.getInt("depositid"),
        rs.getInt("occupiedspace"),
        rs.getInt("maxcapacity"),
        createAddress(rs));
  }

  private Address createAddress(final ResultSet rs) throws SQLException {
    Area a =
        new AreaDAOPostgre()
            .getAreaByZipCodeAndCountry(rs.getString("zipcode"), rs.getString("country"));
    return new Address(
        a.getZipCode(),
        a.getCity(),
        a.getState(),
        a.getCountry(),
        a.getWorldZone(),
        rs.getString("addressno"),
        rs.getString("street"));
  }

  /**
   * PostgreSQL implementation of the method insertCityDeposit.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertCityDeposit(final Deposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }

  @Override
  public final int insertStateDeposit(final StateDeposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }

  @Override
  public final int insertCountryDeposit(final CountryDeposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }

  @Override
  public final int insertCentralDeposit(final CentralDeposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }

  @Override
  public final List<Deposit> getDeposits() throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return null;
  }

  /**
   * PostgreSQL implementation of the method insertCityDeposit.<br>
   * {@inheritDoc}
   */
  @Override
  public final Deposit getDepositById(final int id) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Deposit deposit = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement("SELECT * FROM deposit WHERE orderid = ?");
      psSelect.setInt(1, id);
      rs = psSelect.executeQuery();
      while (rs.next()) {
        deposit = populateDepositFromResultSet(rs);
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
    return deposit;
  }

  /**
   * PostgreSQL implementation of the method insertCityDeposit.<br>
   * {@inheritDoc}
   */
  // XXX @zGenny not sure about type of date. Change LocalDate to the correct type retuned by jfx
  // datepicker
  @Override
  public final List<Deposit> getDepositsForShipmentsToClients(
      final Order order, final LocalDate date) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Deposit> deposits = new ArrayList<>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    int nextField = 1;
    String zipCode = order.getAccount().getAddress().getZipCode();
    String country = order.getAccount().getAddress().getCountry();
    try {
      psSelect =
          con.prepareStatement(
              "SELECT * FROM deposit D WHERE isSameCity(D.zipcode, D.country, ?, ?) AND EXISTS"
                  + " (SELECT 1 FROM stores WHERE D.depositid = depositid AND name=? AND supplier=?"
                  + " AND quantity>=?) AND EXISTS (SELECT 1 FROM transport WHERE D.depositid ="
                  + " depositid AND transporttype='WheeledSmall' AND transportid NOT IN (SELECT"
                  + " transportid FROM covers WHERE date = ?)) OR ) AND EXISTS (SELECT 1 FROM"
                  + " driver WHERE D.depositid = depositid AND businessmail NOT IN (SELECT"
                  + " businessmail FROM drives WHERE date = ?))");
      psSelect.setString(nextField++, zipCode);
      psSelect.setString(nextField++, country);
      psSelect.setString(nextField++, order.getProduct().getName());
      psSelect.setString(nextField++, order.getProduct().getSupplier());
      psSelect.setInt(nextField++, order.getQuantity());
      psSelect.setDate(nextField++, java.sql.Date.valueOf(date));
      rs = psSelect.executeQuery();
      while (rs.next()) {
        deposits.add(populateDepositFromResultSet(rs));
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
    return deposits;
  }

  /**
   * PostgreSQL implementation of the method insertCityDeposit.<br>
   * {@inheritDoc}
   */
  // XXX @zGenny not sure about type of date. Change LocalDate to the correct type retuned by jfx
  // datepicker
  // FIXME @zGenny I'm unsure about the correctness of this query and the one above please check it
  @Override
  public final List<Deposit> getDepositsForShipmentsToDeposits(
      final Order order, final LocalDate date) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Deposit> deposits = new ArrayList<>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    int nextField = 1;
    String zipCode = order.getAccount().getAddress().getZipCode();
    String country = order.getAccount().getAddress().getCountry();
    try {
      psSelect =
          con.prepareStatement(
              "SELECT * FROM deposit D WHERE isSameCity(D.zipcode, D.country, ?, ?) AND EXISTS"
                  + " (SELECT 1 FROM stores WHERE D.depositid = depositid AND name=? AND"
                  + " supplier=?) AND (EXISTS (SELECT 1 FROM transport WHERE D.depositid ="
                  + " depositid AND maxcapacity >= ? AND transportid NOT IN (SELECT transportid"
                  + " FROM covers WHERE date = ?)) OR (transporttype <> 'WheeledSmall' AND"
                  + " transporttype <> 'WheeledLarge') OR EXISTS (SELECT 1 FROM driver WHERE"
                  + " D.depositid = depositid AND businessmail NOT IN (SELECT businessmail FROM"
                  + " drives WHERE date = ?)))");
      psSelect.setString(nextField++, zipCode);
      psSelect.setString(nextField++, country);
      psSelect.setString(nextField++, order.getProduct().getName());
      psSelect.setString(nextField++, order.getProduct().getSupplier());
      psSelect.setInt(nextField++, order.getQuantity());
      psSelect.setDate(nextField++, java.sql.Date.valueOf(date));
      rs = psSelect.executeQuery();
      while (rs.next()) {
        deposits.add(populateDepositFromResultSet(rs));
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
    return deposits;
  }

  @Override
  public final List<Deposit> getDepositsByType(final String type) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return null;
  }

  @Override
  public final List<Deposit> getDepositsByArea(final String area) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return null;
  }

  @Override
  public final List<Deposit> getDepositsByAreaAndType(final String area, final String type)
      throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return null;
  }

  @Override
  public final int updateDeposit(final Deposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }

  @Override
  public final int deleteDeposit(final Deposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }
}
