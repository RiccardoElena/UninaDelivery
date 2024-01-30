package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.AirTransport;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.RailsTransport;
import com.unina.oobd2324gr22.entity.DTO.Transport;
import com.unina.oobd2324gr22.entity.DTO.WaterTransport;
import com.unina.oobd2324gr22.entity.DTO.WheeledLarge;
import com.unina.oobd2324gr22.entity.DTO.WheeledSmall;
import java.sql.SQLException;
import java.util.List;

public class TransportDAOPostgre implements TransportDAO {

  /**
   * PostgreSQL implementation of the method insertWheeledSmall.<br>
   * {@inheritDoc}
   */
  @Override
  public int insertWheeledSmall(final WheeledSmall wheeledSmall) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * PostgreSQL implementation of the method insertWheeledLarge.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertWheeledLarge(final WheeledLarge wheeledLarge) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * PostgreSQL implementation of the method insertRails.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertRails(final RailsTransport railsTransport) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * PostgreSQL implementation of the method insertWater.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertWater(final WaterTransport waterTransport) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * PostgreSQL implementation of the method insertAir.<br>
   * {@inheritDoc}
   */
  @Override
  public final int insertAir(final AirTransport airTransport) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertAir'");
  }

  /**
   * PostgreSQL implementation of the method getTransports.<br>
   * {@inheritDoc}
   */
  @Override
  public final List<Transport> getTransports() throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTransports'");
  }

  /**
   * PostgreSQL implementation of the method getTransportById.<br>
   * {@inheritDoc}
   */
  @Override
  public final Transport getTransportById(final int id) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTransportById'");
  }

  /**
   * PostgreSQL implementation of the method getTransportsByDeposit.<br>
   * {@inheritDoc}
   */
  @Override
  public final List<Transport> getTransportsByDeposit(final Deposit deposit) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTransportsByDeposit'");
  }

  /**
   * PostgreSQL implementation of the method getAvailableTransports.<br>
   * {@inheritDoc}
   */
  @Override
  public final List<Transport> getAvailableTransports() throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAvailableTransports'");
  }

  /**
   * PostgreSQL implementation of the method getAvailableTransportsByDeposit.<br>
   * {@inheritDoc}
   */
  @Override
  public final List<Transport> getAvailableTransportsByDeposit(final Deposit deposit)
      throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'getAvailableTransportsByDeposit'");
  }

  /**
   * PostgreSQL implementation of the method updateTransport.<br>
   * {@inheritDoc}
   */
  @Override
  public final int updateTransport(final Transport transport) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateTransport'");
  }

  /**
   * PostgreSQL implementation of the method deleteTransport.<br>
   * {@inheritDoc}
   */
  @Override
  public final int deleteTransport(final Transport transport) throws SQLException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteTransport'");
  }
}
