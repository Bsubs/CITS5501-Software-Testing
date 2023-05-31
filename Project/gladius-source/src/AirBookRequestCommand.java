import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Represents the data needed for an air book req
 * command.
 */
public class AirBookRequestCommand extends Command {

  final private List<SegmentSubcommand> segments;

  /** Construct an AirBookRequestCommand object. If an empty
   * list is provided to the constructor, a {@link SemanticError}
   * will be thrown.
   *
   * @param segments A non-empty list of {@link SegmentSubcommand} contained in the command.
   */
  public AirBookRequestCommand(List<SegmentSubcommand> segments) {
    this.segments = Collections.unmodifiableList(segments);
  }

  /**
   * @return the segments
   */
  public List<SegmentSubcommand> getSegments() {
    return segments;
  }

  @Override
  public int hashCode() {
    return Objects.hash(segments);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof AirBookRequestCommand))
      return false;
    AirBookRequestCommand other = (AirBookRequestCommand) obj;
    return Objects.equals(segments, other.segments);
  }

  @Override
  public String toString() {
    return "AirBookRequestCommand [segments=" + segments + "]";
  }

}
