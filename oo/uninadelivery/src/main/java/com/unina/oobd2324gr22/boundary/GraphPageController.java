package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.GraphControl;
import java.util.ArrayList;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.util.Duration;

public class GraphPageController extends NonLoginPageController<GraphControl> {

  /** BorderPane of the page. */
  @FXML private BorderPane borderPane;

  /** Asse X. */
  private CategoryAxis xAxis = new CategoryAxis();

  /** Asse Y. */
  private NumberAxis yAxis = new NumberAxis();

  /** LineChart of the page. */
  @FXML private LineChart<String, Number> chart = new LineChart<String, Number>(xAxis, yAxis);

  /** ComboBox to select the month. */
  @FXML private ComboBox<?> monthComboBox;

  /** ComboBox to select the year. */
  @FXML private ComboBox<?> yearComboBox;

  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   */
  @Override
  protected final void initialize(final GraphControl control) {
    setGraphData();
  }

  /** Set the graph data. */
  private void setGraphData() {

    ArrayList<Integer> ordersData = getControl().getGraphData();
    try {

      chart.getData().add(getAverageLine(ordersData));

      chart.getData().add(getOrdersLine(ordersData));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private XYChart.Series<String, Number> getAverageLine(final ArrayList<Integer> ordersData) {
    XYChart.Series<String, Number> average = new XYChart.Series<>();
    for (int i = 0; i < ordersData.size(); i++) {
      XYChart.Data<String, Number> data =
          new XYChart.Data<>(String.valueOf((i + 1)), getAverage(ordersData));
      average.getData().add(data);
      Platform.runLater(() -> data.getNode().setOpacity(0.0));
      setPopupOnDataHover(data, "Media");
    }
    return average;
  }

  private XYChart.Series<String, Number> getOrdersLine(final ArrayList<Integer> ordersData) {
    XYChart.Series<String, Number> ordersLine = new XYChart.Series<>();
    for (int i = 0; i < ordersData.size(); i++) {
      XYChart.Data<String, Number> dataPoint =
          new XYChart.Data<>(String.valueOf(i), ordersData.get(i));
      ordersLine.getData().add(dataPoint);
      setPopupOnDataHover(dataPoint, "Valore");
    }
    return ordersLine;
  }

  private void setPopupOnDataHover(final XYChart.Data<String, Number> data, final String label) {
    Popup popup = new Popup();
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    popup.getContent().add(new Label("" + label + ": " + data.getYValue()));
    data.nodeProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              newValue.getStyleClass().addAll("chart-line-symbol");
              newValue.setOnMouseEntered(
                  mouseEvent -> {
                    pause.setOnFinished(
                        event -> {
                          popup.show(
                              chart.getScene().getWindow(),
                              mouseEvent.getScreenX(),
                              mouseEvent.getScreenY());
                        });
                    pause.playFromStart();
                  });
              newValue.setOnMouseExited(
                  event -> {
                    popup.hide();
                    pause.stop();
                  });
            });
  }

  private Float getAverage(final ArrayList<? extends Number> listM) {
    Float sum = 0f;
    for (Number value : listM) {
      sum += value.floatValue();
    }
    return sum / listM.size();
  }
}
