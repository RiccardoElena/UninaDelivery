package demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.initStyle(StageStyle.TRANSPARENT);
    primaryStage.setTitle("Demo JavaFX");
    
    Parent root = FXMLLoader.load(getClass().getResource("demo.fxml"));
    Scene scene = new Scene(root, 600, 400);

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


  public static void main(String[] args) {
    launch(args);
    System.out.println("Hello World!");
  }
}