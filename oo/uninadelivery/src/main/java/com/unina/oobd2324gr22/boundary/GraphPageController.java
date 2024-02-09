package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.GraphControl;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.util.Duration;

public class GraphPageController extends NonLoginPageController<GraphControl> {

  /** Starting year. */
  private static final int STARTING_YEAR = 2000;

  /** BorderPane of the page. */
  @FXML private BorderPane borderPane;

  /** Asse X. */
  @FXML private CategoryAxis xAxis;

  /** Asse Y. */
  @FXML private NumberAxis yAxis;

  /** LineChart of the page. */
  @FXML private LineChart<String, Number> chart;

  /** ComboBox to select the month. */
  @FXML private ComboBox<Month> monthComboBox;

  /** ComboBox to select the year. */
  @FXML private ComboBox<Year> yearComboBox;

  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   */
  @Override
  protected final void initialize(final GraphControl control) {

    initializeComboBoxes();
    setGraphData();
  }

  private void initializeComboBoxes() {
    initializeMonthComboBox();

    intializeYearComboBox();
  }

  private void initializeMonthComboBox() {
    monthComboBox.getItems().addAll(Month.values());

    monthComboBox.setCellFactory(
        param ->
            new ListCell<Month>() {
              @Override
              protected void updateItem(final Month item, final boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setText(null);
                } else {
                  setText(
                      setInitialAsCapitalLetter(
                          item.getDisplayName(TextStyle.FULL, Locale.ITALIAN)));
                }
              }
            });
    monthComboBox.setOnAction(e -> this.setGraphData());
  }

  private void intializeYearComboBox() {
    for (int i = Year.now().getValue(); i >= STARTING_YEAR; i--) {
      yearComboBox.getItems().add(Year.of(i));
    }
    yearComboBox.setOnAction(e -> this.setGraphData());
  }

  private String setInitialAsCapitalLetter(final String string) {
    return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
  }

  /** Set the graph data. */
  private void setGraphData() {

    if (monthComboBox.getValue() == null || yearComboBox.getValue() == null) {
      return;
    }
    List<Integer> ordersData =
        getControl().getGraphData(monthComboBox.getValue(), yearComboBox.getValue());

    try {

      chart.getData().clear();

      chart.getData().add(getAverageLine(ordersData));

      chart.getData().add(getOrdersLine(ordersData));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private XYChart.Series<String, Number> getAverageLine(final List<Integer> ordersData) {
    XYChart.Series<String, Number> average = new XYChart.Series<>();
    for (int i = 0; i < ordersData.size(); i++) {
      System.err.println("in: " + i + " metto " + getAverage(ordersData));
      XYChart.Data<String, Number> data =
          new XYChart.Data<>(String.valueOf((i + 1)), getAverage(ordersData));
      average.getData().add(data);
      Platform.runLater(() -> data.getNode().setOpacity(0.0));
      setPopupOnDataHover(data, "Media");
    }
    return average;
  }

  private XYChart.Series<String, Number> getOrdersLine(final List<Integer> ordersData) {
    XYChart.Series<String, Number> ordersLine = new XYChart.Series<>();
    for (int i = 0; i < ordersData.size(); i++) {
      System.err.println("in: " + i + " metto " + ordersData.get(i));
      XYChart.Data<String, Number> dataPoint =
          new XYChart.Data<>(String.valueOf(i + 1), ordersData.get(i));
      ordersLine.getData().add(dataPoint);
      setPopupOnDataHover(dataPoint, "Giorno " + String.valueOf(i + 1));
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

  private Float getAverage(final List<? extends Number> listM) {
    Float sum = 0f;
    for (Number value : listM) {
      sum += value.floatValue();
    }
    return sum / listM.size();
  }
}
