package com.unina.oobd2324gr22;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Implementation of the OrderDAO interface for PostgreSQL DataBase.
 */
public class OrderDAOPostgre implements OrderDAO {

  /**
   * Connection istance.
   */
  private Connection con;

  /**
   * Base query for retrieving orders.
   */
  private String baseQuery = "SELECT * FROM \"Orders\" ";

  /**
   * Internal method to populate an order from a ResultSet.

   * @param rs ResultSet to populate the order from
   * @return order populated from the ResultSet
   */
  private Order populateOrderFromResultSet(final ResultSet rs) 
      throws SQLException {
    Order order = null;
    try {
      order = new Order(
        rs.getInt("orderid"),
        rs.getDate("emissiondate").toLocalDate(),
        rs.getBoolean("isexpress"),
        rs.getInt("extrawarranty"),
          new AccountDAOPostgre().getAccountByEmail(
          rs.getString("email")
        ),
        rs.getInt("quantity"),
          new ProductDAOPostgre().getProductByNameAndSupplier(
          rs.getString("name"),
          rs.getString("supplier")
        )
    );
    } catch (SQLException e) {
      e.printStackTrace();
    }
        
    return order;
  }

  /**
   * CREATE a new Order into the DB.

   * @param order Order to insert into the DB
   * @return the number of rows affected by the insert
   */
  @Override
  public int insertOrder(final Order order)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    int rowAffected;
    try {
      int nextField = 1;
      PreparedStatement psInsert = con.prepareStatement(
                              "INSERT INTO \"Orders\" "
                            + "(emissiondate, isexpress, extrawarranty, email, "
                            + "quantity, name, supplier) VALUES "
                            + "(?, ?, ?, ?, ?, ?, ?)"
                            );
      psInsert.setDate(nextField++, java.sql.Date.valueOf(
          order.getEmissionDate()));
      psInsert.setBoolean(nextField++, (order.getIsExpress() ? true : null));
      psInsert.setInt(nextField++, order.getExtraWarranty());
      psInsert.setString(nextField++, order.getAccountEmail());
      psInsert.setInt(nextField++, order.getQuantity());
      psInsert.setString(nextField++, order.getProductName());
      psInsert.setString(nextField++, order.getProductSupplier());
      rowAffected = psInsert.executeUpdate();
      psInsert.close();
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
      con.close();
      throw e;
    }
    return rowAffected;
  }

  /**
   * RETRIEVE an Order from the DB by its id.

   * @param id id
   * @return order of the id passed
   */
  @Override
  public Order getOrderById(final int id) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Order order = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement(baseQuery + "WHERE id = ?");
      psSelect.setInt(1, id);
      rs = psSelect.executeQuery();
      while (rs.next()) {
        order = populateOrderFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      psSelect.close();
      con.close();
    }
    return order;
  }

  /**
   * RETRIEVE all the unfinished Orders from the DB.

   * @return list of unfinished orders
   */
  @Override
  public List<Order> getUnfinishedOrders() throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    Statement st = null;
    ResultSet rs = null;
    try {

      st = con.createStatement();
      rs = st.executeQuery(baseQuery
                                + " WHERE isCompleted = false OR"
                                +       " isCompleted IS NULL");

      while (rs.next()) {
        orders.add(populateOrderFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      st.close();
      con.close();
    }

    return orders;
  }

  /**
   * RETRIEVE all the Orders from the DB.

   * @return lista di ordini
   */
  @Override
  public List<Order> getOrders() throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    Statement st = null;
    ResultSet rs = null;
    try {

      st = con.createStatement();
      rs = st.executeQuery(baseQuery);

      while (rs.next()) {

        orders.add(populateOrderFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      st.close();
      con.close();
    }

    return orders;
  }

  private String createQueryForMinMax(final boolean isMin)
      throws SQLException {
    String st
        = baseQuery + "WHERE quantity = (SELECT "
        + (isMin ? "MIN" : "MAX")
        + "(quantity) FROM \"Orders\") LIMIT 1";
    return st;
  }

  /**
   * RETRIVE the order with the largest quantity of products.

   * @return order with biggest quantity of products
   */
  @Override
  public Order getOrderWithLargestQuantity() throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Order order = null;
    Statement st = null;
    ResultSet rs = null;
    try {

      st = con.createStatement();
      rs = st.executeQuery(createQueryForMinMax(false));

      while (rs.next()) {

        order = populateOrderFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      st.close();
      con.close();
    }

    return order;
  }

  /**
   * RETRIVE the order with the smallest quantity of products.

   * @return order with smallest quantity of products
   */
  @Override
  public Order getOrderWithSmallestQuantity() throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Order order = null;
    Statement st = null;
    ResultSet rs = null;
    try {

      st = con.createStatement();
      rs = st.executeQuery(createQueryForMinMax(true));

      while (rs.next()) {

        order = populateOrderFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      st.close();
      con.close();
    }

    return order;
  }

  /**
   * RETRIEVE all orders from the DB made by the same account.

   * @param client accout to search for
   * @return list of orders matching the search criteria
   */
  @Override
  public List<Order> getOrdersByAccount(final Account client)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement(
                              baseQuery + "WHERE email = ?");
      psSelect.setString(1, client.getEmail());
      rs = psSelect.executeQuery();
      while (rs.next()) {
        orders.add(populateOrderFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      psSelect.close();
      con.close();
    }
    return orders;
  }

  /**
   * RETRIEVE orders from the DB made between two dates.

   * @param start data inizio
   * @param end data fine
   * @return ciao
   */
  @Override
  public List<Order> getOrdersByDate(final LocalDate start, final LocalDate end)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement(
                              baseQuery + "WHERE emissiondate "
                            + "BETWEEN ? AND ?");
      psSelect.setDate(1, java.sql.Date.valueOf(start));
      psSelect.setDate(2, java.sql.Date.valueOf(end));
      rs = psSelect.executeQuery();
      while (rs.next()) {
        orders.add(populateOrderFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      psSelect.close();
      con.close();
    }
    return orders;
  }

  /**
   * RETIRIEVE orders from the DB made from a date untill now.

   * @param start data inizio
   * @return ciao
   */
  @Override
  public List<Order> getOrdersByDate(final LocalDate start)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement(
                              baseQuery + "WHERE emissiondate "
                            + ">= ?");
      psSelect.setDate(1, java.sql.Date.valueOf(start));
      rs = psSelect.executeQuery();
      while (rs.next()) {
        orders.add(populateOrderFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      psSelect.close();
      con.close();
    }
    return orders;
  }

  /**
   * RETRIEVE orders from the DB made by a client between two dates.

   * @param email email to search for
   * @param start start date
   * @param end end date
   * @return list of the orders matching the search criteria
   */
  @Override
  public List<Order> getOrdersByEmailAndDate(final String email,
                                              final LocalDate start,
                                              final LocalDate end)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    int nextField = 1;
    try {
      psSelect = con.prepareStatement(
                              baseQuery + "WHERE email = ? "
                            + "AND emissiondate BETWEEN ? AND ?");
      psSelect.setString(nextField++, email);
      psSelect.setDate(nextField++, java.sql.Date.valueOf(start));
      psSelect.setDate(nextField++, java.sql.Date.valueOf(end));
      rs = psSelect.executeQuery();
      while (rs.next()) {
        orders.add(populateOrderFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      psSelect.close();
      con.close();
    }
    return orders;
  }

  /**
   * RETRIEVE the number of orders for each day of the month.

   * @param month month to search for
   * @param year year to search for
   * @return list of the number of the orders for each day of the month
   */

  @Override
  public List<Integer> getOrdersPerDay(final int month, final int year)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Integer> orders = new ArrayList<Integer>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    int nextField = 1;
    try {
      psSelect = con.prepareStatement(
                              "SELECT COUNT(*) FROM \"Orders\" WHERE "
                            + "EXTRACT(MONTH FROM emissiondate) = ? AND "
                            + "EXTRACT(YEAR FROM emissiondate) = ? "
                            + "GROUP BY EXTRACT(DAY FROM emissiondate)");
      psSelect.setInt(nextField++, month);
      psSelect.setInt(nextField++, year);
      rs = psSelect.executeQuery();
      while (rs.next()) {
        orders.add(rs.getInt(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      psSelect.close();
      con.close();
    }
    return orders;
  }

  /**
   * UPDATE an order in the DB by his id.

   * @param order order to Update
   * @return number of rows affected by the update
   */
  @Override
  public int updateOrderById(final Order order)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    int rowAffected;
    try {
      int nextField = 1;
      PreparedStatement psUpdate = con.prepareStatement(
                              "UPDATE \"Orders\" SET "
                            + "emissiondate = ?, isexpress = ?, "
                            + "extrawarranty = ?, email = ?, quantity = ?, "
                            + "name = ?, supplier = ? WHERE orderid = ?"
                            );
      psUpdate.setDate(nextField++, java.sql.Date.valueOf(
          order.getEmissionDate()));
      psUpdate.setBoolean(nextField++, (order.getIsExpress() ? true : null));
      psUpdate.setInt(nextField++, order.getExtraWarranty());
      psUpdate.setString(nextField++, order.getAccountEmail());
      psUpdate.setInt(nextField++, order.getQuantity());
      psUpdate.setString(nextField++, order.getProductName());
      psUpdate.setString(nextField++, order.getProductSupplier());
      psUpdate.setInt(nextField++, order.getOrderId());
      rowAffected = psUpdate.executeUpdate();
      psUpdate.close();
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
      con.close();
      throw e;
    }
    return rowAffected;
  }

  /**
   * DELETE an order from the DB by his id.

   * @param id orderID to delete
   * @return number of rows affected by the delete
   */
  @Override
  public int deleteOrderById(final int id)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    int rowAffected;
    try {
      PreparedStatement psDelete = con.prepareStatement(
                              "DELETE FROM \"Orders\" WHERE id = ?");
      psDelete.setInt(1, id);
      rowAffected = psDelete.executeUpdate();
      psDelete.close();
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
      con.close();
      throw e;
    }
    return rowAffected;
  }

}