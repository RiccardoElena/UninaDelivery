package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.CentralDeposit;
import com.unina.oobd2324gr22.entity.DTO.CountryDeposit;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.StateDeposit;
import com.unina.oobd2324gr22.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepositDAOPostgre implements DepositDAO {

  /** Connection to the database. */
  private Connection con;

  private Deposit populateDepositFromResultSet(final ResultSet rs) throws SQLException {
    return new Deposit(
        rs.getInt("depositid"),
        rs.getInt("occupiedspace"),
        rs.getInt("maxcapacity"),
        new Address(
            rs.getString("zipcode"),
            rs.getString("city"),
            rs.getString("state"),
            rs.getString("country"),
            rs.getString("worldzone"),
            rs.getString("addressno"),
            rs.getString("street")));
  }

  /**
   * PostgreSQL implementation of the method insertCityDeposit.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertCityDeposit(final Deposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }

  @Override
  public final int insertStateDeposit(final StateDeposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }

  @Override
  public final int insertCountryDeposit(final CountryDeposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }

  @Override
  public final int insertCentralDeposit(final CentralDeposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }

  @Override
  public final List<Deposit> getDeposits() throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return null;
  }

  // TODO @RiccardoElena @zGenny we should make one of this for each type of deposit
  @Override
  public final Deposit getDepositById(final int id) throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Deposit deposit = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;
    try {
      psSelect = con.prepareStatement("SELECT * FROM deposit NATURAL JOIN area WHERE orderid = ?");
      psSelect.setInt(1, id);
      rs = psSelect.executeQuery();
      while (rs.next()) {
        deposit = populateDepositFromResultSet(rs);
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
    return deposit;
  }

  @Override
  public final List<Deposit> getDepositsByType(final String type) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return null;
  }

  @Override
  public final List<Deposit> getDepositsByArea(final String area) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return null;
  }

  @Override
  public final List<Deposit> getDepositsByAreaAndType(final String area, final String type)
      throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return null;
  }

  @Override
  public final int updateDeposit(final Deposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }

  @Override
  public final int deleteDeposit(final Deposit deposit) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return 0;
  }
}
