package rpn;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;


/**
 * Expression evaluator.
 *
 * Evaluates a mathematical expression and returns the resulting number.
 *
 * Acceped operators: +,-,*,/
 * To negate, use the usual "-" before the element to invert:
 *      For example "4 + -5"
 *
 * Accepted parnethesis also
 */
public class ExpressionEvaluator {

    public static List<Object> tokenize(String expression) {
        Pattern tokenPattern = Pattern.compile("\\s*(?:(\\d*\\.\\d+|\\d+)|([+\\-*/()]))");
        Matcher matcher = tokenPattern.matcher(expression);
        List<Object> tokens = new ArrayList<>();

        while (matcher.find()) {
            String number = matcher.group(1);
            String operator = matcher.group(2);
            if (number != null) {
                if (number.contains(".")) {
                    tokens.add(Double.parseDouble(number));
                } else {
                    tokens.add(Integer.parseInt(number));
                }
            } else if (operator != null) {
                tokens.add(operator);
            }
        }

        // Handle negative numbers
        if (tokens.get(0).equals("-")) {
            tokens.set(0, "neg");
        }

        for (int i = 1; i < tokens.size(); i++) {
            if (tokens.get(i).equals("-")) {
                if (Arrays.asList("(", "+", "-", "*", "/").contains(
                        tokens.get(i - 1))) {
                    tokens.set(i, "neg");
                }
            }
        }

        return tokens;
    }

    public static List<Object> shuntingYard(List<Object> tokens) {
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        precedence.put("neg", 3);

        List<Object> output = new ArrayList<>();
        Stack<Object> operators = new Stack<>();

        for (Object token : tokens) {
            if (token instanceof Integer || token instanceof Double) {
                output.add(token);
            } else if (precedence.containsKey(token)) {
                while (!operators.isEmpty() && precedence.containsKey(operators.peek()) &&
                        precedence.get(operators.peek()) >= precedence.get(token)) {
                    output.add(operators.pop());
                }
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                operators.pop();
            }
        }

        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    public static double evaluateRPN(List<Object> tokens) {
        Stack<Double> stack = new Stack<>();

        for (Object token : tokens) {
            if (token instanceof Integer) {
                stack.push(((Integer) token).doubleValue());
            } else if (token instanceof Double) {
                stack.push((Double) token);
            } else if (token.equals("neg")) {
                stack.push(-stack.pop());
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token.toString()) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;
                }
            }
        }

        return stack.pop();
    }

    public static double evaluateExpression(String expression) {
        System.out.println("Evaluating : " + expression);
        List<Object> tokens = tokenize(expression);
        System.out.println(tokens);
        List<Object> rpn = shuntingYard(tokens);
        System.out.println(rpn);
        return evaluateRPN(rpn);
    }

    public static void main(String[] args) {
        List<String> expressions = Arrays.asList(
                "12* 123/-(-5 + 2)",
                "-123",
                "123",
                "2 /2+3 * 4.75- -6",
                "12* 123",
                "2 / (2 + 3) * 4.33 - -6");

        for (String expr : expressions) {
            System.out.println(evaluateExpression(expr));
        }
    }
}

