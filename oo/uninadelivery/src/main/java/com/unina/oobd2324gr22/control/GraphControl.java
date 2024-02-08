package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.boundary.GraphPageController;
import com.unina.oobd2324gr22.boundary.NonLoginPageController;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import java.time.LocalDate;
import java.util.ArrayList;
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
    setGraphSceneTest(currStage, op);
    return;
  }

  // TODO @RiccardoElena is possible to refactor setScene to superclass. Do it tomorro
  /**
   * TEST METHOD TO BE CHANGED WITH DAO ONE.
   *
   * @param op the operator
   * @param currStage the stage
   * @param <T> the type of the operator
   */
  public <T extends NonLoginPageController<GraphControl>> void setGraphSceneTest(
      final Stage currStage, final Operator op) throws Exception {
    this.setLoggedOperator(op);
    this.setStage(currStage);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Graph.fxml"));
    Parent root = loader.load();
    T pageController = loader.getController();
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

  /**
   * TEST METHOD TO BE CHANGED WITH DAO ONE. Get the graph data.
   *
   * @return the graph data
   */
  public ArrayList<Integer> getGraphData() {
    ArrayList<Integer> listM = new ArrayList<>();
    for (int i = 1; i <= LocalDate.now().lengthOfMonth(); i++) {
      int value = (int) (Math.random() * LocalDate.now().getDayOfMonth() * 2) + 1;
      if (i <= LocalDate.now().getDayOfMonth()) {
        listM.add(value);
      } else {
        listM.add((int) value / 2);
      }
    }

    return listM;
  }
}
