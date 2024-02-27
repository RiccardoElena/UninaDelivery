package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Product;
import com.unina.oobd2324gr22.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class implements the ProductDAO interface and provides the PostgreSQL implementation for the
 * shipment data access operations.
 */
public class ProductDAOPostgre implements ProductDAO {

  private Product populateProductFromResultSet(final ResultSet rs) throws SQLException {
    return new Product(
        rs.getString("category"),
        rs.getString("name"),
        rs.getString("supplier"),
        rs.getString("description"),
        rs.getDouble("packagesizeliters"),
        rs.getBoolean("isfragile"),
        rs.getFloat("price"));
  }

  /**
   * PostgreSQL implementation of the method insert.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int insert(final Product product) throws SQLException {
    throw new UnsupportedOperationException();
  }

  /**
   * PostgreSQL implementation of the method getProductByNameAndSupplier.<br>
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Product getProductByNameAndSupplier(final String name, final String supplier)
      throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    Product product = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;

    psSelect = con.prepareStatement("SELECT * FROM product WHERE name = ? AND supplier = ?");
    psSelect.setString(1, name);
    psSelect.setString(2, supplier);
    rs = psSelect.executeQuery();
    while (rs.next()) {
      product = populateProductFromResultSet(rs);
    }

    if (rs != null) {
      rs.close();
    }
    if (psSelect != null) {
      psSelect.close();
    }
    if (con != null) {
      con.close();
    }

    return product;
  }

  /**
   * PostgreSQL implementation of the method getAll.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Product> getAll() throws SQLException {
    throw new UnsupportedOperationException();
  }

  /**
   * PostgreSQL implementation of the method update.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int update(final Product product) throws SQLException {
    throw new UnsupportedOperationException();
  }

  /**
   * PostgreSQL implementation of the method delete.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int delete(final Product product) throws SQLException {
    throw new UnsupportedOperationException();
  }
}
