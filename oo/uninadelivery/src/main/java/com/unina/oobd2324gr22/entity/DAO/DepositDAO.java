package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.CentralDeposit;
import com.unina.oobd2324gr22.entity.DTO.CountryDeposit;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.StateDeposit;
import java.sql.SQLException;
import java.util.List;

public interface DepositDAO {

  /**
   * Insert a new city deposit in the database.
   *
   * @param deposit deposit to insert
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */
  int insertCityDeposit(Deposit deposit) throws SQLException;

  /**
   * Insert a new state deposit in the database.
   *
   * @param deposit deposit to insert
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */
  int insertStateDeposit(StateDeposit deposit) throws SQLException;

  /**
   * Insert a new country deposit in the database.
   *
   * @param deposit deposit to insert
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */
  int insertCountryDeposit(CountryDeposit deposit) throws SQLException;

  /**
   * Insert a new central deposit in the database.
   *
   * @param deposit deposit to insert
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */
  int insertCentralDeposit(CentralDeposit deposit) throws SQLException;

  /**
   * Retrieve all the deposits from the database.
   *
   * @return list of deposits
   * @throws SQLException possible DB related errors
   */
  List<Deposit> getDeposits() throws SQLException;

  /**
   * Retrieve a deposit by its id from the database.
   *
   * @param id id of the deposit to get
   * @return deposit of the id passed
   * @throws SQLException possible DB related errors
   */
  Deposit getDepositById(int id) throws SQLException;

  /**
   * Retrieve all the deposits of a specific type from the database.
   *
   * @param type type of the deposits to get
   * @return list of deposits of the type passed
   * @throws SQLException possible DB related errors
   */
  List<Deposit> getDepositsByType(String type) throws SQLException;

  /**
   * Retrieve all the deposits of a specific area from the database.
   *
   * @param area area of the deposits to get
   * @return list of deposits of the area passed
   * @throws SQLException possible DB related errors
   */
  List<Deposit> getDepositsByArea(String area) throws SQLException;

  /**
   * Retrieve all the deposits of a specific area and type from the database.
   *
   * @param area area of the deposits to get
   * @param type type of the deposits to get
   * @return list of deposits of the area and type passed
   * @throws SQLException possible DB related errors
   */
  List<Deposit> getDepositsByAreaAndType(String area, String type) throws SQLException;

  /**
   * Update a deposit in the database.
   *
   * @param deposit deposit to update
   * @return the number of rows affected by the update
   * @throws SQLException possible DB related errors
   */
  int updateDeposit(Deposit deposit) throws SQLException;

  /**
   * Delete a deposit from the database.
   *
   * @param deposit deposit to delete
   * @return the number of rows affected by the delete
   * @throws SQLException possible DB related errors
   */
  int deleteDeposit(Deposit deposit) throws SQLException;
}
