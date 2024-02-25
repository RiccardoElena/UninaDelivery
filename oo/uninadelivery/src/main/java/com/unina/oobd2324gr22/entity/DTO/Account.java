package com.unina.oobd2324gr22.entity.DTO;

import com.unina.oobd2324gr22.utils.Base64ToImage;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.image.Image;

/**
 * This class represents an account.
 *
 * <ul>
 *   <li>Each account is a Customer.
 *   <li>It can be extended to Operator and Driver to represent specific types of accounts.
 * </ul>
 */
public class Account {

  // Attributes

  /** Name of the account. */
  private String name;

  /** Surname of the account. */
  private String surname;

  /** Email of the account. */
  private String email;

  /** Birthdate of the account. */
  private LocalDate birthdate;

  /** Profile picture of the account. */
  private String propic;

  /** Password of the account. */
  private String password;

  /** Address of the account. */
  private Address address;

  /** List of orders associated with the account. */
  private List<Order> orders;

  /** Amount spent by the account in a certain month. */
  private double amountSpent;

  // Constructors

  /**
   * Default constructor.
   *
   * @param accName ciao
   * @param accSurname ciao
   * @param accEmail ciao
   * @param accBdate ciao
   * @param accPropic ciao
   * @param accPassword ciao
   * @param accAddress ciao
   */
  public Account(
      final String accName,
      final String accSurname,
      final String accEmail,
      final LocalDate accBdate,
      final String accPropic,
      final String accPassword,
      final Address accAddress) {
    name = accName;
    surname = accSurname;
    email = accEmail;
    birthdate = accBdate;
    propic = accPropic;
    password = accPassword;
    address = accAddress;
  }

  // Getters and Setters

  /**
   * @return name of the account
   */
  public String getName() {
    return name;
  }

  /**
   * @param accName Account's name
   */
  public void setName(final String accName) {
    name = accName;
  }

  /**
   * @return surname of the account
   */
  public String getSurname() {
    return surname;
  }

  /**
   * @param accSurname Account's surname
   */
  public void setSurname(final String accSurname) {
    surname = accSurname;
  }

  /**
   * @return email of the account
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param accEmail Account's email
   */
  public void setEmail(final String accEmail) {
    email = accEmail;
  }

  /**
   * @return birthdate of the account
   */
  public LocalDate getBirthdate() {
    return birthdate;
  }

  /**
   * @param accBDate Account's birthdate
   */
  public void setBirthdate(final LocalDate accBDate) {
    birthdate = accBDate;
  }

  /**
   * @return profile picture of the account
   */
  public String getPropicBase64() {
    return propic;
  }

  /**
   * @param accPropic Account's profile picture
   */
  public void setPropicBase64(final String accPropic) {
    propic = accPropic;
  }

  /**
   * @return Account's profile picture
   */
  public Image getPropic() {
    Image img;
    try {
      img = Base64ToImage.convert(propic);
      if (img.getHeight() > img.getWidth()) {
        throw new Exception("Image has wrong orientation!");
      }
    } catch (Exception e) {
      img = new Image("/images/defaultUser.jpg");
    }
    return img;
  }

  /**
   * @return password of the account
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param accPassword Account's password
   */
  public void setPassword(final String accPassword) {
    password = accPassword;
  }

  /**
   * @return address of the account
   */
  public Address getAddress() {
    return address;
  }

  /**
   * @param accAddress Account's address
   */
  public void setAddress(final Address accAddress) {
    address = accAddress;
  }

  /**
   * @return list of orders made by the account
   */
  public List<Order> getOrders() {
    return orders;
  }

  /**
   * @param accOrders Account's orders list
   */
  public void setOrders(final List<Order> accOrders) {
    orders = accOrders;
  }

  /**
   * @return amount spent by the account
   */
  public double getAmountSpent() {
    return amountSpent;
  }

  /**
   * @param accAmountSpent Account's amount spent
   */
  public void setAmountSpent(final double accAmountSpent) {
    amountSpent = accAmountSpent;
  }

  // Methods

  /** To string method. */
  @Override
  public String toString() {
    return "Account{"
        + "\nname = '"
        + name
        + "',"
        + "\nsurname = '"
        + surname
        + "',"
        + "\nemail = '"
        + email
        + "',"
        + "\nbirthdate = '"
        + birthdate
        + "',"
        + "\npropic = '"
        + propic
        + "',"
        + "\npassword = '"
        + password
        + "',"
        + "\naddress = "
        + address.toString().replace("\n", "\n\t")
        + "\n}";
  }
}
