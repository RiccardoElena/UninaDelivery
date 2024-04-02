package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.utils.IterableInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements the OrderDAO interface and provides the PostgreSQL implementation for the
 * shipment data access operations.
 */
public class OrderDAOPostgre implements OrderDAO {

  private Order populateOrderFromResultSet(final ResultSet rs) throws SQLException {
    Order order = null;

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

    return order;
  }

  /**
   * PostgreSQL implementation of the insert method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int insert(final Order order) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    int rowAffected;
    IterableInt fieldNumber = new IterableInt(1);
    PreparedStatement psInsert =
        con.prepareStatement(
            "INSERT INTO \"Order\" "
                + "(emissiondate, isexpress, extrawarranty, email, "
                + "quantity, name, supplier) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)");
    psInsert.setDate(fieldNumber.next(), java.sql.Date.valueOf(order.getEmissionDate()));
    psInsert.setBoolean(fieldNumber.next(), (order.getIsExpress() ? true : null));
    psInsert.setInt(fieldNumber.next(), order.getExtraWarranty());
    psInsert.setString(fieldNumber.next(), order.getAccount().getEmail());
    psInsert.setInt(fieldNumber.next(), order.getQuantity());
    psInsert.setString(fieldNumber.next(), order.getProduct().getName());
    psInsert.setString(fieldNumber.next(), order.getProduct().getSupplier());
    rowAffected = psInsert.executeUpdate();

    if (psInsert != null) {
      psInsert.close();
    }
    if (con != null) {
      con.close();
    }

    return rowAffected;
  }

  /**
   * PostgreSQL implementation of the getOrderById method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Order getOrderById(final int id) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    Order order = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;

    psSelect = con.prepareStatement("SELECT * FROM \"Order\" WHERE orderid = ?");
    psSelect.setInt(1, id);
    rs = psSelect.executeQuery();
    while (rs.next()) {
      order = populateOrderFromResultSet(rs);
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }
    if (con != null) {
      con.close();
    }

    return order;
  }

  /**
   * PostgreSQL implementation of the getUnfinishedOrders method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Order> getUnfinishedOrders() throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    Statement st = null;
    ResultSet rs = null;

    st = con.createStatement();
    rs =
        st.executeQuery(
            "SELECT * FROM \"Order\" WHERE isCompleted IS NULL ORDER BY emissiondate ASC");

    while (rs.next()) {
      orders.add(populateOrderFromResultSet(rs));
    }

    if (rs != null) {
      rs.close();
    }
    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }

    return orders;
  }

  /**
   * PostgreSQL implementation of the getAll method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Order> getAll() throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    Statement st = null;
    ResultSet rs = null;

    st = con.createStatement();
    rs = st.executeQuery("SELECT * FROM \"Order\"");

    while (rs.next()) {

      orders.add(populateOrderFromResultSet(rs));
    }

    if (rs != null) {
      rs.close();
    }
    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }

    return orders;
  }

  /**
   * PostgreSQL implementation of the getOrdersByMonth method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Order getOrderWithLargestQuantity(final Month month, final Year year) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    Order order = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    st =
        con.prepareStatement(
            "SELECT * FROM \"Order\" where EXTRACT(MONTH FROM emissiondate) = ? AND EXTRACT(YEAR"
                + " FROM emissiondate) = ? ORDER BY quantity DESC LIMIT 1");
    st.setInt(1, month.getValue());
    st.setInt(2, year.getValue());
    rs = st.executeQuery();

    while (rs.next()) {
      order = populateOrderFromResultSet(rs);
    }

    if (rs != null) {
      rs.close();
    }
    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }

    return order;
  }

  /**
   * PostgreSQL implementation of the getOrdersByMonth method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Order getOrderWithSmallestQuantity(final Month month, final Year year)
      throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    Order order = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    st =
        con.prepareStatement(
            "SELECT * FROM \"Order\" where EXTRACT(MONTH FROM emissiondate) = ? AND EXTRACT(YEAR"
                + " FROM emissiondate) = ? ORDER BY quantity ASC LIMIT 1");
    st.setInt(1, month.getValue());
    st.setInt(2, year.getValue());
    rs = st.executeQuery();

    while (rs.next()) {

      order = populateOrderFromResultSet(rs);
    }

    if (rs != null) {
      rs.close();
    }
    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }

    return order;
  }

  /**
   * PostgreSQL implementation of the getMostExpensiveOrder method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Order getMostExpensiveOrder(final Month month, final Year year) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new ArrayList<>();
    PreparedStatement st = null;
    ResultSet rs = null;

    st =
        con.prepareStatement(
            "SELECT * FROM \"Order\" where EXTRACT(MONTH FROM emissiondate) = ? AND EXTRACT(YEAR"
                + " FROM emissiondate) = ?");
    st.setInt(1, month.getValue());
    st.setInt(2, year.getValue());
    rs = st.executeQuery();
    while (rs.next()) {
      orders.add(populateOrderFromResultSet(rs));
    }

    Order orderWithMaxPrice = null;
    double maxPrice = 0;
    for (Order order : orders) {
      if (order.getPrice() > maxPrice) {
        maxPrice = order.getPrice();
        orderWithMaxPrice = order;
      }
    }

    if (rs != null) {
      rs.close();
    }
    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }

    return orderWithMaxPrice;
  }

  /**
   * PostgreSQL implementation of the getLessExpensiveOrder method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Order getLessExpensiveOrder(final Month month, final Year year) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new ArrayList<>();
    PreparedStatement st = null;
    ResultSet rs = null;

    st =
        con.prepareStatement(
            "SELECT * FROM \"Order\" where EXTRACT(MONTH FROM emissiondate) = ? AND EXTRACT(YEAR"
                + " FROM emissiondate) = ?");
    st.setInt(1, month.getValue());
    st.setInt(2, year.getValue());
    rs = st.executeQuery();
    while (rs.next()) {
      orders.add(populateOrderFromResultSet(rs));
    }

    Order orderWithMaxPrice = null;
    double minPrice = Double.MAX_VALUE;
    for (Order order : orders) {
      if (order.getPrice() < minPrice) {
        minPrice = order.getPrice();
        orderWithMaxPrice = order;
      }
    }

    if (rs != null) {
      rs.close();
    }
    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }
    return orderWithMaxPrice;
  }

  /**
   * PostgreSQL implementation of the getOrdersByFilters method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Order> getOrdersByFilters(final HashMap<String, Object> filters) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    IterableInt fieldNumber = new IterableInt(1);

    StringBuilder query = new StringBuilder("SELECT * FROM \"Order\" WHERE isCompleted IS NULL ");
    if (filters.size() > 0) {
      for (String key : filters.keySet()) {
        query.append("AND ");
        if (key.equals("email")) {
          query.append("email ILIKE ? ");
        } else if (key.equals("surname")) {
          query.append("email IN (SELECT email FROM Account WHERE surname ILIKE ? ) ");
        } else if (key.equals("startingDate")) {
          query.append("emissiondate >= ? ");
        } else if (key.equals("endingDate")) {
          query.append("emissiondate <= ? ");
        }
      }
    }
    psSelect = con.prepareStatement(query.toString());
    for (String key : filters.keySet()) {
      if (key.equals("email") || key.equals("surname")) {
        psSelect.setString(fieldNumber.next(), (String) filters.get(key) + "%");
      } else if (key.equals("startingDate") || key.equals("endingDate")) {
        psSelect.setDate(fieldNumber.next(), java.sql.Date.valueOf((LocalDate) filters.get(key)));
      }
    }
    rs = psSelect.executeQuery();

    while (rs.next()) {
      orders.add(populateOrderFromResultSet(rs));
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }
    if (con != null) {
      con.close();
    }

    return orders;
  }

  /**
   * PostgreSQL implementation of the getQuantityOfCategoriesByMonth method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public HashMap<String, Integer> getQuantityOfCategoriesByMonth(final Month month, final Year year)
      throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    HashMap<String, Integer> categories = new HashMap<String, Integer>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    IterableInt fieldNumber = new IterableInt(1);

    psSelect =
        con.prepareStatement(
            "SELECT category, sum(quantity) FROM \"Order\" NATURAL JOIN product WHERE EXTRACT(MONTH"
                + " FROM emissiondate) = ? AND EXTRACT(YEAR FROM emissiondate) = ? GROUP BY"
                + " category");
    psSelect.setInt(fieldNumber.next(), month.getValue());
    psSelect.setInt(fieldNumber.next(), year.getValue());
    rs = psSelect.executeQuery();
    while (rs.next()) {
      categories.put(rs.getString(1), rs.getInt(2));
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }
    if (con != null) {
      con.close();
    }

    return categories;
  }

  /**
   * PostgreSQL implementation of the getOrdersPerDay method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Integer> getOrdersPerDay(final Month month, final Year year) throws SQLException {

    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    int dayInMonth = LocalDate.of(year.getValue(), month.getValue(), 1).lengthOfMonth();

    List<Integer> orders = new ArrayList<Integer>(Collections.nCopies(dayInMonth, 0));

    PreparedStatement psSelect = null;
    ResultSet rs = null;
    IterableInt fieldNumber = new IterableInt(1);

    psSelect =
        con.prepareStatement(
            "SELECT EXTRACT(DAY FROM emissiondate), "
                + "COUNT(*) FROM \"Order\" WHERE "
                + "EXTRACT(MONTH FROM emissiondate) = ? AND "
                + "EXTRACT(YEAR FROM emissiondate) = ? "
                + "GROUP BY EXTRACT(DAY FROM emissiondate)");
    psSelect.setInt(fieldNumber.next(), month.getValue());
    psSelect.setInt(fieldNumber.next(), year.getValue());
    rs = psSelect.executeQuery();
    while (rs.next()) {
      orders.set(rs.getInt(1) - 1, rs.getInt(2));
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }
    if (con != null) {
      con.close();
    }

    return orders;
  }

  /**
   * PostgreSQL implementation of the getExpiringOrdersNumber method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int getExpiringOrdersNumber() throws SQLException {
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");

    psSelect =
        con.prepareStatement(
            "SELECT COUNT(*) FROM \"Order\" WHERE emissiondate + 6 <= CURRENT_DATE and"
                + " isCompleted IS NULL");
    rs = psSelect.executeQuery();
    if (rs.next()) {
      return rs.getInt(1);
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }
    if (con != null) {
      con.close();
    }

    return 0;
  }

  /**
   * PostgreSQL implementation of the getUnfinishedOrdersNumber method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int getUnfinishedOrdersNumber() throws SQLException {
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");

    psSelect = con.prepareStatement("SELECT COUNT(*) FROM \"Order\" WHERE isCompleted IS NULL");
    rs = psSelect.executeQuery();
    if (rs.next()) {
      return rs.getInt(1);
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }
    if (con != null) {
      con.close();
    }

    return 0;
  }

  /**
   * PostgreSQL implementation of the getOrdersByAccountAndMonth method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Order> getOrdersByAccountAndMonth(
      final Account client, final Year year, final Month month) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Order> orders = new LinkedList<Order>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    IterableInt fieldNumber = new IterableInt(1);

    psSelect =
        con.prepareStatement(
            "SELECT * FROM \"Order\" "
                + "WHERE email = ? AND EXTRACT(YEAR FROM emissiondate) = ? AND EXTRACT(MONTH FROM"
                + " emissiondate) = ?");
    psSelect.setString(fieldNumber.next(), client.getEmail());
    psSelect.setInt(fieldNumber.next(), year.getValue());
    psSelect.setInt(fieldNumber.next(), month.getValue());
    rs = psSelect.executeQuery();
    while (rs.next()) {
      orders.add(populateOrderFromResultSet(rs));
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }
    if (con != null) {
      con.close();
    }

    return orders;
  }

  /**
   * PostgreSQL implementation of the getOrdersByAccountAndYear method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Year getStartingYear() throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    PreparedStatement psSelect = null;
    ResultSet rs = null;

    psSelect = con.prepareStatement("SELECT EXTRACT(YEAR FROM MIN(emissiondate)) FROM \"Order\"");
    rs = psSelect.executeQuery();
    if (rs.next()) {
      int year = rs.getInt(1);
      return Year.of(year);
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }

    return null;
  }

  /**
   * PostgreSQL implementation of the update method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int update(final Order order) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    int rowAffected;

    IterableInt fieldNumber = new IterableInt(1);
    PreparedStatement psUpdate =
        con.prepareStatement(
            "UPDATE \"Order\" SET "
                + "emissiondate = ?, isexpress = ?, "
                + "extrawarranty = ?, email = ?, quantity = ?, "
                + "name = ?, supplier = ? WHERE orderid = ?");
    psUpdate.setDate(fieldNumber.next(), java.sql.Date.valueOf(order.getEmissionDate()));
    psUpdate.setBoolean(fieldNumber.next(), (order.getIsExpress() ? true : null));
    psUpdate.setInt(fieldNumber.next(), order.getExtraWarranty());
    psUpdate.setString(fieldNumber.next(), order.getAccount().getEmail());
    psUpdate.setInt(fieldNumber.next(), order.getQuantity());
    psUpdate.setString(fieldNumber.next(), order.getProduct().getName());
    psUpdate.setString(fieldNumber.next(), order.getProduct().getSupplier());
    psUpdate.setInt(fieldNumber.next(), order.getId());
    rowAffected = psUpdate.executeUpdate();
    psUpdate.close();
    con.close();

    return rowAffected;
  }

  /**
   * PostgreSQL implementation of the delete method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int delete(final Order order) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    int rowAffected;

    PreparedStatement psDelete = con.prepareStatement("DELETE FROM \"Order\" WHERE orderid = ?");
    psDelete.setInt(1, order.getId());
    rowAffected = psDelete.executeUpdate();

    if (psDelete != null) {
      psDelete.close();
    }
    if (con != null) {
      con.close();
    }

    return rowAffected;
  }

  /**
   * PostgreSQL implementation of the getMostSpendingAccount method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public HashMap<String, Integer> getQuantityOrdersByCategory(final Month month, final Year year)
      throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    HashMap<String, Integer> quantityOrdersByCategory = new HashMap<>();
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    int value;
    psSelect =
        con.prepareStatement(
            "SELECT category, COUNT(*) FROM \"Order\" NATURAL JOIN Product WHERE EXTRACT(MONTH"
                + " FROM emissiondate) = ? AND EXTRACT(YEAR FROM emissiondate) = ? GROUP BY"
                + " category");
    psSelect.setInt(1, month.getValue());
    psSelect.setInt(2, year.getValue());
    rs = psSelect.executeQuery();
    while (rs.next()) {
      value = rs.getInt(rs.getString(1)) + rs.getInt(2);
      quantityOrdersByCategory.put(rs.getString(1), value);
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }
    if (con != null) {
      con.close();
    }

    return quantityOrdersByCategory;
  }
}
