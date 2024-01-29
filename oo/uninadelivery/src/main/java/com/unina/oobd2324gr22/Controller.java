package com.unina.oobd2324gr22;

import com.unina.oobd2324gr22.entity.DAO.AccountDAO;
import com.unina.oobd2324gr22.entity.DAO.AccountDAOPostgre;
import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.utils.SHA256;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// FIXME: Rename the class and file to LoginController.

public class Controller {

  /** AccountDAO instance to retrieve account data from DB. */
  private AccountDAO accountDAO = new AccountDAOPostgre();

  /** Text field to input the email for the login. */
  @FXML private TextField emailTextField;

  /** Text field to input the password for the login. */
  @FXML private PasswordField passwordTextField;

  /** Button to login. */
  @FXML private Button loginButton;

  // FIXME: Renamed the button to match naming convention.
  // If given name was important for JFX implementation, fix it there.
  /** Button to exit the application. */
  @FXML private Button exitButton;

  // ? @zGenny: there's a reason for this method to be public and final?
  /**
   * On loginButton click, retrieves login data and checks if they are correct. In such case, it
   * loads the next page.
   *
   * @param event the event that triggered the method
   */
  public final void loginButtonAction(final ActionEvent event) {

    String errorMessage = "";
    if (!emailTextField.getText().isBlank() && !passwordTextField.getText().isBlank()) {

      /* Account client = checkLogin( emailTextField.getText(),
      passwordTextField.getText()); */
      Account client = null; // TODO!: test pourpose only. Line to be removed

      // TODO!: == is for test pourpose only. Change to != when done

      if (client == null) {
        // this.changePage(client, event);
        /* TODO!: this implementation is for test pourpose only
        Remove this block when done and uncomment the previous line */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Ordini.fxml"));
        Parent root;
        try {
          root = loader.load();
          OrdiniController controller = loader.getController();
          LocalDate bdate =
              LocalDate.parse("1999-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          client =
              new Account(
                  "Gennaro", "De Gregorio", "gdg@gmail.com", bdate, "gdg.jpg", "password", null);
          controller.setAccount(client);
          Scene scene = new Scene(root);
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          stage.setScene(scene);
          stage.show();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        errorMessage = "Email o password errati";
      }
    } else {
      errorMessage = "Inserire email e password";
    }
    this.showAlert(Alert.AlertType.ERROR, "Errore", "Errore di login", errorMessage);
  }

  /* TODO!: we should consider making a custom alert class
  and moving this method there */
  private Optional<ButtonType> showAlert(
      final Alert.AlertType alertType,
      final String title,
      final String header,
      final String content) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert.showAndWait();
  }

  private void changePage(final Account account, final ActionEvent event) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Ordini.fxml"));
    try {
      Parent root = loader.load();
      OrdiniController controller = loader.getController();
      controller.setAccount(account);
      Scene scene = new Scene(root);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // FIXME: Renamed the button to match naming convention.
  // If given name was important for JFX implementation, fix it there.
  // ? @zGenny: there's a reason for this method to be public and final?
  /**
   * On exitButton click, it shows a confirmation alert. If the user confirms, it closes the
   * application.
   *
   * @param event the event that triggered the method
   */
  public final void exitButtonAction(final ActionEvent event) {
    Stage stage = (Stage) exitButton.getScene().getWindow();
    // TODO!: if we make a custom alert class, we should use it here
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Esci");
    alert.setHeaderText("Stai per uscire!");
    alert.setContentText("Confermi?");
    Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
    if (result.get() == javafx.scene.control.ButtonType.OK) {
      stage.close();
    } else {
      alert.close();
    }
  }

  private Account checkLogin(final String email, final String password) {
    try {
      return accountDAO.getAccountByEmailAndPassword(email, new SHA256().toSHA256(password));
    } catch (Exception e) {
      this.showAlert(
          Alert.AlertType.ERROR,
          "Errore",
          "Errore Interno",
          "Si è verificato un errrore interno, si prega di riprovare più tardi");
      return null;
    }
  }
}
