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
import java.util.List;
import java.util.NoSuchElementException;
import javafx.scene.control.Alert;

public class GraphControl extends NonLoginControl {

  /** Order DAO. */
  private OrderDAO orderDAO = new OrderDAOPostgre();

  /** Account DAO. */
  private AccountDAO accountDAO = new AccountDAOPostgre();

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
  public List<Integer> getGraphData(final Month month, final Year year) {
    try {
      List<Integer> o = orderDAO.getOrdersPerDay(month.getValue(), year.getValue());
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
      showInternalError();
      e.printStackTrace();
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
      return orderDAO.getOrderWithLargestQuantity(month.getValue(), year.getValue());
    } catch (SQLException e) {
      showInternalError();
      e.printStackTrace();
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
      return orderDAO.getOrderWithSmallestQuantity(month.getValue(), year.getValue());
    } catch (SQLException e) {
      showInternalError();
      e.printStackTrace();
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
      return orderDAO.getMostExpensiveOrder(month.getValue(), year.getValue());
    } catch (SQLException e) {
      showInternalError();
      e.printStackTrace();
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
      return orderDAO.getLessExpensiveOrder(month.getValue(), year.getValue());
    } catch (SQLException e) {
      showInternalError();
      e.printStackTrace();
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
      return accountDAO.getMostOrderingAccount(month.getValue(), year.getValue());
    } catch (SQLException e) {
      showInternalError();
      e.printStackTrace();
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
      return accountDAO.getMostSpendingAccount(month.getValue(), year.getValue());
    } catch (SQLException e) {
      showInternalError();
      e.printStackTrace();
      return null;
    }
  }
}
