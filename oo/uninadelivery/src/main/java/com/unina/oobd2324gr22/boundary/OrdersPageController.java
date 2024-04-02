package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.OrdersHandlingControl;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.utils.LoadingScreen;
import io.github.palexdev.mfxcore.controls.Label;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class OrdersPageController extends NonLoginPageController<OrdersHandlingControl> {

  /** Table containing the orders. */
  @FXML private TableView<Order> ordersTable;

  /** Column containing the client of the order. */
  @FXML private TableColumn<Order, String> clientColumn;

  /** Column containing the emission date of the order. */
  @FXML private TableColumn<Order, LocalDate> emissionDateColumn;

  /** Column containing the extra warranty of the order. */
  @FXML private TableColumn<Order, Integer> extraWarrantyColumn;

  /** Column containing the express delivery of the order. */
  @FXML private TableColumn<Order, Boolean> isExpressColumn;

  /** Column containing the order number of the order. */
  @FXML private TableColumn<Order, Integer> orderNumberColumn;

  /** Column containing the product of the order. */
  @FXML private TableColumn<Order, String> productColumn;

  /** Column containing the quantity of the order. */
  @FXML private TableColumn<Order, Integer> quantityColumn;

  /** Column containing the supplier of the order. */
  @FXML private TableColumn<Order, String> supplierColumn;

  /** DatePicker to select the start date of the orders. */
  @FXML private DatePicker startDatePicker;

  /** DatePicker to select the end date of the orders. */
  @FXML private DatePicker endDatePicker;

  /** Button to filter the orders. */
  @FXML private Button filterButton;

  /** TextField to filter the orders by client. */
  @FXML private TextField clientTextField;

  /** Loading indicator. */
  @FXML private StackPane loadingPane;

  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   */
  @Override
  public final void initialize(final OrdersHandlingControl control) {
    setTableColumns();
    updateEndDatePickerAccordingToStart();
    Platform.runLater(() -> filterButton.getScene().setOnKeyPressed(this::handleKeyPressed));

    LoadingScreen.loading(
        loadingPane,
        () -> displayUnfilteredOrders(),
        orders ->
            Platform.runLater(
                () -> {
                  if (orders.isEmpty() || orders == null) {
                    ordersTable.setPlaceholder(new Label("Non ci sono ordini da spedire!"));
                    return;
                  }
                  ordersTable.setItems(orders);
                }));
  }

  private void handleKeyPressed(final KeyEvent event) {
    String keyCode = event.getCode().toString();
    if (keyCode.equals("ENTER")) {
      Platform.runLater(() -> filterButton.fire());
    }
  }

  private ObservableList<Order> displayUnfilteredOrders() {
    ordersTable.setPlaceholder(new Label("Caricamento..."));
    return getControl().getUnfinishedOrders();
  }

  /**
   * On filterButton click, it filters the orders.
   *
   * @param event the event that triggered the method
   */
  @FXML
  public final void filterButtonAction(final ActionEvent event) {
    LoadingScreen.loading(
        loadingPane,
        () -> {
          ordersTable.setPlaceholder(new Label("Caricamento..."));
          if (!clientTextField.getText().isEmpty()
              || startDatePicker.getValue() != null
              || endDatePicker.getValue() != null) {

            return getControl()
                .filterOrders(
                    clientTextField.getText(),
                    startDatePicker.getValue(),
                    endDatePicker.getValue());

          } else {
            return displayUnfilteredOrders();
          }
        },
        orders -> {
          if (orders.isEmpty() || orders == null) {
            ordersTable.getItems().clear();
            ordersTable.setPlaceholder(new Label("Nessun ordine compatibile coi filtri inseriti"));
            return;
          }
          Platform.runLater(() -> ordersTable.setItems(orders));
        });
  }

  private void updateEndDatePickerAccordingToStart() {
    startDatePicker
        .valueProperty()
        .addListener(
            (obs, oldDate, newDate) -> {
              endDatePicker.setDayCellFactory(
                  d ->
                      new DateCell() {
                        @Override
                        public void updateItem(final LocalDate item, final boolean empty) {
                          super.updateItem(item, empty);
                          if (newDate == null) {
                            setDisable(false);
                            setStyle(
                                "-fx-background-color: white; -fx-text-fill: -fx-color-primary;");
                          } else {
                            setDisable(item.isBefore(newDate));
                            if (!item.isBefore(newDate) && !item.isAfter(newDate)) {
                              setStyle(
                                  "-fx-background-color: -fx-primary-color; -fx-text-fill:"
                                      + " -fx-secondary-color;");
                            }
                          }
                        }
                      });
              if (newDate == null) {
                return;
              }
              if (endDatePicker.getValue() != null && newDate.isAfter(endDatePicker.getValue())) {
                endDatePicker.setValue(null);
              }
            });
  }

  private void setTableColumns() {
    orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
    supplierColumn.setCellValueFactory(new PropertyValueFactory<>("Product"));
    emissionDateColumn.setCellValueFactory(new PropertyValueFactory<>("EmissionDate"));
    isExpressColumn.setCellValueFactory(new PropertyValueFactory<>("IsExpress"));
    isExpressColumn.setCellFactory(column -> createExpressCell());
    extraWarrantyColumn.setCellValueFactory(new PropertyValueFactory<>("ExtraWarranty"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
    clientColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getAccount().getEmail()));
    productColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
    supplierColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getSupplier()));
    setTableFunctionality(ordersTable, "Azioni", "Spedisci", getControl()::goToShipmentPage);
  }

  private TableCell<Order, Boolean> createExpressCell() {
    return new TableCell<>() {
      @Override
      public void updateItem(final Boolean item, final boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          setText(item ? "⚡" : "🐌");
        }
      }
    };
  }
}
