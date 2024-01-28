package demo;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.Action;

import demo.entity.DTO.Account;
import demo.entity.DTO.Order;
import demo.entity.DTO.Product;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;


public class OrdiniController implements Initializable{
  
  private Account account;

  @FXML
    private HBox topBar;

  @FXML
  private void initialize(){
    if (account != null) {
      nomeLabel.setText(account.getName());
    }
  }

  public void setAccount(Account client) {
    this.account = client;
    nomeLabel.setText(client.getName());
  }

  @FXML
  private TableView<Order> orderTable;
  
  @FXML
  private Label nomeLabel;
  
  @FXML
  private TableColumn<Order, String> clientColumn = new TableColumn<Order, String>("Client");

  @FXML
  private TableColumn<Order, LocalDate> emissionDateColumn;

  @FXML
  private TableColumn<Order, Integer> extraWarrantyColumn;

  @FXML
  private TableColumn<Order, String> isExpressColumn;

  @FXML
  private TableColumn<Order, Integer> orderNumberColumn;

  @FXML
  private TableColumn<Order, Product> productColumn;

  @FXML
  private TableColumn<Order, Integer> quantityColumn;

  @FXML
  private TableColumn<Order, Product> supplierColumn;
  
  @FXML
  private Button closeButton;

  public void closeButtonAction(ActionEvent event) {
    Stage stage = (Stage) closeButton.getScene().getWindow();
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Exit");
    alert.setHeaderText("Stai per uscire!");
    alert.setContentText("Confermi?");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // Crea un effetto di fade out
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), stage.getScene().getRoot());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> stage.close());
        fadeTransition.play();
    } else {
        alert.close();
    }
  }


  @FXML
  private Button minimizeButton;

  public void minimizeButtonAction(ActionEvent event) {
    Stage stage = (Stage) minimizeButton.getScene().getWindow();

    // Crea un effetto di fade out
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), stage.getScene().getRoot());
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.3);
    fadeTransition.setOnFinished(e -> {
      stage.setIconified(true);
      stage.getScene().getRoot().setOpacity(1.0);
    });
    fadeTransition.play();
  }

  @FXML
  private Button resizeButton;

  public void resizeButtonAction(ActionEvent event) {
    Stage stage = (Stage) resizeButton.getScene().getWindow();
    if (stage.isMaximized()) {
        stage.setMaximized(false);
    } else {
        stage.setMaximized(true);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // Variabili per memorizzare la posizione del mouse durante il drag
    final double[] xOffset = new double[1];
    final double[] yOffset = new double[1];

    // Evento pressione mouse sulla barra del titolo
    topBar.setOnMousePressed(event -> {
        xOffset[0] = event.getSceneX();
        yOffset[0] = event.getSceneY();
    });

    // Evento trascinamento mouse sulla barra del titolo
    topBar.setOnMouseDragged(event -> {
        Stage stage = (Stage) topBar.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset[0]);
        stage.setY(event.getScreenY() - yOffset[0]);
    });

    // Configurazione dei pulsanti della barra
    closeButton.setOnMouseEntered(event -> {
    closeButton.setStyle("-fx-background-color: #ff0000;");  // Colore rosso quando il mouse è sopra
    });
    closeButton.setOnMouseExited(event -> {
    closeButton.setStyle("-fx-background-color: transparent;");  // Trasparente quando il mouse non è sopra
    });

    minimizeButton.setOnMouseEntered(event -> {
    minimizeButton.setStyle("-fx-background-color: #ffff00;");
    });
    minimizeButton.setOnMouseExited(event -> {
    minimizeButton.setStyle("-fx-background-color: transparent;");
    });

    resizeButton.setOnMouseEntered(event -> {
    resizeButton.setStyle("-fx-background-color: #00ff00;");
    });
    resizeButton.setOnMouseExited(event -> {
    resizeButton.setStyle("-fx-background-color: transparent;");
    });




    
    // Set up the columns in the table
    orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
    productColumn.setCellValueFactory(new PropertyValueFactory<>("Product"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
    supplierColumn.setCellValueFactory(new PropertyValueFactory<>("Product"));
    emissionDateColumn.setCellValueFactory(new PropertyValueFactory<>("EmissionDate"));
    isExpressColumn.setCellValueFactory(new PropertyValueFactory<>("IsExpress"));
    extraWarrantyColumn.setCellValueFactory(new PropertyValueFactory<>("ExtraWarranty"));
    clientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccount().getEmail()));
    productColumn.setCellFactory(column -> {
      return new TableCell<Order, Product>() {
          @Override
          protected void updateItem(Product item, boolean empty) {
              super.updateItem(item, empty);
              if (item == null || empty) {
                  setText(null);
              } else {
                  setText(item.getName());
              }
          }
      };
  });
    supplierColumn.setCellFactory(column -> {
      return new TableCell<Order, Product>() {
          @Override
          protected void updateItem(Product item, boolean empty) {
              super.updateItem(item, empty);
              if (item == null || empty) {
                  setText(null);
              } else {
                  setText(item.getSupplier());
              }
          }
      };
    });


    addButtonToTable();
    orderTable.setItems(ordersModels);
  }
//TODO! : Qui invece si dovrà utilizzare il DB per recuperare la lista ed inserirla in ordersModels
  private LocalDate bdate = LocalDate.of(2020, 12, 31);
  private Account gege    = new Account("Gennaro", "De Gregorio", "gdg@gmail.com",bdate , "gdg.jpg", "password", null);
  private Account tony    = new Account("Antonio", "De Luca", "wdd@gmail.com",bdate , "gdg.jpg", "password", null);
  private ObservableList<Order> ordersModels = FXCollections.observableArrayList(
    new Order(7, bdate, false, 0, gege,24,new Product("Cancellaria","Penna","Bic","Penna bella",(float) 22.3,false,(float) 0.5)),
    new Order(2, bdate, true, 0, tony,24,new Product("Cancellaria","Gomma","Bic","Gomma bella",(float) 22.3,false,(float) 0.5)),
    new Order(3, bdate, false, 0, gege,24,new Product("Cancellaria","Penna","Bic","Penna bella",(float) 22.3,false,(float) 0.5)),
    new Order(4, bdate, true, 0, tony,24,new Product("Cancellaria","Gomma","Bic","Gomma bella",(float) 22.3,false,(float) 0.5)),
    new Order(5, bdate, false, 0, gege,24,new Product("Cancellaria","Penna","Bic","Penna bella",(float) 22.3,false,(float) 0.5)),
    new Order(6, bdate, true, 0, tony,24,new Product("Cancellaria","Gomma","Bic","Gomma bella",(float) 22.3,false,(float) 0.5))
  );

  private void eseguiAzione(Order order) {
    System.out.println("Hai cliccato su " + order.getOrderId());
  }


  private void addButtonToTable() {
    TableColumn<Order, Void> actionColumn = new TableColumn<>("Actions");

    Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<>() {
        @Override
        public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
            final TableCell<Order, Void> cell = new TableCell<>() {

                private final Button btn = new Button("Esegui");

                {
                    btn.setOnAction(event -> {
                        Order order = getTableView().getItems().get(getIndex());
                        eseguiAzione(order);
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
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

    orderTable.getColumns().add(actionColumn);
}

}
