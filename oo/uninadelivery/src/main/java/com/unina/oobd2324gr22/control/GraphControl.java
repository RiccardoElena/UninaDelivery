package com.unina.oobd2324gr22.control;

import java.time.LocalDate;
import java.util.ArrayList;

public class GraphControl extends NonLoginControl {

  /** Add page related scene settings. */
  @Override
  protected void addSceneSettings() {
    super.addSceneSettings();
    setFileName("Graph");
  }

  /**
   * TEST METHOD TO BE CHANGED WITH DAO ONE. Get the graph data.
   *
   * @return the graph data
   */
  public ArrayList<Integer> getGraphData() {
    ArrayList<Integer> listM = new ArrayList<>();
    for (int i = 1; i <= LocalDate.now().lengthOfMonth(); i++) {
      int value = (int) (Math.random() * LocalDate.now().getDayOfMonth() * 2) + 1;
      if (i <= LocalDate.now().getDayOfMonth()) {
        listM.add(value);
      } else {
        listM.add((int) value / 2);
      }
    }

    return listM;
  }
}
