package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.boundary.LoginPageController;
import com.unina.oobd2324gr22.entity.DAO.AccountDAO;
import com.unina.oobd2324gr22.entity.DAO.AccountDAOPostgre;
import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.utils.SHA256;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    pageController.init(this);
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    scene
        .getStylesheets()
        .add(LoginControl.class.getResource("/style/LoginPage.css").toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Verify if login data are correct. If so, it loads the next page. Otherwise, it shows an error.
   *
   * @param stage the stage to set the scene on
   * @param email the email to check
   * @param password the password to check
   */
  public void login(final Stage stage, final String email, final String password) {
    String errorMessage = "";
    System.err.println(email + " " + password);
    // for email format validation
    // Pattern.matches("^[a-zA-Z0-9]+[a-zA-Z0-9.]*[a-zA-Z0-9]+@[a-zA-Z.]+\\.[a-zA-Z]{2,}$", email)
    if (!email.isBlank() && !password.isBlank()) {

      /* Account client = checkLogin( emailTextField.getText(),
      passwordTextField.getText()); */
      Operator client = null; // TODO!: test pourpose only. Line to be removed

      // TODO!: == is for test pourpose only. Change to != when done

      if (client == null) {
        /* TODO!: this implementation is for test pourpose only
        Remove this block when done and uncomment the previous line */
        try {

          LocalDate bdate =
              LocalDate.parse("1999-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          client =
              new Operator.OperatorBuilder(
                      "Gennaro",
                      "De Gregorio",
                      "gdg@gmail.com",
                      bdate,
                      password,
                      new Address("80100", "Napoli", "NA", "Italia", "EUW", "12A", "Via Roma"),
                      "G.DeGregorio@uninadelivery.operator.com")
                  .build();
          dashboardControl.setScene(stage, client);
          return;
        } catch (Exception e) {
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

  private Operator checkLogin(final String email, final String password) {
    try {
      return accountDAO.getOperatorByBmailAndPassword(email, new SHA256().toSHA256(password));
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
