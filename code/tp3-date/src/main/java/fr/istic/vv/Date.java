package fr.istic.vv;

public class Date implements Comparable<Date> {
    private final int day;
    private final int month;
    private final int year;
    
    private static final int[] DAYS_IN_MONTH = {
        0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public Date(int day, int month, int year) {
        if (!isValidDate(day, month, year)) {
            throw new IllegalArgumentException("Invalid date values provided");
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static boolean isValidDate(int day, int month, int year) {
        if (year < 1 || month < 1 || month > 12 || day < 1) {
            return false;
        }

        // Check days in month
        int maxDays = DAYS_IN_MONTH[month];
        if (month == 2 && isLeapYear(year)) {
            maxDays = 29;
        }

        return day <= maxDays;
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public Date nextDate() {
        int nextDay = day + 1;
        int nextMonth = month;
        int nextYear = year;

        int maxDays = DAYS_IN_MONTH[month];
        if (month == 2 && isLeapYear(year)) {
            maxDays = 29;
        }

        if (nextDay > maxDays) {
            nextDay = 1;
            nextMonth++;
            if (nextMonth > 12) {
                nextMonth = 1;
                nextYear++;
            }
        }

        return new Date(nextDay, nextMonth, nextYear);
    }

    public Date previousDate() {
        int prevDay = day - 1;
        int prevMonth = month;
        int prevYear = year;

        if (prevDay < 1) {
            prevMonth--;
            if (prevMonth < 1) {
                prevMonth = 12;
                prevYear--;
                if (prevYear < 1) {
                    throw new IllegalStateException("Date would be invalid: year < 1");
                }
            }
            prevDay = DAYS_IN_MONTH[prevMonth];
            if (prevMonth == 2 && isLeapYear(prevYear)) {
                prevDay = 29;
            }
        }

        return new Date(prevDay, prevMonth, prevYear);
    }

    @Override
    public int compareTo(Date other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare with null date");
        }
        
        if (this.year != other.year) {
            return this.year - other.year;
        }
        if (this.month != other.month) {
            return this.month - other.month;
        }
        return this.day - other.day;
    }

    // Getters
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Date other = (Date) obj;
        return day == other.day && month == other.month && year == other.year;
    }


    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}