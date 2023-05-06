# CITS5501 Project 

## Question 1

*Give a BNF or EBNF grammar that will parse (or generate) valid gladius commands. Your grammar should use the notation accepted by the BNF playground. It must be in plain text which could be pasted into the BNF playground and compiled without error.*

*The grammar should specify any whitespace that needs to appear between other symbols in the command. (As a simplifying assumption, you may assume that a single space character is sufficient as a separator between words.)*

```<command> ::= "shop flight fares " <origin> <destination> <trip_type> <cabin_type> <departure_date> | "air book req" <newline> <segment_list> "EOC"

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
*The signature for the method should be: static int countTerminalSymbols(List<String>)*

- Basically the method definition for this one is kinda weird
- But anyway any item that appears in double quotes is terminal

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
        return symbol.length() >= 2 && symbol.charAt(0) == '"' && symbol.charAt(symbol.length() - 1) == '"';
    }
}
```