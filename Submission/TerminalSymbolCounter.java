import java.util.List;


public class TerminalSymbolCounter {

    public static void main(String[] args) {
        List<String> myGrammar = List.of("<weekendDay>", "::=", "[1-20]", "\"sat\"", "|", "\"sun\"", "[1-4]", "[a-z]");
        System.out.println(countTerminalSymbols(myGrammar)); // Output: 2
    }

   /**
    Counts the number of terminal symbols in a grammar. Terminals are defined by any String wrapped in " ".  
    Terminals wrapped in [1-3] will be expanded and counted. 
    @param grammar the list of strings representing the grammar rules. 
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