package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import java.sql.SQLException;
import java.util.List;

public interface ShipmentDAO {

  /**
   * Insert a new shipment towards clients in the database.
   *
   * @param shipment shipment to insert
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */
  int insertShipment(Shipment shipment) throws SQLException;

  /**
   * Retrieve all the unfinished the from the database.
   *
   * @return list of shipments
   * @throws SQLException possible DB related errors
   */
  List<Shipment> getUnfinishedShipments() throws SQLException;

  /**
   * Retrieve an unfinished by its id from the database.
   *
   * @param id id of the shipment to get
   * @return shipment of the id passed
   * @throws SQLException possible DB related errors
   */
  Shipment getShipmentById(int id) throws SQLException;

  /**
   * Retrieve all the unfinished the towards clients from the database.
   *
   * @return list of shipments towards clients
   * @throws SQLException possible DB related errors
   */
  List<Shipment> getUnfinishedShipmentsToClient() throws SQLException;

  /**
   * Retrieve all unfinished shipments towards clients by its destination area from the database.
   *
   * @param area area of the shipments to get
   * @return list of shipments towards clients of the area passed
   */
  List<Shipment> getUnfinishedShipmentsToClientByArea(String area) throws SQLException;

  /**
   * Retrieve all the shipments a certain order can be assigned to.
   *
   * @param order order to get the compatible shipments
   * @return list of compatible shipments
   * @throws SQLException possible DB related errors
   */
  List<Shipment> getCompatibleShipments(Order order) throws SQLException;

  /**
   * Update a shipment towards clients in the database.
   *
   * @param shipment shipment to update
   * @return the number of rows affected by the update
   * @throws SQLException possible DB related errors
   */
  int updateShipment(Shipment shipment) throws SQLException;

  /**
   * Delete a shipment in the database.
   *
   * @param shipment shipment to delete
   * @return the number of rows affected by the delete
   * @throws SQLException possible DB related errors
   */
  int deleteShipment(Shipment shipment) throws SQLException;
}
