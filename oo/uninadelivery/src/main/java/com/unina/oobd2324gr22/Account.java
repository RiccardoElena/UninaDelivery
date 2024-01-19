package com.unina.oobd2324gr22;

import java.time.LocalDate;

/**
 * This class represents an account.
 * <ul>
 * <li> Each account is a Customer.
 * <li> It can be extended to Operator and Driver to represent
 * specific types of accounts.
 * </ul>
 */
public class Account {

  /**
   * Default constructor.
   */
  private String name;

  /**
   * Default constructor.
   */
  private String surname;

  /**
   * Default constructor.
   */
  private String email;

  /**
   * Default constructor.
   */
  private LocalDate birthdate;

  /**
   * Default constructor.
   */
  private String propic;

  /**
   * Default constructor.
   */
  private String password;

  /**
   * Default constructor.
   */
  private Address address;

  /**
   * Default constructor.

   * @param accName ciao
   * @param accSurname ciao
   * @param accEmail ciao
   * @param accBdate ciao
   * @param accPropic ciao
   * @param accPassword ciao
   * @param accAddress ciao
   */
  public Account(final String accName, final String accSurname,
                  final String accEmail, final LocalDate accBdate,
                  final String accPropic, final String accPassword,
                  final Address accAddress) {
    this.name = accName;
    this.surname = accSurname;
    this.email = accEmail;
    this.birthdate = accBdate;
    this.propic = accPropic;
    this.password = accPassword;
    this.address = accAddress;
  }

  /**
   * Name getter.

   * @return ciao
   */
  public String getName() {
    return name;
  }

  /**
   * Name setter.

   * @param accName Nome account
   */
  public void setName(final String accName) {
    this.name = accName;
  }

  /**
   * Surname getter.

   * @return cognome account
   */
  public String getSurname() {
    return surname;
  }

  /**
   * Surname Setter.

   * @param accSurname cognome account
   */
  public void setSurname(final String accSurname) {
    this.surname = accSurname;
  }

  /**
   * Email getter.

   * @return email account
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param accEmail email account
   */
  public void setEmail(final String accEmail) {
    this.email = accEmail;
  }

  /**
   * @return data di nascita account
   */
  public LocalDate getBirthdate() {
    return birthdate;
  }

  /**
   * @param accBDate data di nascita account
   */
  public void setBirthdate(final LocalDate accBDate) {
    this.birthdate = accBDate;
  }

  /**
   * @return immagine profilo account
   */
  public String getPropic() {
    return propic;
  }

  /**
   * @param accPropic immagine profilo account
   */
  public void setPropic(final String accPropic) {
    this.propic = accPropic;
  }

  /**
   * @return password account
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param accPassword password account
   */
  public void setPassword(final String accPassword) {
    this.password = accPassword;
  }

  /**
   * @return indirizzo account
   */
  public Address getAddress() {
    return address;
  }

  /**
   * @param accAddress indirizzo account
   */
  public void setAddress(final Address accAddress) {
    this.address = accAddress;
  }

  /**
   * To string method.
   */
  @Override
  public String toString() {
    return "Account{"
          + "\nname = '" + this.name + "',"
          + "\nsurname = '" + this.surname + "',"
          + "\nemail = '" + this.email + "',"
          + "\nbirthdate = '" + this.birthdate + "',"
          + "\npropic = '" + this.propic + "',"
          + "\npassword = '" + this.password + "',"
          + "\naddress = " + this.address.toString().replace("\n", "\n\t")
          + "\n}";
  }

}
