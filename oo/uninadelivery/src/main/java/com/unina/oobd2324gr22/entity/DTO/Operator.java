package com.unina.oobd2324gr22.entity.DTO;

import java.time.LocalDate;

public class Operator extends Account {

  // Attributes

  /** Business mail of the operator. */
  private String businessMail;

  // Constructors

  /**
   * Constructor of the operator using builder pattern.
   *
   * @param builder builder of the operator
   */
  private Operator(final OperatorBuilder builder) {
    super(
        builder.builderAccName,
        builder.builderAccSurname,
        builder.buildarAccEmail,
        builder.builderAccBdate,
        builder.builderAccPropic,
        builder.builderAccPassword,
        builder.builderAccAddress);
    businessMail = builder.builderBusinessMail;
  }

  public static class OperatorBuilder {
    /** Name of the operator to be built. */
    private String builderAccName;

    /** Surname of the operator to be built. */
    private String builderAccSurname;

    /** Email of the operator to be built. */
    private String buildarAccEmail;

    /** Birthdate of the operator to be built. */
    private LocalDate builderAccBdate;

    /** Profile picture of the operator to be built. */
    private String builderAccPropic;

    /** Password of the operator to be built. */
    private String builderAccPassword;

    /** Address of the operator to be built. */
    private Address builderAccAddress;

    /** Business mail of the operator to be built. */
    private String builderBusinessMail;

    /**
     * OperatorBuilder constructor with mandatory fields.
     *
     * @param accName name of the operator to be built
     * @param accSurname surname of the operator to be built
     * @param accEmail email of the operator to be built
     * @param accBdate birthdate of the operator to be built
     * @param accPassword password of the operator to be built
     * @param accAddress address of the operator to be built
     * @param businessMail business mail of the operator to be built
     */
    public OperatorBuilder(
        final String accName,
        final String accSurname,
        final String accEmail,
        final LocalDate accBdate,
        final String accPassword,
        final Address accAddress,
        final String businessMail) {
      builderAccName = accName;
      builderAccSurname = accSurname;
      buildarAccEmail = accEmail;
      builderAccBdate = accBdate;
      builderAccPassword = accPassword;
      builderAccAddress = accAddress;
      builderBusinessMail = businessMail;
    }

    /**
     * Setter of the profile picture.
     *
     * @param accPropic profile picture of the operator to be built
     * @return the builder
     */
    public final OperatorBuilder withAccPropic(final String accPropic) {
      builderAccPropic = accPropic;
      return this;
    }

    /**
     * Build the operator with given data. To instantiate the operator call the build method of the
     * builder after using his constructor and his setters needed.
     *
     * @return the operator
     */
    public final Operator build() {
      return new Operator(this);
    }
  }

  /**
   * Constructor of the operator specializing superclass.
   *
   * @param account account of the operator
   * @param bMail business mail of the operator
   */
  public Operator(final Account account, final String bMail) {
    super(
        account.getName(),
        account.getSurname(),
        account.getEmail(),
        account.getBirthdate(),
        account.getPropicBase64(),
        account.getPassword(),
        account.getAddress());
    businessMail = bMail;
  }

  // Getters and Setters

  /**
   * Getter of the business mail.
   *
   * @return business mail
   */
  public String getBusinessMail() {
    return businessMail;
  }

  /**
   * Getter of the Account.
   *
   * @return account
   */
  public Account getAccount() {
    return new Account(
        getName(),
        getSurname(),
        getEmail(),
        getBirthdate(),
        getPropicBase64(),
        getPassword(),
        getAddress());
  }

  // Methods

  /**
   * Override of the toString method.
   *
   * @return string representation of the operator
   */
  @Override
  public String toString() {
    return "Operator{"
        + "\nname='"
        + getName()
        + "',"
        + "\nsurname='"
        + getSurname()
        + "',"
        + "\nemail='"
        + getEmail()
        + "',"
        + "\nbirthdate='"
        + getBirthdate()
        + "',"
        + "\npropic='"
        + getPropicBase64()
        + "',"
        + "\npassword='"
        + getPassword()
        + "',"
        + "\naddress= "
        + getAddress().toString().replace("\n", "\n\t")
        + ","
        + "\nbusinessmail='"
        + getBusinessMail()
        + "',"
        + '}';
  }
}
