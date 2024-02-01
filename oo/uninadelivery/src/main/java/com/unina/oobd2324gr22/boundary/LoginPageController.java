package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.LoginControl;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

// FIXME: In refactoring a lot of methods have been moved to LoginControl.
// After testing that everything works, remove the commented code.

public class LoginPageController implements Initializable {

  /** Width of the eye icons. */
  static final int ICON_WIDTH = 30;

  /** Height of the eye icons. */
  static final int ICON_HEIGHT = 30;

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
  private LoginControl loginControl = new LoginControl();

  /** Button for toggling password mask. */
  @FXML private Button togglePswVisibilityButton;

  /** Plain PasswordField to input the password for the login. */
  @FXML private TextField plainPasswordTextField;

  @Override
  @FXML
  public final void initialize(final URL location, final ResourceBundle resources) {
    loginControl.setDraggable(topPane);
    togglePswVisibilityButton.setGraphic(getIcon("/images/loginPage/ClosedEye.png"));
    togglePswVisibilityButton.setOnAction(event -> togglePswVisibility());
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
  @FXML
  final void exitButtonAction(final ActionEvent event) {
    Stage stage = (Stage) exitButton.getScene().getWindow();
    loginControl.exit();
  }

  private void togglePswVisibility() {
    if (passwordTextField.isVisible()) {
      togglePswVisibilityButton.setGraphic(getIcon("/images/loginPage/Eye.png"));
      plainPasswordTextField.setText(passwordTextField.getText());
      passwordTextField.setVisible(false);
      plainPasswordTextField.setVisible(true);
    } else {
      togglePswVisibilityButton.setGraphic(getIcon("/images/loginPage/ClosedEye.png"));
      passwordTextField.setText(plainPasswordTextField.getText());
      plainPasswordTextField.setVisible(false);
      passwordTextField.setVisible(true);
    }
  }

  private ImageView getIcon(final String path) {
    ImageView eyeView = new ImageView(new Image(path));
    eyeView.setFitWidth(ICON_WIDTH); // Set the width
    eyeView.setFitHeight(ICON_HEIGHT);
    return eyeView;
  }
}
