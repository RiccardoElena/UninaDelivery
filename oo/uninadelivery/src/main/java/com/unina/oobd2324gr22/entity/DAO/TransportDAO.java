package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface TransportDAO extends BasicDAO<Transport> {

  /**
   * Retrieve a transport by its id from the database.
   *
   * @param id id of the transport to get.
   * @return transport of the id passed.
   * @throws SQLException possible DB related errors.
   */
  Transport getTransportById(int id) throws SQLException;

  /**
   * Retrieve all the transports of a specific deposit from the database.
   *
   * @param deposit the deposit to get transports from.
   * @return list of transports of the deposit passed.
   * @throws SQLException possible DB related errors.
   */
  List<Transport> getTransportsByDeposit(Deposit deposit) throws SQLException;

  /**
   * Retrieve all the transports of a specifc type owned by a deposit from the database.
   *
   * @param deposit the deposit to get transports from.
   * @param type the type of the transport to get.
   * @return list of transports of the deposit passed.
   * @throws SQLException possible DB related errors.
   */
  List<Transport> getTransportsByDeposit(Deposit deposit, String type) throws SQLException;

  /**
   * Retrieve all available transports from the database.
   *
   * @return list of available transports.
   * @throws SQLException possible DB related errors.
   */
  List<Transport> getAvailableTransports() throws SQLException;

  /**
   * Retrieve all transports compatible with a given order, date and deposit.
   *
   * @param order the order to get transports for.
   * @param deposit the deposit to get transports from.
   * @param date the date to get transports from.
   * @return list of available transports of the deposit passed.
   * @throws SQLException possible DB related errors.
   */
  List<Transport> getCompatibleTransports(Order order, Deposit deposit, LocalDate date)
      throws SQLException;

  /**
   * Retrieve all available transports of a specific deposit from the database.
   *
   * @param deposit the deposit to get transports from.
   * @return list of available transports of the deposit passed.
   * @throws SQLException possible DB related errors.
   */
  List<Transport> getAvailableTransportsByDeposit(Deposit deposit) throws SQLException;
}
