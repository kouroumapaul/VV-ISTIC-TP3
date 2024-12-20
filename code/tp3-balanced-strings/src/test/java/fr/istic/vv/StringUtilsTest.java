package fr.istic.vv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static fr.istic.vv.StringUtils.isBalanced;

class StringUtilsTest {

    @Test
    public void testEmptyString() {
        assertTrue(isBalanced(""));
    }

    @Test
    public void testNullString() {
        assertFalse(isBalanced(null));
    }

    @Test
    public void testSimpleParentheses() {
        assertTrue(isBalanced("()"));
    }

    @Test
    public void testBalancedBrackets() {
        assertTrue(isBalanced("[]"));
    }

    @Test
    public void testBalancedBraces() {
        assertTrue(isBalanced("{}"));
    }

    @Test
    public void testMixedBrackets() {
        assertTrue(isBalanced("{[()]}"));
    }

    @Test
    public void testUnbalancedMissingClosing() {
        assertFalse(isBalanced("{["));
    }

    @Test
    public void testUnbalancedMissingOpening() {
        assertFalse(isBalanced("]}"));
    }

    @Test
    public void testMismatchedPairs() {
        assertFalse(isBalanced("([)]"));
    }

    @Test
    public void testMismatchedParenthesesToBracket() {
        assertFalse(isBalanced("(]"));
    }

    @Test
    public void testMismatchedParenthesesToBrace() {
        assertFalse(isBalanced("(}"));
    }

    @Test
    public void testMismatchedBracketToParentheses() {
        assertFalse(isBalanced("[)"));
    }

    @Test
    public void testMismatchedBracketToBrace() {
        assertFalse(isBalanced("[}"));
    }

    @Test
    public void testMismatchedBraceToParentheses() {
        assertFalse(isBalanced("{)"));
    }

    @Test
    public void testMismatchedBraceToBracket() {
        assertFalse(isBalanced("{]"));
    }

    @Test
    public void testAllBracketTypes() {
        assertTrue(isBalanced("({[]})"));
        assertFalse(isBalanced("({[}])"));
    }

    @Test
    public void testIsBalancedWithOpeningParenthese() {
        assertFalse(isBalanced("(")); 
    }

    @Test
    public void testIsBalancedWithOpeningCrochet() {
        assertFalse(isBalanced("[")); 
    }

    @Test
    public void testIsBalancedWithOpeningBracket() {
        assertFalse(isBalanced("{")); 
    }

}
