package com.unina.oobd2324gr22.entity.DTO;

import java.time.LocalDate;

public final class Operator extends Account {

  // Attributes

  /** Business mail of the operator. */
  private String businessMail;

  // Constructors - Builder pattern

  private Operator(final OperatorBuilder builder) {
    super(
        builder.builderAccName,
        builder.builderAccSurname,
        builder.buildarAccEmail,
        builder.builderAccBdate,
        builder.builderAccPropic,
        builder.builderAccPassword,
        builder.builderAccAddress);
    this.businessMail = builder.builderBusinessMail;
  }

  // Constructor - Via account

  /**
   * Constructor with parameters.
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
        account.getPassword(),
        account.getPropic(),
        account.getAddress());
    this.businessMail = bMail;
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
      this.builderAccName = accName;
      this.builderAccSurname = accSurname;
      this.buildarAccEmail = accEmail;
      this.builderAccBdate = accBdate;
      this.builderAccPassword = accPassword;
      this.builderAccAddress = accAddress;
      this.builderBusinessMail = businessMail;
    }

    /**
     * Setter of the profile picture.
     *
     * @param accPropic profile picture of the operator to be built
     * @return the builder
     */
    public final OperatorBuilder withAccPropic(final String accPropic) {
      this.builderAccPropic = accPropic;
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

  // Getters and Setters

  /**
   * Getter of the business mail.
   *
   * @return business mail
   */
  public String getBusinessMail() {
    return this.businessMail;
  }

  // Methods

  @Override
  public String toString() {
    return "Operator{"
        + "\nname='"
        + this.getName()
        + "',"
        + "\nsurname='"
        + this.getSurname()
        + "',"
        + "\nemail='"
        + this.getEmail()
        + "',"
        + "\nbirthdate='"
        + this.getBirthdate()
        + "',"
        + "\npropic='"
        + this.getPropic()
        + "',"
        + "\npassword='"
        + this.getPassword()
        + "',"
        + "\naddress= "
        + this.getAddress().toString().replace("\n", "\n\t")
        + ","
        + "\nbusinessmail='"
        + this.getBusinessMail()
        + "',"
        + '}';
  }
}
