import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


class SegmentSubcommandTest {
  /**
   * Sets up the test fixture.
   *
   * Called before every test case method.
   */
  @BeforeEach
  public void setUp()
  {
  }

  /**
   * Tears down the test fixture.
   *
   * Called after every test case method.
   */
  @AfterEach
  public void tearDown()
  {
  }

  
  /**
    Test case for validating a valid SegmentSubcommand object.
    Test ID: 01
  */
  @Test
  public void testValidSegmentSubcommand() {
    SegmentSubcommand segmentSubcommand1 = new SegmentSubcommand("JFK", "LAX", "AA123", LocalDate.now().plusDays(5), CabinType.EconomyClass, 2);
    String result = segmentSubcommand1.toString();
    assertEquals("SegmentSubcommand [origin=JFK, destination=LAX, flightNumber=AA123, departureDate=2023-05-22, cabinType=EconomyClass, numPeople=2]", result, "the result should be the same as the data entered");
  }
  
  /**
    Test case for ensuring that a SemanticError is thrown when the origin and destination are the same.
    Test ID: 02
  */
  @Test
  public void testSameOriginDestination() {
      assertThrows(SemanticError.class, () -> {
          SegmentSubcommand segmentSubcommand2 = new SegmentSubcommand("JFK", "JFK", "AA123", LocalDate.now().plusDays(5), CabinType.EconomyClass, 2);
      });
  }
  
  /**
    Test case for ensuring that a SemanticError is thrown when the departure date is in the past.
    Test ID: 03
  */
  @Test
  public void testDeparturreDate() {
    assertThrows(SemanticError.class, () -> {
      SegmentSubcommand segmentSubcommand3 = new SegmentSubcommand("JFK", "LAX", "AA123", LocalDate.now().minusDays(5), CabinType.EconomyClass, 2);
    });
}


}
