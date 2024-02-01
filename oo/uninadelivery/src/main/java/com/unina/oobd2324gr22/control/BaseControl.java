package com.unina.oobd2324gr22.control;

import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class BaseControl {

  /** Transition duration. */
  private static final double FADE_OUT_DURATION = 0.2;

  /** Application stage. */
  private Stage stage;

  /**
   * Show an alert.
   *
   * @param alertType the type of the alert
   * @param title the title of the alert
   * @param header the header of the alert
   * @param content the content of the alert
   * @return the button pressed
   */
  protected Optional<ButtonType> showAlert(
      final Alert.AlertType alertType,
      final String title,
      final String header,
      final String content) {
    Alert alert = new Alert(alertType);
    alert
        .getDialogPane()
        .getStylesheets()
        .add(getClass().getResource("/style/components/Alerts.css").toExternalForm());
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert.showAndWait();
  }

  /** Quit the appication. */
  public void exit() {

    Optional<ButtonType> result =
        showAlert(
            Alert.AlertType.CONFIRMATION,
            "Conferma",
            "Conferma chiusura",
            "Sei sicuro di voler uscire?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      // Crea un effetto di fade out
      System.err.println(this.stage);
      System.err.println("wow3 " + this.getStage());
      fadeOutTransition(0.0, e -> stage.close());
    }
  }

  /**
   * Fade out transition.
   *
   * @param toValue
   * @param onFinish
   */
  protected void fadeOutTransition(final double toValue, final EventHandler<ActionEvent> onFinish) {
    final Duration duration = Duration.seconds(FADE_OUT_DURATION);
    final KeyValue kv = new KeyValue(stage.opacityProperty(), toValue);
    final KeyFrame kf = new KeyFrame(duration, kv);
    final Timeline timeline = new Timeline(kf);
    timeline.setOnFinished(onFinish);

    timeline.play();
  }

  /**
   * Make the window draggable.
   *
   * @param titleBar the title bar to make draggable
   */
  public void setDraggable(final Node titleBar) {

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
          stage.setX(event.getScreenX() - xOffset[0]);
          stage.setY(event.getScreenY() - yOffset[0]);
        });
  }

  /**
   * Get the application stage.
   *
   * @return the application stage
   */
  public Stage getStage() {
    return stage;
  }

  /**
   * Set the application stage.
   *
   * @param currStage the application stage
   */
  public void setStage(final Stage currStage) {
    System.err.println(getStage());
    this.stage = currStage;
    System.err.println(getStage());
    System.err.println("wow " + this.stage);
  }
}
