package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.boundary.LoginPageController;
import com.unina.oobd2324gr22.entity.DAO.AccountDAO;
import com.unina.oobd2324gr22.entity.DAO.AccountDAOPostgre;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.utils.SHA256;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

// TODO @zGenny: remember to add all the static resources in the resources folder
// OrderPage.fxml and several images are missing
public class LoginControl extends BaseControl {

  /** Width of the window. */
  protected static final int WIDTH = 600;

  /** Height of the window. */
  protected static final int HEIGHT = 400;

  /** Account Data Access Object. */
  private AccountDAO accountDAO = new AccountDAOPostgre();

  /** Orders selection functionality control class. */
  private DashboardControl dashboardControl = new DashboardControl();

  /**
   * Set Login scene on Stage.
   *
   * @param primaryStage the stage to set the scene on
   * @throws Exception if the scene cannot be set
   */
  public void setScene(final Stage primaryStage) throws Exception {
    this.setStage(primaryStage);
    System.err.println("wow2 " + this.getStage());

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/login.fxml"));
    Parent root = loader.load();
    LoginPageController pageController = loader.getController();
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    scene
        .getStylesheets()
        .add(LoginControl.class.getResource("/style/LoginPage.css").toExternalForm());
    pageController.init(this);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void showErrorMessage(final String msg) {
    this.showAlert(Alert.AlertType.ERROR, "Errore", "Errore di login", msg);
  }

  private boolean isEmailValid(final String email) {
    return Pattern.matches(
        "^([a-zA-Z0-9]+\\.[a-zA-Z0-9]+|[a-zA-Z0-9]+)@[a-zA-Z.]+\\.[a-zA-Z]{2,}$", email);
  }

  private boolean isOperatorEmail(final String email) {
    return Pattern.matches(
        "^[A-Z]\\.([A-Za-z]+\\_[A-Za-z]+|[A-Za-z]+)[0-9]*@uninadelivery\\.operator\\.com$", email);
  }

  /**
   * Verify if login data are correct. If so, it loads the next page. Otherwise, it shows an error.
   *
   * @param email the email to check
   * @param password the password to check
   */
  public void login(final String email, final String password) {
    System.err.println(email + " " + password);

    if (email.isBlank() || password.isBlank()) {
      showErrorMessage("Inserire email e password");
      return;
    }

    if (!isEmailValid(email)) {
      showErrorMessage("Email non valida");
      return;
    }

    if (!isOperatorEmail(email)) {
      showErrorMessage("Accesso consentito solo ad account operatori");
      return;
    }

    Operator client = checkLogin(email, password);

    if (client != null) {
      try {
        dashboardControl.setScene(this.getStage(), client);
        return;
      } catch (Exception e) {
        e.printStackTrace();
        showErrorMessage(
            "Si è verificato un errore interno, si prega di riprovare più tardi o contattare"
                + " l'assistenza");
      }
    }

    showErrorMessage(
        "Email o password errati.\nUsare R.Elena@uninadelivery.operator.com e securepassword");
  }

  private Operator checkLogin(final String email, final String password) {
    try {
      return accountDAO.getOperatorByBmailAndPassword(email, SHA256.toSHA256(password));
    } catch (SQLException e) {
      this.showAlert(
          Alert.AlertType.ERROR,
          "Errore",
          "Errore Interno",
          "Si è verificato un errrore interno, si prega di riprovare più tardi");
      return null;
    }
  }
}
