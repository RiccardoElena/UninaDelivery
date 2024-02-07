package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.LoginControl;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// FIXME: In refactoring a lot of methods have been moved to LoginControl.
// After testing that everything works, remove the commented code.

public class LoginPageController {

  /** Text field to input the email for the login. */
  @FXML private TextField emailTextField;

  /** Text field to input the password for the login. */
  @FXML private PasswordField passwordTextField;

  /** Button to login. */
  @FXML private MFXButton loginButton;

  /** Button to exit the application. */
  @FXML private MFXButton exitButton;

  /** Draggable bar of the application. */
  @FXML private Node topPane;

  /** Login functionality control class. */
  private LoginControl loginControl;

  /** Button for toggling password mask. */
  @FXML private Button togglePswVisibilityButton;

  /** Plain PasswordField to input the password for the login. */
  @FXML private TextField plainPasswordTextField;

  /**
   * Initialize the page.
   *
   * @param control
   */
  public final void init(final LoginControl control) {
    this.loginControl = control;
    loginControl.setDraggable(topPane);
    togglePswVisibilityButton.setGraphic(loginControl.getIcon("/images/loginPage/ClosedEye.png"));
    togglePswVisibilityButton.setOnAction(event -> togglePswVisibility());

    passwordTextField
        .getScene()
        .setOnKeyPressed(
            event -> {
              if (event.getCode().toString().equals("ENTER")) {
                loginButtonAction(new ActionEvent(passwordTextField, null));
              }
              if (event.getCode().toString().equals("T") && event.isControlDown()) {
                togglePswVisibility();
              }
            });
    togglePswVisibilityButton.setOnKeyPressed(
        event -> {
          if (event.getCode().toString().equals("ENTER")) {
            togglePswVisibilityButton.fireEvent(new ActionEvent(passwordTextField, null));
            event.consume();
          }
        });
  }

  // ? @zGenny: there's a reason for this method to be public and final?
  /**
   * On loginButton click, retrieves login data and checks if they are correct. In such case, it
   * loads the next page.
   *
   * @param event the event that triggered the method
   */
  @FXML
  final void loginButtonAction(final ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    loginControl.login(
        stage,
        emailTextField.getText(),
        passwordTextField.isVisible()
            ? passwordTextField.getText()
            : plainPasswordTextField.getText());
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
  @FXML
  final void exitButtonAction(final ActionEvent event) {
    loginControl.exit();
  }

  private void togglePswVisibility() {
    if (passwordTextField.isVisible()) {
      togglePswVisibilityButton.setGraphic(loginControl.getIcon("/images/loginPage/Eye.png"));
      plainPasswordTextField.setText(passwordTextField.getText());
    } else {
      togglePswVisibilityButton.setGraphic(loginControl.getIcon("/images/loginPage/ClosedEye.png"));
      passwordTextField.setText(plainPasswordTextField.getText());
    }

    passwordTextField.setVisible(!passwordTextField.isVisible());
    plainPasswordTextField.setVisible(!plainPasswordTextField.isVisible());
  }
}
