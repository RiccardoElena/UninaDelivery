package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.boundary.GraphPageController;
import com.unina.oobd2324gr22.entity.DTO.Operator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphControl extends NonLoginControl {

  /**
   * Set Graph scene on Stage.
   *
   * @param currStage the stage to set the scene on
   * @param op the logged operator
   * @throws Exception if the scene cannot be set
   */
  public void setGraphScene(final Stage currStage, final Operator op) throws Exception {
    this.setLoggedOperator(op);
    this.setStage(currStage);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Graph.fxml"));
    Parent root = loader.load();
    GraphPageController pageController = loader.getController();
    pageController.init(this);
    Scene graphScene =
        new Scene(
            root, Math.max(currStage.getWidth(), WIDTH), Math.max(currStage.getHeight(), HEIGHT));
    graphScene
        .getStylesheets()
        .add(LoginControl.class.getResource("/style/Graph.css").toExternalForm());
    currStage.setScene(graphScene);
    currStage.show();
    return;
  }

}
