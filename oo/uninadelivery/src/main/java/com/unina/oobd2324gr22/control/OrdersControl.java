package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.boundary.OrdersPageController;
import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Product;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OrdersControl extends NonLoginControl {

  /** Shipment selection functionality control class. */
  private ShipmentControl shipmentControl = new ShipmentControl();

  /**
   * Set Orders scene on Stage.
   *
   * @param currStage the stage to set the scene on
   * @param op the operator logged in
   * @throws Exception if the scene cannot be set
   */
  public void setScene(final Stage currStage, final Operator op) throws Exception {
    this.setLoggedOperator(op);
    this.setStage(currStage);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Ordini.fxml"));
    Parent root = loader.load();
    OrdersPageController pageController = loader.getController();
    pageController.init(this);
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    scene
        .getStylesheets()
        .add(LoginControl.class.getResource("/style/OrdersPage.css").toExternalForm());

    currStage.setScene(scene);
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
    try {
      shipmentControl.setScene(this.getStage(), order, this.getLoggedOperator());
    } catch (Exception e) {
      e.printStackTrace();
    }
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
}
