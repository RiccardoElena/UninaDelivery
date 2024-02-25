package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DAO.OrderDAO;
import com.unina.oobd2324gr22.entity.DAO.OrderDAOPostgre;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DashboardControl extends NonLoginControl {

  /** Order Data Access Object. */
  private OrderDAO ordersDAO = new OrderDAOPostgre();

  /** Order selection functionality control class. */
  private OrdersHandlingControl ordersControl = new OrdersHandlingControl();

  /** Graph selection functionality control class. */
  private GraphControl graphControl = new GraphControl();

  /** Add page related scene settings. */
  @Override
  protected void addSceneSettings() {
    super.addSceneSettings();
    setFileName("Dashboard");
  }

  /** Go to the Orders page. */
  public void goToOrdersHandlingPage() {
    try {
      ordersControl.setScene(getStage(), getLoggedOperator());
    } catch (Exception e) {
      showInternalError(e);
    }
  }

  /** Go to the Monthly Reports page. */
  public void goToMonthlyReports() {
    try {
      graphControl.setScene(getStage(), getLoggedOperator());
    } catch (Exception e) {
      showInternalError(e);
    }
  }

  /**
   * Get the number of today's expiring orders.
   *
   * @return the number of expiring orders
   */
  public int getExpiringOrdersNumber() {
    try {
      return ordersDAO.getExpiringOrdersNumber();
    } catch (SQLException e) {
      showInternalError(e);
    }
    return 0;
  }

  /** Go to the Edit page. */
  public void edit() {
    showAlert(
        Alert.AlertType.INFORMATION,
        "Attenzione!",
        "Funzione non ancora disponibile!",
        "Contattare l'assistenza per ulteriori informazioni!");
  }

  /** Logout the logged account. */
  public void logout() {
    Optional<ButtonType> result =
        showAlert(
            Alert.AlertType.CONFIRMATION,
            "Conferma",
            getLoggedOperator().getName() + " vuoi disconnetterti?",
            "Premendo OK verrai disconnesso e tornerai alla schermata di login. Continuare?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        LoginControl loginControl = new LoginControl();
        loginControl.setScene(getStage());
      } catch (Exception e) {
        showInternalError(e);
      }
    }
  }
}
