package com.unina.oobd2324gr22;

import java.sql.SQLException;

public interface AccountDAO {
  /**
   * @param email email
   * @return ciao
   */
  Account getAccountByEmail(String email) throws SQLException;
}
