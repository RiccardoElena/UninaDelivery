package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import com.unina.oobd2324gr22.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportDAOPostgre implements TransportDAO {

  /** Connection to the database. */
  private Connection con;

  private Transport populateTransportFromResultSet(final ResultSet rs) throws SQLException {
    DepositDAO depositDAO = new DepositDAOPostgre();
    int transportId = rs.getInt("transportid");
    int maxCapacity = rs.getInt("maxcapacity");
    boolean isAvailable = rs.getBoolean("isavailable");
    Deposit deposit = depositDAO.getDepositById(rs.getInt("depositid"));

    return new Transport(transportId, maxCapacity, isAvailable, deposit);
  }

  /**
   * PostgreSQL implementation of the method insertTransport.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertTransport(final Transport transport) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertAir'");
  }

  /**
   * PostgreSQL implementation of the method getTransports.<br>
   * {@inheritDoc}
   */
  @Override
  public final List<Transport> getTransports() throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTransports'");
  }

  /**
   * PostgreSQL implementation of the method getTransportById.<br>
   * {@inheritDoc}
   */
  @Override
  public final Transport getTransportById(final int id) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Transport transport = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement("SELECT * FROM transport WHERE transportid = ?");
      psSelect.setInt(1, id);
      rs = psSelect.executeQuery();
      while (rs.next()) {
        transport = populateTransportFromResultSet(rs);
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
    return transport;
  }

  /**
   * PostgreSQL implementation of the method getTransportsByDeposit.<br>
   * {@inheritDoc}
   */
  @Override
  public final List<Transport> getTransportsByDeposit(final Deposit deposit) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Transport> transports = new ArrayList<>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement("SELECT * FROM transport WHERE depositid = ?");
      psSelect.setInt(1, deposit.getId());
      rs = psSelect.executeQuery();
      while (rs.next()) {
        transports.add(populateTransportFromResultSet(rs));
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
    return transports;
  }

  /**
   * PostgreSQL implementation of the method getTransportsByDeposit.<br>
   * {@inheritDoc}
   */
  @Override
  public final List<Transport> getTransportsByDeposit(final Deposit deposit, final String type)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Transport> transports = new ArrayList<>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect =
          con.prepareStatement("SELECT * FROM transport WHERE depositid = ? AND transporttype = ?");
      psSelect.setInt(1, deposit.getId());
      psSelect.setString(2, type);
      rs = psSelect.executeQuery();
      while (rs.next()) {
        transports.add(populateTransportFromResultSet(rs));
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
    return transports;
  }

  /**
   * PostgreSQL implementation of the method getAvailableTransports.<br>
   * {@inheritDoc}
   */
  @Override
  public final List<Transport> getAvailableTransports() throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAvailableTransports'");
  }

  /**
   * PostgreSQL implementation of the method getAvailableTransportsByDeposit.<br>
   * {@inheritDoc}
   */
  @Override
  public final List<Transport> getAvailableTransportsByDeposit(final Deposit deposit)
      throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'getAvailableTransportsByDeposit'");
  }

  /**
   * PostgreSQL implementation of the method updateTransport.<br>
   * {@inheritDoc}
   */
  @Override
  public final int updateTransport(final Transport transport) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateTransport'");
  }

  /**
   * PostgreSQL implementation of the method deleteTransport.<br>
   * {@inheritDoc}
   */
  @Override
  public final int deleteTransport(final Transport transport) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteTransport'");
  }
}
