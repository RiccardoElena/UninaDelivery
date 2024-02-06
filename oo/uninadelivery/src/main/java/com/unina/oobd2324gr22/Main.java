package com.unina.oobd2324gr22;

import com.unina.oobd2324gr22.entity.DAO.DepositDAO;
import com.unina.oobd2324gr22.entity.DAO.DepositDAOPostgre;
import com.unina.oobd2324gr22.entity.DAO.OrderDAO;
import com.unina.oobd2324gr22.entity.DAO.OrderDAOPostgre;
import com.unina.oobd2324gr22.entity.DAO.ShipmentDAO;
import com.unina.oobd2324gr22.entity.DAO.ShipmentDAOPostgre;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.entity.DTO.Shipment;
import com.unina.oobd2324gr22.utils.App;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

// import com.unina.oobd2324gr22.entity.DAO.OrderDAO;
// import com.unina.oobd2324gr22.entity.DAO.OrderDAOPostgre;
// import java.sql.SQLException;
// import java.util.List;

/** Hello world! */
public final class Main {

  /** Testing variable. */
  private static final int YEAR = 2024;

  // Constructors

  /** Default constructor. */
  private Main() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  // Methods
  /**
   * Main method.
   *
   * @param args Command line arguments.
   */
  public static void main(final String[] args) {
    System.out.println("Hello World!");
    System.out.println("Bypass NON Attivo");
    OrderDAO orderDAO = new OrderDAOPostgre();
    try {
      List<Integer> o = orderDAO.getOrdersPerDay(1, YEAR);
      for (int i : o) {
        System.out.print(o.get(i) + "");
      }
      Order order = orderDAO.getOrderById(1);
      System.out.println(order);
      ShipmentDAO shipmentDAO = new ShipmentDAOPostgre();
      List<Shipment> s = shipmentDAO.getCompatibleShipments(order);
      for (Shipment shipment : s) {
        System.out.println(shipment);
      }
      DepositDAO depositDAO = new DepositDAOPostgre();
      List<Deposit> deposit = depositDAO.getCompatibleDeposits(order, LocalDate.now());
      for (Deposit d : deposit) {
        System.out.println(d);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println(
        "Bypass NON Attivo, per testare l'app lanciare App.launchApp(args) scommentando l'utlima"
            + " linea del main()");
    App.launchApp(args);
  }
}
