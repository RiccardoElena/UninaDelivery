package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.OrdersHandlingControl;
import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Driver;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Product;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

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

  /** Submit Button. */
  @FXML private MFXButton submitButton;

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

    this.populateComboBoxes();

    this.handleComboBoxNullValue(depositComboBox);
    this.handleComboBoxNullValue(transportComboBox);
    this.handleComboBoxNullValue(driverComboBox);

    this.setDatePickerLowerBound();

    this.setButtonEnablerListener(
        shipmentsTable, shipmentDatePicker, depositComboBox, transportComboBox, driverComboBox);

    this.setSubmitButtonAction();
  } // ! end initialize

  private void setButtonEnablerListener(final Node... nodes) {
    for (Node node : nodes) {
      if (node instanceof TableView) {
        ((TableView<?>) node)
            .getSelectionModel()
            .selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> this.enableSubmitButton());
      } else {
        ((ComboBoxBase<?>) node)
            .valueProperty()
            .addListener((observable, oldValue, newValue) -> this.enableSubmitButton());
      }
    }
  }

  private void resetComboBoxes() {
    ComboBox<?>[] comboBoxes = {depositComboBox, transportComboBox, driverComboBox};

    for (ComboBox<?> comboBox : comboBoxes) {
      comboBox.getSelectionModel().clearSelection();
      comboBox.setDisable(true);
    }
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

    orderIdLabel.setText("Ordine N." + order.getId());

    LocalDate expectedDeliveryDate = order.getExpectedDeliveryDate();
    String formattedDeliveryDate =
        expectedDeliveryDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    orderExpectedDeliveryDateLabel.setText("Da consegnare entro il: " + formattedDeliveryDate);

    Product product = order.getProduct();
    String productInfo =
        "Contenente: "
            + order.getQuantity()
            + " "
            + product.getName()
            + " - "
            + product.getSupplier();
    orderProductLabel.setText(productInfo);

    Address address = order.getAccount().getAddress();
    orderAddressLabel.setText("Diretto a: " + address.toString());
  }

  private void populateComboBoxes() {
    resetComboBoxes();

    shipmentDatePicker
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue != null) {
                depositComboBox.setDisable(false);
                depositComboBox.setItems(getControl().getDeposits(newValue));
                depositComboBox.getItems().add(0, null);
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
                transportComboBox.getItems().add(0, null);

                driverComboBox.setDisable(false);
                driverComboBox.setItems(
                    getControl().getDrivers(shipmentDatePicker.getValue(), newValue));
                driverComboBox.getItems().add(0, null);
              } else {
                resetComboBoxes();
                depositComboBox.setDisable(false);
              }
            });
  }

  private <T> void handleComboBoxNullValue(final ComboBox<T> comboBox) {
    comboBox.setCellFactory(
        new Callback<ListView<T>, ListCell<T>>() {
          @Override
          public ListCell<T> call(final ListView<T> param) {
            return new ListCell<T>() {
              @Override
              protected void updateItem(final T item, final boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                  setGraphic(null);
                } else {
                  setText(item.toString());
                }
              }
            };
          }
        });

    comboBox.setButtonCell(
        new ListCell<T>() {
          @Override
          protected void updateItem(final T item, final boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
              setText(comboBox.getPromptText());
            } else {
              setText(item.toString());
            }
          }
        });
  }

  private void setDatePickerLowerBound() {
    shipmentDatePicker.setDayCellFactory(d -> createDayCell(LocalDate.now().plusDays(1)));
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

  private void enableSubmitButton() {
    if (shipmentDatePicker.getValue() != null ^ isShipmentSelected()) {
      if (isFormFilled()) {
        submitButton.setDisable(false);
      }
      if (isShipmentSelected()) {
        submitButton.setDisable(false);
      }
    } else {
      submitButton.setDisable(true);
    }
  }

  private boolean isFormFilled() {
    return shipmentDatePicker.getValue() != null
        && depositComboBox.getValue() != null
        && transportComboBox.getValue() != null
        && driverComboBox.getValue() != null;
  }

  private boolean isShipmentSelected() {
    return shipmentsTable.getSelectionModel().getSelectedItem() != null;
  }

  private void setSubmitButtonAction() {
    submitButton.setDisable(true);
    submitButton.setOnAction(
        e -> {
          if (isShipmentSelected()) {
            getControl().shipsSelectedOrder(shipmentsTable.getSelectionModel().getSelectedItem());
          } else {
            getControl()
                .shipsSelectedOrder(
                    shipmentDatePicker.getValue(),
                    depositComboBox.getValue(),
                    transportComboBox.getValue(),
                    driverComboBox.getValue());
          }
        });
  }
}
