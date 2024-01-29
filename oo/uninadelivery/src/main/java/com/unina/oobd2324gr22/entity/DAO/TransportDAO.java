package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.AirTransport;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.RailsTransport;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import com.unina.oobd2324gr22.entity.DTO.WaterTransport;
import com.unina.oobd2324gr22.entity.DTO.WheeledLarge;
import com.unina.oobd2324gr22.entity.DTO.WheeledSmall;
import java.sql.SQLException;
import java.util.List;

public interface TransportDAO {

  /**
   * Insert a new WheeledSmall transport in the database.
   *
   * @param wheeledSmall WheeledSmall transport to insert.
   * @return the number of rows affected by the insert.
   * @throws SQLException possible DB related errors.
   */
  int insertWheeledSmall(WheeledSmall wheeledSmall) throws SQLException;

  /**
   * Insert a new WheeledLarge transport in the database.
   *
   * @param wheeledLarge WheeledLarge transport to insert.
   * @return the number of rows affected by the insert.
   * @throws SQLException possible DB related errors.
   */
  int insertWheeledLarge(WheeledLarge wheeledLarge) throws SQLException;

  /**
   * Insert a new Rails transport in the database.
   *
   * @param railsTransport Rails transport to insert.
   * @return the number of rows affected by the insert.
   * @throws SQLException possible DB related errors.
   */
  int insertRails(RailsTransport railsTransport) throws SQLException;

  /**
   * Insert a new Water transport in the database.
   *
   * @param waterTransport Water transport to insert.
   * @return the number of rows affected by the insert.
   * @throws SQLException possible DB related errors.
   */
  int insertWater(WaterTransport waterTransport) throws SQLException;

  /**
   * Insert a new Air transport in the database.
   *
   * @param airTransport Air transport to insert.
   * @return the number of rows affected by the insert.
   * @throws SQLException possible DB related errors.
   */
  int insertAir(AirTransport airTransport) throws SQLException;

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
