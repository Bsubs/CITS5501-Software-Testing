
/**
 * Parses a sequence of lines into a {@link Command}.
 */
public interface CommandParser {

  /**
   * Parses a sequence of lines into a {@link Command}.
   * If an appropriate Command cannot be constructed,
   * one of the subtypes of {@link GladiusException} will be thrown.
   *
   * @param args
   */
  public void parse(String[] lines) throws GladiusException;


}
