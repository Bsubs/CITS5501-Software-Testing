# CITS5501 Project 

## Question 1

*Give a BNF or EBNF grammar that will parse (or generate) valid gladius commands. Your grammar should use the notation accepted by the BNF playground. It must be in plain text which could be pasted into the BNF playground and compiled without error.*

*The grammar should specify any whitespace that needs to appear between other symbols in the command. (As a simplifying assumption, you may assume that a single space character is sufficient as a separator between words.)*

```
<command> ::= "shop flight fares " <origin> <destination> <trip_type> <cabin_type> <departure_date> | "air book req" <newline> <segment_list> "EOC"

<origin> ::= <letter> <letter> <letter> " "
<destination> ::= <letter> <letter> <letter> " "
<letter> ::= "A" | "B" | "C" | "D"

<trip_type> ::= "OneWay " | "Return " <number>  " "
<number> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" | "10" | "11" | "12" | "13" | "14" | "15" | "16" | "17" | "18" | "19" | "20"

<cabin_type> ::= "P " | "F " | "J " | "C " | "S " | "Y "

<departure_date> ::= <digit> <digit> <digit> <digit> "-" <digit> <digit> "-" <digit> <digit> " "
<digit> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" 

<segment_list> ::= <segment> <newline> | <segment> <newline> <segment_list>

<segment> ::= "seg " <origin> <destination> <flight_number> <departure_date> <cabin_type> <num_people>

<flight_number> ::= <airline_code> <digit> <digit> " "
<airline_code> ::= <airline_letter> <airline_letter>
<airline_letter> ::= "I" | "J" | "K" | "L"

<num_people> ::= "1 " | "2 " | "3 " | "4 " | "5 " | "6 " | "7 " | "8 " | "9 " | "10 "

<newline> ::= "\n"
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
        if (symbol.length >= 2) {
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
    - Total = 22
4. `<cabin_type>`: 6 possible cabin types
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

    static int countProductions(List<String> grammar) {
        int productionsCount = 0;

        for (int i = 0; i < grammar.size(); i++) {
            String currentSymbol = grammar.get(i);
            String nextSymbol = " ";
            if ((i+1) >= grammar.size()) {
                nextSymbol = " ";
            }
            else {
                nextSymbol = grammar.get(i + 1);
            }

            if (isNonTerminalSymbol(currentSymbol) && !nextSymbol.equals("::=")) {
                productionsCount++;
            }
        }

        return productionsCount;
    }

    private static boolean isNonTerminalSymbol(String symbol) {
        return symbol.length() >= 2 && symbol.charAt(0) == '<' && symbol.charAt(symbol.length() - 1) == '>';
    }
}
```