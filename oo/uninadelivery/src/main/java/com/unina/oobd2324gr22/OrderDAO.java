package com.unina.oobd2324gr22;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface OrderDAO {

  /**
   * Method to insert an order into the DB.
   * @param order Order to insert into the DB
   * @return the number of rows affected by the insert
   * @throws SQLException
   */
  int insertOrder(Order order) throws SQLException;

  /**
   * Method to get all the orders.
   * @return lista di ordini
   * @throws SQLException
   */
  List<Order> getOrders() throws SQLException;

  /**
   * Method to get an order by its id.
   * @param id id of the order to get
   * @return order of the id passed
   * @throws SQLException
   */
  Order getOrderById(int id) throws SQLException;

  /**
   * Method to get all the unfinished orders.
   * @return list of unfinished orders
   * @throws SQLException
   */
  List<Order> getUnfinishedOrders() throws SQLException;

  /**
   * Method to get the order with the largest quantity of products.
   * @return order with largerest quantity of products
   * @throws SQLException
   */
  Order getOrderWithLargestQuantity() throws SQLException;

  /**
   * Method to get the order with the smallest quantity of products.
   * @return order with smallest quantity of products
   * @throws SQLException
   */
  Order getOrderWithSmallestQuantity() throws SQLException;

  /**
   * Method to get a list of the number of the orders for each day
   * of the month given.
   * @param month month to search for
   * @param year year to search for
   * @return list of the number of the orders for each day of the month
   * @throws SQLException
   */
  List<Integer> getOrdersPerDay(int month, int year) throws SQLException;


  /**
   * Method to get the orders by email.
   * @param email email of the account to search for
   * @return list of orders matching the search criteria
   * @throws SQLException
   */
  List<Order> getOrdersByEmail(String email) throws SQLException;

  /**
   * Method to get the orders by range date.
   * @param dateStart start date
   * @param dateEnd end date
   * @return list of orders matching the search criteria
   * @throws SQLException
   */
  List<Order> getOrdersByDate(LocalDate dateStart, LocalDate dateEnd)
  throws SQLException;

  /**
   * Method to get the orders by start date.
   * @param dateStart data inizio
   * @return list of orders matching the search criteria
   * @throws SQLException
   */
  List<Order> getOrdersByDate(LocalDate dateStart)
  throws SQLException;

  /**
   * Method to get the orders by email and range date.
   * @param email email to search for
   * @param dateStart start date
   * @param dateEnd end date
   * @return list of the orders matching the search criteria
   * @throws SQLException
   */
  List<Order> getOrdersByEmailAndDate(String email, LocalDate dateStart,
                                      LocalDate dateEnd)
  throws SQLException;

  /**
   * Method to update an order given its id.
   * @param order order to Update
   * @return number of rows affected by the update
   * @throws SQLException
   */
  int updateOrderById(Order order) throws SQLException;

  /**
   * Method to delete an order given its id.
   * @param id orderID to delete
   * @return number of rows affected by the delete
   * @throws SQLException
   */
  int deleteOrderById(int id) throws SQLException;

}
