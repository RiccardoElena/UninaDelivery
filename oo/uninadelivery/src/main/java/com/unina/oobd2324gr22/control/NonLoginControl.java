package com.unina.oobd2324gr22.control;

import javafx.stage.Stage;

/**
 * NonLoginControl is the abstract class that represents the control of all the functionalities but
 * the login.
 */
public abstract class NonLoginControl extends BaseControl {

  /** Width of the window. */
  protected static final double WIDTH = 1080;

  /** Height of the window. */
  protected static final double HEIGHT = 720;

  protected NonLoginControl(final String defaultPageName) {
    super(defaultPageName);
  }

  /** Resize the window. */
  public void resize() {
    Stage stage = App.getStage();
    stage.setMaximized(!stage.isMaximized());
  }

  /** Minimize the window. */
  public void minimize() {
    fadeOutTransition(
        0.0,
        e -> {
          App.getStage().setIconified(true);
          App.getStage().setOpacity(1.0);
        });
  }

  /** Go to the Dashboard page. */
  public void returnToHomePage() {
    DashboardControl dashboardControl = DashboardControl.getInstance();
    try {
      dashboardControl.setScene();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
