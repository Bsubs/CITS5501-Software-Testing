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

    static int countTerminalSymbols(List<String> grammar) {
        int terminalSymbolsCount = 0;

        for (String symbol : grammar) {
            if (isTerminalSymbol(symbol)) {
                terminalSymbolsCount++;
            }
        }

        return terminalSymbolsCount;
    }

    private static boolean isTerminalSymbol(String symbol) {
        if (symbol.length() >= 2) {
            return symbol.charAt(0) == '"' && symbol.charAt(symbol.length() - 1) == '"';
        }
        return false;
        
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

    public static int countProductions(List<String> grammar) {
        int count = 0;
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

Input Space Partitioning (ISP) is a technique that involves dividing the imput space into partitions. Partitions are a collection of disjoint sets which cover the domain of the function that we are trying to model. 

For the parameterrs origin, destination and flight number, syntatic correctness will be part of the partitions tested. As a postcondition, the method throws an exception if one of the above parameters is syntatically incorrect so that will need to be tested. 

For the parameters cabinType, departureDate and numPeople, syntatic correctness will not be one of the partitions tested as the syntatic corrrectness of those parameters are assumed preconditions of the method. Behaviour is undefined in the event that one of these parameters is not syntatically correct. 

Attempting to pass in null values will not be tested as this is simply testing the ability of the java virtual machine to detech null values. We will also not include any partitions that involve passing in invalid types (such as a int into a Sting parameter) as the code would not compile. 

- `origin`: The origin airport code. It should be a valid 3-letter IATA airport code. Partitions: 
    - Syntatically and semantically valid codes: This is the base choice and tests for what happens when a valid code is input.
    - Syntatically valid but semantically invalid codes: This tests the ability of the method to throw a SemanticError due to invalid IATA codes.
    - Syntatically Invalid Codes: This tests the ability of the method to throw a SyntacticError when presented with IATA codes greater or lesser than 3 letters long.

- `destination`: The destination airport code. It should be a valid 3-letter IATA airport code. Partitions: valid codes, invalid codes, non-string inputs, null/empty.
    - Syntatically and semantically valid codes: This is the base choice and tests for what happens when a valid code is input.
    - Syntatically valid but semantically invalid codes: This tests the ability of the method to throw a SemanticError due to invalid IATA codes.
    - Syntatically Invalid Codes: This tests the ability of the method to throw a SyntacticError when presented with IATA codes greater or lesser than 3 letters long.

- `origin & destination`: The origin airport code should be different from the destination airport code. This is a compound characteristic involving both the origin and destination. There will be no need to test for syntax or semantics here as the ability of the class to handle those situations should have already been tested in the previous partition.
    - Where origin and destination are different, syntatically and semantically correct IATA codes
    - Where origin and destination are the same, syntatically and semantically correct IATA codes

- `flightNumber`: The flight number. It should be a valid flight number. 
    - Syntatically and semantically valid codes: This is the base choice and tests for what happens when a valid flight number is input.
    - Syntatically valid but semantically invalid codes: This tests the ability of the method to throw a SemanticError due to invalid flight numbers.
    - Syntatically Invalid Codes: This tests the ability of the method to throw a SyntacticError when presented with flight numbers that do no adhere to the definition of flight numbers as per the assignment specification.

- `departureDate`: The departure date. It should be a date in the future. 
    - Future Date: This represents a date after the current date. This is the base choice and tests for what happens when a valid departure date is input.
    - Past Date: This represents a date that is strictly before the current date. This tests the ability of the method to throw a SemanticError due to invalid date.
    - Currrent Date: This represents a boundary case where the departure date is set to be the current date. This tests the ability of the method to throw a SemanticError due to invalid date.

- `cabinType`: The cabin type. Partitions: valid cabin types, invalid cabin types, non-string inputs, null/empty.
    - Valid Cabin Type: One of the 6 possible cabin types
    - Invalid Cabin Type: Any letter that is not one of the 6 possible cabin types

- `numPeople`: The number of people for which the booking is to be made. It should be between 1 and 10, inclusive. 
    - 1: Boundary Case
    - 10: Boundary Case
    - -1: Negative Case
    - 11: Exceeds 10

Test Cases:

- Test Case 1
    - Fixtures: Origin = "JFK", Destination = "LAX", FlightNumber = "AA123", DepartureDate = Future Date, CabinType = "Economy", NumPeople = 2.
    - Test Values: All fixtures are valid values for their respective characteristics.
    - Expected Values: The constructor should successfully create a SegmentSubcommand object with these attributes.

- Test Case 2
    - Fixtures: Origin = "JFK", Destination = "JFK", FlightNumber = "AA123", DepartureDate = Future Date, CabinType = "Economy", NumPeople = 2.
    - Test Values: The origin is the same as the destination.
    - Expected Values: A SemanticError should be thrown because the origin is the same as the destination.

- Test Case 3
    - Fixtures: Origin = "JFK", Destination = "LAX", FlightNumber = "AA123", DepartureDate = Past Date, CabinType = "Economy", NumPeople = 2.
    - Test Values: The DepartureDate is a date in the past.
    - Expected Values: A SemanticError should be thrown because the departure date is on or before the current date.

To achieve Base Choice Coverage, we would need to create test cases for each characteristic where we use the base choice for that characteristic and the base choices for all other characteristics. Then, we would create additional test cases where we use other choices for one characteristic while keeping the base choices for all other characteristics.

For example, for the "Origin" characteristic, we would first use a valid IATA code (the base choice) and the base choices for all other characteristics. Then, we would create test cases where we use an invalid IATA code, an empty string, a null value, and a non-string input for the "Origin", while using the base choices for all other characteristics.

We would know Base Choice Coverage was achieved when we had tested each characteristic with its base choice and at least one other choice, and all other characteristics were at their base choice. This would mean we had tested the function's behavior for all characteristics with both their most common inputs and at least one less common or edge case input, while controlling for potential interactions between characteristics.