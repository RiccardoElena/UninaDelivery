package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.DashboardControl;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class DashboardPageController extends NonLoginPageController<DashboardControl> {

  /** Size of the notification badge if exeeding orders are less then 100. */
  private static final int NOTIFICATION_BADGE_SHRINKED_SIZES = 30;

  /** Maximum number of orders to notify. */
  private static final int MAX_NUM_ORDER_NOTIFIED = 99;

  /** Custom title bar. */
  @FXML private BorderPane borderPane;

  /** Label containing the name of the logged account. */
  @FXML private Label nameSurnameLabel;

  /** Label containing the email of the logged account. */
  @FXML private Label emailLabel;

  /** Label containing the ordersExpiring. */
  @FXML private Label ordersExpiringLabel;

  /** Button to logout the logged account. */
  @FXML private MFXButton logoutButton;

  /** Button to edit the logged account. */
  @FXML private MFXButton editAccountButton;

  /** Button to go to Orders Page. */
  @FXML private MFXButton ordersHandlingButton;

  /** Profile Picture of the logged Operator. */
  @FXML private ImageView proPic;

  /** Badge of expiring orders. */
  @FXML private Label badgeExpiringLabel;

  /** Warning image for expiring orders. */
  @FXML private ImageView warningImage;

  /**
   * Initialize the page.
   *
   * @param control of the page
   */
  @Override
  public final void initialize(final DashboardControl control) {
    displayLoggedOperatorData();
    setRoundImageViewImagesAndPosition(getControl().getLoggedOperator().getPropic(), proPic);
    displayExpiringOrders();
    displayBadgeOrders();
  }

  /**
   * Go to the Orders Handling page.
   *
   * @param event the event that triggered the action.
   */
  @FXML
  void ordersHandlingButtonAction(final ActionEvent event) {
    getControl().goToOrdersPage();
  }

  /**
   * Button to go to Monthly Reports page.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void monthlyReportsButtonOnAction(final ActionEvent event) {
    getControl().goToMonthlyReportsPage();
  }

  /**
   * Button to edit the current account.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void editButtonAction(final ActionEvent event) {
    getControl().editOperatorProfile();
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

  private void displayLoggedOperatorData() {
    if (getControl().getLoggedOperator() != null) {
      nameSurnameLabel.setText("Bentornato " + getControl().getLoggedOperator().getName());
    }
  }

  private void displayExpiringOrders() {
    int expiringOrders = getControl().getExpiringOrdersNumber();
    if (expiringOrders > 0) {
      ordersExpiringLabel.setText("Ci sono degli ordini\n" + "in scadenza oggi!");
    } else {
      ordersExpiringLabel.setVisible(false);
      warningImage.setVisible(false);
    }
  }

  private void displayBadgeOrders() {
    int orders = getControl().getUnfinishedOrdersNumber();
    if (orders > 0) {
      if (orders > MAX_NUM_ORDER_NOTIFIED) {
        badgeExpiringLabel.setText(MAX_NUM_ORDER_NOTIFIED + "+");
      } else {
        badgeExpiringLabel.setPrefWidth(NOTIFICATION_BADGE_SHRINKED_SIZES);
        badgeExpiringLabel.setPrefHeight(NOTIFICATION_BADGE_SHRINKED_SIZES);
        badgeExpiringLabel.setText(String.valueOf(orders));
      }
    } else {
      badgeExpiringLabel.setVisible(false);
    }
  }
}
