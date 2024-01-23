package com.unina.oobd2324gr22.entity.DAO;

import java.sql.SQLException;
import com.unina.oobd2324gr22.entity.DTO.Product;

public interface ProductDAO {

  /**
   * @param name name
   * @param supplier supplier
   * @return ciao
   */
  Product getProductByNameAndSupplier(String name, String supplier)
  throws SQLException;

}
