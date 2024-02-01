package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Product;
import com.unina.oobd2324gr22.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAOPostgre implements ProductDAO {

  /** Connection to the database. */
  private Connection con;

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
   * PostgreSQL implementation of the method getProductByNameAndSupplier.<br>
   * {@inheritDoc}
   */
  @Override
  public Product getProductByNameAndSupplier(final String name, final String supplier)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Product product = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement("SELECT * FROM product WHERE name = ? AND supplier = ?");
      psSelect.setString(1, name);
      psSelect.setString(2, supplier);
      rs = psSelect.executeQuery();
      while (rs.next()) {
        product = populateProductFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (psSelect != null) {
        psSelect.close();
      }
      if (con != null) {
        con.close();
      }
    }
    return product;
  }
}
