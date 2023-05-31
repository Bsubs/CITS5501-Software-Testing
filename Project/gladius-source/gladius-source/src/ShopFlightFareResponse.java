
import java.time.LocalDateTime;
import java.util.Objects;


/** A response to a "shop flight fare" command
 * (which command is represented by a {@link ShopFlightFareCommand}
 * object).
 */
public class ShopFlightFareResponse extends CommandResponse {

  final private String currencyCode;
  final private LocalDateTime departureDateTime;
  final private TripType tripType;
  final private LocalDateTime returnDateTime; // null if this is a one-way trip
  final private long fare;
  final private String flightNumber;

  /** Construct a ShopFlightFareResponse object.
   *
   * If the currency code or flight number are not syntactically valid,
   * a {@link SyntacticError} is thrown.
   *
   * If the parameters are syntactically valid, but are incorrect
   * in one of the following ways, then a {@link SemanticError} is
   * thrown:
   *
   * - When the currencyCode is not a valid ISO 4217 currency code.
   * - When the departureDateTime is on or before the current date.
   * - When the flightNumber is not a valid flight number.
   * - When the trip type is ONE_WAY, but a non-null returnDateTime
   *   is specified.
   * - When a non-null returnDateTime is specified, but is before
   *   the departureDateTime.
   * - When a negative fare is specified.
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
   *
   * @param currencyCode An ISO 4127 currency code.
   * @param departureDateTime A departure dateTime, which must be after the
   *          current date.
   * @param tripType The trip type, either ONE_WAY or RETURN.
   * @param returnDateTime For a one-way trip, this must be null; but
   *          for a return trip, this must be a non-null value
   *          specifying the departure date and time for the return trip.
   *          If specified, the returnDateTime must be after the
   *          departureDateTime.
   * @param fare The fare in the specified currency.
   * @param flightNumber A valid flight number.
   */
  public ShopFlightFareResponse(String currencyCode, LocalDateTime departureDateTime, TripType tripType,
      LocalDateTime returnDateTime, long fare, String flightNumber) {
    this.currencyCode = currencyCode;
    this.departureDateTime = departureDateTime;
    this.tripType = tripType;
    this.returnDateTime = returnDateTime;
    this.fare = fare;
    this.flightNumber = flightNumber;
  }

  /**
   * @return the currencyCode
   */
  public String getCurrencyCode() {
    return currencyCode;
  }

  /**
   * @return the departureDateTime
   */
  public LocalDateTime getDepartureDateTime() {
    return departureDateTime;
  }

  /**
   * @return the tripType
   */
  public TripType getTripType() {
    return tripType;
  }

  /**
   * @return the returnDateTime
   */
  public LocalDateTime getReturnDateTime() {
    return returnDateTime;
  }

  /**
   * @return the fare
   */
  public long getFare() {
    return fare;
  }

  /**
   * @return the flightNumber
   */
  public String getFlightNumber() {
    return flightNumber;
  }

  @Override
  public int hashCode() {
    return Objects.hash(currencyCode, departureDateTime, fare, flightNumber, returnDateTime, tripType);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof ShopFlightFareResponse))
      return false;
    ShopFlightFareResponse other = (ShopFlightFareResponse) obj;
    return Objects.equals(currencyCode, other.currencyCode)
        && Objects.equals(departureDateTime, other.departureDateTime) && fare == other.fare
        && Objects.equals(flightNumber, other.flightNumber) && Objects.equals(returnDateTime, other.returnDateTime)
        && tripType == other.tripType;
  }

  @Override
  public String toString() {
    return "ShopFlightFareResponse [currencyCode=" + currencyCode + ", departureDateTime=" + departureDateTime
        + ", tripType=" + tripType + ", returnDateTime=" + returnDateTime + ", fare=" + fare + ", flightNumber="
        + flightNumber + "]";
  }


}
