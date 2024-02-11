package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Order;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

/** This interface represents the Data Access Object for the Order class. */
public interface OrderDAO {

  /**
   * Insert an order into the DB from the database.
   *
   * @param order Order to insert into the DB
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */
  int insertOrder(Order order) throws SQLException;

  /**
   * Retrive all the orders from the database.
   *
   * @return lista di ordini
   * @throws SQLException possible DB related errors
   */
  List<Order> getOrders() throws SQLException;

  /**
   * Retrive an order by its id from the database.
   *
   * @param id id of the order to get
   * @return order of the id passed
   * @throws SQLException possible DB related errors
   */
  Order getOrderById(int id) throws SQLException;

  /**
   * Retrive all the unfinished orders from the database.
   *
   * @return list of unfinished orders
   * @throws SQLException possible DB related errors
   */
  List<Order> getUnfinishedOrders() throws SQLException;

  /**
   * Retrive the order with the largest quantity of products from the database.
   *
   * @param month month to search for
   * @param year year to search for
   * @return order with largerest quantity of products
   * @throws SQLException possible DB related errors
   */
  Order getOrderWithLargestQuantity(Month month, Year year) throws SQLException;

  /**
   * Retrive the order with the smallest quantity of products from the database.
   *
   * @param month month to search for
   * @param year year to search for
   * @return order with smallest quantity of products
   * @throws SQLException possible DB related errors
   */
  Order getOrderWithSmallestQuantity(Month month, Year year) throws SQLException;

  /**
   * RETRIEVE the most expensive order from the DB.
   *
   * @param month month to search for
   * @param year year to search for
   * @return order with the largest cost
   * @throws SQLException possible DB related errors
   */
  Order getMostExpensiveOrder(Month month, Year year) throws SQLException;

  /**
   * RETRIEVE the less expensive order from the DB.
   *
   * @param month month to search for
   * @param year year to search for
   * @return order with the largest cost
   * @throws SQLException possible DB related errors
   */
  Order getLessExpensiveOrder(Month month, Year year) throws SQLException;

  /**
   * Retrive a list of the number of the orders for each day from the database of the month given.
   *
   * @param month month to search for
   * @param year year to search for
   * @return list of the number of the orders for each day of the month
   * @throws SQLException possible DB related errors
   */
  List<Integer> getOrdersPerDay(Month month, Year year) throws SQLException;

  /**
   * Retrive the orders for a specific client from the database.
   *
   * @param client account to search for
   * @return list of orders matching the search criteria
   * @throws SQLException possible DB related errors
   */
  List<Order> getOrdersByAccount(Account client) throws SQLException;

  /**
   * Retrive the orders by range date from the database.
   *
   * @param dateStart start date
   * @param dateEnd end date
   * @return list of orders matching the search criteria
   * @throws SQLException possible DB related errors
   */
  List<Order> getOrdersByDate(LocalDate dateStart, LocalDate dateEnd) throws SQLException;

  /**
   * Retrive the orders by start date from the database.
   *
   * @param dateStart data inizio
   * @return list of orders matching the search criteria
   * @throws SQLException possible DB related errors
   */
  List<Order> getOrdersByDate(LocalDate dateStart) throws SQLException;

  /**
   * Retrive the orders in a range of date for a specific user from the database.
   *
   * @param client client to search for
   * @param dateStart start date
   * @param dateEnd end date
   * @return list of the orders matching the search criteria
   * @throws SQLException possible DB related errors
   */
  List<Order> getOrdersByAccountAndDate(Account client, LocalDate dateStart, LocalDate dateEnd)
      throws SQLException;

  /**
   * Retrive the number of expiring orders from the database.
   *
   * @return number of expiring orders
   * @throws SQLException possible DB related errors
   */
  int getExpiringOrdersNumber();

  /**
   * Retrive all the roders made by an account in a given month.
   *
   * @param client account to search for
   * @param year year to search for
   * @param month month to search for
   * @return list of orders matching the search criteria
   * @throws SQLException possible DB related errors
   */
  List<Order> getOrdersByAccountAndMonth(Account client, Year year, Month month)
      throws SQLException;

  /**
   * Update an order given its id from the database.
   *
   * @param order order to Update
   * @return number of rows affected by the update
   * @throws SQLException possible DB related errors
   */
  int updateOrderById(Order order) throws SQLException;

  /**
   * Delete an order given its id from the database.
   *
   * @param id orderID to delete
   * @return number of rows affected by the delete
   * @throws SQLException possible DB related errors
   */
  int deleteOrderById(int id) throws SQLException;
}
