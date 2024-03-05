package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DAO.AccountDAO;
import com.unina.oobd2324gr22.entity.DAO.AccountDAOPostgre;
import com.unina.oobd2324gr22.entity.DAO.OrderDAO;
import com.unina.oobd2324gr22.entity.DAO.OrderDAOPostgre;
import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Order;
import java.sql.SQLException;
import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import javafx.scene.control.Alert;

public final class GraphControl extends NonLoginControl {

  /** Order DAO. */
  private OrderDAO orderDAO = new OrderDAOPostgre();

  /** Account DAO. */
  private AccountDAO accountDAO = new AccountDAOPostgre();

  /** Singleton instance. */
  private static GraphControl istance;

  private GraphControl() {}

  /**
   * Get the singleton instance.
   *
   * @return the singleton instance
   */
  public static GraphControl getInstance() {
    if (istance == null) {
      istance = new GraphControl();
    }
    return istance;
  }

  /** Add page related scene settings. */
  @Override
  protected void addSceneSettings() {
    super.addSceneSettings();
    setFileName("Graph");
  }

  /**
   * Get the graph data for the given month and year.
   *
   * @param month the month to get the data for
   * @param year the year to get the data for
   * @return the graph data
   * @throws NoSuchElementException if no data is available
   */
  public List<Integer> getLineChartData(final Month month, final Year year)
      throws NoSuchElementException {
    try {
      List<Integer> o = orderDAO.getOrdersPerDay(month, year);
      if (o.stream().allMatch(n -> n == 0)) {
        showAlert(
            Alert.AlertType.WARNING,
            "Nessun dato disponibile",
            "Non ci sono dati disponibili per il mese selezionato",
            "Selezionare un altro mese o un altro anno.");
        throw new NoSuchElementException();
      }
      return o;
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }

  /**
   * Get the pie chart data for the given month and year.
   *
   * @param month the month to get the data for
   * @param year the year to get the data for
   * @return the pie chart data
   */
  public HashMap<String, Integer> getPieChartData(final Month month, final Year year) {
    try {
      return orderDAO.getQuantityOfCategoriesByMonth(month, year);
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }

  /**
   * Get the data for the order containing the highest number of products for the given month and
   * year.
   *
   * @param month the month to get the data for
   * @param year the year to get the data for
   * @return the order data
   */
  public Order getHighestProductOrderData(final Month month, final Year year) {
    try {
      return orderDAO.getOrderWithLargestQuantity(month, year);
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }

  /**
   * Get the data for the order containing the lowest number of products for the given month and
   * year.
   *
   * @param month the month to get the data for
   * @param year the year to get the data for
   * @return the order data
   */
  public Order getLowestProductOrderData(final Month month, final Year year) {
    try {
      return orderDAO.getOrderWithSmallestQuantity(month, year);
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }

  /**
   * Get the data for the most expensive order for the given month and year.
   *
   * @param month the month to get the data for
   * @param year the year to get the data for
   * @return the order data
   */
  public Order getMostExpensiveOrderData(final Month month, final Year year) {
    try {
      return orderDAO.getMostExpensiveOrder(month, year);
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }

  /**
   * Get the data for the least expensive order for the given month and year.
   *
   * @param month the month to get the data for
   * @param year the year to get the data for
   * @return the order data
   */
  public Order getLeastExpensiveOrderData(final Month month, final Year year) {
    try {
      return orderDAO.getLessExpensiveOrder(month, year);
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }

  /**
   * Get the data for the account with the most orders for the given month and year.
   *
   * @param month the month to get the data for
   * @param year the year to get the data for
   * @return the account data
   */
  public Account getMostOrderingAccountData(final Month month, final Year year) {
    try {
      return accountDAO.getMostOrderingAccount(year, month);
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }

  /**
   * Get the data for the account with the most spending for the given month and year.
   *
   * @param month the month to get the data for
   * @param year the year to get the data for
   * @return the account data
   */
  public Account getMostSpendingAccountData(final Month month, final Year year) {
    try {
      return accountDAO.getMostSpendingAccount(year, month);
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }

  /** The maximum percentage. */
  private static final int MAX_PERCENTAGE = 100;

  /**
   * Get the quantity orders by category sent by month and year.
   *
   * @param month the month to get the data for
   * @param year the year to get the data for
   * @return the quantity orders by category
   */
  public HashMap<String, Integer> getQuantityOrdersByCategoryData(
      final Month month, final Year year) {
    HashMap<String, Integer> rawData;
    int allTheProduct;
    try {
      rawData = orderDAO.getQuantityOrdersByCategory(month, year);
      allTheProduct = getAllTheProducts(rawData);
      for (String category : rawData.keySet()) {
        double percentage = (rawData.get(category) / (double) allTheProduct) * MAX_PERCENTAGE;
        rawData.put(category, (int) Math.round(percentage));
      }
      return rawData;
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }

  private int getAllTheProducts(final HashMap<String, Integer> rawData) {
    int total = 0;
    for (String category : rawData.keySet()) {
      total += rawData.get(category);
    }
    return total;
  }

  /**
   * Get the oldest year ther's an order for.
   *
   * @return the oldest year
   */
  public Year getStartingYear() {
    try {
      return orderDAO.getStartingYear();
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }
}
