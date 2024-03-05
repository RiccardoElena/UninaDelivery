package com.unina.oobd2324gr22.control;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Application luncher.
 *
 * <p>Launches the application and sets the first scene.
 */
public final class App extends Application {

  /** Width of the window. */
  static final int WIDTH = 600;

  /** Height of the window. */
  static final int HEIGHT = 400;

  /** App stage. */
  private static Stage stage;

  @Override
  public void start(final Stage primaryStage) throws Exception {
    LoginControl loginControl = LoginControl.getInstance();
    stage = primaryStage;
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setTitle("UninaDelivery");
    primaryStage
        .getIcons()
        .add(new Image(App.class.getResourceAsStream("/images/UninaDelivery_logo.png")));

    loginControl.setScene();
  }

  /**
   * Launches the application.
   *
   * @param args command line arguments
   */
  public static void launchApp(final String[] args) {
    launch(args);
  }

  /**
   * Get the stage.
   *
   * @return the stage
   */
  public static Stage getStage() {
    return stage;
  }

  /**
   * Set stage Width and Height.
   *
   * @param width the width
   * @param height the height
   */
  public static void setStageSizes(final double width, final double height) {
    stage.setWidth(width);
    stage.setHeight(height);
  }
}
