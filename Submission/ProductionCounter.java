import java.util.List;

public class ProductionCounter {

    public static void main(String[] args) {
        List<String> myGrammar =
        List.of("<greeting>", "::=", "\"hello\"", "|", "\"bonjour\"", "|", "\"hallå\"", "|", "[1-4]", "\n",
                "<audience>", "::=", "\"world\"", "|", "[a-e]", "|", "\"y'all\"", "\n",
                "<hello_world>", "::=", "<greeting>", "\" \"", "<audience>", "|", "<audience>");
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
        for (String token : grammar) {

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


        return count;
    }

}
