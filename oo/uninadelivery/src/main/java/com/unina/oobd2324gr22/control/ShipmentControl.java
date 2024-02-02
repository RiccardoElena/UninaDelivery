package com.unina.oobd2324gr22.control;

import java.net.URL;

import com.unina.oobd2324gr22.boundary.ShipmentPageController;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.entity.DTO.Order;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShipmentControl extends NonLoginControl {

  /** Order selected. */
  private Order order;

    /**
   * Set Orders scene on Stage.
   *
   * @param stage the stage to set the scene on
   * @param ord the order to set the scene on
   * @param op    the logged in operator.
   * @throws Exception if the scene cannot be set
   */
  public void setScene(final Stage stage, final Order ord, final Operator op) throws Exception {
    System.out.println("ordine delle anime bast4rd3: " + ord);
    this.setLoggedOperator(op);
    this.order = ord;
    this.setStage(stage);
    System.out.println("Logged2operator: " + this.getLoggedOperator());
    URL resourceUrl = ShipmentControl.class.getResource("/FXML/Shipment.fxml");
    FXMLLoader loader = new FXMLLoader(resourceUrl);
    Parent root = loader.load();
    ShipmentPageController pageController = loader.getController();
    pageController.init(this, ord);
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    scene.getStylesheets().add(LoginControl.class.getResource("/style/ShipmentPage.css")
    .toExternalForm());
    stage.setScene(scene);
    stage.show();
  }
}
