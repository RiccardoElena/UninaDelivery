package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** Implementation of the OrderDAO interface for PostgreSQL DataBase. */
public class OrderDAOPostgre implements OrderDAO {

  /** Connection istance. */
  private Connection con;

  /** Base query for retrieving orders. */
  private String baseQuery = "SELECT * FROM \"Order\" ";

  /**
   * Internal method to populate an order from a ResultSet.
   *
   * @param rs ResultSet to populate the order from
   * @return order populated from the ResultSet
   */
  private Order populateOrderFromResultSet(final ResultSet rs) throws SQLException {
    Order order = null;
    try {
      order =
          new Order(
              rs.getInt("orderid"),
              rs.getDate("emissiondate").toLocalDate(),
              rs.getBoolean("isexpress"),
              rs.getInt("extrawarranty"),
              new AccountDAOPostgre().getAccountByEmail(rs.getString("email")),
              rs.getInt("quantity"),
              new ProductDAOPostgre()
                  .getProductByNameAndSupplier(rs.getString("name"), rs.getString("supplier")));
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return order;
  }

  /**
   * CREATE a new Order into the DB.
   *
   * @param order Order to insert into the DB
   * @return the number of rows affected by the insert
   */
  @Override
  public int insertOrder(final Order order) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    int rowAffected;
    try {
      int nextField = 1;
      PreparedStatement psInsert =
          con.prepareStatement(
              "INSERT INTO \"Order\" "
                  + "(emissiondate, isexpress, extrawarranty, email, "
                  + "quantity, name, supplier) VALUES "
                  + "(?, ?, ?, ?, ?, ?, ?)");
      psInsert.setDate(nextField++, java.sql.Date.valueOf(order.getEmissionDate()));
      psInsert.setBoolean(nextField++, (order.getIsExpress() ? true : null));
      psInsert.setInt(nextField++, order.getExtraWarranty());
      psInsert.setString(nextField++, order.getAccount().getEmail());
      psInsert.setInt(nextField++, order.getQuantity());
      psInsert.setString(nextField++, order.getProduct().getName());
      psInsert.setString(nextField++, order.getProduct().getSupplier());
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
   *
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
      psSelect = con.prepareStatement(baseQuery + "WHERE orderid = ?");
      psSelect.setInt(1, id);
      rs = psSelect.executeQuery();
      while (rs.next()) {
        order = populateOrderFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (psSelect != null) {
        psSelect.close();
      }
      if (con != null) {
        con.close();
      }
    }
    return order;
  }

  /**
   * RETRIEVE all the unfinished Orders from the DB.
   *
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
      rs = st.executeQuery(baseQuery + " WHERE isCompleted = false OR" + " isCompleted IS NULL");

      while (rs.next()) {
        orders.add(populateOrderFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }

    return orders;
  }

  /**
   * RETRIEVE all the Orders from the DB.
   *
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
      if (rs != null) {
        rs.close();
      }
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }

    return orders;
  }

  /**
   * RETRIVE the order with the largest quantity of products.
   *
   * @param month month to search for
   * @param year year to search for
   * @return order with biggest quantity of products
   */
  @Override
  public Order getOrderWithLargestQuantity(final int month, final int year) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Order order = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      st =
          con.prepareStatement(
              "SELECT MAX(quantity) "
                  + "FROM \"Order\" WHERE "
                  + "EXTRACT(MONTH FROM emissiondate) = ? AND "
                  + "EXTRACT(YEAR FROM emissiondate) = ? "
                  + "LIMIT 1");
      st.setInt(1, month);
      st.setInt(2, year);
      rs = st.executeQuery();

      while (rs.next()) {

        order = populateOrderFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }

    return order;
  }

  /**
   * RETRIVE the order with the smallest quantity of products.
   *
   * @return order with smallest quantity of products
   */
  @Override
  public Order getOrderWithSmallestQuantity(final int month, final int year) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Order order = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {

      st =
          con.prepareStatement(
              "SELECT MIN(quantity) "
                  + "FROM \"Order\" WHERE "
                  + "EXTRACT(MONTH FROM emissiondate) = ? AND "
                  + "EXTRACT(YEAR FROM emissiondate) = ? "
                  + "LIMIT 1");
      st.setInt(1, month);
      st.setInt(2, year);
      rs = st.executeQuery();

      while (rs.next()) {

        order = populateOrderFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }

    return order;
  }

  /**
   * RETRIEVE all orders from the DB made by the same account.
   *
   * @param client accout to search for
   * @return list of orders matching the search criteria
   */
  @Override
  public List<Order> getOrdersByAccount(final Account client) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement(baseQuery + "WHERE email = ?");
      psSelect.setString(1, client.getEmail());
      rs = psSelect.executeQuery();
      while (rs.next()) {
        orders.add(populateOrderFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (psSelect != null) {
        psSelect.close();
      }
      if (con != null) {
        con.close();
      }
    }
    return orders;
  }

  /**
   * RETRIEVE orders from the DB made between two dates.
   *
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
      psSelect = con.prepareStatement(baseQuery + "WHERE emissiondate " + "BETWEEN ? AND ?");
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
      if (rs != null) {
        rs.close();
      }
      if (psSelect != null) {
        psSelect.close();
      }
      if (con != null) {
        con.close();
      }
    }
    return orders;
  }

  /**
   * RETIRIEVE orders from the DB made from a date untill now.
   *
   * @param start data inizio
   * @return ciao
   */
  @Override
  public List<Order> getOrdersByDate(final LocalDate start) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement(baseQuery + "WHERE emissiondate " + ">= ?");
      psSelect.setDate(1, java.sql.Date.valueOf(start));
      rs = psSelect.executeQuery();
      while (rs.next()) {
        orders.add(populateOrderFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (psSelect != null) {
        psSelect.close();
      }
      if (con != null) {
        con.close();
      }
    }
    return orders;
  }

  /**
   * RETRIEVE orders from the DB made by a client between two dates.
   *
   * @param client user to search for
   * @param start start date
   * @param end end date
   * @return list of the orders matching the search criteria
   */
  @Override
  public List<Order> getOrdersByAccountAndDate(
      final Account client, final LocalDate start, final LocalDate end) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    int nextField = 1;
    try {
      psSelect =
          con.prepareStatement(baseQuery + "WHERE email = ? " + "AND emissiondate BETWEEN ? AND ?");
      psSelect.setString(nextField++, client.getEmail());
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
      if (rs != null) {
        rs.close();
      }
      if (psSelect != null) {
        psSelect.close();
      }
      if (con != null) {
        con.close();
      }
    }
    return orders;
  }

  /**
   * RETRIEVE the number of orders for each day of the month.
   *
   * <p>The function initialize an ArrayList with the number of days of the month selected as 0.
   * Then for every days that has orders it updates the value of the ArrayList at the index of the
   * day with the number
   *
   * @param month month to search for
   * @param year year to search for
   * @return list of the number of the orders for each day of the month
   */
  @Override
  public List<Integer> getOrdersPerDay(final int month, final int year) throws SQLException {

    con = DBConnection.getConnectionBySchema("uninadelivery");
    int dayInMonth = LocalDate.of(year, month, 1).lengthOfMonth();

    List<Integer> orders = new ArrayList<Integer>(Collections.nCopies(dayInMonth, 0));

    PreparedStatement psSelect = null;
    ResultSet rs = null;
    int nextField = 1;
    try {
      psSelect =
          con.prepareStatement(
              "SELECT EXTRACT(DAY FROM emissiondate), "
                  + "COUNT(*) FROM \"Order\" WHERE "
                  + "EXTRACT(MONTH FROM emissiondate) = ? AND "
                  + "EXTRACT(YEAR FROM emissiondate) = ? "
                  + "GROUP BY EXTRACT(DAY FROM emissiondate)");
      psSelect.setInt(nextField++, month);
      psSelect.setInt(nextField++, year);
      rs = psSelect.executeQuery();
      while (rs.next()) {
        orders.set(rs.getInt(1) - 1, rs.getInt(2));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (psSelect != null) {
        psSelect.close();
      }
      if (con != null) {
        con.close();
      }
    }
    return orders;
  }

  /**
   * UPDATE an order in the DB by his id.
   *
   * @param order order to Update
   * @return number of rows affected by the update
   */
  @Override
  public int updateOrderById(final Order order) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    int rowAffected;
    try {
      int nextField = 1;
      PreparedStatement psUpdate =
          con.prepareStatement(
              "UPDATE \"Order\" SET "
                  + "emissiondate = ?, isexpress = ?, "
                  + "extrawarranty = ?, email = ?, quantity = ?, "
                  + "name = ?, supplier = ? WHERE orderid = ?");
      psUpdate.setDate(nextField++, java.sql.Date.valueOf(order.getEmissionDate()));
      psUpdate.setBoolean(nextField++, (order.getIsExpress() ? true : null));
      psUpdate.setInt(nextField++, order.getExtraWarranty());
      psUpdate.setString(nextField++, order.getAccount().getEmail());
      psUpdate.setInt(nextField++, order.getQuantity());
      psUpdate.setString(nextField++, order.getProduct().getName());
      psUpdate.setString(nextField++, order.getProduct().getSupplier());
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
   *
   * @param id orderID to delete
   * @return number of rows affected by the delete
   */
  @Override
  public int deleteOrderById(final int id) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    int rowAffected;
    try {
      PreparedStatement psDelete = con.prepareStatement("DELETE FROM \"Order\" WHERE orderid = ?");
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
