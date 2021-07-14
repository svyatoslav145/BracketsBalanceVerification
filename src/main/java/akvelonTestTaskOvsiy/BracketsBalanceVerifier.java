package akvelonTestTaskOvsiy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class BracketsBalanceVerifier {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String SPACE = "space";

    private final String openBrackets;
    private final String closeBrackets;

    public BracketsBalanceVerifier(VerifierType verifierType) {
        this.openBrackets = verifierType.getOpenBrackets();
        this.closeBrackets = verifierType.getCloseBrackets();
    }

    private static final Logger logger =
            Logger.getLogger(BracketsBalanceVerifier.class.getName());

    public int verifyBracketsBalance(String input) {

        if (openBrackets.length() != closeBrackets.length()) {
            throw new IllegalArgumentException(
                    "Open brackets and close brackets have different symbols number");
        }

        int result = 0;

        if (input.isEmpty()) {
            logger.info("Inputted string is empty");
            return result;
        }

        checkIfInputContainsForbiddenSymbols(input);

        char[] chars = input.toCharArray();
        List<Character> characters = new ArrayList<>();

        for (char bracket : chars) {
            if (isaOpenBracket(bracket)) {
                characters.add(bracket);
                result++;
            } else if (characters.size() == 0) {
                result++;
                logger.warning("NOT BALANCED("
                        .concat(String.valueOf(result))
                        .concat("), returns "
                                .concat(String.valueOf(result))));
                return result;
            } else {
                char openBracket = characters.get(characters.size() - 1);
                if (!isClosedBracketCorrespondsToOpenBracket(bracket, openBracket)) {
                    result++;
                    break;
                } else {
                    characters.remove(characters.size() - 1);
                    result++;
                }
            }
        }

        if (characters.size() == 0) {
            logger.info(ANSI_CYAN.concat("BALANCED, returns -1").concat(ANSI_RESET));
            result = -1;
        } else {
            logger.warning("NOT BALANCED("
                    .concat(String.valueOf(result))
                    .concat("), returns "
                            .concat(String.valueOf(result))));
        }

        return result;
    }

    static String getInputPattern(String openBrackets, String closeBrackets) {
        char[] charArray = openBrackets.toCharArray();
        for (char c : charArray) {
            if (closeBrackets.contains(String.valueOf(c))) {
                throw new IllegalArgumentException(
                        "Open brackets and close brackets have the same symbols");
            }
        }
        StringBuilder stringBuilder = new StringBuilder("[");

        addCharactersToPattern(openBrackets, stringBuilder, "[",
                "\\[");
        addCharactersToPattern(closeBrackets, stringBuilder, "]",
                "\\]");
        stringBuilder.append("]*");

        return stringBuilder.toString();
    }

    private static void addCharactersToPattern(String brackets,
                                               StringBuilder stringBuilder,
                                               String characterToEscape,
                                               String escapedCharacter) {
        char[] toCharArray = brackets.toCharArray();
        for (char c : toCharArray) {
            if (String.valueOf(c).equals(characterToEscape)) {
                stringBuilder.append(escapedCharacter);
            } else {
                stringBuilder.append(c);
            }
        }
    }

    private boolean isClosedBracketCorrespondsToOpenBracket(char closeBracket,
                                                            char openBracket) {
        Map<Character, Character> brackets = getCharacterMap(openBrackets, closeBrackets);

        return openBracket == brackets.get(closeBracket);
    }

    private boolean isaOpenBracket(char c) {
        return openBrackets.contains(String.valueOf(c));
    }

    private void checkIfInputContainsForbiddenSymbols(String input) {
        String inputPattern = getInputPattern(openBrackets, closeBrackets);
        if (!Pattern.matches(inputPattern, input)) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] chars = input.toCharArray();
            for (char c : chars) {
                String s = String.valueOf(c);
                if (!s.matches(inputPattern)) {
                    if (c == ' ') {
                        stringBuilder.append(SPACE).append(", ");
                    } else {
                        stringBuilder.append(c).append(", ");
                    }
                }
            }
            throw new IllegalArgumentException(String.format(
                    "A character(s): {%s} does not belong to any known brackets type",
                    stringBuilder.substring(0, stringBuilder.lastIndexOf(","))));
        }
    }

    private Map<Character, Character> getCharacterMap(String openBrackets, String closeBrackets) {

        Map<Character, Character> characterHashMap = new HashMap<>();

        char[] openArray = openBrackets.toCharArray();
        char[] closeArray = closeBrackets.toCharArray();

        for (int i = 0; i < openBrackets.length(); i++) {
            characterHashMap.put(closeArray[i], openArray[i]);
        }

        return characterHashMap;
    }
}
