package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DAO.OrderDAO;
import com.unina.oobd2324gr22.entity.DAO.OrderDAOPostgre;
import com.unina.oobd2324gr22.utils.LoggedOperator;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class DashboardControl extends NonLoginControl {

  /** Order Data Access Object. */
  private OrderDAO ordersDAO = new OrderDAOPostgre();

  /** Singleton instance. */
  private static DashboardControl istance;

  private DashboardControl() {}

  /**
   * Get the singleton instance.
   *
   * @return the singleton instance
   */
  public static DashboardControl getInstance() {
    if (istance == null) {
      istance = new DashboardControl();
    }
    return istance;
  }

  /** Add page related scene settings. */
  @Override
  protected void addSceneSettings() {
    super.addSceneSettings();
    setFileName("Dashboard");
  }

  /** Go to the Orders page. */
  public void goToOrdersPage() {
    OrdersHandlingControl ordersControl = OrdersHandlingControl.getInstance();
    try {
      ordersControl.setScene();
    } catch (Exception e) {
      showInternalError(e);
    }
  }

  /** Go to the Monthly Reports page. */
  public void goToMonthlyReportsPage() {
    GraphControl graphControl = GraphControl.getInstance();
    try {
      graphControl.setScene();
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
  public void editOperatorProfile() {
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
            LoggedOperator.getInstance().getName() + " vuoi disconnetterti?",
            "Premendo OK verrai disconnesso e tornerai alla schermata di login. Continuare?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        LoginControl loginControl = LoginControl.getInstance();
        LoggedOperator.getInstance().logout();
        loginControl.setScene();
      } catch (Exception e) {
        showInternalError(e);
      }
    }
  }
}
