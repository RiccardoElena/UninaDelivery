package com.unina.oobd2324gr22;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// TODO @zGenny: remember to add all the static resources in the resources folder
// OrderPage.fxml and several images are missing
public class Main extends Application {

  /** Width of the window. */
  static final int WIDTH = 600;

  /** Height of the window. */
  static final int HEIGHT = 400;

  @Override
  public final void start(final Stage primaryStage) throws Exception {
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setTitle("Demo JavaFX");
    URL fxmlUrl = Main.class.getResource("/demo.fxml");
    System.out.println("Loading FXML from: " + fxmlUrl);

    Parent root = FXMLLoader.load(fxmlUrl);
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    scene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());
    scene.getStylesheets().forEach(System.out::println);
    

    Node topPane = root.lookup("#topPane");

    // Variabili per memorizzare la posizione del mouse durante il drag
    final double[] xOffset = new double[1];
    final double[] yOffset = new double[1];

    // Evento pressione mouse sulla barra del titolo
    topPane.setOnMousePressed(
        event -> {
          xOffset[0] = event.getSceneX();
          yOffset[0] = event.getSceneY();
        });

    // Evento trascinamento mouse sulla barra del titolo
    topPane.setOnMouseDragged(
        event -> {
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
    System.out.println(System.getProperty("user.dir"));
    launch(args);
    System.out.println("Hello World!");
  }
}
