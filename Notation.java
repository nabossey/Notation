
import java.util.regex.Pattern;

public class Notation {

    static MyQueue<Character> output;
    static MyStack<Character> theStack;
    private static String[] operators = {"+", "-", "*", "/"};

    private static final String ops = "-+*/";
    private static final String operands = "0123456789";

    public static String convertInfixToPostfix(String input) throws InvalidNotationFormatException {
        if (input == null || input.length() == 0 || !checkParenthesis(input)) {
            throw new InvalidNotationFormatException("Invalid input");
        }
        output = new MyQueue<Character>(input.length());
        theStack = new MyStack<Character>(input.length());
        try {
            for (int j = 0; j < input.length(); j++) {
                char ch = input.charAt(j);
                switch (ch) {
                    case '+':
                    case '-':
                        gotOper(ch, 1);
                        break; //   (precedence 1)
                    case '*': // it's * or /
                    case '/':
                        gotOper(ch, 2); // go pop operators
                        break; //   (precedence 2)
                    case '(': // it's a left paren
                        theStack.push('('); // push it
                        break;
                    case ')': // it's a right paren
                        gotParen(ch); // go pop operators
                        break;
                    default: // must be an operand
                        output.enqueue(ch);
                        break;
                }
            }
            while (!theStack.isEmpty()) {
                output.enqueue(theStack.pop());

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output.toString(); // return postfix
    }

    public static String convertPostfixToInfix(String expression) throws InvalidNotationFormatException {
        String infix = "";
        String nextChar;
        String operand = "";
        //remove all spaces
        String s = "";
        while (expression.indexOf(' ') >= 0) {
            expression = expression.replaceAll(" ", "");
        }
        //introducing space after each token
        for (int i = 0; i < expression.length(); i++) {
            s = s + expression.charAt(i) + " ";
        }
        expression = s.trim();

        MyStack<String> postfixStack = new MyStack<String>(expression.length());
        MyStack<String> infixStack = new MyStack<String>(expression.length());

        try {
            for (int i = 0; i < expression.length(); i++) {
                infixStack.push(Character.toString(expression.charAt(i)));
            }

            while (!infixStack.isEmpty()) {
                postfixStack.push(infixStack.pop());
            }

            while (!postfixStack.isEmpty()) {
                nextChar = postfixStack.pop();
                if (nextChar.equalsIgnoreCase("+") || nextChar.equalsIgnoreCase("-")
                        || nextChar.equalsIgnoreCase("*") || nextChar.equalsIgnoreCase("/")
                        || nextChar.equalsIgnoreCase("%")) {
                    if (!operand.isEmpty()) {
                        // push the operand
                        // DEBUG: System.out.println("Operand: \"" + operand + "\" pushed to stack");
                        infixStack.push(operand);
                        operand = "";
                    }
                    try {
                        String op2 = infixStack.pop();
                        String op1 = infixStack.pop();

                        if (!postfixStack.isEmpty()) {
                            // surround expression with parenthesis
                            String exp = "(" + op1 + nextChar + op2 + ")";
                            infixStack.push(exp);
                        } else {
                            // last expression does not require surrounding parenthesis
                            String exp = op1 + nextChar + op2;
                            // DEBUG: System.out.println("Expression: \"" + exp + "\" pushed to stack");
                            infixStack.push(exp);
                        }
                    } catch (StackUnderflowException e) {
                        throw new InvalidNotationFormatException("\nUnable to process user entered expression"
                                + "\nERROR - Invalid postfix expression entered"
                                + "\nThe stack was cleared to allow for additional processing of expressions\n\n");

                    }

                } else if (isNumber(nextChar) || nextChar.equals(".")) {
                    // nextChar is a digit
                    operand += nextChar;
                } else if (nextChar.equalsIgnoreCase(" ")) {
                    // ignore all spaces
                    if (!operand.isEmpty()) {
                        // push the operand
                        // DEBUG: System.out.println("Operand: \"" + operand + "\" pushed to stack");
                        infixStack.push(operand);
                        operand = "";
                    }

                } else if (Pattern.matches("[A-Za-z]", nextChar.toString())) {
                    // nextChar is a letter
                    operand += nextChar;

                } else {
                    // unsupported character
                    s = ("Invalid: \"" + nextChar + "\"");
                    s += ("ERROR - unsupport value or character!");
                    s += ("Program terminated with error");
                    throw new InvalidNotationFormatException(s);

                }
            }

            infix = infixStack.top();
            if (infixStack.top().equals("")) {
                // unable to convert user entered Postfix expression 
                throw new InvalidNotationFormatException("Unable to convert user entered Postfix expression: \n  \""
                        + expression + "\"");
            } else {
                // successfully converted user entered Postfix expression 
                // remove trailing space(s) from user entered expression and infix conversion string
                while (expression.endsWith(" ")) {
                    expression = expression.trim();
                }
                while (infix.endsWith(" ")) {
                    infix = infix.substring(0, infix.length() - 2);
                }

            }
        } catch (Exception ex) {
            throw new InvalidNotationFormatException(s);
        }
        return "(" + infix + ")";

    }

    /**
     * a method to evaluatePostfixExpression to evaluate the postfix expression.
     * It will take in a string and return a double
     *
     * @param postfixExpr
     * @return
     * @throws InvalidNotationFormatException
     */
    public static Double evaluatePostfixExpression(String postfixExpr) throws InvalidNotationFormatException {
        //checking for valid    
        if (postfixExpr == null || postfixExpr.length() == 0) {
            throw new InvalidNotationFormatException("Invalid postfix expression!");
        }
        MyStack<Double> stack = new MyStack<Double>();
        try {
            char[] chars = postfixExpr.toCharArray();

            for (char c : chars) {
                if (isOperand(c)) {
                    stack.push(new Double(c - '0')); // convert char to int val
                } else if (isOperator(c)) {
                    Double op1 = 0.0;
                    Double op2 = 0.0;
                    if (stack.isEmpty()) {
                        throw new InvalidNotationFormatException("Invalid format");
                    }
                    op1 = stack.pop();
                    if (stack.isEmpty()) {
                        throw new InvalidNotationFormatException("Invalid format");
                    }
                    op2 = stack.pop();
                    Double result;
                    switch (c) {
                        case '*':
                            result = op1 * op2;
                            stack.push(result);
                            break;
                        case '/':
                            result = op2 / op1;
                            stack.push(result);
                            break;
                        case '+':
                            result = op1 + op2;
                            stack.push(result);
                            break;
                        case '-':
                            result = op2 - op1;
                            stack.push(result);
                            break;
                    }
                }
            }
            return stack.pop();
        } catch (Exception ex) {
            throw new InvalidNotationFormatException("Invalid postfix expression!");
        }
        
    }

    //helper methods
    /**
     * Method to check if given string has balanced parenthesis
     *
     * @param s
     * @return
     */
    private static boolean checkParenthesis(String s) {
        int stack = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '(') {
                ++stack;
            } else if (c == ')') {
                --stack;
                if (stack < 0) {
                    return false;
                }
            }
        }
        return stack == 0;
    }

    private static boolean isOperator(char val) {
        return ops.indexOf(val) >= 0;
    }

    private static boolean isOperand(char val) {
        return operands.indexOf(val) >= 0;
    }

    private static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException numForEx) {
            return false;
        }
    }

    private static void gotOper(char opThis, int prec1) throws StackUnderflowException, QueueOverflowException, StackOverflowException {
        while (!theStack.isEmpty()) {
            char opTop = ((String) (theStack.pop() + "")).charAt(0);
            if (opTop == '(') {
                theStack.push(opTop);
                break;
            }// it's an operator
            else {// precedence of new op
                int prec2;
                if (opTop == '+' || opTop == '-') {
                    prec2 = 1;
                } else {
                    prec2 = 2;
                }
                if (prec2 < prec1) // if prec of new op less
                { //    than prec of old
                    theStack.push(opTop); // save newly-popped op
                    break;
                } else // prec of new not less
                {
                    output.enqueue(opTop); // than prec of old
                }
            }
        }
        theStack.push(opThis);
    }

    private static void gotParen(char ch) throws StackUnderflowException, QueueOverflowException {
        while (!theStack.isEmpty()) {
            char chx = ((String) (theStack.pop() + "")).charAt(0);
            if (chx == '(') {
                break;
            } else {
                output.enqueue(chx);
            }
        }
    }

// --------------------------------------------------------------
    private int operator(String n) {
        for (int i = 0; i < operators.length; i++) {
            if (n.equals(operators[i])) {
                return i;
            }
        }
        return operators.length;
    }

}
