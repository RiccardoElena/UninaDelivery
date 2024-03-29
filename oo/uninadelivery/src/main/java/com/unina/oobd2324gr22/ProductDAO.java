package com.unina.oobd2324gr22;

import java.sql.SQLException;

public interface ProductDAO {

  /**
   * @param name name
   * @param supplier supplier
   * @return ciao
   */
  Product getProductByNameAndSupplier(String name, String supplier)
  throws SQLException;

}
