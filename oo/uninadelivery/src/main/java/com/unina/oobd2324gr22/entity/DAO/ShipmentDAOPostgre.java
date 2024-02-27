package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Driver;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
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
 * This class implements the ShipmentDAO interface and provides the PostgreSQL implementation for
 * the shipment data access operations.
 */
public class ShipmentDAOPostgre implements ShipmentDAO {

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

  /**
   * PostgreSQL implementation of the method insert.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int insert(final Shipment shipment) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    PreparedStatement st = null;
    IterableInt fieldNumber = new IterableInt(1);

    String query =
        "INSERT INTO shipment (shippingdate, transportid, shippedfrom,"
            + " businessmail) VALUES (?, ?, ?, ?);";
    st = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
    st.setDate(fieldNumber.next(), java.sql.Date.valueOf(shipment.getShippingDate()));
    st.setInt(fieldNumber.next(), shipment.getTransport().getId());
    st.setInt(fieldNumber.next(), shipment.getStartingDeposit().getId());
    st.setString(fieldNumber.next(), shipment.getOperator().getBusinessMail());
    st.executeUpdate();
    ResultSet rs = st.getGeneratedKeys();
    if (rs.next()) {
      int id = rs.getInt(1);
      return id;
    }

    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }

    return -1;
  }

  /**
   * PostgreSQL implementation of the method shipOrder.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int shipOrder(final Order order, final Shipment shipment) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    PreparedStatement st = null;
    IterableInt fieldNumber = new IterableInt(1);

    String query = "INSERT INTO ships VALUES (?, ?);";
    st = con.prepareStatement(query);
    st.setInt(fieldNumber.next(), shipment.getId());
    st.setInt(fieldNumber.next(), order.getId());

    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }
    return st.executeUpdate();
  }

  /**
   * PostgreSQL implementation of the method assignDriver.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int assignDriver(final Shipment shipment, final Driver driver) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    PreparedStatement st = null;
    IterableInt fieldNumber = new IterableInt(1);

    String query = "INSERT INTO drives (businessmail, transportid, date) " + "VALUES (?, ?, ?);";
    st = con.prepareStatement(query);
    st.setString(fieldNumber.next(), driver.getBusinessMail());
    st.setInt(fieldNumber.next(), shipment.getTransport().getId());
    st.setDate(fieldNumber.next(), java.sql.Date.valueOf(shipment.getShippingDate()));

    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }
    return st.executeUpdate();
  }

  /**
   * PostgreSQL implementation of the method getAll.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Shipment> getAll() throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method getShipmentById.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Shipment getShipmentById(final int id) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method getCompatibleShipments.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Shipment> getCompatibleShipments(final Order order) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Shipment> shipments = new ArrayList<>();
    PreparedStatement st = null;
    ResultSet rs = null;
    IterableInt fieldNumber = new IterableInt(1);

    String query =
        "SELECT *"
            + "FROM shipment S LEFT JOIN (covers NATURAL JOIN transport) C "
            + "ON S.transportid = C.transportid AND S.shippingdate = date "
            + "WHERE S.directedto IS NULL AND "
            + "S.shippingdate >= CURRENT_DATE AND "
            + "(hasarrived = FALSE OR hasarrived IS NULL) AND "
            + "C.occupiedspace + ? <= C.maxcapacity AND "
            + "shippedfrom IN ( "
            + "SELECT depositid "
            + "FROM deposit NATURAL JOIN stores "
            + "WHERE name=? AND supplier=? AND "
            + "quantity >= ? AND isSameCity(zipcode, country, ?,?)) "
            + "ORDER BY shippingdate ASC;";
    st = con.prepareStatement(query);
    st.setDouble(
        fieldNumber.next(), order.getProduct().getPackageSizeLiters() * order.getQuantity());
    st.setString(fieldNumber.next(), order.getProduct().getName());
    st.setString(fieldNumber.next(), order.getProduct().getSupplier());
    st.setInt(fieldNumber.next(), order.getQuantity());
    st.setString(fieldNumber.next(), order.getAccount().getAddress().getZipCode());
    st.setString(fieldNumber.next(), order.getAccount().getAddress().getCountry());

    rs = st.executeQuery();
    while (rs.next()) {
      shipments.add(populateShipmentFromResultSet(rs));
    }

    if (rs != null) {
      rs.close();
    }
    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }

    return shipments;
  }

  /**
   * PostgreSQL implementation of the method update.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int update(final Shipment shipment) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the method delete.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int delete(final Shipment shipment) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    PreparedStatement st = null;

    String query = "DELETE FROM shipment WHERE shipmentid = ?;";
    st = con.prepareStatement(query);
    st.setInt(1, shipment.getId());

    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }

    return st.executeUpdate();
  }
}
