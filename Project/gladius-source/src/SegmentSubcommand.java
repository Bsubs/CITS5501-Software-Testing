import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a segment subcommand: a reservation on
 * a direct flight between two locations.
 */
public class SegmentSubcommand {

  final private String origin;
  final private String destination;
  final private String flightNumber;
  final private LocalDate departureDate;
  final private CabinType cabinType;
  final private int numPeople;

  /** Construct a SegmentSubCommand object.
   *
   * If the origin, destination or flight number are syntactically invalid,
   * a {@link SyntacticError} is thrown.
   *
   * A {@link SemanticError} is thrown in the following cases where a parameter
   * is syntactically valid, but is invalid for other reasons:
   *
   * - If the origin is the same as the destination
   * - If the origin or destination are not valid IATA airport codes
   * - If the flightNumber is not a valid flight number.
   * - If the departure date for the flight is on or before the current
   *   date
   * - If the numPeople parameter is not between 1 and 10, inclusive.
   *
   * @param origin The origin location, a valid 3-letter IATA airport code.
   * @param destination The destination location, a valid 3-letter IATA airport code.
   * @param flightNumber A valid flight number.
   * @param departureDate The departure date for the flight, which must be after the current date.
   * @param cabinType The cabin type.
   * @param numPeople The number of seats to book.
   */
  public SegmentSubcommand(String origin, String destination, String flightNumber, LocalDate departureDate,
      CabinType cabinType, int numPeople) {
    this.origin = origin;
    this.destination = destination;
    this.flightNumber = flightNumber;
    this.departureDate = departureDate;
    this.cabinType = cabinType;
    this.numPeople = numPeople;
  }

  /**
   * @return the origin
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * @return the destination
   */
  public String getDestination() {
    return destination;
  }

  /**
   * @return the flightNumber
   */
  public String getFlightNumber() {
    return flightNumber;
  }

  /**
   * @return the departureDate
   */
  public LocalDate getDepartureDate() {
    return departureDate;
  }

  /**
   * @return the cabinType
   */
  public CabinType getCabinType() {
    return cabinType;
  }

  /**
   * @return the numPeople
   */
  public int getNumPeople() {
    return numPeople;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cabinType, departureDate, destination, flightNumber, numPeople, origin);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof SegmentSubcommand))
      return false;
    SegmentSubcommand other = (SegmentSubcommand) obj;
    return cabinType == other.cabinType && Objects.equals(departureDate, other.departureDate)
        && Objects.equals(destination, other.destination) && Objects.equals(flightNumber, other.flightNumber)
        && numPeople == other.numPeople && Objects.equals(origin, other.origin);
  }

  @Override
  public String toString() {
    return "SegmentSubcommand [origin=" + origin + ", destination=" + destination + ", flightNumber=" + flightNumber
        + ", departureDate=" + departureDate + ", cabinType=" + cabinType + ", numPeople=" + numPeople + "]";
  }



}
