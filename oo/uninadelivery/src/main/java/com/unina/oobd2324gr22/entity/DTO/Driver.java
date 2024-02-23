package com.unina.oobd2324gr22.entity.DTO;

import java.time.LocalDate;

public final class Driver extends Account {

  public enum DrivingLicenceType {
    /** Driving licence type BE. Used for max weight of 7000 kg. */
    BE,
    /** Driving licence type CE. Used for trucks and TIRs. */
    CE
  }

  // Attributes

  /** Business mail of the driver. */
  private String businessMail;

  /** Driving Licence of the driver. */
  private DrivingLicenceType drivingLicence;

  /** Workplace of the driver. */
  private Deposit workplace;

  // Constructors - Builder pattern

  private Driver(final DriverBuilder builder) {
    super(
        builder.builderAccName,
        builder.builderAccSurname,
        builder.buildarAccEmail,
        builder.builderAccBdate,
        builder.builderAccPropic,
        builder.builderAccPassword,
        builder.builderAccAddress);
    this.businessMail = builder.builderBusinessMail;
    this.drivingLicence = builder.builderDrivingLicence;
    this.workplace = builder.builderWorkplace;
  }

  public static class DriverBuilder {
    /** Name of the driver to be built. */
    private String builderAccName;

    /** Surname of the driver to be built. */
    private String builderAccSurname;

    /** Email of the driver to be built. */
    private String buildarAccEmail;

    /** Birthdate of the driver to be built. */
    private LocalDate builderAccBdate;

    /** Profile picture of the driver to be built. */
    private String builderAccPropic;

    /** Password of the driver to be built. */
    private String builderAccPassword;

    /** Address of the driver to be built. */
    private Address builderAccAddress;

    /** Business mail of the driver to be built. */
    private String builderBusinessMail;

    /** Driving Licence of the driver to be built. */
    private DrivingLicenceType builderDrivingLicence;

    /** Workplace of the driver to be built. */
    private Deposit builderWorkplace;

    /**
     * Constructor of the builder.
     *
     * @param name name of the driver to be built
     * @param surname surname of the driver to be built
     * @param email email of the driver to be built
     * @param bdate birthdate of the driver to be built
     * @param password password of the driver to be built
     * @param address address of the driver to be built
     * @param businessMail business mail of the driver to be built
     */
    public DriverBuilder(
        final String name,
        final String surname,
        final String email,
        final LocalDate bdate,
        final String password,
        final Address address,
        final String businessMail) {
      this.builderAccName = name;
      this.builderAccSurname = surname;
      this.buildarAccEmail = email;
      this.builderAccBdate = bdate;
      this.builderAccPassword = password;
      this.builderAccAddress = address;
      this.builderBusinessMail = businessMail;
    }

    /**
     * Setter of the profile picture.
     *
     * @param propic profile picture of the driver to be built
     * @return the driver builder
     */
    public DriverBuilder withAccPropic(final String propic) {
      this.builderAccPropic = propic;
      return this;
    }

    /**
     * Setter of the driving licence.
     *
     * @param drivingLicence driving licence of the driver to be built
     * @return the driver builder
     */
    public DriverBuilder withDrivingLicence(final DrivingLicenceType drivingLicence) {
      this.builderDrivingLicence = drivingLicence;
      return this;
    }

    /**
     * Setter of the workplace.
     *
     * @param workplace workplace of the driver to be built
     * @return the driver builder
     */
    public DriverBuilder withWorkplace(final Deposit workplace) {
      this.builderWorkplace = workplace;
      return this;
    }

    /**
     * Build method. It creates a new driver with given data if all the required fields are filled.
     * To be filled manually are:
     *
     * <ul>
     *   <li>Driving Licence
     *   <li>Workplace
     * </ul>
     *
     * Use the 'with' methods to fill them alogside the optional ones.
     *
     * @return the driver
     */
    public Driver build() throws IllegalStateException {
      if (this.builderDrivingLicence == null || this.builderWorkplace == null) {
        throw new IllegalStateException("Missing required fields");
      }
      return new Driver(this);
    }
  }

  /**
   * Constructor of the driver.
   *
   * @param account personal data of the driver
   * @param bmail business mail of the driver
   * @param dLicence driving licence type of the driver
   * @param wPlace workplace of the driver
   */
  public Driver(
      final Account account,
      final String bmail,
      final DrivingLicenceType dLicence,
      final Deposit wPlace) {
    super(
        account.getName(),
        account.getSurname(),
        account.getEmail(),
        account.getBirthdate(),
        account.getPropicBase64(),
        account.getPassword(),
        account.getAddress());
    this.businessMail = bmail;
    this.drivingLicence = dLicence;
    this.workplace = wPlace;
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

  /**
   * Setter of the business mail.
   *
   * @param bMail business mail
   */
  public void setBusinessMail(final String bMail) {
    this.businessMail = bMail;
  }

  /**
   * Getter of the driving licence.
   *
   * @return driving licence
   */
  public DrivingLicenceType getDrivingLicence() {
    return this.drivingLicence;
  }

  /**
   * Setter of the driving licence.
   *
   * @param dLicence driving licence
   */
  public void setDrivingLicence(final DrivingLicenceType dLicence) {
    this.drivingLicence = dLicence;
  }

  /**
   * Getter of the workplace.
   *
   * @return workplace
   */
  public Deposit getWorkplace() {
    return this.workplace;
  }

  /**
   * Setter of the workplace.
   *
   * @param wPlace workplace
   */
  public void setWorkplace(final Deposit wPlace) {
    this.workplace = wPlace;
  }

  // Methods

  @Override
  public String toString() {
    return getSurname()
        + " "
        + getName()
        + " - "
        + businessMail.substring(0, businessMail.indexOf('@'));
  }
}
