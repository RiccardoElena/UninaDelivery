package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.DashboardControl;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class DashboardPageController extends NonLoginPageController<DashboardControl> {

  /** Width and Height of the logged Operator Profile Pic. */
  private static final int PRO_PIC_SIZE = 100;

  /** Custom title bar. */
  @FXML private BorderPane borderPane;

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
  @FXML private MFXButton ordersHandlingButton;

  /** Profile Picture of the logged Operator. */
  @FXML private ImageView proPic;

  /**
   * Initialize the page.
   *
   * @param control of the page
   */
  @Override
  public final void initialize(final DashboardControl control) {
    displayLoggedOperatorData();
    setProfilePicture();
  }

  private void setProfilePicture() {
    proPic = new ImageView(new Image("/images/defaultUser.jpg"));
    proPic.setFitWidth(PRO_PIC_SIZE);
    proPic.setFitHeight(PRO_PIC_SIZE);
  }

  /**
   * Go to the Orders Handling page.
   *
   * @param event the event that triggered the action.
   */
  @FXML
  void ordersHandlingButtonAction(final ActionEvent event) {
    getControl().goToOrdersHandlingPage();
  }

  /**
   * Button to go to Monthly Reports page.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void monthlyReportsButtonOnAction(final ActionEvent event) {
    getControl().goToMonthlyReports();
  }

  /**
   * Button to edit the current account.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void editButtonAction(final ActionEvent event) {
    getControl().edit();
  }

  /**
   * Button to loggout from the current account.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void logoutButtonAction(final ActionEvent event) {
    getControl().logout();
  }

  // FIXME @zGenny label display is causing errors abouth string length. We should FIX
  private void displayLoggedOperatorData() {
    Operator loggedOperator = getControl().getLoggedOperator();
    if (loggedOperator != null) {
      nameLabel.setText(loggedOperator.getName());
      surnameLabel.setText(loggedOperator.getSurname());
      emailLabel.setText(loggedOperator.getBusinessMail());
    }
  }
}
