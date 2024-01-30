package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.LoginControl;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

// FIXME: In refactoring a lot of methods have been moved to LoginControl.
// After testing that everything works, remove the commented code.

public class LoginPageController implements Initializable {

  /** Text field to input the email for the login. */
  @FXML private MFXTextField emailTextField;

  /** Text field to input the password for the login. */
  @FXML private MFXPasswordField passwordTextField;

  /** Button to login. */
  @FXML private MFXButton loginButton;

  /** Button to exit the application. */
  @FXML private MFXButton exitButton;

  /** Draggable bar of the application. */
  @FXML private Node topPane;

  /** Login functionality control class. */
  private LoginControl loginControl = new LoginControl();

  @Override
  @FXML
  public final void initialize(final URL location, final ResourceBundle resources) {
    loginControl.setDraggable(topPane);
  }

  // ? @zGenny: there's a reason for this method to be public and final?
  /**
   * On loginButton click, retrieves login data and checks if they are correct. In such case, it
   * loads the next page.
   *
   * @param event the event that triggered the method
   */
  public final void loginButtonAction(final ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    loginControl.login(stage, emailTextField.getText(), passwordTextField.getText());
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
    loginControl.exit(stage);
  }
}
