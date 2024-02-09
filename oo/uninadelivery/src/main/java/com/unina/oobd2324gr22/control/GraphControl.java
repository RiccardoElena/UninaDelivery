package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DAO.OrderDAO;
import com.unina.oobd2324gr22.entity.DAO.OrderDAOPostgre;
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
   * TEST METHOD TO BE CHANGED WITH DAO ONE. Get the graph data.
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
}
