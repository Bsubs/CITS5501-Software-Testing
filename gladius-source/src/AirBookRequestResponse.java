import java.util.Objects;

/** A response to an "air book req" command, as represented
 * by an {@link AirBookRequestCommand} object.
 */
public class AirBookRequestResponse extends CommandResponse {

  final private String confirmationCode;

  /** Construct an AirBookRequestResponse object.
   *
   * If the confirmation code is not 6 characters in length,
   * a {@link SyntacticError} will be thrown.
   *
   * If the confirmation code is of the correct length but is
   * otherwise invalid, a {@link SemanticError} will be thrown.
   *
   * @param confirmationCode A valid 6-character confirmation code.
   */
  public AirBookRequestResponse(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }

  /**
   * @return the confirmationCode
   */
  public String getConfirmationCode() {
    return confirmationCode;
  }

  @Override
  public int hashCode() {
    return Objects.hash(confirmationCode);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof AirBookRequestResponse))
      return false;
    AirBookRequestResponse other = (AirBookRequestResponse) obj;
    return Objects.equals(confirmationCode, other.confirmationCode);
  }

  @Override
  public String toString() {
    return "AirBookRequestResponse [confirmationCode=" + confirmationCode + "]";
  }



}
