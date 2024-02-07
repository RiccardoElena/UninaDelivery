package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.OrdersHandlingControl;
import com.unina.oobd2324gr22.entity.DTO.Order;
import java.time.LocalDate;
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

public class OrdersPageController extends NonLoginPageController<OrdersHandlingControl> {

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

  /** TextField to filter the orders by client. */
  @FXML private TextField clientTextField;

  /** BorderPane. */
  @FXML private BorderPane borderPane;

  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   */
  @Override
  public final void initialize(final OrdersHandlingControl control) {
    setDraggableNode(titleBar);

    setTableColumns();
    ordersTable.setItems(getControl().getTestOrders());
    updateEndDatePickerAccordingToStart();
  }

  /**
   * On exitButton click, it shows a confirmation alert. If the user confirms, it closes the
   * application.
   *
   * @param event
   */
  @Override
  @FXML
  public final void exitButtonAction(final ActionEvent event) {
    getControl().exit();
  }

  /**
   * On minimizeButton click, it minimizes the application.
   *
   * @param event the event that triggered the method
   */
  @Override
  @FXML
  public final void minimizeButtonAction(final ActionEvent event) {
    getControl().minimize();
  }

  /**
   * On resizeButton click, it resizes the application.
   *
   * @param event
   */
  @Override
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
    getControl()
        .filterOrders(
            clientTextField.getText(), startDatePicker.getValue(), endDatePicker.getValue());
  }

  private void updateEndDatePickerAccordingToStart() {
    startDatePicker
        .valueProperty()
        .addListener(
            (obs, oldDate, newDate) -> {
              if (newDate == null) {
                return;
              }

              if (endDatePicker.getValue() != null && newDate.isAfter(endDatePicker.getValue())) {
                endDatePicker.setValue(null);
              }

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
            });
  }

  private void setTableColumns() {
    orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
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
    setTableFunctionality(ordersTable, "Azioni", "Crea", getControl()::execAction);
  }

  private TableCell<Order, Boolean> createExpressCell() {
    return new TableCell<>() {
      @Override
      public void updateItem(final Boolean item, final boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          setText(item ? "⚡" : "🐌"); // Emoji for true and false
        }
      }
    };
  }

  /**
   * Button to go back to the previous page.
   *
   * @param event the event that triggered the action
   */
  @Override
  @FXML
  void backButtonAction(final ActionEvent event) {
    getControl().returnToOrdersPage();
  }

  /**
   * Button to go back to the home page.
   *
   * @param event the event that triggered the action
   */
  @Override
  @FXML
  void homeButtonAction(final ActionEvent event) {
    try {
      getControl().returnToHomePage();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
