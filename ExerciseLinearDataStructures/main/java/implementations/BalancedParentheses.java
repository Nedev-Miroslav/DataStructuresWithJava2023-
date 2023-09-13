package implementations;

import interfaces.Solvable;

import java.util.ArrayDeque;


public class BalancedParentheses implements Solvable {
    private String parentheses;
    private java.util.ArrayDeque<Character> stack;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
        this.stack = new ArrayDeque<>();
    }

    @Override
    public Boolean solve() {

        boolean toReturn = true;


        for (char bracket : parentheses.toCharArray()) {

            if (bracket == '(' || bracket == '{' || bracket == '[') {
                stack.push(bracket);
            } else if (bracket == ')' || bracket == '}' || bracket == ']') {


                if (bracket == ')' && !stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();

                } else if (bracket == '}' && !stack.isEmpty() && stack.peek() == '{') {
                    stack.pop();

                } else if (bracket == ']' && !stack.isEmpty() && stack.peek() == '[') {
                    stack.pop();

                } else {
                    toReturn = false;
                    break;
                }
            }
        }


        return toReturn;
    }
}
