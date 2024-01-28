package com.unina.oobd2324gr22;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

  /**
   * Width of the window.
   */
  static final int WIDTH = 600;

  /**
   * Height of the window.
   */
  static final int HEIGHT = 400;

  @Override
  public final void start(final Stage primaryStage) throws Exception {
    primaryStage.initStyle(StageStyle.TRANSPARENT);
    primaryStage.setTitle("Demo JavaFX");

    Parent root = FXMLLoader.load(getClass().getResource("demo.fxml"));
    Scene scene = new Scene(root, WIDTH, HEIGHT);

    Node topPane = root.lookup("#topPane");

    // Variabili per memorizzare la posizione del mouse durante il drag
    final double[] xOffset = new double[1];
    final double[] yOffset = new double[1];

    // Evento pressione mouse sulla barra del titolo
    topPane.setOnMousePressed(event -> {
      xOffset[0] = event.getSceneX();
      yOffset[0] = event.getSceneY();
    });

    // Evento trascinamento mouse sulla barra del titolo
    topPane.setOnMouseDragged(event -> {
      primaryStage.setX(event.getScreenX() - xOffset[0]);
      primaryStage.setY(event.getScreenY() - yOffset[0]);
    });

    primaryStage.setScene(scene);
    primaryStage.show();
  }


  /**
   * Main method. Launches the application.
   *
   * @param args command line arguments
   */
  public static void main(final String[] args) {
    launch(args);
    System.out.println("Hello World!");
  }
}
