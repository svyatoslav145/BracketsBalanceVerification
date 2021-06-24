package akvelonTestTaskOvsiy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class BracketsBalanceVerification {

    private static final Logger logger =
            Logger.getLogger(BracketsBalanceVerification.class.getName());
    public static final Pattern INPUT_PATTERN = Pattern.compile("[{}()\\[\\]]*");

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String SPACE = "space";

    public int verifyBracketsBalance(String input) {

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

    private boolean isClosedBracketCorrespondsToOpenBracket(char closingBracket,
                                                            char openBracket) {
        final boolean result;

        if (closingBracket == ')' && openBracket == '(') {
            result = true;
        } else if (closingBracket == ']' && openBracket == '[') {
            result = true;
        } else if (closingBracket == '}' && openBracket == '{') {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    private boolean isaOpenBracket(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    private void checkIfInputContainsForbiddenSymbols(String input) {
        if (!Pattern.matches(String.valueOf(INPUT_PATTERN), input)) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] chars = input.toCharArray();
            for (char c : chars) {
                String s = String.valueOf(c);
                if (!s.matches(String.valueOf(INPUT_PATTERN))) {
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
}
