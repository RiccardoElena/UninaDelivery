package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Driver;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the ShipmentDAO interface and provides the PostgreSQL implementation for
 * the shipment data access operations.
 */
public class ShipmentDAOPostgre implements ShipmentDAO {

  /** Connection to the database. */
  private Connection con;

  private Shipment populateShipmentFromResultSet(final ResultSet rs) throws SQLException {
    int shipmentId = rs.getInt("shipmentid");
    LocalDate shippingDate = rs.getDate("shippingdate").toLocalDate();
    TransportDAO transportDAO = new TransportDAOPostgre();
    DepositDAO depositDAO = new DepositDAOPostgre();
    float occupiedSpace = rs.getFloat("occupiedspace");

    return new Shipment(
        shipmentId,
        shippingDate,
        transportDAO.getTransportById(rs.getInt("transportid")),
        depositDAO.getDepositById(rs.getInt("shippedfrom")),
        occupiedSpace);
  }

  /** PostgreSQL implementation of the method insertShipment. {@inheritDoc} */
  @Override
  public int insertShipment(final Shipment shipment) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    PreparedStatement st = null;
    int nextField = 1;
    try {
      String query =
          "INSERT INTO shipment (shippingdate, transportid, shippedfrom,"
              + " businessmail) VALUES (?, ?, ?, ?);";
      st = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      st.setDate(nextField++, java.sql.Date.valueOf(shipment.getShippingDate()));
      st.setInt(nextField++, shipment.getTransport().getId());
      st.setInt(nextField++, shipment.getStartingDeposit().getId());
      st.setString(nextField++, shipment.getOperator().getBusinessMail());
      st.executeUpdate();
      ResultSet rs = st.getGeneratedKeys();
      if (rs.next()) {
        int id = rs.getInt(1);
        System.err.println("Shipment inserted with id: " + id);
        return id;
      }
      return -1;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }
  }

  /** PostgreSQL implementation of the method shipOrder. {@inheritDoc} */
  @Override
  public int shipOrder(final Order order, final Shipment shipment) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    PreparedStatement st = null;
    int nextField = 1;
    try {
      System.err.println(shipment.getId());
      String query = "INSERT INTO ships VALUES (?, ?);";
      st = con.prepareStatement(query);
      st.setInt(nextField++, shipment.getId());
      st.setInt(nextField++, order.getId());
      return st.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }
  }

  /** PostgreSQL implementation of the method assignDriver. {@inheritDoc} */
  @Override
  public int assignDriver(final Shipment shipment, final Driver driver) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    PreparedStatement st = null;
    int nextField = 1;
    try {
      String query = "INSERT INTO drives (businessmail, transportid, date) " + "VALUES (?, ?, ?);";
      st = con.prepareStatement(query);
      st.setString(nextField++, driver.getBusinessMail());
      st.setInt(nextField++, shipment.getTransport().getId());
      st.setDate(nextField++, java.sql.Date.valueOf(shipment.getShippingDate()));
      return st.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }
  }

  /** PostgreSQL implementation of the method getUnfinishedShipments. {@inheritDoc} */
  @Override
  public List<Shipment> getUnfinishedShipments() throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUnfinishedShipments'");
  }

  /** PostgreSQL implementation of the method getShipmentById. {@inheritDoc} */
  @Override
  public Shipment getShipmentById(final int id) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getShipmentById'");
  }

  /** PostgreSQL implementation of the method getUnfinishedShipmentsToClient. {@inheritDoc} */
  @Override
  public List<Shipment> getUnfinishedShipmentsToClient() throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'getUnfinishedShipmentsToClient'");
  }

  /** PostgreSQL implementation of the method getUnfinishedShipmentsToClientByArea. {@inheritDoc} */
  @Override
  public List<Shipment> getUnfinishedShipmentsToClientByArea(final String area)
      throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'getUnfinishedShipmentsToClientByArea'");
  }

  /** PostgreSQL implementation of the method getCompatibleShipments. {@inheritDoc} */
  @Override
  public List<Shipment> getCompatibleShipments(final Order order) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Shipment> shipments = new ArrayList<>();
    PreparedStatement st = null;
    ResultSet rs = null;
    int nextField = 1;
    try {
      String query =
          "SELECT *"
              + "FROM shipment S LEFT JOIN (covers NATURAL JOIN transport) C "
              + "ON S.transportid = C.transportid AND S.shippingdate = date "
              + "WHERE S.directedto IS NULL AND "
              + "(hasarrived = FALSE OR hasarrived IS NULL) AND "
              + "C.occupiedspace + ? <= C.maxcapacity AND "
              + "shippedfrom IN ( "
              + "SELECT depositid "
              + "FROM deposit NATURAL JOIN stores "
              + "WHERE name=? AND supplier=? AND "
              + "quantity >= ? AND isSameCity(zipcode, country, ?,?)) "
              + "ORDER BY shippingdate ASC;";
      st = con.prepareStatement(query);
      st.setDouble(nextField++, order.getProduct().getPackageSizeLiters() * order.getQuantity());
      st.setString(nextField++, order.getProduct().getName());
      st.setString(nextField++, order.getProduct().getSupplier());
      st.setInt(nextField++, order.getQuantity());
      st.setString(nextField++, order.getAccount().getAddress().getZipCode());
      st.setString(nextField++, order.getAccount().getAddress().getCountry());

      rs = st.executeQuery();
      while (rs.next()) {
        shipments.add(populateShipmentFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }
    return shipments;
  }

  /** PostgreSQL implementation of the method updateShipment. {@inheritDoc} */
  @Override
  public int updateShipment(final Shipment shipment) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateShipment'");
  }

  /** PostgreSQL implementation of the method deleteShipment. {@inheritDoc} */
  @Override
  public int deleteShipment(final Shipment shipment) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    PreparedStatement st = null;
    try {
      String query = "DELETE FROM shipment WHERE shipmentid = ?;";
      st = con.prepareStatement(query);
      st.setInt(1, shipment.getId());
      return st.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }
  }
}
