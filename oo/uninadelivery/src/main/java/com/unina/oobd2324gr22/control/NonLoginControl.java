package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DTO.Operator;
import javafx.stage.Stage;

public abstract class NonLoginControl extends BaseControl {

  /** Width of the window. */
  protected static final double WIDTH = 1080;

  /** Height of the window. */
  protected static final double HEIGHT = 720;

  /** Logged in operator. */
  private Operator loggedOperator;

  /**
   * Template method for page launch.
   *
   * @param s the stage to set the scene on
   * @param op the operator that is logged in
   * @throws Exception if the scene cannot be set
   */
  public void setScene(final Stage s, final Operator op) throws Exception {
    setLoggedOperator(op);
    super.setScene(s);
  }

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

  /**
   * Get Logged in operator.
   *
   * @return the logged in operator
   */
  public Operator getLoggedOperator() {

    return loggedOperator;
  }

  /**
   * Set Logged in operator.
   *
   * @param op the logged in operator
   */
  public void setLoggedOperator(final Operator op) {
    this.loggedOperator = op;
  }

  /** Go to the Dashboard page. */
  public void returnToHomePage() {
    DashboardControl dashboardControl = new DashboardControl();
    try {
      dashboardControl.setScene(this.getStage(), this.getLoggedOperator());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
