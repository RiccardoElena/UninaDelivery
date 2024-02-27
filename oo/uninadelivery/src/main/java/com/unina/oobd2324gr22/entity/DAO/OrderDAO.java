package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Order;
import java.sql.SQLException;
import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.List;

/** This interface represents the Data Access Object for the Order class. */
public interface OrderDAO extends BasicDAO<Order> {

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
   * @return list of the number of the orders for each day of the month. The list has the length of
   *     the month and the i-th element is the number of orders for the i-th day of the month
   * @throws SQLException possible DB related errors
   */
  List<Integer> getOrdersPerDay(Month month, Year year) throws SQLException;

  /**
   * Retrive the orders by filters from the database.
   *
   * @param filters filters to apply
   * @return list of orders matching the search criteria
   * @throws SQLException possible DB related errors
   */
  List<Order> getOrdersByFilters(HashMap<String, Object> filters) throws SQLException;

  /**
   * Retrive the number of expiring orders from the database.
   *
   * @return number of expiring orders
   * @throws SQLException possible DB related errors
   */
  int getExpiringOrdersNumber() throws SQLException;

  /**
   * Retrive all the orders made by an account in a given month.
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
   * Retrive the smalest year there is an order in the database.
   *
   * @return the smallest year there is an order in the database
   * @throws SQLException possible DB related errors
   */
  Year getStartingYear() throws SQLException;
}
