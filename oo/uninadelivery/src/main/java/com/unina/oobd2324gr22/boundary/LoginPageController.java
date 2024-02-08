package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.LoginControl;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class LoginPageController extends BasePageController<LoginControl> {

  /** Text field to input the email for the login. */
  @FXML private TextField emailTextField;

  /** Text field to input the password for the login. */
  @FXML private PasswordField passwordTextField;

  /** Button to login. */
  @FXML private MFXButton loginButton;

  /** Button for toggling password mask. */
  @FXML private Button togglePswVisibilityButton;

  /** Plain PasswordField to input the password for the login. */
  @FXML private TextField plainPasswordTextField;

  /**
   * Initialize the page.
   *
   * @param control
   */
  @Override
  protected void initialize(final LoginControl control) {
    togglePswVisibilityButton.setGraphic(getIcon("/images/loginPage/ClosedEye.png"));
    togglePswVisibilityButton.setOnAction(event -> togglePswVisibility());

    passwordTextField.getScene().setOnKeyPressed(this::handleKeyPressed);
    togglePswVisibilityButton.setOnKeyPressed(this::handleToggleKeyPressed);
  }

  private void handleKeyPressed(final KeyEvent event) {
    String keyCode = event.getCode().toString();
    if (keyCode.equals("ENTER")) {
      loginButtonAction(new ActionEvent(passwordTextField, null));
    }
    if (keyCode.equals("T") && event.isControlDown()) {
      togglePswVisibility();
    }
  }

  private void handleToggleKeyPressed(final KeyEvent event) {
    String keyCode = event.getCode().toString();
    if (keyCode.equals("ENTER")) {
      togglePswVisibilityButton.fireEvent(new ActionEvent(passwordTextField, null));
      event.consume();
    }
  }

  /**
   * On loginButton click, retrieves login data and checks if they are correct. In such case, it
   * loads the next page.
   *
   * @param event the event that triggered the method
   */
  @FXML
  final void loginButtonAction(final ActionEvent event) {
    String password =
        passwordTextField.isVisible()
            ? passwordTextField.getText()
            : plainPasswordTextField.getText();
    getControl().login(emailTextField.getText(), password);
  }

  private void togglePswVisibility() {
    boolean passwordVisible = passwordTextField.isVisible();
    togglePswVisibilityButton.setGraphic(
        getIcon(passwordVisible ? "/images/loginPage/Eye.png" : "/images/loginPage/ClosedEye.png"));

    if (passwordVisible) {
      plainPasswordTextField.setText(passwordTextField.getText());
    } else {
      passwordTextField.setText(plainPasswordTextField.getText());
    }

    passwordTextField.setVisible(!passwordVisible);
    plainPasswordTextField.setVisible(passwordVisible);
  }
}
