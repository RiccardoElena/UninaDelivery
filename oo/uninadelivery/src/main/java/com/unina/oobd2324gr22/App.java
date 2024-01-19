package com.unina.oobd2324gr22;

import java.sql.SQLException;
import java.util.List;

/**
 * Hello world!
 *
 */
public final class App {

  // Constructors
  /**
   * Testing variable.
   */
  private static final int YEAR = 2024;
  /**
   * Default constructor.
   */
  private App() {
    throw new UnsupportedOperationException(
    "This is a utility class and cannot be instantiated");
  }

  // Methods
  /**
   * Main method.

   * @param args Command line arguments.
   */
  public static void main(final String[] args) {
    System.out.println("Hello World!");
    OrderDAO orderDAO = new OrderDAOPostgre();
    try {
    List<Integer> o = orderDAO.getOrdersPerDay(1, YEAR);
    System.out.println(o);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
