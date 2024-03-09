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
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * This class provides the control logic for Shipping functionality.
 *
 * <p>It handles the interaction between the user interface and the data access layer.
 *
 * <p>It's responsible for displaying the correct view and handling the user's input for the Orders
 * Page and Shipment Page.
 */
public final class OrdersHandlingControl extends NonLoginControl {

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

  /** Singleton instance. */
  private static OrdersHandlingControl istance;

  private OrdersHandlingControl(final String defaultPageName) {
    super(defaultPageName);
  }

  /**
   * Get the singleton instance.
   *
   * @return the singleton instance
   */
  public static OrdersHandlingControl getInstance() {
    if (istance == null) {
      istance = new OrdersHandlingControl("Orders");
    }
    return istance;
  }

  // Page Navigation Methods

  /**
   * Go to the Shipment Page relative to an order.
   *
   * @param order seleted order
   */
  public void goToShipmentPage(final Order order) {
    Session.selectOrder(order);
    try {
      setScene("Shipment");
    } catch (Exception e) {
      showInternalError(e);
    }
  }

  /** Go to the Orders page. */
  public void goToOrdersPage() {
    Session.unselectOrder();
    try {
      setScene("Orders");
    } catch (Exception e) {
      showInternalError(e);
    }
  }

  // Data Handling Methods

  /**
   * Filter orders.
   *
   * @param accountData the name to filter by
   * @param startingDate the starting date to filter by
   * @param endingDate the ending date to filter by
   * @return the filtered orders
   */
  public ObservableList<Order> filterOrders(
      final String accountData, final LocalDate startingDate, final LocalDate endingDate) {
    HashMap<String, Object> filters = new HashMap<>();

    if (isValidEmail(accountData)) {
      filters.put("email", accountData);
    } else if (!accountData.isEmpty()) {
      filters.put("surname", accountData);
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
      showInternalError(e);
      return null;
    }
  }

  private boolean isValidEmail(final String email) {
    return email != null
        && !email.isEmpty()
        && Pattern.matches(
            "^([a-zA-Z0-9]+\\.[a-zA-Z0-9]+|[a-zA-Z0-9]+)@[a-zA-Z.]+\\.[a-zA-Z]{2,}$", email);
  }

  /**
   * Get the order.
   *
   * @return the selected order
   */
  public Order getOrder() {
    return Session.getSelectedOrder();
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
      showInternalError(e);
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
          FXCollections.observableArrayList(
              shipmentDAO.getCompatibleShipments(Session.getSelectedOrder()));
    } catch (SQLException e) {
      showInternalError(e);
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
              depositsDAO.getCompatibleDeposits(Session.getSelectedOrder(), selectedDate));
    } catch (SQLException e) {
      showInternalError(e);
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
    try {
      Order selectedOrder = Session.getSelectedOrder();
      double totalVolume =
          selectedOrder.getProduct().getPackageSizeLiters() * selectedOrder.getQuantity();

      return FXCollections.observableArrayList(
          transportDAO.getCompatibleTransports(totalVolume, selectedDeposit, selectedDate));
    } catch (SQLException e) {
      showInternalError(e);
      return null;
    }
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
      showInternalError(e);
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
      if (isShippingConfirmed(selectedShipment)) {
        shipmentDAO.shipOrder(Session.getSelectedOrder(), selectedShipment);
        showSuccessModal();
      }
    } catch (Exception e) {
      showInternalError(e);
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
    Shipment newShipment = createShipment(shippingDate, startDeposit, transport);
    try {
      if (isShippingConfirmed(shippingDate)) {
        insertShipment(newShipment);
        shipOrder(Session.getSelectedOrder(), newShipment);
        assignDriver(newShipment, driver);
        showSuccessModal();
      }
    } catch (SQLException e) {
      handleShippingError(newShipment, e);
    }
  }

  private Shipment createShipment(
      final LocalDate shippingDate, final Deposit startDeposit, final Transport transport) {
    return new Shipment(shippingDate, Session.getLoggedOperator(), startDeposit, transport);
  }

  private void insertShipment(final Shipment shipment) throws SQLException {
    shipment.setId(shipmentDAO.insert(shipment));
  }

  private void shipOrder(final Order order, final Shipment shipment) throws SQLException {
    shipmentDAO.shipOrder(order, shipment);
  }

  private void assignDriver(final Shipment shipment, final Driver driver) throws SQLException {
    shipmentDAO.assignDriver(shipment, driver);
  }

  // Modal Handling Methods

  private String createConfirmationMessage(final LocalDate shippingDate) {
    return "Premendo OK verrà creata una nuova spedizione per il giorno "
        + shippingDate
        + " con i dati inseriti a cui verrà"
        + " aggiunto l'ordine selezionato. Continuare?";
  }

  private String createConfirmationMessage(final Shipment selectedShipment) {
    return "Premendo OK verrà aggiunto l'ordine selezionato alla spedizione "
        + selectedShipment.getId()
        + " in partenza il "
        + selectedShipment.getShippingDate()
        + ". Continuare?";
  }

  private void handleShippingError(final Shipment newShipment, final SQLException e) {
    try {
      shipmentDAO.delete(newShipment);
    } catch (SQLException e1) {
      showInternalError(e1);
    }
    showInternalError(e);
  }

  private boolean isShippingConfirmed(final Shipment shipment) {
    Optional<ButtonType> modalResponse =
        showAlert(
            Alert.AlertType.CONFIRMATION,
            "Conferma",
            "Spedire Ordine?",
            createConfirmationMessage(shipment));
    return modalResponse.isPresent() && modalResponse.get() == ButtonType.OK;
  }

  private boolean isShippingConfirmed(final LocalDate date) {
    Optional<ButtonType> modalResponse =
        showAlert(
            Alert.AlertType.CONFIRMATION,
            "Conferma",
            "Spedire Ordine?",
            createConfirmationMessage(date));
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
            "La spedizione è stata creata e l'ordine è stato aggiunto. Continuare a gestire gli"
                + " ordini o tornare alla home?",
            orderButtonType,
            homeButtonType);

    modalResponse.ifPresent(
        response -> {
          if (response == orderButtonType) {
            goToOrdersPage();
          } else {
            returnToHomePage();
          }
        });
  }
}
