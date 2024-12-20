# Test the Date class

Implement a class `Date` with the interface shown below:

```java
class Date implements Comparable<Date> {

    public Date(int day, int month, int year) { ... }

    public static boolean isValidDate(int day, int month, int year) { ... }

    public static boolean isLeapYear(int year) { ... }

    public Date nextDate() { ... }

    public Date previousDate { ... }

    public int compareTo(Date other) { ... }

}
```

The constructor throws an exception if the three given integers do not form a valid date.

`isValidDate` returns `true` if the three integers form a valid year, otherwise `false`.

`isLeapYear` says if the given integer is a leap year.

`nextDate` returns a new `Date` instance representing the date of the following day.

`previousDate` returns a new `Date` instance representing the date of the previous day.

`compareTo` follows the `Comparable` convention:

* `date.compareTo(other)` returns a positive integer if `date` is posterior to `other`
* `date.compareTo(other)` returns a negative integer if `date` is anterior to `other`
* `date.compareTo(other)` returns `0` if `date` and `other` represent the same date.
* the method throws a `NullPointerException` if `other` is `null` 

Design and implement a test suite for this `Date` class.
You may use the test cases discussed in classes as a starting point. 
Also, feel free to add any extra method you may need to the `Date` class.


Use the following steps to design the test suite:

1. With the help of *Input Space Partitioning* design a set of initial test inputs for each method. Write below the characteristics and blocks you identified for each method. Specify which characteristics are common to more than one method.
2. Evaluate the statement coverage of the test cases designed in the previous step. If needed, add new test cases to increase the coverage. Describe below what you did in this step.
3. If you have in your code any predicate that uses more than two boolean operators check if the test cases written to far satisfy *Base Choice Coverage*. If needed add new test cases. Describe below how you evaluated the logic coverage and the new test cases you added.
4. Use PIT to evaluate the test suite you have so far. Describe below the mutation score and the live mutants. Add new test cases or refactor the existing ones to achieve a high mutation score.

Use the project in [tp3-date](../code/tp3-date) to complete this exercise.

## Answer


# 1 Input Space Partitioning pour la classe Date

## Caractéristiques et Blocs Communs 
- L'année est invalide : `year < 1` 
- Le mois est invalide: `month < 1 || month > 12` 
- Le jour est invalide: `day < 1 || day > le jour max (en fonction des cas)`

## Caractéristiques et Blocs Spécifiques par Méthode

### Pour isLeapYear()
#### C4: Divisibilité de l'année
- B4.1: divisible par 4 et non par 100 (ex: 2020)
- B4.2: divisible par 400 (ex: 2000)
- B4.3: divisible par 100 mais pas 400 (ex: 1900)
- B4.4: non divisible par 4 (ex: 2023)

### Pour nextDate() et previousDate()
#### C5: Position dans le mois
- B5.1: premier jour du mois (jour = 1)
- B5.2: milieu du mois (jour entre 2 et avant-dernier)
- B5.3: dernier jour du mois

#### C6: Position dans l'année
- B6.1: premier mois (janvier)
- B6.2: mois intermédiaire (février à novembre)
- B6.3: dernier mois (décembre)

### Pour compareTo()
#### C7: Relation entre dates
- B7.1: this avant other
- B7.2: this égal à other
- B7.3: this après other
- B7.4: other est null



# 2 Coverage suite a l'ISP

![1er resultat du coverage](../images/coverage%20Date.PNG)

J'ai rajouté les test

```java
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
```

![Nouveau resultat coverage](../images/coverage%202%20date.PNG)

# 3 Base coverage

- Pour isLeapYear

`return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);`

```java
    @Test
    void testIsLeapYear_DivisibleBy4Not100() {
        assertTrue(Date.isLeapYear(2024)); // Divisible par 4, et non par 100
    }

    @Test
    void testIsLeapYear_DivisibleBy400() {
        assertTrue(Date.isLeapYear(2000)); // Divisible par 400
    }

    @Test
    void testIsLeapYear_DivisibleBy100Not400() {
        assertFalse(Date.isLeapYear(1900)); // Divisible par 100, et non par 400
    }

    @Test
    void testIsLeapYear_NotDivisibleBy4() {
        assertFalse(Date.isLeapYear(2023)); // non divisible par 4
    }
```

# 4 Mutation Testing avec PIT

Résultats de l'analyse de mutation :

![Resultatde PIT](../images/pit%20date%20v2.PNG)



