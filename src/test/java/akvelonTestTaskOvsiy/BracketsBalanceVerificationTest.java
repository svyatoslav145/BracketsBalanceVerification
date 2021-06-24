package akvelonTestTaskOvsiy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static akvelonTestTaskOvsiy.BracketsBalanceVerification.INPUT_PATTERN;
import static org.junit.jupiter.api.Assertions.*;

class BracketsBalanceVerificationTest {

    public static final String EMPTY_STRING = "";
    public static final String INCORRECT_BRACKET_SEQUENCE = "()a_c[]# W";
    public static final String WRONG_FOURTH_BRACKET = "{[(]}";
    public static final String WRONG_FIRST_BRACKET = ")[]{}(";
    private final BracketsBalanceVerification bracketsBalanceVerification =
            new BracketsBalanceVerification();

    @Test
    @DisplayName("Test with empty bracket sequence. Should be returned zero")
    void testWithEmptyBracketSequence() {
        //when
        int returnedInt = bracketsBalanceVerification.verifyBracketsBalance(EMPTY_STRING);
        //then
        assertEquals(0, returnedInt);
    }

    @Test
    @DisplayName("Test with incorrect bracket sequence. IllegalArgumentException " +
            "should be thrown and should contain specific message")
    void testWithIncorrectBracketSequence() {
        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bracketsBalanceVerification
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
            int returnedInt = bracketsBalanceVerification.verifyBracketsBalance(s);

            assertEquals(-1, returnedInt);
        }
    }

    @Test
    @DisplayName("Test with not balanced bracket sequence. Should returns 4")
    void testWithNotBalancedBracketSequence_bracket4() {
        //when
        int incorrectBracketNumber =
                bracketsBalanceVerification.verifyBracketsBalance(WRONG_FOURTH_BRACKET);
        //then
        assertEquals(4, incorrectBracketNumber);
    }

    @Test
    @DisplayName("Test with nit balanced bracket sequence. Should returns 1")
    void testWithNotBalancedBracketSequence_bracket1() {
        //when
        int incorrectBracketNumber =
                bracketsBalanceVerification.verifyBracketsBalance(WRONG_FIRST_BRACKET);
        //then
        assertEquals(1, incorrectBracketNumber);
    }

    @ParameterizedTest
    @MethodSource("createCorrectBracketSequences")
    @DisplayName("Pattern test with correct symbols in bracket sequences")
    void testBracketSequenceRegexp_whenBracketSequencesCorrect(String correctSequence) {
        assertTrue(correctSequence.matches(String.valueOf(INPUT_PATTERN)));
    }

    @ParameterizedTest
    @MethodSource("createWrongBracketSequences")
    @DisplayName("Pattern test with wrong symbols in bracket sequences")
    void testBracketSequenceRegexp_whenBracketSequencesWrong(String correctSequence) {
        assertFalse(correctSequence.matches(String.valueOf(INPUT_PATTERN)));
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
        return List.of("{} ()[]",
                "{{}}((a))[[]]",
                "{{(())}}_[[(){[]}]]",
                "}}}(((]]#](((",
                "(((~%]]]@{{{");
    }
}
