import java.util.List;

public class ProductionCounter {

    public static void main(String[] args) {
        List<String> myGrammar =
        List.of("<greeting>", "::=", "\"hello\"", "|", "\"bonjour\"", "|", "\"hallå\"", "\n",
                "<audience>", "::=", "\"world\"", "|", "\"y'all\"", "\n",
                "<hello_world>", "::=", "<greeting>", "\" \"", "<audience>", "|", "<audience>");
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
