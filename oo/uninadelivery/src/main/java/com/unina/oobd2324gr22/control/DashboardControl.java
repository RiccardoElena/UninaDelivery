package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.boundary.DashboardPageController;
import com.unina.oobd2324gr22.boundary.OrdersPageController;
import com.unina.oobd2324gr22.entity.DTO.Operator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardControl extends NonLoginControl {

  /**
   * Set Orders scene on Stage.
   *
   * @param stage the stage to set the scene on
   * @param op    the logged in operator.
   * @throws Exception if the scene cannot be set
   */
  public void setScene(final Stage stage, final Operator op) throws Exception {
    this.setLoggedOperator(op);
    this.setStage(stage);
    System.out.println("Logged2operator: " + this.getLoggedOperator());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/dashboard.fxml"));
    Parent root = loader.load();
    DashboardPageController pageController = loader.getController();
    pageController.init(this);
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    scene.getStylesheets().add(LoginControl.class.getResource("/style/Dashboard.css")
    .toExternalForm());
    stage.setScene(scene);
    stage.show();
    return;
  }

}
