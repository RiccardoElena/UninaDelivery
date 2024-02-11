package com.unina.oobd2324gr22.entity.DAO;

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
  Connection con;

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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertShipment'");
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
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
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
              + "ORDER BY (directedto IS NULL) DESC, shippingdate ASC;";
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
      closeResources(rs, st, con);
    }

    return shipments;
  }

  private void closeResources(
      final ResultSet rs, final PreparedStatement st, final Connection con) {
    try {
      if (rs != null) {
        rs.close();
      }
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteShipment'");
  }
}
