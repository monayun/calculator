import org.junit.platform.commons.util.StringUtils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // 입력은 콘솔로, 5 * 6 형식으로 input
        System.out.print("입력 식 : ");

        // 공백 제거하기
        String input = inputs(scanner.nextLine());

        // 어떤 연산자인지 찾아오기
        String operation = getOperation(input);

        // 연산자 기준으로 피연산자 분리하고
        double[] operands = getOperand(input);

        // 계산하기
        double result = calculate(operands[0], operands[1], operation);

        // 결과 콘솔에 출력하기
        System.out.println("result : " + result);
    }

    /**
     * 피연산자 추출하기
     * @param input
     * @return
     */
    public static double[] getOperand(String input) {
        String[] tokens = input.split("\\*|\\+|-|/");
        // 숫자 추출하기, 소수점이 존재함
        if (tokens.length == 1) {
            throw new ArrayIndexOutOfBoundsException("숫자를 잘못입력하셨습니다.");
        }
        return new double[] {checkOperand(tokens[0]), checkOperand(tokens[1])};
    }

    /**
     * 공백제거하기
     * @param scanner
     * @return
     */
    public static String inputs(String scanner) {
        if (StringUtils.isBlank(scanner)) {
            throw new NullPointerException("값을 입력하지 않았습니다");
        }
        return scanner.trim().replace(" ", "");
    }

    /**
     * 연산자 리턴하기, 만약 사칙연산이 아닌 다른 연산이라면 예외처리
     * @param input
     * @return
     */
    public static String getOperation(String input) {
        Pattern pattern = Pattern.compile("[*+/-]");

        Matcher matcher = pattern.matcher(input);

        checkOperatorCount(matcher);

        matcher = pattern.matcher(input);
        // 매칭 검사 및 추출
        if(matcher.find()) {
            return matcher.group();
        }
        throw new IllegalArgumentException("잠시 후 다시 시도해주세요");
    }

    /**
     * 연산자 기호 형식 체크하기
     * @param matcher
     */
    public static void checkOperatorCount(Matcher matcher) {
        int count = 0;
        while(matcher.find()) {
            count++;
        }

        if(count >= 2) {
            throw new IllegalArgumentException("연산기호가 2개 이상 있습니다");

        } else if(count == 0) {
            throw new IllegalArgumentException("잘못된 연산기호입니다.");
        }
    }

    /**
     * 피연산자 형식 체크하기
     * @param operand
     * @return
     */
    public static double checkOperand(String operand) {
        Pattern pattern = Pattern.compile("-?[0-9,]+(\\.[0-9,]*)?");
        Matcher matcher = pattern.matcher(operand);

        if(!matcher.matches()) {
            throw new IllegalArgumentException("잘못된 피연산자 형식입니다.");
        }

        // 콤마가 있는 경우 제거
        operand = operand.replaceAll(",", "");
        return Double.parseDouble(operand);
    }

    /**
     * 계산하기
     * @param num1
     * @param num2
     * @param op
     * @return
     */
    public static double calculate(double num1, double num2, String op) {
        switch(op) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            default:
                return 0;
        }
    }
}