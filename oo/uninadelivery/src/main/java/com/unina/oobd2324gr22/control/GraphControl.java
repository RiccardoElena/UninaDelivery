package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DAO.OrderDAO;
import com.unina.oobd2324gr22.entity.DAO.OrderDAOPostgre;
import com.unina.oobd2324gr22.entity.DTO.Order;
import java.sql.SQLException;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

public class GraphControl extends NonLoginControl {

  /** Order DAO. */
  private OrderDAO orderDAO = new OrderDAOPostgre();

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
   */
  public List<Integer> getGraphData(final Month month, final Year year) {
    try {
      return orderDAO.getOrdersPerDay(month.getValue(), year.getValue());
    } catch (SQLException e) {
      this.showAlert(
          Alert.AlertType.ERROR,
          "Errore",
          "Errore inaspettato.",
          "Si è verifacto un errore interno inatteso, si prega di riprovare o riavviare"
              + " l'applicazione.");
      e.printStackTrace();
      return new ArrayList<Integer>();
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
      this.showAlert(
          Alert.AlertType.ERROR,
          "Errore",
          "Errore inaspettato.",
          "Si è verifacto un errore interno inatteso, si prega di riprovare o riavviare"
              + " l'applicazione.");
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
      this.showAlert(
          Alert.AlertType.ERROR,
          "Errore",
          "Errore inaspettato.",
          "Si è verifacto un errore interno inatteso, si prega di riprovare o riavviare"
              + " l'applicazione.");
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
      this.showAlert(
          Alert.AlertType.ERROR,
          "Errore",
          "Errore inaspettato.",
          "Si è verifacto un errore interno inatteso, si prega di riprovare o riavviare"
              + " l'applicazione.");
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
      this.showAlert(
          Alert.AlertType.ERROR,
          "Errore",
          "Errore inaspettato.",
          "Si è verifacto un errore interno inatteso, si prega di riprovare o riavviare"
              + " l'applicazione.");
      e.printStackTrace();
      return null;
    }
  }
}
