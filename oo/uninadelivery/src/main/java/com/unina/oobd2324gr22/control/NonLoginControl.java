package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DTO.Operator;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NonLoginControl extends BaseControl {

  /** Size of the resize zone. */
  private static final int RESIZE_MARGIN = 5;

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
    scene.setCursor(Cursor.SE_RESIZE);
    scene.setOnMouseMoved(
        event -> {
          double test2 = primaryStage.getHeight() - RESIZE_MARGIN;
          double test1 = primaryStage.getWidth() - RESIZE_MARGIN;
          // Change cursor icon if we're near the edge of the stage
          if (event.getX() > primaryStage.getWidth() - RESIZE_MARGIN
              || event.getY() > primaryStage.getHeight() - RESIZE_MARGIN
              || event.getX() < RESIZE_MARGIN) {
            System.err.println(event.getX() + " " + event.getY() + " " + test1 + " " + test2);
            scene.setCursor(Cursor.SE_RESIZE);
          } else {
            scene.setCursor(Cursor.DEFAULT);
          }
        });
    scene.setCursor(Cursor.SE_RESIZE);
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

  /**
   * Set navigation buttons base icon and hover effect.
   *
   * @param homeButton the home button
   * @param backButton the back button
   */
  public void setNavigationButtons(final Button homeButton, final Button backButton) {
    homeButton.setGraphic(getIcon("/images/nonLoginPage/Home.png"));
    backButton.setGraphic(getIcon("/images/nonLoginPage/BackArrow.png"));
    homeButton.setOnMouseEntered(
        event -> homeButton.setGraphic(getIcon("/images/nonLoginPage/HomeHover.png")));
    homeButton.setOnMouseExited(
        event -> homeButton.setGraphic(getIcon("/images/nonLoginPage/Home.png")));
    backButton.setOnMouseEntered(
        event -> backButton.setGraphic(getIcon("/images/nonLoginPage/BackArrowHover.png")));
    backButton.setOnMouseExited(
        event -> backButton.setGraphic(getIcon("/images/nonLoginPage/BackArrow.png")));
  }
}
