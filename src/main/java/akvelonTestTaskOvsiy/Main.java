package akvelonTestTaskOvsiy;

import static akvelonTestTaskOvsiy.VerifierType.TYPE_ROUND;
import static akvelonTestTaskOvsiy.VerifierType.TYPE_ROUND_SQUARE_CURLY;

public class Main {

    public static void main(String[] args) {

        BracketsBalanceVerifier bracketsBalanceVerifier =
                new BracketsBalanceVerifier(TYPE_ROUND);

        bracketsBalanceVerifier.verifyBracketsBalance("");
        bracketsBalanceVerifier.verifyBracketsBalance("((()))");
        bracketsBalanceVerifier.verifyBracketsBalance(")(");

        BracketsBalanceVerifier balanceVerifier =
                new BracketsBalanceVerifier(TYPE_ROUND_SQUARE_CURLY);

        balanceVerifier.verifyBracketsBalance("");
        balanceVerifier.verifyBracketsBalance("(([]{}))");
        balanceVerifier.verifyBracketsBalance("({[(]})");
        balanceVerifier.verifyBracketsBalance("}{()[]");

        balanceVerifier.verifyBracketsBalance("a{}bc() []e");
    }
}
