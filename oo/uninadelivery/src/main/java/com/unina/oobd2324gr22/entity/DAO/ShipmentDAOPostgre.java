package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Deposit;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the ShipmentDAO interface and provides the PostgreSQL implementation for
 * the shipment data access operations.
 */
public class ShipmentDAOPostgre implements ShipmentDAO {

  private Shipment populateShipmentFromResultSet(final ResultSet rs) throws SQLException {
    int shipmentId = rs.getInt("shipmentid");
    LocalDate shippingDate = rs.getDate("shippingdate").toLocalDate();
    TransportDAO transportDAO = new TransportDAOPostgre();
    DepositDAO depositDAO = new DepositDAOPostgre();
    Deposit shippedFrom = depositDAO.getDepositById(rs.getInt("shippedfrom"));
    Shipment shipment;

    if (rs.getString("directedto") == null) {
      WheeledSmall transport =
          (WheeledSmall) transportDAO.getTransportById(rs.getInt("transportid"));
      shipment = new ShipmentToClient(shipmentId, shippingDate, transport, shippedFrom);
    } else {
      Deposit directedTo = depositDAO.getDepositById(rs.getInt("directedto"));
      shipment =
          new ShipmentToDeposit(
              shipmentId,
              shippingDate,
              transportDAO.getTransportById(rs.getInt("transportid")),
              shippedFrom,
              directedTo);
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
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Shipment> shipments = new ArrayList<>();
    PreparedStatement st = null;
    ResultSet rs = null;
    int nextField = 1;
    try {
      String query =
          "SELECT * FROM shipment S WHERE (hasarrived = FALSE OR hasarrived IS NULL) AND"
              + " shippedfrom IN ( SELECT depositid FROM deposit NATURAL JOIN stores WHERE"
              + " name=? AND supplier=? AND (directedto IS NOT NULL OR (quantity >= ? AND"
              + " isSameCity(zipcode, country, ?,?)))) AND EXISTS ( SELECT 1 FROM transport"
              + " NATURAL JOIN covers WHERE S.transportid = transportid AND S.shippingdate ="
              + " date AND ((S.directedto IS NOT NULL AND occupiedspace + quantity * ? <="
              + " maxcapacity) OR(S.directedto IS NULL AND occupiedspace + ? <= maxcapacity))"
              + " ORDER BY (directedto IS NULL) DESC, shippingdate ASC";
      st = con.prepareStatement(query);
      st.setString(nextField++, order.getProduct().getName());
      st.setString(nextField++, order.getProduct().getSupplier());
      st.setInt(nextField++, order.getQuantity());
      st.setString(nextField++, order.getAccount().getAddress().getZipCode());
      st.setString(nextField++, order.getAccount().getAddress().getCountry());
      double packageSizeLiters = order.getProduct().getPackageSizeLiters();
      st.setDouble(nextField++, packageSizeLiters);
      st.setDouble(nextField++, packageSizeLiters * order.getQuantity());

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
