package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Deposit;
import com.unina.oobd2324gr22.entity.DTO.Driver;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import com.unina.oobd2324gr22.entity.DTO.Order;
import com.unina.oobd2324gr22.utils.DBConnection;
import com.unina.oobd2324gr22.utils.IterableInt;
import com.unina.oobd2324gr22.utils.UnimplementedMethodException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the AccounttDAO interface and provides the PostgreSQL implementation for
 * the shipment data access operations.
 */
public class AccountDAOPostgre implements AccountDAO {

  private Operator populateOperatorFromResultSet(final ResultSet rs) throws SQLException {
    return new Operator(populateAccountFromResultSet(rs), rs.getString("businessmail"));
  }

  private Driver populateDriverFromResultSet(final ResultSet rs) throws SQLException {
    Deposit deposit = new DepositDAOPostgre().getDepositById(rs.getInt("depositid"));
    Driver.DrivingLicenceType licence =
        Driver.DrivingLicenceType.valueOf(rs.getString("drivinglicencetype"));
    return new Driver(
        populateAccountFromResultSet(rs), rs.getString("businessmail"), licence, deposit);
  }

  private Account populateAccountFromResultSet(final ResultSet rs) throws SQLException {
    AreaDAO areaDAO = new AreaDAOPostgre();
    return new Account(
        rs.getString("name"),
        rs.getString("surname"),
        rs.getString("email"),
        rs.getDate("birthdate").toLocalDate(),
        rs.getString("propic"),
        rs.getString("password"),
        areaDAO.extractAddress(
            rs.getString("addressno"),
            rs.getString("street"),
            rs.getString("zipcode"),
            rs.getString("country")));
  }

  /**
   * PostgreSQL implementation of the insert method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int insert(final Account account) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the getAll method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Account> getAll() throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the getAccountByEmail method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Account getAccountByEmail(final String email) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    Account account = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;

    psSelect = con.prepareStatement("SELECT * FROM account WHERE email = ?");
    psSelect.setString(1, email);
    rs = psSelect.executeQuery();
    while (rs.next()) {
      account = populateAccountFromResultSet(rs);
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

    return account;
  }

  /**
   * PostgreSQL implementation of the getAccountByEmailAndPassword method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Account getAccountByEmailAndPassword(final String email, final String password)
      throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the getAccountByNameAndSurname method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Account> getAccountByNameAndSurname(final String name, final String surname)
      throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the getAccountByBmail method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public final Operator getOperatorByBmailAndPassword(final String bmail, final String password)
      throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    Operator op = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    st =
        con.prepareStatement(
            "SELECT * FROM account NATURAL JOIN operator WHERE businessmail ILIKE ? AND"
                + " password = ?");
    st.setString(1, bmail);
    st.setString(2, password);
    rs = st.executeQuery();
    if (rs.next()) {
      op = populateOperatorFromResultSet(rs);
    }

    if (rs != null) {
      rs.close();
    }
    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }

    return op;
  }

  /**
   * PostgreSQL implementation of the getAccountByBmail method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Account getMostOrderingAccount(final Year year, final Month month) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    Account account = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;

    psSelect =
        con.prepareStatement(
            "SELECT A.* FROM account A JOIN \"Order\" O ON A.email = O.email WHERE EXTRACT(YEAR"
                + " FROM O.emissiondate) = ? AND EXTRACT(MONTH FROM O.emissiondate) = ? GROUP BY"
                + " A.email ORDER BY COUNT(O.orderid) DESC LIMIT 1");
    psSelect.setInt(1, year.getValue());
    psSelect.setInt(2, month.getValue());
    rs = psSelect.executeQuery();
    if (rs.next()) {
      account = populateAccountFromResultSet(rs);
      account.setOrders(new OrderDAOPostgre().getOrdersByAccountAndMonth(account, year, month));
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

    return account;
  }

  /**
   * PostgreSQL implementation of the getMostSpendingAccount method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public Account getMostSpendingAccount(final Year year, final Month month) throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    Account account = null;
    PreparedStatement psSelect = null;
    ResultSet rs = null;

    psSelect =
        con.prepareStatement(
            "SELECT * FROM account WHERE email IN (SELECT email FROM \"Order\" WHERE EXTRACT(YEAR"
                + " FROM emissiondate) = ? AND EXTRACT(MONTH FROM emissiondate) = ?)");
    psSelect.setInt(1, year.getValue());
    psSelect.setInt(2, month.getValue());
    rs = psSelect.executeQuery();
    List<Account> accounts = new ArrayList<>();
    while (rs.next()) {
      accounts.add(populateAccountFromResultSet(rs));
    }
    double maxPrice = 0;
    for (Account a : accounts) {
      for (Order o : new OrderDAOPostgre().getOrdersByAccountAndMonth(a, year, month)) {
        a.setAmountSpent(a.getAmountSpent() + o.getPrice());
      }
      if (a.getAmountSpent() > maxPrice) {
        maxPrice = a.getAmountSpent();
        account = a;
      }
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

    return account;
  }

  /**
   * PostgreSQL implementation of the getCompatibleDrivers method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public List<Driver> getCompatibleDrivers(final Deposit deposit, final LocalDate date)
      throws SQLException {
    Connection con = DBConnection.getConnectionBySchema("uninadelivery");
    List<Driver> drivers = new ArrayList<>();
    PreparedStatement st = null;
    ResultSet rs = null;
    IterableInt fieldNumber = new IterableInt(1);

    String query =
        "select * from account natural join driver where businessmail NOT IN (select businessmail"
            + " from drives where date = ?) and depositid = ?;";
    st = con.prepareStatement(query);
    st.setDate(fieldNumber.next(), java.sql.Date.valueOf(date));
    st.setInt(fieldNumber.next(), deposit.getId());
    rs = st.executeQuery();
    while (rs.next()) {
      drivers.add(populateDriverFromResultSet(rs));
    }

    if (rs != null) {
      rs.close();
    }
    if (st != null) {
      st.close();
    }
    if (con != null) {
      con.close();
    }

    return drivers;
  }

  /**
   * PostgreSQL implementation of the update method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int update(final Account account) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the updateAccountEmail method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int updateAccountEmail(final Account account, final String newEmail) throws SQLException {
    throw new UnimplementedMethodException();
  }

  /**
   * PostgreSQL implementation of the deleteAccount method.
   *
   * <p>{@inheritDoc}
   */
  @Override
  public int delete(final Account account) throws SQLException {
    throw new UnimplementedMethodException();
  }
}
