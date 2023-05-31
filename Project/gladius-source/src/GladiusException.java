
/**
 * The base class for GLADIUS-specific exceptions.
 */
public class GladiusException extends Exception {

  private static final long serialVersionUID = -9178809530532064733L;

  /** Constructs a new exception with the specified detail
   *  message.  The cause is not initialized.
   *  @param message The detail message which is later
   *                 retrieved using the getMessage method
  **/
  public GladiusException(String message) {
    super(message);
  }

  /** Constructs a new exception with the specified detail
   *  message and cause.
   *
   *  @param message The detail message which is later retrieved
   *                 using the getMessage method
   *  @param cause   The cause which is saved for later
   *                 retrieval by the getCause method
  **/
  public GladiusException(String message, Throwable cause) {
    super(message, cause);
  }

}


