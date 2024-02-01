package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DTO.Operator;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class NonLoginControl extends BaseControl {

  /** Size of the resize zone. */
  private static final int RESIZE_MARGIN = 20;

  /** Width of the window. */
  protected static final int WIDTH = 1080;

  /** Height of the window. */
  protected static final int HEIGHT = 720;

  /** Logged in operator. */
  private Operator loggedOperator;

  /** Minimize the window. */
  public void minimize() {
    fadeOutTransition(
        0.0,
        e -> {
          getStage().setIconified(true);
          getStage().setOpacity(1.0);
        });
  }

  /**
   * Make the window resizable.
   *
   * @param primaryStage the stage to make resizable
   */
  public void setResizable(final Stage primaryStage) {
    Scene scene = primaryStage.getScene();
    scene.setOnMouseDragged(
        event -> {
          // Only resize if the mouse is near the edge of the stage
          if (event.getX() > primaryStage.getWidth() - RESIZE_MARGIN) {
            double newWidth = event.getX();
            newWidth = Math.max(newWidth, WIDTH); // MIN_WIDTH is the minimum width
            primaryStage.setWidth(newWidth);
          }
          if (event.getY() > primaryStage.getHeight() - RESIZE_MARGIN) {
            double newHeight = event.getY();
            newHeight = Math.max(newHeight, HEIGHT); // MIN_HEIGHT is the minimum height
            primaryStage.setHeight(newHeight);
          }
          // Resize from the left (width and x position)
          if (event.getX() < RESIZE_MARGIN) {
            double newWidth = primaryStage.getX() + primaryStage.getWidth() - event.getScreenX();
            newWidth = Math.max(newWidth, WIDTH);
            primaryStage.setWidth(newWidth);
            if (newWidth > WIDTH) {
              primaryStage.setX(event.getScreenX());
            }
          }
        });
  }

    /**
   * Get Logged in operator.
   *
   * @return the logged in operator
   */
  public Operator getLoggedOperator() {

    return loggedOperator;
  }

  /**
   * Set Logged in operator.
   *
   * @param op the logged in operator
   */
  public void setLoggedOperator(final Operator op) {
    this.loggedOperator = op;
  }
}
