package com.unina.oobd2324gr22.boundary;

import java.util.ArrayList;

import com.unina.oobd2324gr22.control.GraphControl;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Popup;
import javafx.util.Duration;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class GraphPageController extends NonLoginPageController<GraphControl> {

  /** BorderPane of the page. */
  @FXML private BorderPane borderPane;

  /** Asse X. */
  private CategoryAxis xAxis = new CategoryAxis();

  /** Asse Y. */
  private NumberAxis yAxis = new NumberAxis();
  /** LineChart of the page. */
  @FXML  private LineChart<String, Number> chart = new LineChart<String, Number>(xAxis, yAxis);

  /** Button to exit the application. */
  @FXML private Button exitButton;

  /** Button to go back to dashboard. */
  @FXML private Button homeButton;

  /** Button to minimize the application. */
  @FXML private Button minimizeButton;

  /** ComboBox to select the month. */
  @FXML private ComboBox<?> monthComboBox;

  /** Button to resize the application. */
  @FXML private Button resizeButton;

  /** AnchorPane of the title bar. */
  @FXML private AnchorPane titleBar;

  /** ComboBox to select the year. */
  @FXML private ComboBox<?> yearComboBox;


  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   */
  @Override
  protected final void initialize(final GraphControl control) {
    setDraggableNode(titleBar);
    ArrayList<Integer> listM = new ArrayList<>();
    for (int i = 1; i < 31; i++) {
    listM.add(i);
    }
    System.out.println(listM.toString());
    setGraphData(listM);
  }




  /**
   * Set the graph data.
   *
   * @param listM the data to set
   */
  private void setGraphData(final ArrayList<Integer> listM) {
    try {
      XYChart.Series<String, Number> series = new XYChart.Series<>();
      for (int i = 1; i <= listM.size(); i++) {
        XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(String.valueOf(i),
                                                      listM.get(i-1));
        series.getData().add(dataPoint);
        dataPoint.nodeProperty().addListener((observable, oldValue, newValue) -> {
          if (newValue != null) {
            newValue.getStyleClass().add("chart-line-symbol");
            newValue.setOnMouseClicked(event -> {
              Popup popup = new Popup();
              Label label = new Label("Valore: " + dataPoint.getYValue());
              label.setStyle("-fx-background-color: white; -fx-padding: 10;");
              popup.getContent().add(label);
              popup.show(chart.getScene().getWindow(), event.getScreenX(), event.getScreenY());
              label.setOnMouseClicked(e -> popup.hide());
              Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
                popup.hide();
              }));
              timeline.play();
            });
          }
        });
      }
      chart.getData().clear();
      chart.getData().add(series);
  } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
