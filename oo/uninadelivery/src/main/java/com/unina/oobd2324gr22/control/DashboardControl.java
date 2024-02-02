package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.boundary.DashboardPageController;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class DashboardControl extends NonLoginControl {

  /** Order selection functionality control class. */
  private OrdersHandlingControl ordersControl = new OrdersHandlingControl();

  /**
   * Set Orders scene on Stage.
   *
   * @param stage the stage to set the scene on
   * @param op the logged in operator.
   * @throws Exception if the scene cannot be set
   */
  public void setScene(final Stage stage, final Operator op) throws Exception {
    this.setLoggedOperator(op);
    this.setStage(stage);
    System.out.println("Logged2operator: " + this.getLoggedOperator());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/dashboard.fxml"));
    Parent root = loader.load();
    DashboardPageController pageController = loader.getController();
    pageController.init(this);
    System.err.println(
        Math.max(stage.getWidth(), WIDTH) + " " + Math.max(stage.getHeight(), HEIGHT));
    Scene scene =
        new Scene(root, Math.max(stage.getWidth(), WIDTH), Math.max(stage.getHeight(), HEIGHT));
    scene
        .getStylesheets()
        .add(LoginControl.class.getResource("/style/Dashboard.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Go to the Orders page.
   *
   * @param stage the stage to set the scene on
   */
  public void goToGestioneOrdini(final Stage stage) {
    try {
      ordersControl.setOrdersScene(stage, this.getLoggedOperator());
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
      this.setLoggedOperator(null);
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
