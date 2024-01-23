package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Product;

public class ProductDAOPostgre implements ProductDAO {

    /**
    * Default constructor.
    */
    public ProductDAOPostgre() {
    }

    /**
    * @param name email
    * @param supplier supplier
    * @return ciao
    */
    @Override
    public Product getProductByNameAndSupplier(final String name,
                                                final String supplier) {
      return null;
    }
}
