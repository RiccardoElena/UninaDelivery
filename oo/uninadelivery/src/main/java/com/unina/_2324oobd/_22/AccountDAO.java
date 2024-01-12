package com.unina._2324oobd._22;

import java.sql.SQLException;

public interface AccountDAO {
  /**
   * @param email email
   * @return ciao
   */
  Account getAccountByEmail(String email) throws SQLException;
}
