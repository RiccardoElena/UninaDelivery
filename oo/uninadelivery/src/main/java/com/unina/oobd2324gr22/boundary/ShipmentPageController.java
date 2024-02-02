package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.ShipmentControl;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.entity.DTO.Order;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ShipmentPageController {

  /** Shipment selection functionality control class. */
  private ShipmentControl shipmentControl = new ShipmentControl();

  /** Logged account. */
  private Operator operator = shipmentControl.getLoggedOperator();

  /** Order selected. */
  private Order order;

  /** Back button. */
  @FXML private Button backButton;

  /** Border pane. */
  @FXML private BorderPane borderPane;

  /** Home button. */
  @FXML private Button homeButton;

  /** Button to exit the application. */
  @FXML private Button exitButton;

  /** Button to minimize the application. */
  @FXML private Button minimizeButton;

  /** Button to resize the application. */
  @FXML private Button resizeButton;

  /** Button to open the orders page. */
  @FXML private AnchorPane titleBar;

  /** Order label. */
  @FXML private Label orderLabel;

  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   * @param ord     the order to set the scene on
   */
  public final void init(final ShipmentControl control, final Order ord) {
    this.order = ord;
    this.shipmentControl = control;
    this.operator = control.getLoggedOperator();
    this.displayOrderData();
    shipmentControl.setDraggable(titleBar);
    Platform.runLater(
        () -> {
          Stage stage = (Stage) borderPane.getScene().getWindow();
          //shipmentControl.setResizable(stage); FIXME: NOT WORKIN!!
        });
  } // ! end initialize

  private void displayOrderData() {
    if (order != null) {
      orderLabel.setText("Ordine N."+order.getOrderId()
                        +" ordinato il "+ order.getEmissionDate()
                        +"  e da consegnare entro il "+ order.getExpectedDeliveryDate()
                        + "\nContenuto: " + order.getProduct().getName()
                        + " x" + order.getQuantity()
                        + " del venditore " + order.getProduct().getSupplier()
                        + "\n Consegnare a " + order.getAccount().getName()
                        + " " + order.getAccount().getSurname()
                        + " al seguente indirizzo: " + order.getAccount().getAddress()
                    );
    }
  }

  /** Button to close the window.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void exitButtonAction(final ActionEvent event) {
    shipmentControl.exit();
  }

  /** Button to minimize the window.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void minimizeButtonAction(final ActionEvent event) {
    shipmentControl.minimize();
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
}
