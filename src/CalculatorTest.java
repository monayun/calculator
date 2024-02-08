import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @DisplayName("값 받아오기 성공")
    @Test
    void inputsTestSuccess() {
        // 형식은 Operand Operation Operand
        String input = "    100  /  10    ";
        String data = input.trim().replace(" ", "");
        assertEquals("100/10", data);
    }

    @DisplayName("값 받아오기 실패")
    @Test
    void inputsTestFail() {
        assertAll(
                () ->  Assertions.assertThrows(NullPointerException.class, () -> Calculator.inputs(null)),
                () ->  Assertions.assertThrows(NullPointerException.class, () -> Calculator.inputs("     "))
        );
    }

    @DisplayName("연산자 추출 성공")
    @Test
    void getOperationSuccess() {
        // 사칙연산만 추출하도록 한다
        String plus = "100+10";
        String minus = "100-10";
        String multiply = "100*10";
        String division = "100/10";
        String[] inputs = new String[] {plus, minus, multiply, division};
        String[] operations = new String[] {"+", "-", "*", "/"};

        for (int i = 0; i < inputs.length; i++) {
            String operation = Calculator.getOperation(inputs[i]);
            assertEquals(operations[i], operation);
        }
    }

    @DisplayName("연산자 추출 실패")
    @Test
    void getOperationFail() {
        Pattern pattern = Pattern.compile("[*+/-]");
        assertAll(
                () ->  Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.checkOperatorCount(pattern.matcher("100+-10"))),
                () ->  Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.checkOperatorCount(pattern.matcher("100>>10"))),
                () ->  Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.checkOperatorCount(pattern.matcher("100,10")))
        );
    }

    @DisplayName("피연산자 추출 성공")
    @Test
    void getOperandSuccess() {
        String input = "100/10";
        double[] operands = Calculator.getOperand(input);
        assertAll(
                () ->  assertEquals(100, operands[0]),
                () ->  assertEquals(10, operands[1])
        );
    }

    @DisplayName("피연산자 콤마 제거 후 추출 성공")
    @Test
    void getOperandSuccess2() {
        String input = "10,000/10";
        double[] operands = Calculator.getOperand(input);
        assertAll(
                () ->  assertEquals(10000, operands[0]),
                () ->  assertEquals(10, operands[1])
        );
    }

    @DisplayName("피연산자 추출 실패")
    @Test
    void getOperandFail() {
        String input = "10,000/";
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> Calculator.getOperand(input));
    }

    @DisplayName("계산 성공")
    @Test
    void calculateSuccess() {
        String[] operations = new String[] {"+", "-", "*", "/"};
        double[] results = new double[] {120, 80, 2000, 5};
        for (int i = 0; i < operations.length; i++) {
            double result = Calculator.calculate(100, 20, operations[i]);
            assertEquals(results[i], result);
        }
    }
}