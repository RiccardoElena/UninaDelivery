package com.unina.oobd2324gr22.entity.DAO;

import java.sql.SQLException;
import java.util.List;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.entity.DTO.ShipmentToClient;
import com.unina.oobd2324gr22.entity.DTO.ShipmentToDeposit;

public interface ShipmentDAO {

  /**
   * Insert a new shipment towards clients in the database.

   * @param shipment shipment to insert
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */
  int insertShipment(ShipmentToClient shipment) throws SQLException;

  /**
   * Insert a new shipment towards a deposit in the database.

   * @param shipment shipment to insert
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */
  int insertShipment(ShipmentToDeposit shipment) throws SQLException;

  /**
   * Retrieve all the unfinished the  from the database.

   * @return list of shipments
   * @throws SQLException possible DB related errors
   */
  List<Shipment> getUnfinishedShipments() throws SQLException;

  /**
   * Retrieve an unfinished  by its id from the database.

   * @param id id of the shipment to get
   * @return shipment of the id passed
   * @throws SQLException possible DB related errors
   */
  Shipment getShipmentById(int id) throws SQLException;

  /**
   * Retrieve all the unfinished the  towards clients from the database.

   * @return list of shipments towards clients
   * @throws SQLException possible DB related errors
   */
  List<ShipmentToClient> getUnfinishedShipmentsToClient() throws SQLException;

  /**
   * Retrieve all the unfinished the  towards a deposit from the database.

   * @return list of shipments towards a deposit
   * @throws SQLException possible DB related errors
   */
  List<ShipmentToDeposit> getUnfinishedShipmentsToDeposit() throws SQLException;

  /**
   * Retrieve all unfinished shipments towards clients by its destination area
   * from the database.

   * @param area area of the shipments to get
   * @return list of shipments towards clients of the area passed
   */
  List<ShipmentToClient> getUnfinishedShipmentsToClientByArea(String area)
    throws SQLException;

  /**
   * Retrieve all unfinished shipments towards a deposit
   * from the database.

   * @param deposit deposit of the shipments to get
   * @return list of shipments towards a deposit
   */
  List<ShipmentToDeposit> getUnfinishedShipmentsByDestinationDeposit(
                                                  int deposit)
                                                  throws SQLException;

  /**
   * Update a shipment towards clients in the database.

   * @param shipment shipment to update
   * @return the number of rows affected by the update
   * @throws SQLException possible DB related errors
   */
  int updateShipment(ShipmentToClient shipment) throws SQLException;

  /**
   * Update a shipment towards a deposit in the database.

   * @param shipment shipment to update
   * @return the number of rows affected by the update
   * @throws SQLException possible DB related errors
   */
  int updateShipmentToDeposit(ShipmentToDeposit shipment) throws SQLException;

  /**
   * Delete a shipment in the database.

   * @param shipment shipment to delete
   * @return the number of rows affected by the delete
   * @throws SQLException possible DB related errors
   */
  int deleteShipment(Shipment shipment) throws SQLException;
}
