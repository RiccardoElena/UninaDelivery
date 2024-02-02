package com.unina.oobd2324gr22.utils;

import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SwitchButton extends Control {

  /** Boolean value of the button. */
  private final BooleanProperty switchedOn = new SimpleBooleanProperty(false);

  /** Transition of the button. */
  private final TranslateTransition transition;

  /** Thumb of the button. */
  private final Rectangle switchThumb;

  /** Pane of the button. */
  private final StackPane switchPane;

  /** Background of the button. */
  private final Rectangle switchBackground;

  /** Width of the button. */
  private final int bgWidth = 80;

  /** Height of the button. */
  private final int bgHeight = 30;

  /** Width of the thumb. */
  private final int thumbWidth = 40;

  /** Height of the thumb. */
  private final int thumbHeight = 20;

  /** Animation time. */
  private final double animationTime = 0.25;

  /** Create a new SwitchButton. */
  public SwitchButton() {
    // Imposta il layout
    switchBackground = new Rectangle(bgWidth, bgHeight);
    switchBackground.getStyleClass().add("switch-pane");

    switchThumb = new Rectangle(thumbWidth, thumbHeight);
    switchThumb.getStyleClass().add("switch-thumb");

    switchPane = new StackPane();
    switchPane.getChildren().addAll(switchBackground, switchThumb);
    getChildren().add(switchPane);

    // Configura l'animazione
    transition = new TranslateTransition(Duration.seconds(animationTime), switchThumb);
    transition.setToX(0);

    // Aggiunge un listener alla proprietà switchedOn
    switchedOn.addListener(
        (obs, oldState, newState) -> {
          if (newState) {
            transition.setToX(switchBackground.getWidth() - switchThumb.getWidth());
            switchBackground.getStyleClass().add("switch-pane-on");
            switchBackground.getStyleClass().remove("switch-pane");
          } else {
            transition.setToX(0);
            switchBackground.getStyleClass().add("switch-pane");
            switchBackground.getStyleClass().remove("switch-pane-on");
          }
          transition.play();
        });

    // Imposta un gestore clic per cambiare lo stato
    setOnMouseClicked(event -> switchedOn.set(!switchedOn.get()));
  }

  /**
   * On/Off property of the button.
   *
   * @return on/off property
   */
  public BooleanProperty switchedOnProperty() {
    return switchedOn;
  }
}
