package akvelonTestTaskOvsiy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static akvelonTestTaskOvsiy.VerifierType.TYPE_ROUND_SQUARE_CURLY;
import static org.junit.jupiter.api.Assertions.*;

class Test_ROUND_SQUARE_CURLY_BRACKETS_Verifier {

    private static final String EMPTY_STRING = "";
    private static final String INCORRECT_BRACKET_SEQUENCE = "()a_c[]# W";
    private static final String WRONG_FOURTH_BRACKET = "{[(]}";
    private static final String WRONG_FIRST_BRACKET = ")[]{}(";

    private final BracketsBalanceVerifier bracketsBalanceVerifier =
            new BracketsBalanceVerifier(TYPE_ROUND_SQUARE_CURLY);
    private static final String INPUT_PATTERN = BracketsBalanceVerifier.getInputPattern(
            TYPE_ROUND_SQUARE_CURLY.getOpenBrackets(),
            TYPE_ROUND_SQUARE_CURLY.getCloseBrackets());

    @Test
    @DisplayName("Test with empty bracket sequence. Should be returned zero")
    void testWithEmptyBracketSequence() {
        //when
        int returnedInt = bracketsBalanceVerifier.verifyBracketsBalance(EMPTY_STRING);
        //then
        assertEquals(0, returnedInt);
    }

    @Test
    @DisplayName("Test with incorrect bracket sequence. IllegalArgumentException " +
            "should be thrown and should contain specific message")
    void testWithIncorrectBracketSequence() {
        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bracketsBalanceVerifier
                        .verifyBracketsBalance(INCORRECT_BRACKET_SEQUENCE));
        //then
        assertEquals("A character(s): {a, _, c, #, space, W} does not belong " +
                "to any known brackets type", exception.getMessage());
    }

    @Test
    @DisplayName("Test with balanced bracket sequences. Should be returned '-1' each time")
    void testWithBalancedBracketSequences() {
        //then
        for (String s : balancedBracketSequences()) {
            int returnedInt = bracketsBalanceVerifier.verifyBracketsBalance(s);

            assertEquals(-1, returnedInt);
        }
    }

    @Test
    @DisplayName("Test with not balanced bracket sequence. Should returns 4")
    void testWithNotBalancedBracketSequence_bracket4() {
        //when
        int incorrectBracketNumber =
                bracketsBalanceVerifier.verifyBracketsBalance(WRONG_FOURTH_BRACKET);
        //then
        assertEquals(4, incorrectBracketNumber);
    }

    @Test
    @DisplayName("Test with nit balanced bracket sequence. Should returns 1")
    void testWithNotBalancedBracketSequence_bracket1() {
        //when
        int incorrectBracketNumber =
                bracketsBalanceVerifier.verifyBracketsBalance(WRONG_FIRST_BRACKET);
        //then
        assertEquals(1, incorrectBracketNumber);
    }

    @ParameterizedTest
    @MethodSource("createCorrectBracketSequences")
    @DisplayName("Pattern test with correct symbols in bracket sequences")
    void testBracketSequenceRegexp_whenBracketSequencesCorrect(String correctSequence) {
        assertTrue(correctSequence.matches(INPUT_PATTERN));
    }

    @ParameterizedTest
    @MethodSource("createWrongBracketSequences")
    @DisplayName("Pattern test with wrong symbols in bracket sequences")
    void testBracketSequenceRegexp_whenBracketSequencesWrong(String wrongSequence) {
        assertFalse(wrongSequence.matches(INPUT_PATTERN));
    }

    private List<String> balancedBracketSequences() {
        return List.of("{}()[]",
                "{{}}(())[[]]",
                "{{(())}}[[(){[]}]]");
    }

    /**
     * These sequences can be either balanced or unbalanced
     */
    private static List<String> createCorrectBracketSequences() {
        return List.of("{}()[]",
                "{{}}(())[[]]",
                "{{(())}}[[(){[]}]]",
                "}}}(((]]](((",
                "(((]]]{{{");
    }

    private static List<String> createWrongBracketSequences() {
        List<String> wrongBracketSequences;

        String allowedSymbols = "{}()[]";

        List<Character> prohibitedCharacters = new ArrayList<>();

        IntStream.range(' ', '(').forEach(c -> prohibitedCharacters.add((char) c));
        IntStream.range('*', '[').forEach(c -> prohibitedCharacters.add((char) c));
        IntStream.range('\\', ']').forEach(c -> prohibitedCharacters.add((char) c));
        IntStream.range('^', '{').forEach(c -> prohibitedCharacters.add((char) c));
        IntStream.range('|', '}').forEach(c -> prohibitedCharacters.add((char) c));
        IntStream.range('~', '\u0080').forEach(c -> prohibitedCharacters.add((char) c));

        wrongBracketSequences = prohibitedCharacters.stream()
                .map(character -> allowedSymbols.concat(String.valueOf(character)))
                .collect(Collectors.toList());

        return wrongBracketSequences;
    }
}
