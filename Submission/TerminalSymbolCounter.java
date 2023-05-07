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