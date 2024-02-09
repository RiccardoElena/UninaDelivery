package com.unina.oobd2324gr22.utils;

import com.unina.oobd2324gr22.control.LoginControl;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// TODO @zGenny: remember to add all the static resources in the resources folder
// OrderPage.fxml and several images are missing
public class App extends Application {

  /** Width of the window. */
  static final int WIDTH = 600;

  /** Height of the window. */
  static final int HEIGHT = 400;

  /** Login functionality control class. */
  private LoginControl loginControl = new LoginControl();

  @Override
  public final void start(final Stage primaryStage) throws Exception {
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setTitle("UninaDelivery");
    primaryStage.getIcons().add(new Image(App.class
      .getResourceAsStream("/images/UninaDelivery_logo.png"))
    );

    loginControl.setScene(primaryStage);
  }

  /**
   * Main method. Launches the application.
   *
   * @param args command line arguments
   */
  public static void launchApp(final String[] args) {
    System.out.println(System.getProperty("user.dir"));
    launch(args);
    System.out.println("Hello World!");
  }
}
