package com.unina.oobd2324gr22;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * This interface represents the Data Access Object for the Order class.
 */
public interface OrderDAO {

  /**
   * Method to insert an order into the DB.

   * @param order Order to insert into the DB
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */

  int insertOrder(Order order) throws SQLException;

  /**
   * Method to get all the orders.

   * @return lista di ordini
   * @throws SQLException possible DB related errors
   */

  List<Order> getOrders() throws SQLException;

  /**
   * Method to get an order by its id.

   * @param id id of the order to get
   * @return order of the id passed
   * @throws SQLException possible DB related errors
   */

  Order getOrderById(int id) throws SQLException;

  /**
   * Method to get all the unfinished orders.

   * @return list of unfinished orders
   * @throws SQLException possible DB related errors
   */

  List<Order> getUnfinishedOrders() throws SQLException;

  /**
   * Method to get the order with the largest quantity of products.

   * @return order with largerest quantity of products
   * @throws SQLException possible DB related errors
   */

  Order getOrderWithLargestQuantity() throws SQLException;

  /**
   * Method to get the order with the smallest quantity of products.

   * @return order with smallest quantity of products
   * @throws SQLException possible DB related errors
   */

  Order getOrderWithSmallestQuantity() throws SQLException;

  /**
   * Method to get a list of the number of the orders for each day
   * of the month given.

   * @param month month to search for
   * @param year year to search for
   * @return list of the number of the orders for each day of the month
   * @throws SQLException possible DB related errors
   */

  List<Integer> getOrdersPerDay(int month, int year) throws SQLException;


  /**
   * Method to get the orders for a specific client.

   * @param client account to search for
   * @return list of orders matching the search criteria
   * @throws SQLException possible DB related errors
   */

  List<Order> getOrdersByAccount(Account client) throws SQLException;

  /**
   * Method to get the orders by range date.

   * @param dateStart start date
   * @param dateEnd end date
   * @return list of orders matching the search criteria
   * @throws SQLException possible DB related errors
   */

  List<Order> getOrdersByDate(LocalDate dateStart, LocalDate dateEnd)
      throws SQLException;

  /**
   * Method to get the orders by start date.

   * @param dateStart data inizio
   * @return list of orders matching the search criteria
   * @throws SQLException possible DB related errors
   */

  List<Order> getOrdersByDate(LocalDate dateStart)
      throws SQLException;

  /**
   * Method to get the orders in a range of date for a specific user.

   * @param client client to search for
   * @param dateStart start date
   * @param dateEnd end date
   * @return list of the orders matching the search criteria
   * @throws SQLException possible DB related errors
   */

  List<Order> getOrdersByAccountAndDate(Account client, 
                                      LocalDate dateStart,
                                      LocalDate dateEnd)
      throws SQLException;

  /**
   * Method to update an order given its id.

   * @param order order to Update
   * @return number of rows affected by the update
   * @throws SQLException possible DB related errors
   */
  int updateOrderById(Order order) throws SQLException;

  /**
   * Method to delete an order given its id.

   * @param id orderID to delete
   * @return number of rows affected by the delete
   * @throws SQLException possible DB related errors
   */

  int deleteOrderById(int id) throws SQLException;

}
