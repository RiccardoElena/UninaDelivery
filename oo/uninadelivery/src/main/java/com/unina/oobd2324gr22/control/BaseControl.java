package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.boundary.BasePageController;
import com.unina.oobd2324gr22.utils.StaticResourceLoadingException;
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
import javafx.util.Duration;

public abstract class BaseControl {

  /** Transition duration. */
  private static final double FADE_OUT_DURATION = 0.2;

  /** Width of the eye icons. */
  static final int ICON_WIDTH = 30;

  /** Height of the eye icons. */
  static final int ICON_HEIGHT = 30;

  /** Name of the page related FXML and CSS files. */
  protected String fileName;

  protected BaseControl(final String defaultFileName) {
    fileName = defaultFileName;
  }

  /**
   * Set the given Scene.
   *
   * @param scene the scene to set
   */
  protected void setScene(final String newFileName) throws Exception {
    fileName = newFileName;
    setScene();
  }

  /**
   * Set the default scene.
   *
   * @throws Exception if the scene cannot be set
   */
  protected void setScene() throws Exception {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/" + fileName + ".fxml"));

      Scene scene = setupScene(loader.load());
      loadController(loader);

      App.switchScene(getWidth(), getHeight(), scene);
    } catch (IllegalStateException e) {
      showInternalError(e);
      System.err.println("fileName attuale: " + fileName);
    }
  }

  private <T extends BasePageController<BaseControl>> void loadController(final FXMLLoader loader) {
    T pageController = loader.getController();

    pageController.init(this);
  }

  private Scene setupScene(final Parent root) throws StaticResourceLoadingException {
    Scene scene = new Scene(root, getWidth(), getHeight());

    scene
        .getStylesheets()
        .add(BaseControl.class.getResource("/style/" + fileName + ".css").toExternalForm());

    return scene;
  }

  /**
   * Get the width of the window.
   *
   * @return the width of the window
   */
  public double getWidth() {
    return this instanceof LoginControl
        ? LoginControl.WIDTH
        : Math.max(NonLoginControl.WIDTH, App.getStage().getWidth());
  }

  /**
   * Get the height of the window.
   *
   * @return the height of the window
   */
  public double getHeight() {
    return this instanceof LoginControl
        ? LoginControl.HEIGHT
        : Math.max(NonLoginControl.HEIGHT, App.getStage().getHeight());
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
      fadeOutTransition(0.0, e -> App.getStage().close());
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
    final KeyValue kv = new KeyValue(App.getStage().opacityProperty(), toValue);
    final KeyFrame kf = new KeyFrame(duration, kv);
    final Timeline timeline = new Timeline(kf);
    timeline.setOnFinished(onFinish);

    timeline.play();
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
