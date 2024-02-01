package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.AirTransport;
import com.unina.oobd2324gr22.entity.DTO.CentralDeposit;
import com.unina.oobd2324gr22.entity.DTO.CountryDeposit;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.RailsTransport;
import com.unina.oobd2324gr22.entity.DTO.StateDeposit;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import com.unina.oobd2324gr22.entity.DTO.WaterTransport;
import com.unina.oobd2324gr22.entity.DTO.WheeledLarge;
import com.unina.oobd2324gr22.entity.DTO.WheeledSmall;
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
    String transportType = rs.getString("transporttype");
    int transportId = rs.getInt("transportid");
    int maxCapacity = rs.getInt("maxcapacity");
    int occupiedSpace = rs.getInt("occupiedspace");
    boolean isAvailable = rs.getBoolean("isavailable");
    Deposit deposit = depositDAO.getDepositById(rs.getInt("depositid"));

    switch (transportType) {
      case "WheeledSmall":
        return new WheeledSmall(transportId, maxCapacity, occupiedSpace, isAvailable, deposit);
      case "WheeledLarge":
        return new WheeledLarge(
            transportId, maxCapacity, occupiedSpace, isAvailable, (StateDeposit) deposit);
      case "Rails":
        return new RailsTransport(
            transportId, maxCapacity, occupiedSpace, isAvailable, (CountryDeposit) deposit);
      case "Water":
        return new WaterTransport(
            transportId, maxCapacity, occupiedSpace, isAvailable, (CentralDeposit) deposit);
      case "Air":
        return new AirTransport(
            transportId, maxCapacity, occupiedSpace, isAvailable, (CentralDeposit) deposit);
      default:
        return null;
    }
  }

  /**
   * PostgreSQL implementation of the method insertWheeledSmall.<br>
   * {@inheritDoc}
   */
  @Override
  public int insertWheeledSmall(final WheeledSmall wheeledSmall) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * PostgreSQL implementation of the method insertWheeledLarge.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertWheeledLarge(final WheeledLarge wheeledLarge) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * PostgreSQL implementation of the method insertRails.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertRails(final RailsTransport railsTransport) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * PostgreSQL implementation of the method insertWater.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertWater(final WaterTransport waterTransport) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * PostgreSQL implementation of the method insertAir.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertAir(final AirTransport airTransport) throws SQLException {
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
      psSelect = con.prepareStatement("SELECT * FROM deposit WHERE orderid = ?");
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
