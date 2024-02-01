package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Address;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// TODO @zGenny @RiccardoElena nly signature has been written, no implementation

public class AccountDAOPostgre implements AccountDAO {

  /** Connection to the database. */
  private Connection con;

  private Operator populateOperatorFromResultSet(final ResultSet rs) throws SQLException {
    return new Operator.OperatorBuilder(
            rs.getString("name"),
            rs.getString("surname"),
            rs.getString("email"),
            rs.getDate("birthdate").toLocalDate(),
            rs.getString("password"),
            new Address(
                rs.getString("zipcode"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("country"),
                rs.getString("worldzone"),
                rs.getString("addressno"),
                rs.getString("street")),
            rs.getString("businessmail"))
        .build();
  }

  /** PostgreSQL implementation of the insertAccount method. */
  @Override
  public int insertAccount(final Account account) throws SQLException {
    return 0;
  }

  /** PostgreSQL implementation of the getAccounts method. */
  @Override
  public List<Account> getAccounts() throws SQLException {
    return null;
  }

  /** PostgreSQL implementation of the getAccountByEmail method. */
  @Override
  public Account getAccountByEmail(final String email) throws SQLException {
    return null;
  }

  /** PostgreSQL implementation of the getAccountByEmailAndPassword method. */
  @Override
  public Account getAccountByEmailAndPassword(final String email, final String password)
      throws SQLException {
    throw new UnsupportedOperationException("Unimplemented method 'getAccountByEmailAndPassword'");
  }

  /** PostgreSQL implementation of the getAccountByNameAndSurname method. */
  @Override
  public List<Account> getAccountByNameAndSurname(final String name, final String surname)
      throws SQLException {
    return null;
  }

  /** PostgreSQL implementation of the getAccountByBmail method. */
  @Override
  public final Operator getOperatorByBmailAndPassword(final String bmail, final String password)
      throws SQLException {
    con = DBConnection.getConnectionBySchema("uninadelivery");
    Operator op = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      st =
          con.prepareStatement(
              "SELECT * FROM account NATURAL JOIN operator NATURAL JOIN area WHERE bmail = ? AND"
                  + " password = ?");
      st.setString(1, bmail);
      st.setString(2, password);
      rs = st.executeQuery();
      if (rs.next()) {
        op = populateOperatorFromResultSet(rs);
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }
    return op;
  }

  /** PostgreSQL implementation of the updateAccount method. */
  @Override
  public int updateAccount(final Account account) throws SQLException {
    return 0;
  }

  /** PostgreSQL implementation of the updateAccountEmail method. */
  @Override
  public int updateAccountEmail(final Account account, final String newEmail) throws SQLException {
    return 0;
  }

  /** PostgreSQL implementation of the deleteAccount method. */
  @Override
  public int deleteAccount(final Account account) throws SQLException {
    return 0;
  }
}
