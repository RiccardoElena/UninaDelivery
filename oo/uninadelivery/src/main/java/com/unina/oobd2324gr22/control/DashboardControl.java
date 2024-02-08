package com.unina.oobd2324gr22.control;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DashboardControl extends NonLoginControl {

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
      ordersControl.setScene(this.getStage(), this.getLoggedOperator());
    } catch (Exception e) {
      e.printStackTrace();
      this.showAlert(
          Alert.AlertType.ERROR,
          "Errore",
          "Errore inaspettato.",
          "Si è verifacto un errore interno inatteso, si prega di riprovare o riavviare"
              + " l'applicazione.");
    } // TODO! @RiccardoElena we should change the message of the Alert ig, but in what?
  }

  /** Go to the Monthly Reports page. */
  public void goToMonthlyReports() {
    try {
      graphControl.setScene(this.getStage(), this.getLoggedOperator());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      this.showAlert(
          Alert.AlertType.ERROR,
          "Errore",
          "Errore inaspettato.",
          "Si è verifacto un errore interno inatteso, si prega di riprovare o riavviare"
              + " l'applicazione.");
    }
  }

  /** Go to the Edit page. */
  public void edit() {
    this.showAlert(
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
        loginControl.setScene(this.getStage());
      } catch (Exception e) {
        e.printStackTrace();
        this.showAlert(
            Alert.AlertType.ERROR,
            "Errore",
            "Errore inaspettato.",
            "Si è verifacto un errore interno inatteso, si prega di riprovare o riavviare"
                + " l'applicazione.");
      } // TODO! @RiccardoElena we should change the message of the Alert ig, but in what?
    }
  }
}
