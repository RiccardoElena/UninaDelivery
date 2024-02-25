package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Area;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.utils.DBConnection;
import com.unina.oobd2324gr22.utils.IterableInt;
import com.unina.oobd2324gr22.utils.UnimplementedMethodException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the DepositDAO interface and provides the PostgreSQL implementation for the
 * shipment data access operations.
 */
public class DepositDAOPostgre implements DepositDAO {

  /** Connection to the database. */
  private Connection con;

  private Deposit populateDepositFromResultSet(final ResultSet rs) throws SQLException {
    return new Deposit(
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
   * PostgreSQL implementation of the method insert.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final int insert(final Deposit deposit) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method getAll.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final List<Deposit> getAll() throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method insertCityDeposit.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final Deposit getDepositById(final int id) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Deposit deposit = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;

    psSelect = con.prepareStatement("SELECT * FROM deposit WHERE depositid = ?");
    psSelect.setInt(1, id);
    rs = psSelect.executeQuery();
    while (rs.next()) {
      deposit = populateDepositFromResultSet(rs);
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

    return deposit;
  }

  /**
   * PostgreSQL implementation of the method getCompatibleDeposits.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final List<Deposit> getCompatibleDeposits(final Order order, final LocalDate date)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Deposit> deposits = new ArrayList<>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    IterableInt fieldNumber = new IterableInt(1);
    String zipCode = order.getAccount().getAddress().getZipCode();
    String country = order.getAccount().getAddress().getCountry();

    psSelect =
        con.prepareStatement(
            "SELECT * FROM deposit D WHERE isSameCity(D.zipcode, D.country, ?, ?) AND EXISTS"
                + " (SELECT 1 FROM stores WHERE D.depositid = stores.depositid AND name = ? AND"
                + " supplier = ? AND quantity >= ?) AND EXISTS (SELECT 1 FROM transport WHERE"
                + " D.depositid = depositid AND transporttype = 'WheeledSmall' AND isAvailable ="
                + " TRUE AND transportid NOT IN (SELECT transportid FROM covers WHERE date = ?)"
                + " and maxcapacity >= ?) AND EXISTS (SELECT 1 FROM driver WHERE D.depositid ="
                + " depositid AND businessmail NOT IN (SELECT businessmail FROM drives WHERE date"
                + " = ?))");
    psSelect.setString(fieldNumber.next(), zipCode);
    psSelect.setString(fieldNumber.next(), country);
    psSelect.setString(fieldNumber.next(), order.getProduct().getName());
    psSelect.setString(fieldNumber.next(), order.getProduct().getSupplier());
    psSelect.setInt(fieldNumber.next(), order.getQuantity());
    psSelect.setDate(fieldNumber.next(), java.sql.Date.valueOf(date));
    psSelect.setDouble(
        fieldNumber.next(), order.getProduct().getPackageSizeLiters() * order.getQuantity());
    psSelect.setDate(fieldNumber.next(), java.sql.Date.valueOf(date));
    rs = psSelect.executeQuery();
    while (rs.next()) {
      deposits.add(populateDepositFromResultSet(rs));
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

    return deposits;
  }

  /**
   * PostgreSQL implementation of the method getDepositsByType.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final List<Deposit> getDepositsByType(final String type) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method getDepositsByArea.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final List<Deposit> getDepositsByArea(final String area) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method getDepositsByAreaAndType.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final List<Deposit> getDepositsByAreaAndType(final String area, final String type)
      throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method update.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final int update(final Deposit deposit) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method delete.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final int delete(final Deposit deposit) throws SQLException {
    throw new UnimplementedMethodException();
  }
}
