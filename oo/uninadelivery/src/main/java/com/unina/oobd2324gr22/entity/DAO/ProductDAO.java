package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Product;
import java.sql.SQLException;

public interface ProductDAO extends BasicDAO<Product> {

  /**
   * @param name name of the product
   * @param supplier supplier of the product
   * @return the product with the given name and supplier
   */
  Product getProductByNameAndSupplier(String name, String supplier) throws SQLException;
}
