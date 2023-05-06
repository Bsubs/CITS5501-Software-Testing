

/** The interface for systems that can process
 * a {@link Command} and return a {@link CommandResponse}.
 */
public interface CommandProcessor {

  /** Process a {@link Command} and return a response.
   *
   * If the command cannot be processed because the
   * system is unreachable or an internal error
   * occurs in the system, a {@link ProcessingError} will be thrown.
   *
   * Each sub-type of command, when processed, must return
   * the equivalent type of {@link CommandResponse}.
   *
   * @param command A command to be processed.
   * @return A response to the command.
   * @throws ProcessingError
   */
  public CommandResponse process(Command command) throws ProcessingError;

}
