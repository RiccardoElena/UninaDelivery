package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.DashboardControl;
import com.unina.oobd2324gr22.control.OrdersControl;
import com.unina.oobd2324gr22.entity.DTO.Operator;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DashboardPageController {

  /** Dashboard selection functionality control class. */
  private DashboardControl dashboardControl = new DashboardControl();

  /** Orders selection functionality control class. */
  private OrdersControl ordersControl;

  /** Logged account. */
  private Operator operator = dashboardControl.getLoggedOperator();;

  /** Custom title bar. */
  @FXML private BorderPane borderPane;

  /** Button to exit the application. */
  @FXML private Button exitButton;

  /** Button to minimize the application. */
  @FXML private Button minimizeButton;

  /** Button to resize the application. */
  @FXML private Button resizeButton;

  /** Button to open the orders page. */
  @FXML private AnchorPane titleBar;

  /** Button to edit the logged account. */
  @FXML private MFXButton editButton;

  /** Label containing the name of the logged account. */
  @FXML private Label nameLabel;

  /** Label containing the surname of the logged account. */
  @FXML private Label surnameLabel;

  /** Label containing the email of the logged account. */
  @FXML private Label emailLabel;

  /** Button to logout the logged account. */
  @FXML private MFXButton logoutButton;

  /** Button to edit the logged account. */
  @FXML private MFXButton editAccountButton;

  /** Button to go to Orders Page. */
  @FXML private MFXButton gestisciOrdiniButton;

  /**
   * Initialize the page.
   *
   * @param control of the page
   */
  public final void init(final DashboardControl control) {
    this.dashboardControl = control;
    this.operator = dashboardControl.getLoggedOperator();
    this.displayLoggedOperatorData();
    dashboardControl.setDraggable(titleBar);
    Platform.runLater(
        () -> {
          Stage stage = (Stage) borderPane.getScene().getWindow();
          dashboardControl.setResizable(stage);
        });
  }

  /** Button to close the window.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void exitButtonAction(final ActionEvent event) {
    dashboardControl.exit();
  }

  /** Button to minimize the window.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void minimizeButtonAction(final ActionEvent event) {
    dashboardControl.minimize();
  }

  /** Button to go to Orders Page.
   *
   * @param event the event that triggered the action.
  */
  @FXML
  void gestisciOrdiniButtonAction(final ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    dashboardControl.goToGestioneOrdini(stage);
  }

  /** Button to edit the current account.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void editButtonAction(final ActionEvent event) {
    dashboardControl.goToEdit();
  }

  /** Button to loggout from the current account.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void logoutButtonAction(final ActionEvent event) {
    dashboardControl.logout();
  }

  /** Button to resize the window.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void resizeButtonAction(final ActionEvent event) {
    Stage stage = (Stage) resizeButton.getScene().getWindow();
    stage.setMaximized(!stage.isMaximized());
  }


  private void displayLoggedOperatorData() {
    if (operator != null) {
      nameLabel.setText(dashboardControl.getLoggedOperator().getName());
      surnameLabel.setText(dashboardControl.getLoggedOperator().getSurname());
      emailLabel.setText(dashboardControl.getLoggedOperator().getEmail());
    }
  }
}