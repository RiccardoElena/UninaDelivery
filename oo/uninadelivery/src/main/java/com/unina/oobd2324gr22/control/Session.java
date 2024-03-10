package com.unina.oobd2324gr22.control;

import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.utils.UtilityClassInstantiationException;

public final class Session {

  /** The logged operator. */
  private static Operator loggedOperator;

  /** The selected order. */
  private static Order selectedOrder;

  private Session() {
    throw new UtilityClassInstantiationException();
  }

  /**
   * Set the logged operator.
   *
   * @param operator the operator to be logged
   */
  static void loginOperator(final Operator operator) {
    Session.loggedOperator = operator;
  }

  /**
   * Set the selected order.
   *
   * @param order the order to be selected
   */
  static void selectOrder(final Order order) {
    Session.selectedOrder = order;
  }

  /**
   * Get the logged operator.
   *
   * @return the logged operator
   */
  static Operator getLoggedOperator() {
    return Session.loggedOperator;
  }

  /**
   * Get the selected order.
   *
   * @return the selected order
   */
  static Order getSelectedOrder() {
    return Session.selectedOrder;
  }

  /** Log out the operator. */
  static void logoutOperator() {
    Session.loggedOperator = null;
  }

  /** Unselect the order. */
  static void unselectOrder() {
    Session.selectedOrder = null;
  }
}
