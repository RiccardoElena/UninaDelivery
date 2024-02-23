package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.OrdersHandlingControl;
import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Driver;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Product;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class ShipmentPageController extends NonLoginPageController<OrdersHandlingControl> {

  /** Border pane. */
  @FXML private BorderPane borderPane;

  /** Order Id label. */
  @FXML private Label orderIdLabel;

  /** Order Expected Delivery Date label. */
  @FXML private Label orderExpectedDeliveryDateLabel;

  /** Order Product label. */
  @FXML private Label orderProductLabel;

  /** Order Quantity label. */
  @FXML private Label orderQuantityLabel;

  /** Order Client label. */
  @FXML private Label orderClientLabel;

  /** Order Address label. */
  @FXML private Label orderAddressLabel;

  /** Shipment Date picker. */
  @FXML private DatePicker shipmentDatePicker;

  /** Table of shipments. */
  @FXML private TableView<Shipment> shipmentsTable;

  /** Column containing the shipment id. */
  @FXML private TableColumn<Shipment, Integer> shipmentIdTableColumn;

  /** Column containing the shipment date. */
  @FXML private TableColumn<Shipment, LocalDate> shipmentDateTableColumn;

  /** Column containing starting deposit. */
  @FXML private TableColumn<Shipment, String> startingDepositTableColumn;

  /** Column containing the transport. */
  @FXML private TableColumn<Shipment, String> transportTableColumn;

  /** Column containing the remaining space. */
  @FXML private TableColumn<Shipment, Float> remainingSpaceTableColumn;

  /** Depoists Combo Box. */
  @FXML private ComboBox<Deposit> depositComboBox;

  /** Transport Combo Box. */
  @FXML private ComboBox<Transport> transportComboBox;

  /** Driver Combo Box. */
  @FXML private ComboBox<Driver> driverComboBox;

  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   */
  @Override
  public final void initialize(final OrdersHandlingControl control) {

    this.displayOrderData();

    this.setTableColumns();

    // TODO @zGenny: implement loading pane functionality here too and change placeholder text.
    shipmentsTable.setItems(getControl().getShipments());

    this.setDatePickerLowerBound();

    this.populateComboBoxes();
  } // ! end initialize

  private void resetComboBoxes() {
    depositComboBox.setValue(null);
    depositComboBox.setDisable(true);
    transportComboBox.setValue(null);
    transportComboBox.setDisable(true);
    driverComboBox.setValue(null);
    driverComboBox.setDisable(true);
  }

  /**
   * Button to go back to the previous page.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void backButtonAction(final ActionEvent event) {
    getControl().goToOrdersPage();
  }

  private void displayOrderData() {
    Order order = getControl().getOrder();
    if (order == null) {
      return;
    }

    orderIdLabel.setText("Ordine N." + order.getOrderId());

    LocalDate expectedDeliveryDate = order.getExpectedDeliveryDate();
    String formattedDeliveryDate =
        expectedDeliveryDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    orderExpectedDeliveryDateLabel.setText("Da consegnare entro il: " + formattedDeliveryDate);

    Product product = order.getProduct();
    String productInfo =
        String.format(
            "Contenente: %d %s - %s",
            order.getQuantity(), product.getName(), product.getSupplier());
    orderProductLabel.setText(productInfo);

    Address address = order.getAccount().getAddress();
    orderAddressLabel.setText("Diretto a: " + address.toString());
  }

  private void populateComboBoxes() {
    shipmentDatePicker
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue != null) {
                depositComboBox.setDisable(false);
                depositComboBox.setItems(getControl().getDeposits(newValue));
              } else {
                resetComboBoxes();
              }
            });

    depositComboBox
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue != null) {
                transportComboBox.setDisable(false);
                transportComboBox.setItems(
                    getControl().getTransports(shipmentDatePicker.getValue(), newValue));

                driverComboBox.setDisable(false);
                driverComboBox.setItems(
                    getControl().getDrivers(shipmentDatePicker.getValue(), newValue));
              } else {
                resetComboBoxes();
                depositComboBox.setDisable(false);
              }
            });
  }

  private void setDatePickerLowerBound() {
    LocalDate defaultDate = LocalDate.now().plusDays(1);
    shipmentDatePicker.setValue(defaultDate);
    shipmentDatePicker.setDayCellFactory(d -> createDayCell(defaultDate));
  }

  private DateCell createDayCell(final LocalDate defaultDate) {
    return new DateCell() {
      @Override
      public void updateItem(final LocalDate item, final boolean empty) {
        super.updateItem(item, empty);
        setDisable(item.isBefore(defaultDate));
        if (!item.isBefore(defaultDate)) {
          setStyle(getDayCellStyle(item));
        }
      }
    };
  }

  private String getDayCellStyle(final LocalDate item) {
    if (item.isBefore(getControl().getOrder().getExpectedDeliveryDate().plusDays(1))) {
      return "-fx-background-color: -fx-secondary-color; -fx-text-fill: -fx-primary-color;";
    } else {
      return "-fx-background-color: red; -fx-text-fill: white;";
    }
  }

  private void setTableColumns() {
    shipmentIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
    shipmentDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("ShippingDate"));
    remainingSpaceTableColumn.setCellValueFactory(new PropertyValueFactory<>("RemainingSpace"));

    // TODO! @zGenny: we can consider to add the getAccountEmail method to the
    // Order class if this makes the code more readable
    startingDepositTableColumn.setCellValueFactory(
        cellData ->
            new SimpleStringProperty(
                String.valueOf(cellData.getValue().getStartingDeposit().getId())));
    // Same here!
    transportTableColumn.setCellValueFactory(
        cellData ->
            new SimpleStringProperty(String.valueOf(cellData.getValue().getTransport().getId())));
    setTableFunctionality(shipmentsTable);
  }

  /**
   * Button to close the window.
   *
   * @param event the event that triggered the action
   */
  @Override
  @FXML
  void exitButtonAction(final ActionEvent event) {
    getControl().exit();
  }

  /**
   * Button to minimize the window.
   *
   * @param event the event that triggered the action
   */
  @Override
  @FXML
  void minimizeButtonAction(final ActionEvent event) {
    getControl().minimize();
  }
}
