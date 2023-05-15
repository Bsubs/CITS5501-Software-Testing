import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;


class SegmentSubcommandTest {

  @Test
  void testSegmentSubcommand() {
    // Test Case 1: Valid Inputs
    try {
      SegmentSubcommand segmentSubcommand1 = new SegmentSubcommand("JFK", "LAX", "AA123", LocalDate.now().plusDays(5), CabinType.EconomyClass, 2);
      assertNotNull(segmentSubcommand1);
    } catch (Exception e) {
      fail("Exception should not be thrown with valid inputs.");
    }

    // Test Case 2: Origin and Destination are the same
    try {
      SegmentSubcommand segmentSubcommand2 = new SegmentSubcommand("JFK", "JFK", "AA123", LocalDate.now().plusDays(5), CabinType.EconomyClass, 2);
      fail("Exception should have been thrown when origin and destination are the same.");
    } catch (SemanticError e) {
      // Expected error, so the test passes.
    } catch (Exception e) {
      fail("Unexpected exception type thrown.");
    }

    // Test Case 3: Departure Date is in the past
    try {
      SegmentSubcommand segmentSubcommand3 = new SegmentSubcommand("JFK", "LAX", "AA123", LocalDate.now().minusDays(5), CabinType.EconomyClass, 2);
      fail("Exception should have been thrown when departure date is in the past.");
    } catch (SemanticError e) {
      // Expected error, so the test passes.
    } catch (Exception e) {
      fail("Unexpected exception type thrown.");
    }
  }
}
