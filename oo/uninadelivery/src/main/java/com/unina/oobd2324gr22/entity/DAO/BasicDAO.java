package com.unina.oobd2324gr22.entity.DAO;

import java.sql.SQLException;
import java.util.List;

public interface BasicDAO<T> {

  // CRUD operations

  /**
   * Create a new entity in the database.
   *
   * @param t the entity to insert
   * @return the number of rows affected
   * @throws SQLException possible DB related errors
   */
  int insert(T t) throws SQLException;

  /**
   * Retrieve an entity from the database.
   *
   * @return a list of all the entities in the database
   * @throws SQLException possible DB related errors
   */
  List<T> getAll() throws SQLException;

  /**
   * Update an entity in the database.
   *
   * @param t the entity to update
   * @return the number of rows affected
   * @throws SQLException possible DB related errors
   */
  int update(T t) throws SQLException;

  /**
   * Delete an entity from the database.
   *
   * @param t the entity to delete
   * @return the number of rows affected
   * @throws SQLException possible DB related errors
   */
  int delete(T t) throws SQLException;
}
