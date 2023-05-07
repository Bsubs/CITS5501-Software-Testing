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