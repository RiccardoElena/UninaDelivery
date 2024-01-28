package com.unina.oobd2324gr22.entity.DAO;

import java.sql.SQLException;
import java.util.List;
import com.unina.oobd2324gr22.entity.DTO.Account;

// TODO @zGenny @RiccardoElena nly signature has been written, no implementation

public class AccountDAOPostgre implements AccountDAO {

  /**
   * PostgreSQL implementation of the insertAccount method.
   */

  @Override
  public int insertAccount(final Account account)
      throws SQLException {
    return 0;
  }

  /**
   * PostgreSQL implementation of the getAccounts method.
   */

  @Override
  public List<Account> getAccounts()
      throws SQLException {
    return null;
  }

  /**
  * PostgreSQL implementation of the getAccountByEmail method.
  */

  @Override
  public Account getAccountByEmail(final String email)
      throws SQLException {
    return null;
  }

  /**
   * PostgreSQL implementation of the getAccountByEmailAndPassword method.
   */
  @Override
  public Account getAccountByEmailAndPassword(final String email,
                                              final String password)
                                                throws SQLException {
    throw new UnsupportedOperationException(
      "Unimplemented method 'getAccountByEmailAndPassword'");
  }

  /**
   * PostgreSQL implementation of the getAccountByNameAndSurname method.
   */

  @Override
  public List<Account> getAccountByNameAndSurname(
                                                  final String name,
                                                  final String surname)
      throws SQLException {
    return null;
  }

  /**
   * PostgreSQL implementation of the updateAccount method.
   */

  @Override
  public int updateAccount(final Account account)
      throws SQLException {
    return 0;
  }

  /**
   * PostgreSQL implementation of the updateAccountEmail method.
   */

  @Override
  public int updateAccountEmail(final Account account,
                                final String newEmail)
      throws SQLException {
    return 0;
  }

  /**
   * PostgreSQL implementation of the deleteAccount method.
   */

  @Override
  public int deleteAccount(final Account account)
      throws SQLException {
    return 0;
  }


}
