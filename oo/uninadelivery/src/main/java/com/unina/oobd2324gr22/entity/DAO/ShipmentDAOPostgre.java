package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.entity.DTO.ShipmentToClient;
import com.unina.oobd2324gr22.entity.DTO.ShipmentToDeposit;
import com.unina.oobd2324gr22.entity.DTO.WheeledSmall;
import com.unina.oobd2324gr22.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    Shipment shipment = null;
    if (rs.getString("directedto") == null) {
      shipment =
          new ShipmentToClient(
              rs.getInt("shipmentid"),
              rs.getDate("shippingdate").toLocalDate(),
              (WheeledSmall) new TransportDAOPostgre().getTransportById(rs.getInt("transportid")),
              new DepositDAOPostgre().getDepositById(rs.getInt("shippedfrom")));

    } else {
      shipment =
          new ShipmentToDeposit(
              rs.getInt("shipmentid"),
              rs.getDate("shippingdate").toLocalDate(),
              new TransportDAOPostgre().getTransportById(rs.getInt("transportid")),
              new DepositDAOPostgre().getDepositById(rs.getInt("shippedfrom")),
              new DepositDAOPostgre().getDepositById(rs.getInt("directedto")));
    }
    return shipment;
  }

  /** PostgreSQL implementation of the method insertShipment. {@inheritDoc} */
  @Override
  public int insertShipment(final ShipmentToClient shipment) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertShipment'");
  }

  /** PostgreSQL implementation of the method insertShipment. {@inheritDoc} */
  @Override
  public int insertShipment(final ShipmentToDeposit shipment) throws SQLException {
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
  public List<ShipmentToClient> getUnfinishedShipmentsToClient() throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'getUnfinishedShipmentsToClient'");
  }

  /** PostgreSQL implementation of the method getUnfinishedShipmentsToDeposit. {@inheritDoc} */
  @Override
  public List<ShipmentToDeposit> getUnfinishedShipmentsToDeposit() throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'getUnfinishedShipmentsToDeposit'");
  }

  /** PostgreSQL implementation of the method getUnfinishedShipmentsToClientByArea. {@inheritDoc} */
  @Override
  public List<ShipmentToClient> getUnfinishedShipmentsToClientByArea(final String area)
      throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'getUnfinishedShipmentsToClientByArea'");
  }

  /**
   * PostgreSQL implementation of the method getUnfinishedShipmentsToDepositByDeposit. {@inheritDoc}
   */
  @Override
  public List<ShipmentToDeposit> getUnfinishedShipmentsByDestinationDeposit(final int deposit)
      throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'getUnfinishedShipmentsToDepositByDeposit'");
  }

  /** PostgreSQL implementation of the method getCompatibleShipments. {@inheritDoc} */
  @Override
  public List<Shipment> getCompatibleShipments(final Order order) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Shipment> shipments = new ArrayList<Shipment>();
    PreparedStatement st = null;
    ResultSet rs = null;
    int nextField = 1;
    try {
      // TODO!: @zGenny I'm super unsure about this query, please check it
      st =
          con.prepareStatement(
              "SELECT * FROM shipment S WHERE (hasarrived = FALSE OR hasarrived IS NULL) AND"
                  + " shippedfrom IN ( SELECT depositid FROM deposit NATURAL JOIN stores WHERE"
                  + " name=? AND supplier=? AND (directedto IS NOT NULL OR (quantity >= ? AND"
                  + " isSameCity(zipcode, country, ?,?)))) AND EXISTS ( SELECT 1 FROM transport"
                  + " NATURAL JOIN covers WHERE S.transportid = transportid AND S.shippingdate ="
                  + " date AND ((S.directedto IS NOT NULL AND occupiedspace + quantity * ? <="
                  + " maxcapacity) OR(S.directedto IS NULL AND occupiedspace + ? <= maxcapacity))"
                  + " ORDER BY (directedto IS NULL) DESC, shippingdate ASC");
      st.setString(nextField++, order.getProduct().getName());
      st.setString(nextField++, order.getProduct().getSupplier());
      st.setInt(nextField++, order.getQuantity());
      st.setString(nextField++, order.getAccount().getAddress().getZipCode());
      st.setString(nextField++, order.getAccount().getAddress().getCountry());
      st.setDouble(nextField++, order.getProduct().getPackageSizeLiters());
      st.setDouble(nextField++, order.getProduct().getPackageSizeLiters() * order.getQuantity());

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
  public int updateShipment(final ShipmentToClient shipment) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateShipment'");
  }

  /** PostgreSQL implementation of the method updateShipment. {@inheritDoc} */
  @Override
  public int updateShipmentToDeposit(final ShipmentToDeposit shipment) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateShipmentToDeposit'");
  }

  /** PostgreSQL implementation of the method deleteShipment. {@inheritDoc} */
  @Override
  public int deleteShipment(final Shipment shipment) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteShipment'");
  }
}
