package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.OrdersControl;
import com.unina.oobd2324gr22.entity.DTO.Order;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OrdersPageController {

  /** Orders selection functionality control class. */
  private OrdersControl ordersControl;

  /** Custom title bar. */
  @FXML private AnchorPane titleBar;

  // All this parameter are a mess, isn't a better way to encapsulate them
  // in a custom table class?

  /** Label containing the name of the logged account. */
  @FXML private Label nameLabel;

  /** Table containing the orders. */
  @FXML private TableView<Order> ordersTable;

  /** Column containing the client of the order. */
  @FXML private TableColumn<Order, String> clientColumn;

  /** Column containing the emission date of the order. */
  @FXML private TableColumn<Order, LocalDate> emissionDateColumn;

  /** Column containing the extra warranty of the order. */
  @FXML private TableColumn<Order, Integer> extraWarrantyColumn;

  /** Column containing the express delivery of the order. */
  @FXML private TableColumn<Order, String> isExpressColumn;

  /** Column containing the order number of the order. */
  @FXML private TableColumn<Order, Integer> orderNumberColumn;

  /** Column containing the product of the order. */
  @FXML private TableColumn<Order, String> productColumn;

  /** Column containing the quantity of the order. */
  @FXML private TableColumn<Order, Integer> quantityColumn;

  /** Column containing the supplier of the order. */
  @FXML private TableColumn<Order, String> supplierColumn;

  /** Button to exit the application. */
  @FXML private Button exitButton;

  /** Button to resize the application. */
  @FXML private Button resizeButton;

  /** Button to minimize the application. */
  @FXML private Button minimizeButton;

  /** DatePicker to select the start date of the orders. */
  @FXML private DatePicker startDatePicker;

  /** DatePicker to select the end date of the orders. */
  @FXML private DatePicker endDatePicker;

  /** BorderPane. */
  @FXML private BorderPane borderPane;

  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   */
  public final void init(final OrdersControl control) {

    this.setOrdersControl(control);
    // FIXME: this is a test pourpose only, remove this when the DB connection
    this.displayLoggedAccountName();

    ordersControl.setDraggable(titleBar);

    Platform.runLater(
        () -> {
          Stage stage = (Stage) borderPane.getScene().getWindow();
          ordersControl.setResizable(stage);
        });

    this.setTableColumns();

    // FIXME: this line is using a test function with dummy data
    // when DB connection is implemented, this line should be changed
    ordersTable.setItems(ordersControl.getTestOrders());

    setDatePickerLowerBound();
  } // ! end initialize

  /**
   * Set Orders selection functionality control class.
   *
   * @param control the Orders selection functionality control class
   */
  public void setOrdersControl(final OrdersControl control) {
    this.ordersControl = control;
  }

  /**
   * On exitButton click, it shows a confirmation alert. If the user confirms, it closes the
   * application.
   *
   * @param event
   */
  public final void exitButtonAction(final ActionEvent event) {
    ordersControl.exit();
  }

  /**
   * On minimizeButton click, it minimizes the application.
   *
   * @param event the event that triggered the method
   */
  public final void minimizeButtonAction(final ActionEvent event) {
    ordersControl.minimize();
  }

  /**
   * On resizeButton click, it resizes the application.
   *
   * @param event
   */
  public final void resizeButtonAction(final ActionEvent event) {
    Stage stage = (Stage) resizeButton.getScene().getWindow();
    stage.setMaximized(!stage.isMaximized());
  }

  private void displayLoggedAccountName() {
    // TODO! : Test pourpose only, remove this when the DB connection is implemented

    nameLabel.setText(ordersControl.getLoggedOperator().getName());
  }

  private void setDatePickerLowerBound() {
    startDatePicker
        .valueProperty()
        .addListener(
            (obs, oldDate, newDate) -> {
              if (newDate == null) {
                return;
              }

              if (endDatePicker.getValue() != null && newDate.isAfter(endDatePicker.getValue())) {
                endDatePicker.setValue(null);
              } else {
                // Imposta la data minima del secondo DatePicker a newDate
                endDatePicker.setDayCellFactory(
                    d ->
                        new DateCell() {
                          @Override
                          public void updateItem(final LocalDate item, final boolean empty) {
                            super.updateItem(item, empty);
                            setDisable(item.isBefore(newDate));
                            if (!item.isBefore(newDate) && !item.isAfter(newDate)) {
                              setStyle(
                                  "-fx-background-color: -fx-primary-color; -fx-text-fill:"
                                      + " -fx-secondary-color;");
                            }
                          }
                        });
              }
            });
  }

  private void setTableColumns() {
    orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
    supplierColumn.setCellValueFactory(new PropertyValueFactory<>("Product"));
    emissionDateColumn.setCellValueFactory(new PropertyValueFactory<>("EmissionDate"));
    isExpressColumn.setCellValueFactory(new PropertyValueFactory<>("IsExpress"));
    extraWarrantyColumn.setCellValueFactory(new PropertyValueFactory<>("ExtraWarranty"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

    // TODO! @zGenny: we can consider to add the getAccountEmail method to the
    // Order class if this makes the code more readable
    clientColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getAccount().getEmail()));
    // Same here!
    productColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
    supplierColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getSupplier()));

    this.addButtonToTable();

    this.setColumnSize();
  }

  private void addButtonToTable() {
    TableColumn<Order, Void> actionColumn = new TableColumn<>("");

    Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory =
        new Callback<>() {
          @Override
          public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
            final TableCell<Order, Void> cell =
                new TableCell<>() {

                  private final Button btn = new Button("Crea");

                  {
                    btn.setOnAction(
                        event -> {
                          Order order = getTableView().getItems().get(getIndex());
                          ordersControl.execAction(order);
                        });
                  }

                  @Override
                  public void updateItem(final Void item, final boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setGraphic(null);
                    } else {
                      setGraphic(btn);
                    }
                  }
                };
            return cell;
          }
        };

    actionColumn.setCellFactory(cellFactory);

    ordersTable.getColumns().add(actionColumn);
  }

  private void setColumnSize() {
    for (TableColumn<Order, ?> column : ordersTable.getColumns()) {
      column
          .prefWidthProperty()
          .bind(ordersTable.widthProperty().divide(ordersTable.getColumns().size()));
    }
  }
}
