package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.utils.LoggedOperator;

/**
 * NonLoginControl is the abstract class that represents the control of all the functionalities but
 * the login.
 */
public abstract class NonLoginControl extends BaseControl {

  /** Width of the window. */
  protected static final double WIDTH = 1080;

  /** Height of the window. */
  protected static final double HEIGHT = 720;

  /** Add page related scene settings. */
  @Override
  protected void addSceneSettings() {
    setSizes(Math.max(WIDTH, getStage().getWidth()), Math.max(HEIGHT, getStage().getHeight()));
  }

  /** Minimize the window. */
  public void minimize() {
    fadeOutTransition(
        0.0,
        e -> {
          getStage().setIconified(true);
          getStage().setOpacity(1.0);
        });
  }

  /** Go to the Dashboard page. */
  public void returnToHomePage() {
    DashboardControl dashboardControl = DashboardControl.getInstance();
    try {
      dashboardControl.setScene(getStage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the logged operator.
   *
   * @return the logged operator
   */
  public Operator getLoggedOperator() {
    return LoggedOperator.getInstance();
  }
}
