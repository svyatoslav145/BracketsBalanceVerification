package akvelonTestTaskOvsiy;

public enum VerifierType {

    TYPE_ROUND("(", ")"),
    TYPE_ROUND_SQUARE_CURLY("([{", ")]}");

    private final String openBrackets;
    private final String closeBrackets;

    VerifierType(String openBrackets, String closeBrackets) {
        this.openBrackets = openBrackets;
        this.closeBrackets = closeBrackets;
    }

    public String getOpenBrackets() {
        return openBrackets;
    }

    public String getCloseBrackets() {
        return closeBrackets;
    }
}
