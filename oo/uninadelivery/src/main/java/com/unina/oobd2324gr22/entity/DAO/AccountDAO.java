package com.unina.oobd2324gr22.entity.DAO;

import java.sql.SQLException;
import java.util.List;
import com.unina.oobd2324gr22.entity.DTO.Account;

public interface AccountDAO {

  /**
   * Create an account into the DB.

   * @param account Account to insert into the DB
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */

  int insertAccount(Account account) throws SQLException;

  /**
   * Retrieve all the accounts.

   * @return list of accounts
   * @throws SQLException possible DB related errors
   */

  List<Account> getAccounts() throws SQLException;

  /**
   * Retrieve an account by its email.

   * @param email email
   * @return ciao
   */

  Account getAccountByEmail(String email) throws SQLException;

  /**
   * Retrieve all accounts with given name and surname.

   * @param name name to search
   * @param surname surname to search
   * @return list of accounts with given name and surname
   * @throws SQLException possible DB related errors
   */

  List<Account> getAccountByNameAndSurname(String name, String surname)
      throws SQLException;

  /**
   * Update an account.

   * @param account Account to update
   * @return the number of rows affected by the update
   * @throws SQLException possible DB related errors
   */

  int updateAccount(Account account) throws SQLException;

  /**
   * Update an account's email.
   *
   * Being the email the primary key of the account table,
   * it has to be updated with a specific avoid errors.

   * @param account Account to update
   * @param newEmail new email to set
   * @return the number of rows affected by the update
   * @throws SQLException possible DB related errors
   */

  int updateAccountEmail(Account account, String newEmail) throws SQLException;

  /**
   * Delete an account.

   * @param account Account to delete
   * @return the number of rows affected by the delete
   * @throws SQLException possible DB related errors
   */

  int deleteAccount(Account account) throws SQLException;
}