package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import java.sql.SQLException;
import java.util.List;

public interface TransportDAO {

  /**
   * Insert a new Transport in the database.
   *
   * @param transport Transport to insert.
   * @return the number of rows affected by the insert.
   * @throws SQLException possible DB related errors.
   */
  int insertTransport(Transport transport) throws SQLException;

  /**
   * Retrieve all the transports from the database.
   *
   * @return list of transports.
   * @throws SQLException possible DB related errors.
   */
  List<Transport> getTransports() throws SQLException;

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
   * Retrieve all available transports of a specific deposit from the database.
   *
   * @param deposit the deposit to get transports from.
   * @return list of available transports of the deposit passed.
   * @throws SQLException possible DB related errors.
   */
  List<Transport> getAvailableTransportsByDeposit(Deposit deposit) throws SQLException;

  /**
   * Update a transport in the database.
   *
   * @param transport transport to update.
   * @return the number of rows affected by the update.
   * @throws SQLException possible DB related errors.
   */
  int updateTransport(Transport transport) throws SQLException;

  /**
   * Delete a transport from the database.
   *
   * @param transport transport to delete.
   * @return the number of rows affected by the delete.
   * @throws SQLException possible DB related errors.
   */
  int deleteTransport(Transport transport) throws SQLException;
}
