package rpn;
import java.util.*;

// Class required
public class PrefixDiff {

    public static void main(String rgs[]) {
        String exps[] = new String[] {
            "(+ x (+ x x))",
            "(cos (* 2 x))",
            "(tan (* 2 x))",
            "* -1 (sin x))",
            "(* 2 (exp (* 2 x)))",
            "(/ 2 (+ 1 x))"};

                
        for(final String x : exps) {
            System.out.println("Derivate of " + x + ":" + diff(x));
        }
    }



    public static String diff(String expression) {
        List<String> tokens = tokenize(expression);
        System.out.println(tokens);
        Node root = parse(tokens);
        Node derivative = derivative(root);
        return simplify(derivative).toString();
    }

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, " ()", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (!token.equals(" ")) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    private static Node parse(List<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression");
        }
        String token = tokens.remove(0);
        if (token.equals("(")) {
            String op = tokens.remove(0);
            List<Node> args = new ArrayList<>();
            while (!tokens.get(0).equals(")")) {
                args.add(parse(tokens));
            }
            tokens.remove(0);  // remove ")"
            return new Node(op, args);
        } else if (token.equals(")")) {
            throw new IllegalArgumentException("Unexpected ')'");
        } else {
            return new Node(token);
        }
    }

    private static Node derivative(Node node) {
        if (node.isNumber()) {
            return new Node("0");
        } else if (node.isVariable()) {
            return new Node("1");
        } else {
            String op = node.value;
            List<Node> args = node.args;
            switch (op) {
                case "+":
                    return new Node("+", derivative(args.get(0)), derivative(args.get(1)));
                case "-":
                    return new Node("-", derivative(args.get(0)), derivative(args.get(1)));
                case "*":
                    return new Node("+",
                            new Node("*", args.get(0), derivative(args.get(1))),
                            new Node("*", derivative(args.get(0)), args.get(1)));
                case "/":
                    return new Node("/",
                            new Node("-",
                                    new Node("*", derivative(args.get(0)), args.get(1)),
                                    new Node("*", args.get(0), derivative(args.get(1)))),
                            new Node("^", args.get(1), new Node("2")));
                case "^":
                    Node base = args.get(0);
                    Node exponent = args.get(1);
                    return new Node("*",
                            new Node("*", exponent, new Node("^", base, new Node("-", exponent, new Node("1")))),
                            derivative(base));
                case "cos":
                    return new Node("*",
                             derivative(args.get(0)),
                             new Node("*", new Node("-1"), new Node("sin", args.get(0))));
                case "sin":
                    return new Node("*", new Node("cos", args.get(0)), derivative(args.get(0)));
                case "tan":
                    return new Node("*",
                            new Node("^", new Node("cos", args.get(0)), new Node("-2")),
                            derivative(args.get(0)));
                case "exp":
                    return new Node("*", new Node("exp", args.get(0)), derivative(args.get(0)));
                case "ln":
                    return new Node("/", derivative(args.get(0)), args.get(0));
                default:
                    throw new IllegalArgumentException("Unknown operator: " + op);
            }
        }
    }

  
    private static Node simplify(Node node) {
        if (node.isNumber() || node.isVariable()) {
            return node;
        }

        List<Node> simplifiedArgs = new ArrayList<>();
        for (Node arg : node.args) {
            simplifiedArgs.add(simplify(arg));
        }

        String op = node.value;
        switch (op) {
            case "+":
                if (simplifiedArgs.get(0).isZero()) {
                    return simplifiedArgs.get(1);
                } else if (simplifiedArgs.get(1).isZero()) {
                    return simplifiedArgs.get(0);
                } else if(simplifiedArgs.get(0).isNumber() && simplifiedArgs.get(1).isNumber()) {
                    return new Node(Double.parseDouble(simplifiedArgs.get(0).value) + Double.parseDouble(simplifiedArgs.get(1).value));
                }
                break;
            case "-":
                if (simplifiedArgs.get(1).isZero()) {
                    return simplifiedArgs.get(0);
                } else if(simplifiedArgs.get(0).isNumber() && simplifiedArgs.get(1).isNumber()) {
                    return new Node(Double.parseDouble(simplifiedArgs.get(0).value) - Double.parseDouble(simplifiedArgs.get(1).value));
                }
                break;
            case "*":
                if (simplifiedArgs.get(0).isOne()) {
                    return simplifiedArgs.get(1);
                } else if (simplifiedArgs.get(1).isOne()) {
                    return simplifiedArgs.get(0);
                } else if (simplifiedArgs.get(0).isZero() || simplifiedArgs.get(1).isZero()) {
                    return new Node("0");
                } else if(!simplifiedArgs.get(0).isNumber() && simplifiedArgs.get(1).isNumber()) {
                    return new Node(node.value, simplifiedArgs.get(1), simplifiedArgs.get(0));
                } else if(simplifiedArgs.get(0).isNumber() && simplifiedArgs.get(1).isNumber()) {
                    return new Node(Double.parseDouble(simplifiedArgs.get(0).value) * Double.parseDouble(simplifiedArgs.get(1).value));
                }
                break;
            case "/":
                if (simplifiedArgs.get(1).isOne()) {
                    return simplifiedArgs.get(0);
                } else if(simplifiedArgs.get(0).isNumber() && simplifiedArgs.get(1).isNumber()) {
                    return new Node(Double.parseDouble(simplifiedArgs.get(0).value) / Double.parseDouble(simplifiedArgs.get(1).value));
                }
                
                break;
            case "^":
                if (simplifiedArgs.get(1).isZero()) {
                    return new Node("1");
                } else if (simplifiedArgs.get(1).isOne()) {
                    return simplifiedArgs.get(0);
                } else if(simplifiedArgs.get(0).isNumber() && simplifiedArgs.get(1).isNumber()) {
                    return new Node(Math.pow(Double.parseDouble(simplifiedArgs.get(0).value), Double.parseDouble(simplifiedArgs.get(1).value)));
                }
                break;
        }

        return new Node(op, simplifiedArgs);
    }

  
    static class Node {
        String value;
        List<Node> args;

      
        Node(double value) {
            this(Double.toString(value));
        }

        Node(String value) {
            this.value = value;
            this.args = new ArrayList<>();
        }

        Node(String value, Node... args) {
            this.value = value;
            this.args = Arrays.asList(args);
        }

        Node(String value, List<Node> args) {
            this.value = value;
            this.args = args;
        }

        boolean isNumber() {
            try {
                Double.parseDouble(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        boolean isVariable() {
            return value.equals("x");
        }

        boolean isZero() {
            return isNumber() && Double.parseDouble(value) == 0;
        }

        boolean isOne() {
            return isNumber() && Double.parseDouble(value) == 1;
        }

        @Override
        public String toString() {
            if (args.isEmpty()) {
              if(this.isNumber()) {
                    final double d = Double.parseDouble(value);
                    if(Math.round(d) == d) {
                        return Integer.toString((int) d);
                    } else {
                        return(value);
                    }
                } else {
                    return(value);
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("(");
                if(this.isNumber()) {
                    final double d = Double.parseDouble(value);
                    if(Math.round(d) == d) {
                        sb.append((int) d);
                    } else {
                        sb.append(value);
                    }
                } else {
                    sb.append(value);
                }
                for (Node arg : args) {
                    sb.append(" ").append(arg.toString());
                }
                sb.append(")");
                return sb.toString();
            }
        }
    }
}

