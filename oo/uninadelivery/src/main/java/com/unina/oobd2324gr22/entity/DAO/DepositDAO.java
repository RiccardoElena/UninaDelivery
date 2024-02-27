package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Order;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface DepositDAO extends BasicDAO<Deposit> {

  /**
   * Retrieve a deposit by its id from the database.
   *
   * @param id id of the deposit to get
   * @return deposit of the id passed
   * @throws SQLException possible DB related errors
   */
  Deposit getDepositById(int id) throws SQLException;

  /**
   * Retrieve all the deposits of a specific area from the database.
   *
   * @param area area of the deposits to get
   * @return list of deposits of the area passed
   * @throws SQLException possible DB related errors
   */
  List<Deposit> getDepositsByArea(String area) throws SQLException;

  /**
   * Retrieve all the deposit elegible for a shipment towards clients for a specific order in a
   * certain date.
   *
   * @param order order to get compatible deposits
   * @param date date to get compatible deposits
   * @return list of compatible deposits
   * @throws SQLException
   */
  List<Deposit> getCompatibleDeposits(Order order, LocalDate date) throws SQLException;
}
