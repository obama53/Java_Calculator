import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<Character, Integer> ROMAN_NUMERALS = new HashMap<>();

    static {
        ROMAN_NUMERALS.put('I', 1);
        ROMAN_NUMERALS.put('V', 5);
        ROMAN_NUMERALS.put('X', 10);
        ROMAN_NUMERALS.put('L', 50);
        ROMAN_NUMERALS.put('C', 100);
        ROMAN_NUMERALS.put('D', 500);
        ROMAN_NUMERALS.put('M', 1000);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String input = scanner.nextLine();
        String result = calc(input);
        System.out.println("Результат: " + result);
    }

    public static String calc(String input) {
        String[] expressions = input.split(" ");
        if (expressions.length != 3) {
            return "Некорректное выражение!";
        }

        String operand1 = expressions[0];
        String operator = expressions[1];
        String operand2 = expressions[2];

        boolean isRomanNumeral = isRomanNumeral(operand1) && isRomanNumeral(operand2);
        int num1 = isRomanNumeral ? romanToInteger(operand1) : Integer.parseInt(operand1);
        int num2 = isRomanNumeral ? romanToInteger(operand2) : Integer.parseInt(operand2);

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                return "Неподдерживаемая операция!";
        }

        if (isRomanNumeral) {
            if (result < 1) {
                throw new IllegalArgumentException("Результатом работы калькулятора с римскими числами должны быть только положительные числа!");
            }
            return integerToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRomanNumeral(String input) {
        return input.matches("[IVXLCDM]+");
    }

    private static int romanToInteger(String roman) {
        int result = 0;
        int previousValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char currentChar = roman.charAt(i);
            int currentValue = ROMAN_NUMERALS.get(currentChar);

            if (currentValue < previousValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            previousValue = currentValue;
        }

        return result;
    }

    private static String integerToRoman(int number) {
        StringBuilder result = new StringBuilder();
        int[] numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] letters = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < numbers.length; i++) {
            while (number >= numbers[i]) {
                result.append(letters[i]);
                number -= numbers[i];
            }
        }

        return result.toString();
    }
}