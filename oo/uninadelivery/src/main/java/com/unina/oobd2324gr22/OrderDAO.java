package com.unina.oobd2324gr22;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface OrderDAO {

  /**
   * @return lista di ordini
   * @throws SQLException
   */
  List<Order> getOrders() throws SQLException;

  /**
   * @param id
   * @return order of the id passed
   * @throws SQLException
   */
  Order getOrderById(int id) throws SQLException;

  /**
   * @return list of unfinished orders
   * @throws SQLException
   */
  List<Order> getUnfinishedOrders() throws SQLException;

  /**
   * @return order with largerest quantity of products
   * @throws SQLException
   */
  Order getOrderWithLargestQuantity() throws SQLException;

  /**
   * @return order with smallest quantity of products
   * @throws SQLException
   */
  Order getOrderWithSmallestQuantity() throws SQLException;

  /**
   * @param month month to search for
   * @param year year to search for
   * @return list of the number of the orders for each day of the month
   * @throws SQLException
   */
  List<Integer> getOrdersPerDay(int month, int year) throws SQLException;

  /**
   * @param order order to insert into the DB
   * @return number of rows affected by the insert
   * @throws SQLException
   */
  int insertOrder(Order order) throws SQLException;

  /**
   * @param email email to search for
   * @return ciao
   * @throws SQLException
   */
  List<Order> getOrdersByEmail(String email) throws SQLException;

  /**
   * @param dateStart start date
   * @param dateEnd end date
   * @return ciao
   * @throws SQLException
   */
  List<Order> getOrdersByDate(LocalDate dateStart, LocalDate dateEnd)
  throws SQLException;

  /**
   * @param dateStart start date
   * @return ciao
   * @throws SQLException
   */
  List<Order> getOrdersByDate(LocalDate dateStart)
  throws SQLException;

  /**
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
   * @param order orderId to Update
   * @return row affected
   */
  int updateOrderById(Order order) throws SQLException;

  /**
   * @param id orderId to delete
   * @return row affected
   */
  int deleteOrderById(int id) throws SQLException;

}
