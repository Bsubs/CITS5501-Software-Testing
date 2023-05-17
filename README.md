# CITS5501 Project 

## Question 1

*Give a BNF or EBNF grammar that will parse (or generate) valid gladius commands. Your grammar should use the notation accepted by the BNF playground. It must be in plain text which could be pasted into the BNF playground and compiled without error.*

*The grammar should specify any whitespace that needs to appear between other symbols in the command. (As a simplifying assumption, you may assume that a single space character is sufficient as a separator between words.)*

```
<gladius_command> ::= "shop flight fares" <space> <origin> <destination> <trip_type> <cabin_type> <space> <departure_date> | "air book req" <newline> <segment_list> "EOC"

<origin> ::= <letter> <letter> <letter> <space>
<destination> ::= <letter> <letter> <letter> <space>
<letter> ::= "A" | "B" | "C" | "D"

<trip_type> ::= "OneWay " | "Return " <length_of_stay>  <space>
<length_of_stay> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" | "10" | "11" | "12" | "13" | "14" | "15" | "16" | "17" | "18" | "19" | "20"

<cabin_type> ::= "P" | "F" | "J" | "C" | "S" | "Y"

<departure_date> ::= <digit> <digit> <digit> <digit> "-" <digit> <digit> "-" <digit> <digit>
<digit> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" 

<segment_list> ::= <segment> <newline> | <segment> <newline> <segment_list>

<segment> ::= "seg" <space> <origin> <destination> <flight_number> <departure_date> <space> <cabin_type> <space> <num_people>

<flight_number> ::= <airline_code> <digit> <space> | <airline_code> <digit> <digit> <space> | <airline_code> <digit> <digit> <digit> <space> | <airline_code> <digit> <digit> <digit> <digit> <space>
<airline_code> ::= <airline_letter> <airline_letter>
<airline_letter> ::= "I" | "J" | "K" | "L"

<num_people> ::= "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" | "10"

<newline> ::= "\n"
<space> ::= " "
```

## Question 2

*Write a static Java method, countTerminalSymbols, which counts the terminal symbols in a grammar.*
*The signature for the method should be: static int countTerminalSymbols(List`<String>`)*

- List passed in like this: `List.of("<weekendDay>", "::=", "\"sat\"", "|", "\"sun\"")`
    - Output: `<weekendDay>` ::= "sat" | "sun"
- Basically check for anything in double quotes

```
import java.util.List;

public class TerminalSymbolCounter {

    public static void main(String[] args) {
        List<String> myGrammar = List.of("<weekendDay>", "::=", "\"sat\"", "|", "\"sun\"");
        System.out.println(countTerminalSymbols(myGrammar)); // Output: 2
    }
    
    /**
        Counts the number of terminal symbols in a grammar.
        @param grammar the list of strings representing the grammar rules
        @return the count of terminal symbols in the grammar
    */
    public static int countTerminalSymbols(List<String> grammar) {
        int count = 0;

        for (String rule : grammar) {
            String[] tokens = rule.split("\\s+"); // Split rule into tokens
            
            for (String token : tokens) {
                if (token.startsWith("\"") && token.endsWith("\"")) {
                    count++; // Increment count for each terminal symbol
                } 
                else if (token.matches("\\[\\d{1,2}-\\d{1,2}\\]")) {
                    String[] rangeTokens = token.substring(1, token.length() - 1).split("-");
                    int start = Integer.parseInt(rangeTokens[0]);
                    int end = Integer.parseInt(rangeTokens[1]);
                    count += (end - start + 1); // Increment count for each terminal symbol in the range
                }            
                else if (token.matches("\\[\\w-\\w+\\]")) {
                    String[] rangeTokens = token.substring(1, token.length() - 1).split("-");
                    char start = rangeTokens[0].charAt(0);
                    char end = rangeTokens[1].charAt(0);
                    count += (Character.toLowerCase(end) - Character.toLowerCase(start) + 1); // Increment count for each terminal symbol in the range
                }
            }
        }

        return count;
    }
}
```

### Question 3

*Would it be possible to exhaustively test the syntax of gladius commands (that is, to write tests which have derivation coverage)? Why or why not? What about if we restricted ourselves to just the shop flight fare command? Give reasons for your conclusions. (Maximum 500 word answer.)*

It is not possible to exhaustively test the syntax of gladius commands. 

Derivation Coverage implies that test requirements encompass every possible string derivable from grammar G. In the context of this assignment, the grammar of concern is the `<command>` grammar developed in Question 1. To achieve derivation coverage, we need to comprehensively test each string from both the `air book req` and `shop flight fares` commands.

However, exhaustively testing the syntax of GLADUS commands is not possible, primarily due to the AirBookRequest command. The AirBookRequest command starts with `air book req`, includes an arbitrary number of segments, and concludes with `EOC`. The grammar for the segment_list, representing the segments, is displayed below:

`<segment_list> ::= <segment> <newline> | <segment> <newline> <segment_list>`

The grammar consists of two production rules:

1. `<segment> <newline>`: This rule denotes that a segment_list can be a single segment followed by a newline character.
2. `<segment> <newline> <segment_list>`: This rule indicates that a segment_list can be a segment followed by another segment_list. This allows for recursive concatenation of multiple segments.

The second production rule means that the segment_list grammar can comprise any combination of segments in any order, with no restrictions on segment repetitions. Consequently, the segment_list can become infinitely large, rendering it impossible to exhaustively test the syntax of GLADUS commands.

---

By focusing solely on the shop flight fare command, attaining derivation coverage becomes possible. The `shop flight fares` command is structured as follows:

`<command> ::= "shop flight fares " <origin> <destination> <trip_type> <cabin_type> <departure_date>`

The following is the breakdown for each of the number of possibilities for each non-terminal symbol in the production:

1. `<origin>`: 3-letter IATA code consisting of letters from 'A' to 'D'. Total combinations = 4^3 = 64
2. `<destination>`: 3-letter IATA code consisting of letters from 'A' to 'D'. Total combinations = 4^3 = 64
3. `<trip_type>`: 2 possible trip types: "OneWay" and "Return"
    - "Return" has 21 possible combinations due to the "Length of Stay" ranging from 0-20
    - Total = 1 + 21 = 22
4. `<cabin_type>`: 6 possible cabin types (as stated in assignment sheet)
5. `<departure_date>`: 
    - YYYY: 4-digit combination with 10 possible digits: 10^4 = 10,000
    - MM: 2-digit combination with 10 possible digits: 10^2 = 100
    - DD: 2-digit combination with 10 possible digits: 10^2 = 100
    - Total = 10,000 * 100 * 100 = 100,000,000

Total number of combinations: 64 * 64 * 22 * 6 * 100,000,000 = 536,870,912,000,000

There are a more than 500 trillion combinations required to achieve derivation coverage of the `shop flight fare` command. This is a finite number which shows that it is possible. However, it is not practical as it would require a considerable amount of time and computational resources to process such a large number of test cases.

## Question 4

*Write a static Java method, countProductions, which counts the number of productions in a grammar (see the Amman and Offutt text for a definition of “production”). The signature for the method should be: static int countTerminalSymbols(List`<String>`). The input will be a grammar in BNF or EBNF format (specifically, the notation accepted by the BNF playground), as detailed in question 2.*

- Symbols in the grammar are either nonterminals, which must be rewritten further, or terminals, for which no rewriting is possible.
    - Everything in quotes is a terminal. i.e. "A"
    - Each possible rewriting of a given nonterminal is called a production or rule.
    - We assume that any `<>` which is immediately followed by a `::=` is not a production

```
import java.util.List;

public class ProductionCounter {

    public static void main(String[] args) {
        List<String> myGrammar = List.of("<weekendDay>", "::=", "\"sat\"", "|", "<one_two>", "\"sun\"", "<day>");
        System.out.println(countProductions(myGrammar)); // Output: 2
    }

    /**
        Counts the number of productions in a grammar.
        @param grammar the list of strings representing the grammar rules
        @return the count of productions in the grammar
    */
    public static int countProductions(List<String> grammar) {
        int count = 0;
        
        // Adds a count for each instance of "|" which symbolizes a production
        for (String symbol : grammar) {
            if (symbol.equals("|")) {
                count++;
            }
        }

        // Add one for each production, as the first production does not have a preceding "|"
        for (String symbol : grammar) {
            if (symbol.contains("::=")) {
                count++;
            }
        }
        
        // Accounts for BNF playground syntactic sugar where [1-x] represents x productions 
        for (String symbol : grammar) {
            String[] tokens = symbol.split("\\s+"); // Split rule into tokens
            
            for (String token : tokens) {
                // Accounts for digits 
                if (token.matches("\\[\\d{1,2}-\\d{1,2}\\]")) {
                    String[] rangeTokens = token.substring(1, token.length() - 1).split("-");
                    int start = Integer.parseInt(rangeTokens[0]);
                    int end = Integer.parseInt(rangeTokens[1]);
                    count += (end - start); // Increment count for each terminal symbol in the range
                }            
                // Accounts for characters
                else if (token.matches("\\[\\w-\\w+\\]")) {
                    String[] rangeTokens = token.substring(1, token.length() - 1).split("-");
                    char start = rangeTokens[0].charAt(0);
                    char end = rangeTokens[1].charAt(0);
                    count += (Character.toLowerCase(end) - Character.toLowerCase(start)); // Increment count for each terminal symbol in the range
                }
            }
        }


        return count;
    }
}
```

## Question 5

*Describe in detail the preconditions and postconditions of the constructor for the ShopFlightFareCommand class, justifying your answer.*

Preconditions are any conditions which should be satisfied by the parameters or system state when the ShopFlightFare command is called. If the preconditions for the method are not satisfied, then the behaviour is undefined. The system does not guarantee any behaviour. 

It should be noted that the Javadoc for the ShopFlightFareCommand is incomplete. It should say that `This is a precondition of the method` or `The behaviour of the method is undefined if this condition is not satisfied` in order to make it complete. However, the following preconditions will be based on the definition above. 

- The origin, destination, tripType, lengthOfStay, cabinType, and departureDate parameters must be provided as input to the constructor.
- The origin and destination should be syntactically valid 3-letter IATA codes.
- The tripType should be either ONE_WAY or RETURN.
- If the tripType is RETURN, the lengthOfStay should be a number from 0 to 20 (inclusive).
- The cabinType should be a syntactically and semantically valid cabin type code.
- The departureDate should be a syntactically valid date
- The currency code should be syntatically valid
- The departureDateTime should be a syntactically valid dateTime
- returnDateTime can be null or a non-null value depending on tripType
- flightNumber should be a syntatically valid flight number 

---

Postconditions are what the ShopFlightFare command returns after a successful execution. This also included any side effects of the functions. (changes to the system state). In Java throwing exceptions is another type of postcondition. The programmer is guaranteeing the behaviour of the method in that scenario. Therefore, we will count all the possible exceptions thrown as part of the postconditions. 

- A ShopFlightFareResponse object is constructed and returned upon successful execution.
- A SyntacticError is thrown if the currencyCode or flightNumber are not syntactically valid.
- A SemanticError is thrown is one of the following parameters are syntactically valid, but are incorrect in one of the following ways:
    - When the currencyCode is not a valid ISO 4217 currency code.
    - When the departureDateTime is on or before the current date.
    - When the flightNumber is not a valid flight number.
    - When the trip type is ONE_WAY, but a non-null returnDateTime is specified.
    - When a non-null returnDateTime is specified, but is before the departureDateTime.
    - When a negative fare is specified.


## Question 6

*Apply Input Space Partitioning (ISP) to the constructor for the SegmentSubcommand constructor, explaining in detail the steps you take and what characteristics and partitions you would use.*

*You should describe*:
- *eight characteristics that would be useful for testing the constructor.*
- *three test cases in detail, including all fixtures, test values and expected values.*

*You need not exhaustively describe all characteristics, partitions, test cases and values that would be needed, but should briefly discuss what more would be needed – beyond the eight characteristics and three test cases you have described – to achieve Base Choice Coverage, and how you would know when it was achieved.*

Input Space Partitioning (ISP) is a technique that involves dividing the input space into partitions. Partitions are a collection of disjoint sets which cover the domain of the function that we are trying to model. 

For the parameters origin, destination and flight number, syntactic correctness will be part of the partitions tested. As a postcondition, the method throws an exception if one of the above parameters is syntactically incorrect so that will need to be tested. 

For the parameters cabinType, departureDate and numPeople, syntatic correctness will not be one of the partitions tested as the syntatic correctness of those parameters are assumed preconditions of the method. Behaviour is undefined in the event that one of these parameters is not syntactically correct. 

We will also not include any partitions that involve passing in invalid types (such as a int into a Sting parameter) as the code would not compile. 

- `origin`: The origin airport code. It should be a valid 3-letter IATA airport code. Partitions: 
    - Semantically valid codes: This is the base choice
    - Semantically invalid codes: This tests the ability of the method to throw a SemanticError due to invalid IATA codes.
    - 

- `destination`: The destination airport code. It should be a valid 3-letter IATA airport code. Partitions: valid codes, invalid codes, non-string inputs, null/empty.
    - Semantically valid codes: This is the base choice
    - Semantically invalid codes: This tests the ability of the method to throw a SemanticError due to invalid IATA codes.

- `origin & destination`: This tests if the parser can correctly detect syntactically valid and invalid codes
    - Syntactically Valid Codes: This is the base choice
    - Syntactically Invalid Codes: This tests the ability of the method to throw a Syntactic Error when presented with IATA codes greater or lesser than 3 letters long.

- `origin & destination`: The origin airport code should be different from the destination airport code. This is a compound characteristic involving both the origin and destination. There will be no need to test for syntax or semantics here as the ability of the class to handle those situations should have already been tested in the previous partition.
    - Where origin and destination are different, syntactically and semantically correct IATA codes. This is the base choice
    - Where origin and destination are the same, syntactically and semantically correct IATA codes

- `flightNumber`: The flight number. It should be a valid flight number. 
    - Syntactically and semantically valid codes: This is the base choice
    - Syntactically valid but semantically invalid codes: This tests the ability of the method to throw a SemanticError due to invalid flight numbers.
    - Syntactically Invalid Codes: This tests the ability of the method to throw a SyntacticError when presented with flight numbers that do not adhere to the definition of flight numbers as per the assignment specification.

- `departureDate`: The departure date. It should be a date in the future. 
    - Future Date: This represents a date after the current date. This is the base choice and tests for what happens when a valid departure date is input. This is the base choice.
    - Past Date: This represents a date that is strictly before the current date. This tests the ability of the method to throw a SemanticError due to invalid date.
    - Current Date: This represents a boundary case where the departure date is set to be the current date. 

- `cabinType`: The cabin type. Partitions: valid cabin types, invalid cabin types, non-string inputs, null/empty.
    - Valid Cabin Type: One of the 6 possible cabin types. This is the base choice.
    - Invalid Cabin Type: Any letter that is not one of the 6 possible cabin types

- `numPeople`: The number of people for which the booking is to be made. It should be between 1 and 10, inclusive. 
    - 1: Boundary Case. This is the base choice.
    - 10: Boundary Case
    - -1: Negative Case
    - 11: Exceeds 10

Test Cases:

- Test Case 1 (Test ID: 01)
    - Fixtures: Origin = "JFK", Destination = "LAX", FlightNumber = "AA123", DepartureDate = Future Date, CabinType = "Economy", NumPeople = 2.
    - Test Values: All fixtures are valid values for their respective characteristics.
    - Expected Values: The constructor should successfully create a SegmentSubcommand object with these attributes.

- Test Case 2 (Test ID: 02)
    - Fixtures: Origin = "JFK", Destination = "JFK", FlightNumber = "AA123", DepartureDate = Future Date, CabinType = "Economy", NumPeople = 2.
    - Test Values: The origin is the same as the destination.
    - Expected Values: A SemanticError should be thrown because the origin is the same as the destination.

- Test Case 3 (Test ID: 03)
    - Fixtures: Origin = "JFK", Destination = "LAX", FlightNumber = "AA123", DepartureDate = Past Date, CabinType = "Economy", NumPeople = 2.
    - Test Values: The DepartureDate is a date in the past.
    - Expected Values: A SemanticError should be thrown because the departure date is on or before the current date.

To achieve Base Choice Coverage, we would need to create test cases for each characteristic where we use the base choice for that characteristic and the base choices for all other characteristics. Then, we would create additional test cases where we use other choices for one characteristic while keeping the base choices for all other characteristics.

For example, for the "Origin" characteristic, we would first use a valid IATA code (the base choice) and the base choices for all other characteristics. Then, we would create test cases where we use an invalid IATA code, for the "Origin", while using the base choices for all other characteristics.

We would know Base Choice Coverage was achieved when we had tested each characteristic with its base choice and at least one other choice, and all other characteristics were at their base choice. This would mean we had tested the function's behaviour for all characteristics with both their most common inputs and at least one less common or edge case input, while controlling for potential interactions between characteristics.



## Question 7 

*Write a test class using JUnit 5 called SegmentSubcommandTest, which contains @Test methods which implement the three test cases you described in question 6. Skeleton code for this class is provided in the supplied Java code, in the “test” directory. Your test cases should implement all appropriate best practices for unit tests*

```
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;


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

```

## Question 8 

*Describe a set of test cases for the CommandParser.parse() method which have production coverage of the grammar you specified as an answer to question 1. Explain why it is that your test cases satisfy this coverage criterion.*

Production coverage means that each production is covered by at least one test case. In the case of the `<gladius_command>` grammer, the breakdown of the number of productions for each rule is as follows:

- `<gladius_command>`: 2
- `<origin>`: 1
- `<destination>`:1
- `<letter>`: 4
- `<trip_type>`: 2
- `<length_of_stay>`: 21
- `<cabin_type>`: 6
- `<departure_date>`: 1
- `<digit>`: 10
- `<segment_list>`: 2
- `<segment>`: 1
- `<flight_number>`: 4
- `<airline_code>`: 1
- `<airline_letter>`: 4
- `<num_people>`: 10
- `<newline>`: 1
- `<space>`: 1

Summing these up, there are a total of 72 total productions in the `<gladius_command>` grammer, a maximum of 72 tests will be required to achieve production coverage of the language by exercising each one of the productions. However, we can achieve production coverage using only 30 tests:

1. `shop flight fares ABCD DCBA OneWay P 0123-45-67`
2. `shop flight fares ABCD DCBA Return 0 F 0123-45-89`
3. `shop flight fares ABCD DCBA Return 1 J 0123-45-89`
4. `shop flight fares ABCD DCBA Return 2 C 0123-45-89`
5. `shop flight fares ABCD DCBA Return 3 S 0123-45-89`
6. `shop flight fares ABCD DCBA Return 4 Y 0123-45-89`
7. `shop flight fares ABCD DCBA Return 5 Y 0123-45-89`
8. `shop flight fares ABCD DCBA Return 6 Y 0123-45-89`
9. `shop flight fares ABCD DCBA Return 7 Y 0123-45-89`
10. `shop flight fares ABCD DCBA Return 8 Y 0123-45-89`
11. `shop flight fares ABCD DCBA Return 9 Y 0123-45-89`
12. `shop flight fares ABCD DCBA Return 10 Y 0123-45-89`
13. `shop flight fares ABCD DCBA Return 11 Y 0123-45-89`
14. `shop flight fares ABCD DCBA Return 12 Y 0123-45-89`
15. `shop flight fares ABCD DCBA Return 13 Y 0123-45-89`
16. `shop flight fares ABCD DCBA Return 14 Y 0123-45-89`
17. `shop flight fares ABCD DCBA Return 15 Y 0123-45-89`
18. `shop flight fares ABCD DCBA Return 16 Y 0123-45-89`
19. `shop flight fares ABCD DCBA Return 17 Y 0123-45-89`
20. `shop flight fares ABCD DCBA Return 18 Y 0123-45-89`
21. `shop flight fares ABCD DCBA Return 19 Y 0123-45-89`
22. `shop flight fares ABCD DCBA Return 20 Y 0123-45-89`

23. `air book req\nseg ABCD DCBA IJ0 2023-12-12 P 1\nEOC`
24. `air book req\nseg ABCD DCBA KL12 2023-12-12 P 2\nseg ABCD DCBA KL345 2023-12-12 P 3\nseg ABCD DCBA KL6789 2023-12-12 P 4\nEOC`
25. `air book req\nseg ABCD DCBA IJ0 2023-12-12 P 5\nEOC`
26. `air book req\nseg ABCD DCBA IJ0 2023-12-12 P 6\nEOC`
27. `air book req\nseg ABCD DCBA IJ0 2023-12-12 P 7\nEOC`
28. `air book req\nseg ABCD DCBA IJ0 2023-12-12 P 8\nEOC`
29. `air book req\nseg ABCD DCBA IJ0 2023-12-12 P 9\nEOC`
30. `air book req\nseg ABCD DCBA IJ0 2023-12-12 P 10\nEOC`

It is important to note that production coverage could have been achieved in 24 tests. This would be done by simply adding the segments from 25-20 to the segment list in 24. However, they have been formatted as 30 tests for ease of reading. The 30 tests cases above exercise each production in the grammer at least once. Therefore, it can be said that we have achieved production coverage. 

## Question 10

*Your colleague Oreb has been reading about logic-based testing, and wonders whether it would be a good use of time to assess – once an initial suite of tests is in place – what level of coverage the tests for the gladius system have (e.g. do they have Active Clause Coverage)?*

*Answer the following question: Would measuring logic-based coverage for the gladius system be a good use of the team’s time? Why or why not? Explain your reasoning.*

Logic based testing models the internal structure of the boolean conditions in the Gladius system. Logic expressions show up in every program whenever a user or the program makes a decision based on a condition. In this way, logig based coverage aims to assess the extent to which the different logical conditions and paths within a program or system have been executed.

The aim would be to ensure that all these paths are covered by the tests, thereby ensuring that the function can handle all possible inputs and scenarios. This would include not only expected values and edge cases but also incorrect and unexpected inputs to test the robustness of the command and how it handles errors.

In order to achieve active clause coverage we need to find values for each clause c in the predicate p, we find values for all the other clauses in p such that c determines p. In the context of gladius commands, this means that we need to make sure that for each of our clauses in the command we need to make the validity of the entire predicate hinge on that. This can be achieved by passing valid values for each of the other clauses and only changing the clauses we are looking at. 

For example, consider the `shop flight fares ORIGIN DESTINATION TRIP_TYPE [LENGTH_OF_STAY] CABIN_TYPE DEPARTURE_DATE` command. If we want ORIGIN to determine the validity of the command, we can use the 2 following commands:

- `shop flight fares JFK LAX Return 5 P 2023-06-01` Expected output: true
- `shop flight fares HHG LAX Return 5 P 2023-06-01` Expected output: false

Destination, trip type, length of stay, cabin type and departure date are correct, leaving the fate of the predicate hinging on the ORIGIN variable. 

Applying logic based testing to the Gladius system is also not very difficult as we have great controllabiility of the variables. We are able to directly assign values to each of our variables through the command line and not have to deal with internal variables. 