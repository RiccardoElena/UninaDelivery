package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Transport;
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
 * This class implements the TransportDAO interface and provides the PostgreSQL implementation for
 * the shipment data access operations.
 */
public class TransportDAOPostgre implements TransportDAO {

  private Transport populateTransportFromResultSet(final ResultSet rs) throws SQLException {
    DepositDAO depositDAO = new DepositDAOPostgre();
    int transportId = rs.getInt("transportid");
    int maxCapacity = rs.getInt("maxcapacity");
    boolean isAvailable = rs.getBoolean("isavailable");
    Deposit deposit = depositDAO.getDepositById(rs.getInt("depositid"));

    return new Transport(transportId, maxCapacity, isAvailable, deposit);
  }

  /**
   * PostgreSQL implementation of the method insert.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final int insert(final Transport transport) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method getAll.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final List<Transport> getAll() throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method getTransportById.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final Transport getTransportById(final int id) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    Transport transport = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;

    psSelect = con.prepareStatement("SELECT * FROM transport WHERE transportid = ?");
    psSelect.setInt(1, id);
    rs = psSelect.executeQuery();
    while (rs.next()) {
      transport = populateTransportFromResultSet(rs);
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

    return transport;
  }

  /**
   * PostgreSQL implementation of the method getTransportsByDeposit.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final List<Transport> getTransportsByDeposit(final Deposit deposit) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Transport> transports = new ArrayList<>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;

    psSelect = con.prepareStatement("SELECT * FROM transport WHERE depositid = ?");
    psSelect.setInt(1, deposit.getId());
    rs = psSelect.executeQuery();
    while (rs.next()) {
      transports.add(populateTransportFromResultSet(rs));
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

    return transports;
  }

  /**
   * PostgreSQL implementation of the method getCompatibleTransports.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final List<Transport> getCompatibleTransports(
      final double spaceOccupied, final Deposit deposit, final LocalDate date) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Transport> transports = new ArrayList<>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    IterableInt fieldNumber = new IterableInt(1);

    String query =
        "select * from transport where depositid = ? AND isAvailable = TRUE AND transporttype ="
            + " 'WheeledSmall' AND NOT EXISTS (select 1 from covers where covers.transportid ="
            + " transport.transportid and date = ?) and maxcapacity >= ?";
    psSelect = con.prepareStatement(query);
    psSelect.setInt(fieldNumber.next(), deposit.getId());
    psSelect.setDate(fieldNumber.next(), java.sql.Date.valueOf(date));
    psSelect.setDouble(fieldNumber.next(), spaceOccupied);
    rs = psSelect.executeQuery();
    while (rs.next()) {
      transports.add(populateTransportFromResultSet(rs));
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

    return transports;
  }

  /**
   * PostgreSQL implementation of the method update.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final int update(final Transport transport) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method delete.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final int delete(final Transport transport) throws SQLException {
    throw new UnimplementedMethodException();
  }
}
