import java.time.LocalDate;
import java.util.Objects;


/** Data required for a valid "shop flight fare" command.
 */
public class ShopFlightFareCommand extends Command {

  final private String origin;
  final private String destination;
  final private TripType tripType;
  final private Integer lengthOfStay; // null if this is a one-way trip
  final private CabinType cabinType;
  final private LocalDate departureDate;

  /** Construct a ShopFlightFareCommand object.
   *
   * If the origin or destination are not syntactically valid,
   * a {@link SyntacticError} is thrown.
   *
   * If the parameters are syntactically valid, but are incorrect
   * in one of the following ways, then a {@link SemanticError} is
   * thrown:
   *
   * - When the origin is the same as the destination
   * - When the origin or destination are not valid IATA airport codes
   * - When the flightNumber is not a valid flight number.
   * - When the trip type is ONE_WAY, but a non-null lengthOfStay
   *   is specified.
   * - When a non-null lengthOfStay is specified, but is not a number
   *   from 0 to 20 (inclusive).
   *
   * @param origin The 3-letter IATA code for the origin.
   * @param destination The 3-letter IATA code for the destination.
   * @param tripType Whether the trip is one-way or return.
   * @param lengthOfStay If the tripType is ONE_WAY, this should be null.
   *            Otherwise, a length of stay in days.
   * @param cabinType A cabin type code representing a seating location.
   * @param departureDate A departure date. This must be after, but no more than 100 days
   *            past the current date, or a {@link SemanticError} will be
   *            thrown.
   */
  public ShopFlightFareCommand(String origin, String destination, TripType tripType, Integer lengthOfStay,
      CabinType cabinType, LocalDate departureDate) {
    this.origin = origin;
    this.destination = destination;
    this.tripType = tripType;
    this.lengthOfStay = lengthOfStay;
    this.cabinType = cabinType;
    this.departureDate = departureDate;
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
   * @return the tripType
   */
  public TripType getTripType() {
    return tripType;
  }


  /**
   * @return the lengthOfStay
   */
  public Integer getLengthOfStay() {
    return lengthOfStay;
  }


  /**
   * @return the cabinType
   */
  public CabinType getCabinType() {
    return cabinType;
  }


  /**
   * @return the departureDate
   */
  public LocalDate getDepartureDate() {
    return departureDate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cabinType, departureDate, destination, lengthOfStay, origin, tripType);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof ShopFlightFareCommand))
      return false;
    ShopFlightFareCommand other = (ShopFlightFareCommand) obj;
    return cabinType == other.cabinType && Objects.equals(departureDate, other.departureDate)
        && Objects.equals(destination, other.destination) && Objects.equals(lengthOfStay, other.lengthOfStay)
        && Objects.equals(origin, other.origin) && tripType == other.tripType;
  }

  @Override
  public String toString() {
    return "ShopFlightFareCommand [origin=" + origin + ", destination=" + destination + ", tripType=" + tripType
        + ", lengthOfStay=" + lengthOfStay + ", cabinType=" + cabinType + ", departureDate=" + departureDate + "]";
  }


}
