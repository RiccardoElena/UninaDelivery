package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.GraphControl;
import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.utils.NumToStringFormatter;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.Duration;

public class GraphPageController extends NonLoginPageController<GraphControl> {

  /** Starting year. */
  private static final int STARTING_YEAR = 2000;

  /** Label for the money spent by the most spending account. */
  @FXML private Label moneySpent;

  /** Label for the total number of orders of the most ordering accunt. */
  @FXML private Label orderNumber;

  /** Label for email of the most spending account. */
  @FXML private Label highestCostEmail;

  /** Label for name and surname of the most spending account. */
  @FXML private Label highestCostNameSurname;

  /** Label for the of highest cost order cost. */
  @FXML private Label highestCostOrderCost;

  /** Label for the highest cost order id. */
  @FXML private Label highestCostOrderId;

  /** Label for the highest cost order account. */
  @FXML private Label highestCostOrderIdAccount;

  /** Label for the highest cost order product. */
  @FXML private Label highestCostOrderProduct;

  /** Label for email of the most ordering account. */
  @FXML private Label highestNumberEmail;

  /** Label for number name and surname of the most ordering account. */
  @FXML private Label highestNumberNameSurname;

  /** Label for the highest number of product order account. */
  @FXML private Label highestProductNumberOrderAccount;

  /** Label for the highest number of product order cost. */
  @FXML private Label highestProductNumberOrderCost;

  /** Label for the highest number of product order id. */
  @FXML private Label highestProductNumberOrderId;

  /** Label for the highest number of product order product. */
  @FXML private Label highestProductNumberOrderProduct;

  /** Label for the lowest cost order account. */
  @FXML private Label lowestCostOrderAccount;

  /** Label for the lowest cost order cost. */
  @FXML private Label lowestCostOrderCost;

  /** Label for the lowest cost order id. */
  @FXML private Label lowestCostOrderId;

  /** Label for the lowest cost order product. */
  @FXML private Label lowestCostOrderProduct;

  /** Label for the lowest number or product order account. */
  @FXML private Label lowestProductNumberOrderAccount;

  /** Label for the lowest number of product order cost. */
  @FXML private Label lowestProductNumberOrderCost;

  /** Label for the lowest number of product order id. */
  @FXML private Label lowestProductNumberOrderId;

  /** Label for the lowest number of product order product. */
  @FXML private Label lowestProductNumberOrderProduct;

  /** ImageView of the most ordering account. */
  @FXML private ImageView mostOrderingPropic;

  /** ImageView of the most spending account. */
  @FXML private ImageView mostSpendingPropic;

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

  /** VBox containing the monthly report data. */
  @FXML private VBox monthlyReportData;

  /** ScrollPane of the page. */
  @FXML private ScrollPane scrollPane;

  /**
   * Initialize the page.
   *
   * @param control the Orders selection functionality control class
   */
  @Override
  protected final void initialize(final GraphControl control) {
    monthlyReportData.setVisible(false);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.addEventFilter(
        ScrollEvent.SCROLL,
        e -> {
          if (!monthlyReportData.isVisible()) {
            e.consume();
          }
        });
    initializeComboBoxes();
  }

  private void initializeComboBoxes() {
    initializeMonthComboBox();

    intializeYearComboBox();
  }

  private void initializeMonthComboBox() {
    monthComboBox.getItems().addAll(Month.values());

    // Set cell factory to display months in Italian
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.ITALIAN);
    monthComboBox.setCellFactory(column -> createMonthListCell(formatter));
    monthComboBox.setButtonCell(createMonthListCell(formatter));

    monthComboBox.setOnAction(e -> displayMonthlyReportData());
  }

  private ListCell<Month> createMonthListCell(final DateTimeFormatter formatter) {
    return new ListCell<Month>() {
      @Override
      protected void updateItem(final Month item, final boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          String month = setInitialAsCapitalLetter(formatter.format(item));
          setText(month);
        }
      }
    };
  }

  private void intializeYearComboBox() {
    for (int i = Year.now().getValue(); i >= STARTING_YEAR; i--) {
      yearComboBox.getItems().add(Year.of(i));
    }
    yearComboBox.setOnAction(e -> this.displayMonthlyReportData());
  }

  private String setInitialAsCapitalLetter(final String string) {
    return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
  }

  private void displayMonthlyReportData() {
    if (monthComboBox.getValue() == null || yearComboBox.getValue() == null) {
      return;
    }
    try {
      setGraphData();
    } catch (Exception e) {
      chart.getData().clear();
      monthlyReportData.setVisible(false);
      return;
    }
    System.err.println("hello");
    displayOrderData(
        getControl()::getMostExpensiveOrderData,
        highestCostOrderId,
        highestCostOrderIdAccount,
        highestCostOrderProduct,
        highestCostOrderCost);
    displayOrderData(
        getControl()::getHighestProductOrderData,
        highestProductNumberOrderId,
        highestProductNumberOrderAccount,
        highestProductNumberOrderProduct,
        highestProductNumberOrderCost);
    displayOrderData(
        getControl()::getLeastExpensiveOrderData,
        lowestCostOrderId,
        lowestCostOrderAccount,
        lowestCostOrderProduct,
        lowestCostOrderCost);
    displayOrderData(
        getControl()::getLowestProductOrderData,
        lowestProductNumberOrderId,
        lowestProductNumberOrderAccount,
        lowestProductNumberOrderProduct,
        lowestProductNumberOrderCost);
    monthlyReportData.setVisible(true);
    displayMostOrderingAccountData();
    displayMostSpendingAccountData();
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
  }

  private void displayOrderData(
      final BiFunction<Month, Year, Order> getOrderData,
      final Label orderIdLabel,
      final Label orderAccountLabel,
      final Label orderProductLabel,
      final Label orderCostLabel) {
    Order order = getOrderData.apply(monthComboBox.getValue(), yearComboBox.getValue());
    if (order != null) {
      orderIdLabel.setText("Ordine N." + order.getOrderId());
      orderAccountLabel.setText("Effettuato da: " + order.getAccount().getEmail());
      orderProductLabel.setText(
          "Contenente: "
              + order.getQuantity()
              + " "
              + order.getProduct().getName()
              + " di "
              + order.getProduct().getSupplier());
      orderCostLabel.setText(
          "Al costo di: " + NumToStringFormatter.trunkDecimal(order.getPrice(), 2) + "€");
    }
  }

  private void displayMostOrderingAccountData() {
    Account account =
        getControl().getMostOrderingAccountData(monthComboBox.getValue(), yearComboBox.getValue());

    if (account != null) {
      setRoundImageViewImagesAndPosition(account.getPropic(), mostOrderingPropic);
      highestNumberEmail.setText(account.getEmail());
      highestNumberNameSurname.setText(account.getName() + " " + account.getSurname());
      orderNumber.setText("N° Ordini: " + account.getOrders().size());
    }
  }

  private void displayMostSpendingAccountData() {
    Account account =
        getControl().getMostSpendingAccountData(monthComboBox.getValue(), yearComboBox.getValue());
    if (account != null) {
      setRoundImageViewImagesAndPosition(account.getPropic(), mostSpendingPropic);
      highestCostEmail.setText(account.getEmail());
      highestCostNameSurname.setText(account.getName() + " " + account.getSurname());
      moneySpent.setText(
          "Speso: " + NumToStringFormatter.trunkDecimal(account.getAmountSpent(), 2) + "€");
    }
  }

  /** Set the graph data. */
  private void setGraphData() {
    List<Integer> ordersData;

    ordersData = getControl().getGraphData(monthComboBox.getValue(), yearComboBox.getValue());

    try {

      chart.getData().clear();

      XYChart.Series<String, Number> avarageLine = getAverageLine(ordersData);
      avarageLine.setName("Media: " + NumToStringFormatter.trunkDecimal(getAverage(ordersData), 2));
      chart.getData().add(avarageLine);

      XYChart.Series<String, Number> ordersLine = getOrdersLine(ordersData);
      ordersLine.setName("N° Ordini per Giorno");
      chart.getData().add(ordersLine);

      removeLineSymbol(avarageLine);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private XYChart.Series<String, Number> getAverageLine(final List<Integer> ordersData) {
    XYChart.Series<String, Number> average = new XYChart.Series<>();
    for (int i = 0; i < ordersData.size(); i++) {

      XYChart.Data<String, Number> data =
          new XYChart.Data<>(String.valueOf((i + 1)), getAverage(ordersData));
      average.getData().add(data);
      setPopupOnDataHover(data, "Media");
    }
    return average;
  }

  private XYChart.Series<String, Number> getOrdersLine(final List<Integer> ordersData) {
    XYChart.Series<String, Number> ordersLine = new XYChart.Series<>();
    for (int i = 0; i < ordersData.size(); i++) {

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

  private void removeLineSymbol(final XYChart.Series<String, Number> line) {
    for (XYChart.Data<String, Number> data : line.getData()) {
      // this node is StackPane
      StackPane stackPane = (StackPane) data.getNode();
      stackPane.setVisible(false);
    }
  }
}
