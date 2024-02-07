package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.boundary.OrdersPageController;
import com.unina.oobd2324gr22.boundary.ShipmentPageController;
import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Product;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OrdersHandlingControl extends NonLoginControl {

  /** Order selected. */
  private Order selectedOrder;

  /**
   * Set Orders scene on Stage.
   *
   * @param currStage the stage to set the scene on
   * @param op the operator logged in
   * @throws Exception if the scene cannot be set
   */
  public void setOrdersScene(final Stage currStage, final Operator op) throws Exception {
    this.setLoggedOperator(op);
    this.setStage(currStage);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Orders.fxml"));
    Parent root = loader.load();
    OrdersPageController pageController = loader.getController();
    pageController.init(this);
    Scene ordersScene =
        new Scene(
            root, Math.max(currStage.getWidth(), WIDTH), Math.max(currStage.getHeight(), HEIGHT));
    ordersScene
        .getStylesheets()
        .add(LoginControl.class.getResource("/style/OrdersPage.css").toExternalForm());
    currStage.setScene(ordersScene);
    currStage.show();
    return;
  }

  // FIXME: to be changed to real functionality
  /**
   * Execute an action on an order.
   *
   * @param order the order to execute the action on
   */
  public void execAction(final Order order) {
    System.out.println("Hai cliccato su " + order);
    this.selectedOrder = order;
    try {
      this.setShipmentScene();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Filter orders.
   *
   * @param name the name to filter by
   * @param startingDate the starting date to filter by
   * @param endingDate the ending date to filter by
   */
  public void filterOrders(
      final String name, final LocalDate startingDate, final LocalDate endingDate) {
    if (name != null && !name.isEmpty()) {
      System.out.println("Filtering by name: " + name);
    }
    if (startingDate != null) {
      System.out.println("Filtering by starting date: " + startingDate);
    }
    if (endingDate != null) {
      System.out.println("Filtering by ending date: " + endingDate);
    }
  }

  /**
   * Set Orders scene on Stage.
   *
   * @throws Exception if the scene cannot be set
   */
  public void setShipmentScene() throws Exception {
    Stage stage = this.getStage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Shipment.fxml"));
    Parent root = loader.load();
    ShipmentPageController pageController = loader.getController();
    pageController.init(this);
    Scene shipmentScene =
        new Scene(root, Math.max(stage.getWidth(), WIDTH), Math.max(stage.getHeight(), HEIGHT));
    shipmentScene
        .getStylesheets()
        .add(LoginControl.class.getResource("/style/ShipmentPage.css").toExternalForm());
    stage.setScene(shipmentScene);
    stage.show();
  }

  /** Go to the Dashboard page. */
  public void returnToHomePage() throws Exception {
    DashboardControl dashboardControl = new DashboardControl();
    dashboardControl.setScene(this.getStage(), this.getLoggedOperator());
  }

  /** Go to the Orders page. */
  public void returnToOrdersPage() {
    try {
      this.setOrdersScene(this.getStage(), this.getLoggedOperator());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the order.
   *
   * @return the selected order
   */
  public Order getOrder() {
    return selectedOrder;
  }

  // TODO! : Test pourpose only, remove this when the DB connection is implemented
  /**
   * Get test orders.
   *
   * @return a list of test orders
   */
  public ObservableList<Order> getTestOrders() {
    final int outOfPlaceOrderId = 7;
    int orderId = 2;
    final int qty = 24;
    final float price = 22.3f;
    final float weight = 0.5f;
    ObservableList<Order> ordersModels =
        FXCollections.observableArrayList(
            new Order(
                outOfPlaceOrderId,
                LocalDate.parse(
                    LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                false,
                0,
                new Account(
                    "Gennaro",
                    "De Gregorio",
                    "gdgwadawdwadd@gmail.com",
                    LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    "gdg.jpg",
                    "password",
                    new Address("80100", "Napoli", "NA", "Italia", "euw", "2", "Via Barone")),
                qty,
                new Product("Cancellaria", "Penna", "Bic", "Penna bella", price, false, weight)),
            new Order(
                orderId++,
                LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                true,
                0,
                new Account(
                    "Antonio",
                    "De Luca",
                    "wdd@gmail.com",
                    LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    "gdg.jpg",
                    "password",
                    new Address("80100", "napoli", "italia", "napoli", "euw", "2", "barone")),
                qty,
                new Product("Cancellaria", "Gomma", "Bic", "Gomma bella", price, false, weight)),
            // indent these orders using the format above
            new Order(
                orderId++,
                LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                false,
                0,
                new Account(
                    "Gennaro",
                    "De Gregorio",
                    "gdg@gmail.com",
                    LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    "gdg.jpg",
                    "password",
                    new Address("80100", "napoli", "italia", "napoli", "euw", "2", "barone")),
                qty,
                new Product("Cancellaria", "Penna", "Bic", "Penna bella", price, false, weight)),
            new Order(
                orderId++,
                LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                true,
                0,
                new Account(
                    "Antonio",
                    "De Luca",
                    "wdd@gmail.com",
                    LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    "gdg.jpg",
                    "password",
                    new Address("80100", "napoli", "italia", "napoli", "euw", "2", "barone")),
                qty,
                new Product("Cancellaria", "Gomma", "Bic", "Gomma bella", price, false, weight)),
            new Order(
                orderId++,
                LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                false,
                0,
                new Account(
                    "Gennaro",
                    "De Gregorio",
                    "gdg@gmail.com",
                    LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    "gdg.jpg",
                    "password",
                    new Address("80100", "napoli", "italia", "napoli", "euw", "2", "barone")),
                qty,
                new Product("Cancellaria", "Penna", "Bic", "Penna bella", price, false, weight)),
            new Order(
                orderId++,
                LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                true,
                0,
                new Account(
                    "Antonio",
                    "De Luca",
                    "wdd@gmail.com",
                    LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    "gdg.jpg",
                    "password",
                    new Address("80100", "napoli", "italia", "napoli", "euw", "2", "barone")),
                qty,
                new Product("Cancellaria", "Gomma", "Bic", "Gomma bella", price, false, weight)));

    return ordersModels;
  }

  // TODO! : Test pourpose only, remove this when the DB connection is implemented
  /**
   * Get test shipments.
   *
   * @return a list of test shipments
   */
  public ObservableList<Shipment> getTestShipments() {
    Deposit deposit =
        new Deposit(
            1,
            ICON_HEIGHT,
            ICON_HEIGHT * 2,
            new Address("80100", "napoli", "italia", "napoli", "euw", "2", "barone"));
    ObservableList<Shipment> shipments =
        FXCollections.observableArrayList(
            new Shipment(
                1,
                LocalDate.now().minusDays(1),
                new Transport(1, ICON_HEIGHT, false, deposit),
                deposit,
                (float) ICON_HEIGHT / (2 + 1)),
            new Shipment(
                2,
                LocalDate.now().minusDays(2),
                new Transport(1, ICON_HEIGHT, false, deposit),
                deposit,
                ICON_HEIGHT / (2 + 1)),
            new Shipment(
                2 + 1,
                LocalDate.now().minusDays(2 + 1),
                new Transport(1, ICON_HEIGHT, false, deposit),
                deposit,
                ICON_HEIGHT / (2 + 1)),
            new Shipment(
                2 + 2,
                LocalDate.now().minusDays(2 + 2),
                new Transport(1, ICON_HEIGHT, false, deposit),
                deposit,
                ICON_HEIGHT / (2 + 1)));
    return shipments;
  }

  /**
   * Set the table functions.
   *
   * @param <T> the type of the table
   * @param table the table to set the functions on
   */
  public <T> void setTableFunctionality(final TableView<T> table) {
    this.setRowDeselection(table);
    this.setColumnSize(table);
  }

  /**
   * Set the table functions when action buttons are needed.
   *
   * @param <T> the type of the table
   * @param table the table to set the functions on
   * @param columnTitle the action column title
   * @param buttonText the action button text
   * @param action the action to execute
   */
  public <T> void setTableFunctionality(
      final TableView<T> table,
      final String columnTitle,
      final String buttonText,
      final Consumer<T> action) {
    this.addButtonToTable(table, columnTitle, buttonText, action);
    this.setTableFunctionality(table);
  }

  private <T> void setRowDeselection(final TableView<T> table) {
    table.setRowFactory(
        tableView -> {
          final TableRow<T> row = new TableRow<>();
          row.addEventFilter(
              MouseEvent.MOUSE_PRESSED,
              event -> {
                final int index = row.getIndex();
                if (index >= 0
                    && index < table.getItems().size()
                    && table.getSelectionModel().isSelected(index)) {
                  table.getSelectionModel().clearSelection();
                  event.consume();
                }
              });
          return row;
        });
  }

  private <T> void setColumnSize(final TableView<T> table) {
    for (TableColumn<T, ?> column : table.getColumns()) {
      column.prefWidthProperty().bind(table.widthProperty().divide(table.getColumns().size()));
    }
  }

  private <T> void addButtonToTable(
      final TableView<T> table,
      final String columnTitle,
      final String buttonText,
      final Consumer<T> action) {

    TableColumn<T, Void> actionColumn = new TableColumn<>(columnTitle);

    actionColumn.setCellFactory(
        param -> {
          final TableCell<T, Void> cell =
              new TableCell<>() {
                private final Button btn = new Button(buttonText);

                {
                  btn.setOnAction(
                      event -> {
                        T item = getTableView().getItems().get(getIndex());
                        action.accept(item);
                      });
                }

                @Override
                public void updateItem(final Void item, final boolean empty) {
                  super.updateItem(item, empty);
                  setGraphic(empty ? null : btn);
                }
              };
          return cell;
        });

    table.getColumns().add(actionColumn);
  }
}
