package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.OrdersHandlingControl;
import com.unina.oobd2324gr22.entity.DTO.Order;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OrdersPageController {

  /** Orders selection functionality control class. */
  private OrdersHandlingControl ordersHandlingControl;

  /** Custom title bar. */
  @FXML private AnchorPane titleBar;

  // All this parameter are a mess, isn't a better way to encapsulate them
  // in a custom table class?

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

  /** Button to filter the orders. */
  @FXML private Button filterButton;

  /** Button to go back to the home page. */
  @FXML private Button homeButton;

  /** Button to go back to the previous page. */
  @FXML private Button backButton;

  /** TextField to filter the orders by client. */
  @FXML private TextField clientTextField;

  /** BorderPane. */
  @FXML private BorderPane borderPane;

  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   */
  public final void init(final OrdersHandlingControl control) {

    this.setOrdersHandlingControl(control);

    ordersHandlingControl.setDraggable(titleBar);

    Platform.runLater(
        () -> {
          Stage stage = (Stage) borderPane.getScene().getWindow();
          ordersHandlingControl.setResizable(stage);
        });

    this.setTableColumns();

    ordersHandlingControl.setNavigationButtons(homeButton, backButton);

    // FIXME: this line is using a test function with dummy data
    // when DB connection is implemented, this line should be changed
    ordersTable.setItems(ordersHandlingControl.getTestOrders());

    setDatePickerLowerBound();
  } // ! end initialize

  /**
   * Set Orders selection functionality control class.
   *
   * @param control the Orders selection functionality control class
   */
  @FXML
  public void setOrdersHandlingControl(final OrdersHandlingControl control) {
    this.ordersHandlingControl = control;
  }

  /**
   * On exitButton click, it shows a confirmation alert. If the user confirms, it closes the
   * application.
   *
   * @param event
   */
  @FXML
  public final void exitButtonAction(final ActionEvent event) {
    ordersHandlingControl.exit();
  }

  /**
   * On minimizeButton click, it minimizes the application.
   *
   * @param event the event that triggered the method
   */
  @FXML
  public final void minimizeButtonAction(final ActionEvent event) {
    ordersHandlingControl.minimize();
  }

  /**
   * On resizeButton click, it resizes the application.
   *
   * @param event
   */
  @FXML
  public final void resizeButtonAction(final ActionEvent event) {
    Stage stage = (Stage) resizeButton.getScene().getWindow();
    stage.setMaximized(!stage.isMaximized());
  }

  /**
   * On filterButton click, it filters the orders.
   *
   * @param event the event that triggered the method
   */
  @FXML
  public final void filterButtonAction(final ActionEvent event) {
    ordersHandlingControl.filterOrders(
        clientTextField.getText(), startDatePicker.getValue(), endDatePicker.getValue());
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
    isExpressColumn.setCellFactory(column -> new TableCell<Order, Boolean>() {
      @Override
      public void updateItem(final Boolean item, final boolean empty) {
        super.updateItem(item, empty);
          super.updateItem(item, empty);
          if (item == null || empty) {
              setText(null);
          } else {
              setText(item ? "🚚" : ""); // Emoji per true e false
          }
      }
  });
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
                          ordersHandlingControl.execAction(order);
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

  /**
   * Button to go back to the previous page.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void backButtonAction(final ActionEvent event) {
    ordersHandlingControl.returnToOrdersPage();
  }

  /**
   * Button to go back to the home page.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void homeButtonAction(final ActionEvent event) {
    Stage stage = (Stage) homeButton.getScene().getWindow();
    try {
      ordersHandlingControl.returnToHomePage(stage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
