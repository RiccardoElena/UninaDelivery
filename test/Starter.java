package test;

/**
 * This is the starter class for the test package.
 *
 * <br>
 * This class has method main() as entry point.
 *
 * <br>
 *
 * Being a utility class, it cannot be instantiated,
 * so it throws and exeption trying.
 *
 * <br>
 *
 * @version 1.0
 * @author Riccardo Elena
 */

public final class Starter {

  // Contructors

  private Starter() {
    throw new UnsupportedOperationException(
      "This is a utility class and cannot be instantiated");
  }

  // Methods

  /**
   * This is the entry point for the test package.
   *
   * @param args the command line arguments
   */
  public static void main(final String[] args) {
    System.out.println("Hello World!");
  }
}

