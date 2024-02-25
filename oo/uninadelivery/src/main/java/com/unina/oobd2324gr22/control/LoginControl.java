package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DAO.AccountDAO;
import com.unina.oobd2324gr22.entity.DAO.AccountDAOPostgre;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.utils.SHA256;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;

public class LoginControl extends BaseControl {

  /** Width of the window. */
  protected static final double WIDTH = 600;

  /** Height of the window. */
  protected static final double HEIGHT = 400;

  /** Account Data Access Object. */
  private AccountDAO accountDAO = new AccountDAOPostgre();

  /** Orders selection functionality control class. */
  private DashboardControl dashboardControl = new DashboardControl();

  /** Add page related scene settings. */
  @Override
  protected void addSceneSettings() {
    setSizes(WIDTH, HEIGHT);
    setFileName("Login");
  }

  private void showLoginErrorMessage(final String msg) {
    showAlert(Alert.AlertType.ERROR, "Errore", "Errore di login", msg);
  }

  private boolean isEmailValid(final String email) {
    return Pattern.matches(
        "^([a-zA-Z0-9]+\\.[a-zA-Z0-9]+|[a-zA-Z0-9]+)@[a-zA-Z.]+\\.[a-zA-Z]{2,}$", email);
  }

  private boolean isOperatorEmail(final String email) {
    return Pattern.matches(
        "^[a-zA-Z]\\.([a-zA-Z]+\\_[A-Za-z]+|[A-Za-z]+)[0-9]*@uninadelivery\\.operator\\.com$",
        email);
  }

  /**
   * Verify if login data are correct. If so, it loads the next page. Otherwise, it shows an error.
   *
   * @param email the email to check
   * @param password the password to check
   */
  public void login(final String email, final String password) {

    if (email.isBlank() || password.isBlank()) {
      showLoginErrorMessage("Inserire email e password");
      return;
    }

    if (!isEmailValid(email)) {
      showLoginErrorMessage("Email non valida");
      return;
    }

    if (!isOperatorEmail(email)) {
      showLoginErrorMessage("Accesso consentito solo ad account operatori");
      return;
    }

    Operator client = checkLogin(email, password);

    if (client != null) {
      try {
        dashboardControl.setScene(getStage(), client);
        return;
      } catch (Exception e) {
        e.printStackTrace();
        showAlert(
            Alert.AlertType.ERROR,
            "Errore",
            "Errore inaspettato.",
            "Si è verifacto un errore interno inatteso, si prega di riprovare o riavviare"
                + " l'applicazione.");
      }
    }

    showLoginErrorMessage(
        "Email o password errati.\nUsare R.Elena@uninadelivery.operator.com e securepassword");
  }

  private Operator checkLogin(final String email, final String password) {
    try {
      return accountDAO.getOperatorByBmailAndPassword(email, SHA256.toSHA256(password));
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
  }
}
