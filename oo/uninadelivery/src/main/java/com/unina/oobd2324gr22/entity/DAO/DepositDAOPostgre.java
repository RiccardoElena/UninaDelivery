package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.CentralDeposit;
import com.unina.oobd2324gr22.entity.DTO.CountryDeposit;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.StateDeposit;
import java.sql.SQLException;
import java.util.List;

public class DepositDAOPostgre implements DepositDAO {

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

  @Override
  public final Deposit getDepositById(final int id) throws SQLException {
    // TODO @RiccardoElena @zGenny Auto-generated method stub
    return null;
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
