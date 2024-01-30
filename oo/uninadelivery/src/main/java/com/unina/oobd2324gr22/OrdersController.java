package com.unina.oobd2324gr22;

import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Product;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.util.Duration;


public class OrdersController implements Initializable {

  /** Duration of the fade out effect. */
  private static final float FADE_OUT_DURATION = 0.3f;

  /** Logged account. */
  private Account account;

  /** Custom title bar. */
  @FXML private AnchorPane titleBar;

  // FIXME: Renamed the TableView to more descriptive name.
  // If given name was important for JFX implementation, fix it there.

  // All this parameter are a mess, isn't a better way to encapsulate them
  // in a custom table class?

  /** Label containing the name of the logged account. */
  @FXML private Label nameLabel;

  /** Table containing the orders. */
  @FXML private TableView<Order> ordersTable;

  // FIXME: Renamed the label to match naming convention.
  // If given name was important for JFX implementation, fix it there.

  /** Column containing the client of the order. */
  @FXML private TableColumn<Order, String> clientColumn = new TableColumn<Order, String>("Client");

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

  // FIXME: Renamed the button to match naming convention.
  // If given name was important for JFX implementation, fix it there.
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

  @FXML
  private void initialize() {
    if (account != null) {
      nameLabel.setText(account.getName());
    }
  }

  // ? @zGenny there's a reason why this method is public?
  // if not make it private and remove the javaDoc
  /**
   * Sets the label text to match the logged account name.
   *
   * @param client
   */
  public void setAccount(final Account client) {
    this.account = client;
    nameLabel.setText(client.getName());
  }

  /**
   * On exitButton click, it shows a confirmation alert. If the user confirms, it closes the
   * application.
   *
   * @param event
   */
  public final void exitButtonAction(final ActionEvent event) {
    Stage stage = (Stage) exitButton.getScene().getWindow();
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Exit");
    alert.setHeaderText("Stai per uscire!");
    alert.setContentText("Confermi?");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      // Crea un effetto di fade out
      applyFadeTransition(stage, 1.0, 0.0, e -> stage.close());
    } else {
      alert.close();
    }
  }

  // ? @zGenny: can't understand the pourpose of this button
  // this page should have a default exit button on the top right corner.
  // Anyway this implementatos seems more accurate then the one in the
  // LoginController, so I'll keep this one as reference in case we want to
  // update the other controller or make a new custom allert class.

  private void applyFadeTransition(
      final Stage stage,
      final double fromValue,
      final double toValue,
      final EventHandler<ActionEvent> onFinish) {
    FadeTransition fadeTransition =
        new FadeTransition(Duration.seconds(FADE_OUT_DURATION), stage.getScene().getRoot());
    fadeTransition.setFromValue(fromValue);
    fadeTransition.setToValue(toValue);
    fadeTransition.setOnFinished(onFinish);
    fadeTransition.play();
  }


  /**
   * On minimizeButton click, it minimizes the application.
   *
   * @param event the event that triggered the method
   */
  public final void minimizeButtonAction(final ActionEvent event) {
    Stage stage = (Stage) minimizeButton.getScene().getWindow();

    // Crea un effetto di fade out
    applyFadeTransition(
        stage,
        1.0,
        0.0,
        e -> {
          stage.setIconified(true);
          stage.getScene().getRoot().setOpacity(1.0);
        });
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

  @Override
  public final void initialize(final URL location, final ResourceBundle resources) {

    // ? @zGenny: this one needs a lot of explaination
    // Why on heart we need an array of fized size 1?
    // Variabili per memorizzare la posizione del mouse durante il drag
    final double[] xOffset = new double[1];
    final double[] yOffset = new double[1];

    // Evento pressione mouse sulla barra del titolo
    titleBar.setOnMousePressed(
        event -> {
          xOffset[0] = event.getSceneX();
          yOffset[0] = event.getSceneY();
        });

    // Evento trascinamento mouse sulla barra del titolo
    titleBar.setOnMouseDragged(
      event -> {
        Stage stage = (Stage) titleBar.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset[0]);
        stage.setY(event.getScreenY() - yOffset[0]);
    });

    // Set up the columns in the table
    orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
    productColumn.setCellValueFactory(new PropertyValueFactory<>("Product"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
    supplierColumn.setCellValueFactory(new PropertyValueFactory<>("Product"));
    emissionDateColumn.setCellValueFactory(new PropertyValueFactory<>("EmissionDate"));
    isExpressColumn.setCellValueFactory(new PropertyValueFactory<>("IsExpress"));
    extraWarrantyColumn.setCellValueFactory(new PropertyValueFactory<>("ExtraWarranty"));

    // TODO! @zGenny: we can consider to add the getAccountEmail method to the
    // Order class if this makes the code more readable
    clientColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getAccount().getEmail()));
    // Same here!
    productColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
    supplierColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getSupplier()));

    addButtonToTable();

    for (TableColumn<Order, ?> column : ordersTable.getColumns()) {
      column.prefWidthProperty().bind(ordersTable.widthProperty()
      .divide(ordersTable.getColumns().size()));
    }

    // Aggiungi un ChangeListener alla proprietà value del primo DatePicker
    startDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
      if (newDate != null && endDatePicker.getValue() != null
      &&
      newDate.isAfter(endDatePicker.getValue())) {
        endDatePicker.setValue(null);
      }
      if (newDate != null) {
          // Imposta la data minima del secondo DatePicker a newDate
          endDatePicker.setDayCellFactory(d -> new DateCell() {
              @Override
              public void updateItem(final LocalDate item, final boolean empty) {
                  super.updateItem(item, empty);
                  setDisable(item.isBefore(newDate));
              }
          });
      }
    });


    // FIXME: this line is using a test function with dummy data
    // when DB connection is implemented, this line should be changed
    ordersTable.setItems(getTestOrders());
  } //! end initialize

  // TODO! : Test pourpose only, remove this when the DB connection
  // is implemented

  // ordersModels

  private ObservableList<Order> getTestOrders() {
    final int outOfPlaceOrderId = 7;
    int orderId = 2;
    final int qty = 24;
    final float price = 22.3f;
    final float weight = 0.5f;
    ObservableList<Order> ordersModels =
        FXCollections.observableArrayList(
            new Order(
                outOfPlaceOrderId,
                LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                false,
                0,
                new Account(
                    "Gennaro",
                    "De Gregorio",
                    "gdgwadawdwadd@gmail.com",
                    LocalDate.parse("2020-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    "gdg.jpg",
                    "password",
                    null),
                qty,
                new Product("Cancellaria", "Penna", "Bic", "Penna bella", price, false, weight)),
            new Order(
                orderId,
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
                    null),
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
                    null),
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
                    null),
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
                    null),
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
                    null),
                qty,
                new Product("Cancellaria", "Gomma", "Bic", "Gomma bella", price, false, weight)));

    return ordersModels;
  }

  // FIXME: change name to follow naming convention
  private void eseguiAzione(final Order order) {
    System.out.println("Hai cliccato su " + order.getOrderId());
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
                          eseguiAzione(order);
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


}
