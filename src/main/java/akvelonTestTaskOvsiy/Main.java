package akvelonTestTaskOvsiy;

public class Main {

    public static void main(String[] args) {
        BracketsBalanceVerification bracketsBalanceVerification =
                new BracketsBalanceVerification();

        bracketsBalanceVerification.verifyBracketsBalance("");
        bracketsBalanceVerification.verifyBracketsBalance("(([]{}))");
        bracketsBalanceVerification.verifyBracketsBalance("({[(]})");
        bracketsBalanceVerification.verifyBracketsBalance("}{()[]");

        bracketsBalanceVerification.verifyBracketsBalance("a{}bc() []e");
    }
}
