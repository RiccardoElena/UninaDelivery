package com.unina.oobd2324gr22.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.StackPane;

public final class LoadingScreenUtil {

  private LoadingScreenUtil() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Run a background task showing a loading screen.
   *
   * @param <T> the type of the result of the background task
   * @param loadingPane the progress indicator to show
   * @param backgroundTask the background task to run
   * @param onFinish the consumer to run when the background task is finished
   */
  public static <T> void loading(
      final StackPane loadingPane, final Supplier<T> backgroundTask, final Consumer<T> onFinish) {
    Platform.runLater(() -> loadingPane.setVisible(true));

    Task<T> task =
        new Task<>() {
          @Override
          protected T call() throws Exception {
            return backgroundTask.get();
          }
        };

    task.setOnSucceeded(
        e ->
            Platform.runLater(
                () -> {
                  loadingPane.setVisible(false);
                  if (onFinish != null) {
                    onFinish.accept(task.getValue());
                  }
                }));

    task.setOnFailed(
        e ->
            Platform.runLater(
                () -> {
                  loadingPane.setVisible(false);
                }));

    new Thread(task).start();
  }
}
