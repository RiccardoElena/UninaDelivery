package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.NonLoginControl;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class NonLoginPageController<T extends NonLoginControl>
    extends BasePageController<T> {

  /** Size of the resize zone. */
  private static final int RESIZE_MARGIN = 5;

  /** Min Width of the window. */
  private static final int MIN_WIDTH = 1080;

  /** Min Height of the window. */
  private static final int MIN_HEIGHT = 720;

  /** Button to minimize the application. */
  @FXML private Button minimizeButton;

  /** Button to resize the application. */
  @FXML private Button resizeButton;

  /** Home button. */
  @FXML private Button homeButton;

  /** Back button. */
  @FXML private Button backButton;

  /**
   * Implementation of the hook method for page initialization.
   *
   * @param c of the page
   */
  @Override
  public void init(final T c) {
    super.init(c);
    setResizable();
    setNavigationButtons();
  }

  /** Make the window resizable. */
  protected void setResizable() {
    Platform.runLater(
        () -> {
          Stage stage = getControl().getStage();
          Scene scene = stage.getScene();
          scene.setCursor(Cursor.SE_RESIZE);
          scene.setOnMouseDragged(this::handleSceneResizing);
          scene.setOnMouseMoved(this::handleCursorSwitch);
        });
  }

  private void handleSceneResizing(final MouseEvent event) {
    Stage stage = getControl().getStage();
    double x = event.getX();
    double y = event.getY();
    double screenWidth = event.getScreenX();
    double newWidth = Math.max(x, MIN_WIDTH);
    double newHeight = Math.max(y, MIN_HEIGHT);

    if (x > stage.getWidth() - RESIZE_MARGIN) {
      stage.setWidth(newWidth);
    }
    if (y > stage.getHeight() - RESIZE_MARGIN) {
      stage.setHeight(newHeight);
    }

    if (x < RESIZE_MARGIN) {
      newWidth = stage.getX() + stage.getWidth() - screenWidth;
      newWidth = Math.max(newWidth, MIN_WIDTH);
      stage.setWidth(newWidth);
      if (newWidth > MIN_WIDTH) {
        stage.setX(screenWidth);
      }
    }
  }

  private void handleCursorSwitch(final MouseEvent event) {
    Stage stage = getControl().getStage();
    Scene scene = stage.getScene();
    double x = event.getX();
    double y = event.getY();
    double width = stage.getWidth();
    double height = stage.getHeight();

    boolean nearEdge = x > width - RESIZE_MARGIN || y > height - RESIZE_MARGIN || x < RESIZE_MARGIN;
    scene.setCursor(nearEdge ? Cursor.SE_RESIZE : Cursor.DEFAULT);
  }

  /** Set navigation buttons base icon and hover effect. */
  protected void setNavigationButtons() {
    if (homeButton != null) {
      setButtonIconAndHoverEffect(
          homeButton, "/images/nonLoginPage/Home.png", "/images/nonLoginPage/HomeHover.png");
    }
    if (backButton != null) {
      setButtonIconAndHoverEffect(
          backButton,
          "/images/nonLoginPage/BackArrow.png",
          "/images/nonLoginPage/BackArrowHover.png");
    }
  }

  /**
   * Set button icon and hover effect.
   *
   * @param button the button
   * @param baseIconPath the base icon path
   * @param hoverIconPath the hover icon path
   */
  private void setButtonIconAndHoverEffect(
      final Button button, final String baseIconPath, final String hoverIconPath) {
    button.setGraphic(getIcon(baseIconPath));
    button.setOnMouseEntered(event -> button.setGraphic(getIcon(hoverIconPath)));
    button.setOnMouseExited(event -> button.setGraphic(getIcon(baseIconPath)));
  }

  /**
   * Button to minimize the window.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void minimizeButtonAction(final ActionEvent event) {
    getControl().minimize();
  }

  /**
   * Button to resize the window.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void resizeButtonAction(final ActionEvent event) {
    Stage stage = getControl().getStage();
    stage.setMaximized(!stage.isMaximized());
  }

  /**
   * Button to go back to the home page.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void homeButtonAction(final ActionEvent event) {
    try {
      getControl().returnToHomePage();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Set the table functions.
   *
   * @param <U> the type of the table
   * @param table the table to set the functions on
   */
  public <U> void setTableFunctionality(final TableView<U> table) {
    this.setRowDeselection(table);
    this.setColumnSize(table);
  }

  /**
   * Set the table functions when action buttons are needed.
   *
   * @param <U> the type of the table
   * @param table the table to set the functions on
   * @param columnTitle the action column title
   * @param buttonText the action button text
   * @param action the action to execute
   */
  public <U> void setTableFunctionality(
      final TableView<U> table,
      final String columnTitle,
      final String buttonText,
      final Consumer<U> action) {
    this.addButtonToTable(table, columnTitle, buttonText, action);
    this.setTableFunctionality(table);
  }

  private <U> void setRowDeselection(final TableView<U> table) {
    table.setRowFactory(
        tableView -> {
          final TableRow<U> row = new TableRow<>();
          row.addEventFilter(
              MouseEvent.MOUSE_PRESSED,
              event -> {
                final int index = row.getIndex();
                if (index >= 0
                    && index < table.getItems().size()
                    && table.getSelectionModel().isSelected(index)) {
                  table.getSelectionModel().clearSelection();
                  event.consume();
                }
              });
          return row;
        });
  }

  private <U> void setColumnSize(final TableView<U> table) {
    for (TableColumn<U, ?> column : table.getColumns()) {
      column.prefWidthProperty().bind(table.widthProperty().divide(table.getColumns().size()));
    }
  }

  private <U> void addButtonToTable(
      final TableView<U> table,
      final String columnTitle,
      final String buttonText,
      final Consumer<U> action) {

    TableColumn<U, Void> actionColumn = new TableColumn<>(columnTitle);

    actionColumn.setCellFactory(
        param -> {
          final TableCell<U, Void> cell =
              new TableCell<>() {
                private final Button btn = new Button(buttonText);

                {
                  btn.setOnAction(
                      event -> {
                        U item = getTableView().getItems().get(getIndex());
                        action.accept(item);
                      });
                }

                @Override
                public void updateItem(final Void item, final boolean empty) {
                  super.updateItem(item, empty);
                  setGraphic(empty ? null : btn);
                }
              };
          return cell;
        });

    table.getColumns().add(actionColumn);
  }
}
