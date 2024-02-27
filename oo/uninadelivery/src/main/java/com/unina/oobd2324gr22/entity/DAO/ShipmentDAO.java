package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Driver;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import java.sql.SQLException;
import java.util.List;

public interface ShipmentDAO extends BasicDAO<Shipment> {

  /**
   * Insert an order in ships.
   *
   * @param order order to ships
   * @param shipment shipment where to insert the order
   * @return list of shipments
   * @throws SQLException possible DB related errors
   */
  int shipOrder(Order order, Shipment shipment) throws SQLException;

  /**
   * Assign a driver to a shipment.
   *
   * @param shipment shipment to assign the driver to
   * @param driver id of the driver to assign
   * @return the number of rows affected by the update
   * @throws SQLException possible DB related errors
   */
  int assignDriver(Shipment shipment, Driver driver) throws SQLException;

  /**
   * Retrieve an unfinished by its id from the database.
   *
   * @param id id of the shipment to get
   * @return shipment of the id passed
   * @throws SQLException possible DB related errors
   */
  Shipment getShipmentById(int id) throws SQLException;

  /**
   * Retrieve all the shipments a certain order can be assigned to.
   *
   * @param order order to get the compatible shipments
   * @return list of compatible shipments
   * @throws SQLException possible DB related errors
   */
  List<Shipment> getCompatibleShipments(Order order) throws SQLException;
}
