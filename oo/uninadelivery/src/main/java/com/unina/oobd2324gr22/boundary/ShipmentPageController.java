package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.OrdersHandlingControl;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Driver;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import com.unina.oobd2324gr22.utils.LoadingScreen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class ShipmentPageController extends NonLoginPageController<OrdersHandlingControl> {

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

  /** Loading Pane. */
  @FXML private StackPane loadingPane;

  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   */
  @Override
  public final void initialize(final OrdersHandlingControl control) {

    displayOrderData();

    setTableColumns();

    LoadingScreen.loading(
        loadingPane,
        () -> displayCompatibleShipments(),
        shipments ->
            Platform.runLater(
                () -> {
                  if (shipments.isEmpty() || shipments == null) {
                    shipmentsTable.setPlaceholder(
                        new Label("Non ci sono spedizioni compatibili con questo ordine!"));
                    return;
                  }
                  shipmentsTable.setItems(shipments);
                }));

    populateComboBoxes();

    handleComboBoxNullValue(depositComboBox);
    handleComboBoxNullValue(transportComboBox);
    handleComboBoxNullValue(driverComboBox);

    setDatePickerLowerBound();

    setButtonEnablerListener(
        shipmentsTable, shipmentDatePicker, depositComboBox, transportComboBox, driverComboBox);

    setSubmitButtonAction();
  }

  private ObservableList<Shipment> displayCompatibleShipments() {
    shipmentsTable.setPlaceholder(new Label("Caricamento..."));
    return getControl().getShipments();
  }

  private void setButtonEnablerListener(final Node... nodes) {
    for (Node node : nodes) {
      if (node instanceof TableView) {
        ((TableView<?>) node)
            .getSelectionModel()
            .selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> enableSubmitButton());
      } else {
        ((ComboBoxBase<?>) node)
            .valueProperty()
            .addListener((observable, oldValue, newValue) -> enableSubmitButton());
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

    orderIdLabel.setText("Codice ordine:" + order.getId());

    LocalDate expectedDeliveryDate = order.getExpectedDeliveryDate();
    String formattedDeliveryDate =
        expectedDeliveryDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    orderExpectedDeliveryDateLabel.setText("Da consegnare entro il: " + formattedDeliveryDate);

    String productInfo =
        "Contenente: "
            + order.getQuantity()
            + " "
            + order.getProduct().getName()
            + " - "
            + order.getProduct().getSupplier();
    orderProductLabel.setText(productInfo);

    orderAddressLabel.setText("Diretto a: " + order.getAccount().getAddress().toString());
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

    startingDepositTableColumn.setCellValueFactory(
        cellData ->
            new SimpleStringProperty(
                String.valueOf(cellData.getValue().getStartingDeposit().getId())));

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
