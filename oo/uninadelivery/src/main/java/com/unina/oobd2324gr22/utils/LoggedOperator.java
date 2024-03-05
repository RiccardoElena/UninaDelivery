package com.unina.oobd2324gr22.utils;

import com.unina.oobd2324gr22.entity.DTO.Operator;

public final class LoggedOperator extends Operator {

  /** Singleton instance. */
  private static LoggedOperator istance;

  private LoggedOperator(final Operator operator) {
    super(operator.getAccount(), operator.getBusinessMail());
  }

  /**
   * Get the singleton instance.
   *
   * @param operator the operator to be logged
   * @return the singleton instance
   */
  public static LoggedOperator getInstance(final Operator operator) {
    if (istance == null) {
      istance = new LoggedOperator(operator);
    }
    return istance;
  }

  /**
   * Get the singleton instance.
   *
   * @return the singleton instance
   */
  public static LoggedOperator getInstance() {
    return istance;
  }

  /** Log out the operator. */
  public void logout() {
    istance = null;
  }
}
