package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.boundary.BasePageController;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class BaseControl {

  /** Transition duration. */
  private static final double FADE_OUT_DURATION = 0.2;

  /** Width of the eye icons. */
  static final int ICON_WIDTH = 30;

  /** Height of the eye icons. */
  static final int ICON_HEIGHT = 30;

  /** Width of the window. */
  private double width;

  /** Height of the window. */
  private double height;

  /** Name of the page. */
  private String fileName;

  /** Application stage. */
  private Stage stage;

  /** Hook method for page launch. */
  protected abstract void addSceneSettings();

  /**
   * Template method for page launch.
   *
   * @param s the stage to set the scene on
   * @throws Exception if the scene cannot be set
   */
  protected void setScene(final Stage s) throws Exception {
    setStage(s);
    addSceneSettings();

    launchScene();
  }

  private <T extends BasePageController<BaseControl>> void launchScene() throws Exception {
    try {
      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/FXML/" + getFileName() + ".fxml"));
      Parent root = loader.load();
      T pageController = loader.getController();

      setupStage();

      pageController.init(this);

      getStage().setScene(setupScene(root));
      getStage().show();
    } catch (IllegalStateException e) {
      e.printStackTrace();
      System.err.println(
          "Errore nel caricamento della pagina. Assicurarsi di aver specificato il valore corretto"
              + " per il campo fileName del controllore.");
      System.err.println("fileName attuale: " + fileName);
    }
  }

  private void setupStage() {
    stage.setWidth(width);
    stage.setHeight(height);
  }

  private Scene setupScene(final Parent root) {
    Scene scene = new Scene(root, width, height);
    try {
      scene
          .getStylesheets()
          .add(LoginControl.class.getResource("/style/" + fileName + ".css").toExternalForm());
    } catch (IllegalStateException e) {
      e.printStackTrace();
      System.err.println(
          "Errore nel caricamento dello Stile. Assicurarsi di aver specificato il valore corretto"
              + " per il campo fileName del controllore.");
      System.err.println("fileName attuale: " + fileName);
    }
    return scene;
  }

  /**
   * Get the FXML file path.
   *
   * @return the FXML file path
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Set the FXML file path.
   *
   * @param name the FXML file path
   */
  public void setFileName(final String name) {
    fileName = name;
  }

  /**
   * Get the width of the window.
   *
   * @return the width of the window
   */
  public double getWidth() {
    return width;
  }

  /**
   * Set the width of the window.
   *
   * @param w the width of the window
   */
  public void setWidth(final double w) {
    width = w;
  }

  /**
   * Get the height of the window.
   *
   * @return the height of the window
   */
  public double getHeight() {
    return height;
  }

  /**
   * Set the height of the window.
   *
   * @param h the height of the window
   */
  public void setHeight(final double h) {
    height = h;
  }

  /**
   * Set window sizes.
   *
   * @param h the height of the window
   * @param w the width of the window
   */
  public void setSizes(final double w, final double h) {
    setHeight(h);
    setWidth(w);
  }

  /**
   * Show an alert.
   *
   * @param alertType the type of the alert
   * @param title the title of the alert
   * @param header the header of the alert
   * @param content the content of the alert
   * @param buttons the buttons of the alert
   * @return the button pressed
   */
  protected Optional<ButtonType> showAlert(
      final Alert.AlertType alertType,
      final String title,
      final String header,
      final String content,
      final ButtonType... buttons) {
    Alert alert = new Alert(alertType, content, buttons);
    alert
        .getDialogPane()
        .getStylesheets()
        .add(getClass().getResource("/style/components/Alerts.css").toExternalForm());
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);

    if (alertType == Alert.AlertType.NONE) {
      DialogPane dialogPane = alert.getDialogPane();
      dialogPane.getScene().getWindow().setOnCloseRequest(event -> event.consume());
    }
    return alert.showAndWait();
  }

  /** Quit the appication. */
  public void exit() {

    Optional<ButtonType> result =
        showAlert(
            Alert.AlertType.CONFIRMATION,
            "Conferma",
            "Conferma chiusura",
            "Sei sicuro di voler uscire dall'applicazione?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      // Add fade out transition before closing the stage
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
    stage = currStage;
  }

  /**
   * Display an internal error specific modal window.
   *
   * @param e the exception to show
   */
  protected void showInternalError(final Exception e) {
    e.printStackTrace();
    showAlert(
        Alert.AlertType.ERROR,
        "Errore",
        "Errore inaspettato.",
        "Si è verifacto un errore interno inatteso, si prega di riprovare o riavviare"
            + " l'applicazione.");
  }
}
