package fr.istic.vv;
import java.util.Stack;

public class StringUtils {

    private StringUtils() {}

    public static boolean isBalanced(String str) {
        if (str == null) return false;
        
        Stack<Character> stack = new Stack<>();
        
        for (char c : str.toCharArray()) {
            if (isOpenBracket(c)) {
                stack.push(c);
            } else if (isCloseBracket(c)) {
                if (stack.isEmpty()) return false;
                
                char open = stack.pop();
                if (!isMatchingPair(open, c)) {
                    return false;
                }
            }
            
        }
        return stack.isEmpty();
    }
    
    private static boolean isOpenBracket(char c) {
        return c == '(' || c == '[' || c == '{';
    }
    
    private static boolean isCloseBracket(char c) {
        return c == ')' || c == ']' || c == '}';
    }
    
    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }
}
