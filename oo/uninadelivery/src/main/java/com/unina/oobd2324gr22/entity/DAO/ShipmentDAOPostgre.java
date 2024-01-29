package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.entity.DTO.ShipmentToClient;
import com.unina.oobd2324gr22.entity.DTO.ShipmentToDeposit;
import java.sql.SQLException;
import java.util.List;

/**
 * This class implements the ShipmentDAO interface and provides the PostgreSQL implementation for
 * the shipment data access operations.
 */
public class ShipmentDAOPostgre implements ShipmentDAO {

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
