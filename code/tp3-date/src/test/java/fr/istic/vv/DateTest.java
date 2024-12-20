package fr.istic.vv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DateTest {

    // Tests pour isValidDate
    @Test
    void testIsValidDate_ValidDate() {
        assertTrue(Date.isValidDate(15, 6, 2023));
    }

    @Test
    void testIsValidDate_InvalidDay() {
        assertFalse(Date.isValidDate(32, 1, 2023));
        assertFalse(Date.isValidDate(0, 1, 2023));
    }

    @Test
    void testIsValidDate_InvalidMonth() {
        assertFalse(Date.isValidDate(15, 13, 2023));
        assertFalse(Date.isValidDate(15, 0, 2023));
    }

    @Test
    void testIsValidDate_InvalidYear() {
        assertFalse(Date.isValidDate(15, 6, -1));
    }

    @Test
    void testIsValidDate_LeapYear() {
        assertTrue(Date.isValidDate(29, 2, 2020)); // Année bissextile
        assertFalse(Date.isValidDate(29, 2, 2023)); // Année non bissextile
    }

    @Test
    void testIsLeapYear_DivisibleBy4Not100() {
        assertTrue(Date.isLeapYear(2024)); // Divisible by 4, not 100
    }

    @Test
    void testIsLeapYear_DivisibleBy400() {
        assertTrue(Date.isLeapYear(2000)); // Divisible by 400
    }

    @Test
    void testIsLeapYear_DivisibleBy100Not400() {
        assertFalse(Date.isLeapYear(1900)); // Divisible by 100, not 400
    }

    @Test
    void testIsLeapYear_NotDivisibleBy4() {
        assertFalse(Date.isLeapYear(2023)); // Not divisible by 4
    }

    // Tests pour nextDate
    @Test
    void testNextDate_SameMonth() {
        Date date = new Date(15, 6, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(16, 6, 2023), next);
    }

    @Test
    void testNextDate_NextMonth() {
        Date date = new Date(30, 6, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 7, 2023), next);
    }

    @Test
    void testNextDate_NextYear() {
        Date date = new Date(31, 12, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 1, 2024), next);
    }

    // Tests pour previousDate
    @Test
    void testPreviousDate_SameMonth() {
        Date date = new Date(15, 6, 2023);
        Date previous = date.previousDate();
        assertEquals(new Date(14, 6, 2023), previous);
    }

    @Test
    void testPreviousDate_PreviousMonth() {
        Date date = new Date(1, 7, 2023);
        Date previous = date.previousDate();
        assertEquals(new Date(30, 6, 2023), previous);
    }

    @Test
    void testPreviousDate_PreviousYear() {
        Date date = new Date(1, 1, 2024);
        Date previous = date.previousDate();
        assertEquals(new Date(31, 12, 2023), previous);
    }

    @Test
    void testPreviousDate_InvalidYear() {
        Date date = new Date(1, 1, 1);
        assertThrows(IllegalStateException.class, date::previousDate);
    }

    // Tests pour compareTo
    @Test
    void testCompareTo_Before() {
        Date date1 = new Date(15, 6, 2023);
        Date date2 = new Date(16, 6, 2023);
        assertTrue(date1.compareTo(date2) < 0);
    }

    @Test
    void testCompareTo_Equal() {
        Date date1 = new Date(15, 6, 2023);
        Date date2 = new Date(15, 6, 2023);
        assertEquals(0, date1.compareTo(date2));
    }

    @Test
    void testCompareTo_After() {
        Date date1 = new Date(16, 6, 2023);
        Date date2 = new Date(15, 6, 2023);
        assertTrue(date1.compareTo(date2) > 0);
    }

    @Test
    void testCompareTo_Null() {
        Date date = new Date(15, 6, 2023);
        assertThrows(NullPointerException.class, () -> date.compareTo(null));
    }

    @Test
    void testNextDate_LeapYearFebruary() {
        Date date = new Date(28, 2, 2020); // Année bissextile
        Date next = date.nextDate();
        assertEquals(new Date(29, 2, 2020), next);
    }

    @Test
    void testEquals_DifferentDay() {
        Date date1 = new Date(15, 6, 2023);
        Date date2 = new Date(16, 6, 2023);
        assertNotEquals(date1, date2);
    }

    @Test
    void testEquals_DifferentMonth() {
        Date date1 = new Date(15, 6, 2023);
        Date date2 = new Date(15, 7, 2023);
        assertNotEquals(date1, date2);
    }

    @Test
    void testEquals_DifferentYear() {
        Date date1 = new Date(15, 6, 2023);
        Date date2 = new Date(15, 6, 2024);
        assertNotEquals(date1, date2);
    }

    @Test
    void testNextDate_EndOfMonthNonLeapYearFebruary() {
        Date date = new Date(28, 2, 2023); // Février non bissextile
        Date next = date.nextDate();
        assertEquals(new Date(1, 3, 2023), next);
    }

    @Test
    void testNextDate_EndOfMonthApril() {
        Date date = new Date(30, 4, 2023); // Avril
        Date next = date.nextDate();
        assertEquals(new Date(1, 5, 2023), next);
    }

    @Test
    void testNextDate_EndOfYear() {
        Date date = new Date(31, 12, 2023);
        Date next = date.nextDate();
        assertEquals(new Date(1, 1, 2024), next);
    }

    @Test
    void testNextDate_EndOfMonthWith31Days() {
        Date date = new Date(31, 1, 2023); // Janvier avec 31 jours
        Date next = date.nextDate();
        assertEquals(new Date(1, 2, 2023), next);
    }

    // Ajouter apres PIT
    @Test
    void testPreviousDate_JanuaryFirstToPreviousYear() {
        Date date = new Date(1, 1, 2023);
        Date prev = date.previousDate();
        assertEquals(new Date(31, 12, 2022), prev);
    }

    @Test
    void testPreviousDate_LeapYearFebruary29() {
        Date date = new Date(1, 3, 2020); // Leap year
        Date prev = date.previousDate();
        assertEquals(new Date(29, 2, 2020), prev);
    }

    @Test
    void testPreviousDate_NonLeapYearFebruary28() {
        Date date = new Date(1, 3, 2021); // Non-leap year
        Date prev = date.previousDate();
        assertEquals(new Date(28, 2, 2021), prev);
    }

    @Test
    void testCompareTo_DifferentYears() {
        Date date1 = new Date(1, 1, 2023);
        Date date2 = new Date(1, 1, 2022);
        assertTrue(date1.compareTo(date2) > 0);
    }

    @Test
    void testCompareTo_DifferentMonths() {
        Date date1 = new Date(1, 2, 2023);
        Date date2 = new Date(1, 1, 2023);
        assertTrue(date1.compareTo(date2) > 0);
    }

    @Test
    void testCompareTo_DifferentDays() {
        Date date1 = new Date(2, 1, 2023);
        Date date2 = new Date(1, 1, 2023);
        assertTrue(date1.compareTo(date2) > 0);
    }

    @Test
    void testEquals_SameDate() {
        Date date1 = new Date(15, 6, 2023);
        Date date2 = new Date(15, 6, 2023);
        assertTrue(date1.equals(date2));
    }

    @Test
    void testEquals_DifferentDate() {
        Date date1 = new Date(15, 6, 2023);
        Date date2 = new Date(16, 6, 2023);
        assertFalse(date1.equals(date2));
    }

    @Test
    void testEquals_NullOrDifferentClass() {
        Date date = new Date(15, 6, 2023);
        assertFalse(date.equals(null));
        assertFalse(date.equals("Not a Date"));
    }

    @Test
    void testGetDay() {
        Date date = new Date(15, 6, 2023);
        assertEquals(15, date.getDay());
    }

    @Test
    void testGetMonth() {
        Date date = new Date(15, 6, 2023);
        assertEquals(6, date.getMonth());
    }

    @Test
    void testGetYear() {
        Date date = new Date(15, 6, 2023);
        assertEquals(2023, date.getYear());
    }

}
