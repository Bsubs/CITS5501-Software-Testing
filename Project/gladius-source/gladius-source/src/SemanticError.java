
/**
 * An exception thrown when a parameter is syntactically
 * invalid.
 */
public class SemanticError extends GladiusException {

  private static final long serialVersionUID = -716635435516695361L;

  /** Constructs a new exception with the specified detail
   *  message.  The cause is not initialized.
   *  @param message The detail message which is later
   *                 retrieved using the getMessage method
  **/
  public SemanticError(String message) {
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
  public SemanticError(String message, Throwable cause) {
    super(message, cause);
  }

}


