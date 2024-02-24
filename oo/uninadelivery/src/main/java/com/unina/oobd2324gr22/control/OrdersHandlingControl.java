package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DAO.AccountDAO;
import com.unina.oobd2324gr22.entity.DAO.AccountDAOPostgre;
import com.unina.oobd2324gr22.entity.DAO.DepositDAO;
import com.unina.oobd2324gr22.entity.DAO.DepositDAOPostgre;
import com.unina.oobd2324gr22.entity.DAO.OrderDAO;
import com.unina.oobd2324gr22.entity.DAO.OrderDAOPostgre;
import com.unina.oobd2324gr22.entity.DAO.ShipmentDAO;
import com.unina.oobd2324gr22.entity.DAO.ShipmentDAOPostgre;
import com.unina.oobd2324gr22.entity.DAO.TransportDAO;
import com.unina.oobd2324gr22.entity.DAO.TransportDAOPostgre;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Driver;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class OrdersHandlingControl extends NonLoginControl {

  /** Order Data Access Object. */
  private OrderDAO ordersDAO = new OrderDAOPostgre();

  /** Shipment Data Access Object. */
  private ShipmentDAO shipmentDAO = new ShipmentDAOPostgre();

  /** Deposit Data Access Object. */
  private DepositDAO depositsDAO = new DepositDAOPostgre();

  /** Transport Data Access Object. */
  private TransportDAO transportDAO = new TransportDAOPostgre();

  /** Driver Data Access Object. */
  private AccountDAO accountDAO = new AccountDAOPostgre();

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
   * @param account the name to filter by
   * @param startingDate the starting date to filter by
   * @param endingDate the ending date to filter by
   * @return the filtered orders
   */
  public ObservableList<Order> filterOrders(
      final String account, final LocalDate startingDate, final LocalDate endingDate) {

    HashMap<String, Object> filters = new HashMap<>();

    if (account != null
        && !account.isEmpty()
        && account.matches("^[a-zA-Z0-9]+[a-zA-Z0-9.]*[a-zA-Z0-9]+@[a-zA-Z.]+\\.[a-zA-Z]{2,}$")) {
      filters.put("email", account);
    } else if (account != null && !account.isEmpty()) {
      filters.put("surname", account);
    }
    if (startingDate != null) {
      filters.put("startingDate", startingDate);
    }
    if (endingDate != null) {
      filters.put("endingDate", endingDate);
    }

    try {
      return FXCollections.observableArrayList(ordersDAO.getOrdersByFilters(filters));

    } catch (SQLException e) {
      showInternalError();
      return null;
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

  /**
   * Get the unfinished orders.
   *
   * @return list of unfinished orders
   */
  public ObservableList<Order> getUnfinishedOrders() {
    ObservableList<Order> orders = null;
    try {
      orders = FXCollections.observableArrayList(ordersDAO.getUnfinishedOrders());
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return orders;
  }

  /**
   * Get compatible the shipments.
   *
   * @return list of shipments
   */
  public ObservableList<Shipment> getShipments() {
    ObservableList<Shipment> shipments = null;
    try {

      shipments =
          FXCollections.observableArrayList(shipmentDAO.getCompatibleShipments(selectedOrder));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return shipments;
  }

  /**
   * Get the deposits.
   *
   * @param selectedDate the selected date
   * @return list of deposits
   */
  public ObservableList<Deposit> getDeposits(final LocalDate selectedDate) {
    ObservableList<Deposit> deposits = null;
    try {
      deposits =
          FXCollections.observableArrayList(
              depositsDAO.getCompatibleDeposits(selectedOrder, selectedDate));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return deposits;
  }

  /**
   * Get the transport.
   *
   * @param selectedDate the selected date
   * @param selectedDeposit the selected deposit
   * @return list of transports
   */
  public ObservableList<Transport> getTransports(
      final LocalDate selectedDate, final Deposit selectedDeposit) {
    ObservableList<Transport> transports = null;
    try {
      transports =
          FXCollections.observableArrayList(
              transportDAO.getCompatibleTransports(selectedOrder, selectedDeposit, selectedDate));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return transports;
  }

  /**
   * Get the drivers.
   *
   * @param selectedDate the selected date
   * @param selectedDeposit the selected deposit
   * @return list of drivers
   */
  public ObservableList<Driver> getDrivers(
      final LocalDate selectedDate, final Deposit selectedDeposit) {
    ObservableList<Driver> drivers = null;
    try {
      drivers =
          FXCollections.observableArrayList(
              accountDAO.getCompatibleDrivers(selectedDeposit, selectedDate));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return drivers;
  }

  /**
   * Ships the selected order with an already existing shipment.
   *
   * @param selectedShipment the selected shipment
   */
  public void shipsSelectedOrder(final Shipment selectedShipment) {
    try {
      if (isShippingConfirmed(
          "Premendo OK verrà aggiunto l'ordine selezionato alla spedizione "
              + selectedShipment.getId()
              + " in partenza il "
              + selectedShipment.getShippingDate()
              + ". Continuare?")) {
        shipmentDAO.shipOrder(selectedOrder, selectedShipment);
        showSuccessModal();
      }
    } catch (Exception e) {
      e.printStackTrace();
      showInternalError();
    }
  }

  /**
   * Ships the selected order with a new shipment.
   *
   * @param shippingDate the shipping date
   * @param startDeposit the start deposit
   * @param transport the transport
   * @param driver the driver
   */
  public void shipsSelectedOrder(
      final LocalDate shippingDate,
      final Deposit startDeposit,
      final Transport transport,
      final Driver driver) {
    Shipment newShipment = new Shipment(shippingDate, getLoggedOperator(), startDeposit, transport);
    try {
      if (isShippingConfirmed(
          "Premendo OK verrà creata una nuova spedizione per il giorno "
              + shippingDate
              + " con i dati inseriti a cui verrà"
              + " aggiunto l'ordine selezionato. Continuare?")) {
        newShipment.setId(shipmentDAO.insertShipment(newShipment));
        shipmentDAO.shipOrder(selectedOrder, newShipment);
        shipmentDAO.assignDriver(newShipment, driver);
        showSuccessModal();
      }
    } catch (SQLException e) {
      try {
        shipmentDAO.deleteShipment(newShipment);
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
      showInternalError();
    }
  }

  private boolean isShippingConfirmed(final String content) {
    Optional<ButtonType> modalResponse =
        showAlert(Alert.AlertType.CONFIRMATION, "Conferma", "Spedire Ordine?", content);
    return modalResponse.isPresent() && modalResponse.get() == ButtonType.OK;
  }

  private void showSuccessModal() {
    ButtonType orderButtonType = new ButtonType("Gestisci ordini");
    ButtonType homeButtonType = new ButtonType("Torna alla home");
    Optional<ButtonType> modalResponse =
        showAlert(
            Alert.AlertType.NONE,
            "Successo",
            "Operazione terminata con successo.",
            "La spedizione è stata creata e l'ordine è stato aggiunto. Continuare a gestire"
                + " gli ordini o tornare alla home?",
            orderButtonType,
            homeButtonType);
    if (modalResponse.isPresent()) {
      if (modalResponse.get() == orderButtonType) {
        goToOrdersPage();
      } else {
        returnToHomePage();
      }
    }
  }
}
