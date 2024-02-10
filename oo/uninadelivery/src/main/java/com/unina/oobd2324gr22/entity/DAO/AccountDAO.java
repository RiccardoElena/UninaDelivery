package com.unina.oobd2324gr22.entity.DAO;

import com.unina.oobd2324gr22.entity.DTO.Account;
import com.unina.oobd2324gr22.entity.DTO.Operator;
import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {

  /**
   * Create an account into the DB.
   *
   * @param account Account to insert into the DB
   * @return the number of rows affected by the insert
   * @throws SQLException possible DB related errors
   */
  int insertAccount(Account account) throws SQLException;

  /**
   * Retrieve all the accounts.
   *
   * @return list of accounts
   * @throws SQLException possible DB related errors
   */
  List<Account> getAccounts() throws SQLException;

  /**
   * Retrieve an account by its email.
   *
   * @param email email
   * @return ciao
   */
  Account getAccountByEmail(String email) throws SQLException;

  /**
   * Retrieve an account by its email and password.
   *
   * @param email email
   * @param password password
   * @return the account with given email and password or null if not found
   */
  Account getAccountByEmailAndPassword(String email, String password) throws SQLException;

  /**
   * Retrieve an operator account by its businessmail and password.
   *
   * @param bmail email
   * @param password password
   * @return the account with given email and password or null if not found
   */
  Operator getOperatorByBmailAndPassword(String bmail, String password) throws SQLException;

  /**
   * Retrieve all accounts with given name and surname.
   *
   * @param name name to search
   * @param surname surname to search
   * @return list of accounts with given name and surname
   * @throws SQLException possible DB related errors
   */
  List<Account> getAccountByNameAndSurname(String name, String surname) throws SQLException;

  /**
   * Retrieve the account with the most orders in a given month and year.
   *
   * @param year year to search
   * @param month month to search
   * @return the account with the most orders in the given month and year
   * @throws SQLException possible DB related errors
   */
  Account getMostOrderingAccount(int year, int month) throws SQLException;

  /**
   * Retrieve the account with the most spending in a given month and year.
   *
   * @param year year to search
   * @param month month to search
   * @return the account with the most spending in the given month and year
   * @throws SQLException possible DB related errors
   */
  Account getMostSpendingAccount(int year, int month) throws SQLException;

  /**
   * Update an account.
   *
   * @param account Account to update
   * @return the number of rows affected by the update
   * @throws SQLException possible DB related errors
   */
  int updateAccount(Account account) throws SQLException;

  /**
   * Update an account's email.
   *
   * <p>Being the email the primary key of the account table, it has to be updated with a specific
   * avoid errors.
   *
   * @param account Account to update
   * @param newEmail new email to set
   * @return the number of rows affected by the update
   * @throws SQLException possible DB related errors
   */
  int updateAccountEmail(Account account, String newEmail) throws SQLException;

  /**
   * Delete an account.
   *
   * @param account Account to delete
   * @return the number of rows affected by the delete
   * @throws SQLException possible DB related errors
   */
  int deleteAccount(Account account) throws SQLException;
}
