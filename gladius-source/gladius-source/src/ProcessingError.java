
/**
 * An exception thrown when a {@link Command} is unable to
 * be processed by a {@link CommandProcessor}.
 */
public class ProcessingError extends RuntimeException {

  private static final long serialVersionUID = 8089089744207113825L;

  /** Constructs a new exception with the specified detail
   *  message.  The cause is not initialized.
   *  @param message The detail message which is later
   *                 retrieved using the getMessage method
  **/
  public ProcessingError(String message) {
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
  public ProcessingError(String message, Throwable cause) {
    super(message, cause);
  }

}


