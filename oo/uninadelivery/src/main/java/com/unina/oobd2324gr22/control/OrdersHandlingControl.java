package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DAO.OrderDAO;
import com.unina.oobd2324gr22.entity.DAO.OrderDAOPostgre;
import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Product;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.entity.DTO.Transport;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrdersHandlingControl extends NonLoginControl {

  /** Order Data Access Object. */
  private OrderDAO ordersDAO = new OrderDAOPostgre();

  /** Order selected. */
  private Order selectedOrder;

  /** Alternative page to load. */
  private String currPage = "Orders";

  /** Add page related scene settings. */
  @Override
  protected void addSceneSettings() {
    super.addSceneSettings();
    setFileName(currPage);
  }

  // FIXME: to be changed to real functionality
  /**
   * Execute an action on an order.
   *
   * @param order the order to execute the action on
   */
  public void goToShipmentPage(final Order order) {
    System.out.println("Hai cliccato su " + order);
    this.selectedOrder = order;
    try {
      this.currPage = "Shipment";
      setScene(getStage(), getLoggedOperator());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Go to the Orders page. */
  public void goToOrdersPage() {
    this.selectedOrder = null;
    try {
      this.currPage = "Orders";
      setScene(this.getStage(), this.getLoggedOperator());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Filter orders.
   *
   * @param email the name to filter by
   * @param startingDate the starting date to filter by
   * @param endingDate the ending date to filter by
   *
   * @return the filtered orders
   */
  public ObservableList<Order> filterOrders(
      final String email, final LocalDate startingDate, final LocalDate endingDate) {
    ObservableList<Order> filteredOrders = null;
    try{
    if (email != null  && startingDate == null && endingDate == null) {
      filteredOrders = ordersDAO.getOrdersByEmail(email);
    } else if ((email == null || email.isEmpty()) && startingDate != null && endingDate != null) {
      filteredOrders = ordersDAO.getOrdersByDate(startingDate, endingDate);
    } else if ((email == null || email.isEmpty()) && startingDate != null && endingDate == null) {
      filteredOrders = ordersDAO.getOrdersByDate(startingDate);
    } else if (email != null  && startingDate != null && endingDate != null) {
      filteredOrders = ordersDAO.getOrdersByEmailAndDate(email, startingDate, endingDate);
    }
  } catch (SQLException e) {
      e.getMessage();
    }
    return filteredOrders;
  }

  /**
   * Get the order.
   *
   * @return the selected order
   */
  public Order getOrder() {
    return selectedOrder;
  }

  /**
   * Get the unfinished orders.
   *
   * @return list of unfinished orders
   */
  public ObservableList<Order> getUnfinishedOrders() {
    ObservableList<Order> orders = null;
    try {
      orders = ordersDAO.getUnfinishedOrders();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return orders;
  }

  // TODO! : Test pourpose only, remove this when the DB connection is implemented
  /**
   * Get test orders.
   *
   * @return a list of test orders
   */
  public ObservableList<Order> getTestOrdersXX() {
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
}
