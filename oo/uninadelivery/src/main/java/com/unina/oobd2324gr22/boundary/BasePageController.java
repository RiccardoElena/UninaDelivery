package com.unina.oobd2324gr22.boundary;

import com.unina.oobd2324gr22.control.App;
import com.unina.oobd2324gr22.control.BaseControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public abstract class BasePageController<T extends BaseControl> {

  /** Control of the functionality of the page. */
  private T control;

  /** Width of the eye icons. */
  private static final int ICON_WIDTH = 30;

  /** Height of the eye icons. */
  private static final int ICON_HEIGHT = 30;

  /** Node of the title bar. */
  @FXML private Node titleBar;

  /** Button to exit the application. */
  @FXML private Button exitButton;

  /**
   * Hook method for page initialization.
   *
   * @param c of the page
   */
  protected abstract void initialize(T c);

  /**
   * Template method for page initialization.
   *
   * @param c
   */
  public void init(final T c) {
    setControl(c);
    initialize(c);

    setDraggable();
  }

  /**
   * Get the control.
   *
   * @return the control
   */
  public T getControl() {
    return control;
  }

  /**
   * Set the control.
   *
   * @param c the control to set
   */
  public void setControl(final T c) {
    control = c;
  }

  /** Make the window draggable. */
  protected void setDraggable() {
    Stage stage = App.getStage();
    final double[] xOffset = new double[1];
    final double[] yOffset = new double[1];

    // Evento pressione mouse sulla barra del titolo
    titleBar.setOnMousePressed(
        event -> {
          xOffset[0] = event.getSceneX();
          yOffset[0] = event.getSceneY();
        });

    // Evento trascinamento mouse sulla barra del titolo
    titleBar.setOnMouseDragged(
        event -> {
          if (stage.isMaximized()) {
            stage.setMaximized(false);
          }
          stage.setX(event.getScreenX() - xOffset[0]);
          stage.setY(event.getScreenY() - yOffset[0]);
        });
  }

  /**
   * Get an icon.
   *
   * @param path the path of the icon
   * @return the icon
   */
  protected ImageView getIcon(final String path) {
    ImageView icon = new ImageView(new Image(path));
    icon.setFitWidth(ICON_WIDTH); // Set the width
    icon.setFitHeight(ICON_HEIGHT);
    return icon;
  }

  /**
   * Button to close the window.
   *
   * @param event the event that triggered the action
   */
  @FXML
  void exitButtonAction(final ActionEvent event) {
    getControl().exit();
  }
}
