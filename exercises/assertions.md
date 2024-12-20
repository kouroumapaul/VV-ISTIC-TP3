# On assertions

Answer the following questions:

1. The following assertion fails `assertTrue(3 * .4 == 1.2)`. Explain why and describe how this type of check should be done.

2. What is the difference between `assertEquals` and `assertSame`? Show scenarios where they produce the same result and scenarios where they do not produce the same result.

3. In classes we saw that `fail` is useful to mark code that should not be executed because an exception was expected before. Find other uses for `fail`. Explain the use case and add an example.

4. In JUnit 4, an exception was expected using the `@Test` annotation, while in JUnit 5 there is a special assertion method `assertThrows`. In your opinion, what are the advantages of this new way of checking expected exceptions?

## Answer

# 1 

L'assertion echoue a cause de la représentation des nombres à virgule flottante. Le calcul 3 * .4 donne en réalité quelque chose comme 1.1999999999999997 à cause des imprécisions d'arrondis.

Pour que ça marche on doit preciser une marge d'erreur (delta) 

```java
assertEquals(1.2, 3 * .4, 0.0001);
```

# 2

- ```assertEquals``` vérifie si deux objets ont le même contenu (utilise la méthode .equals()).
- ```assertSame``` vérifie si deux variables référencent exactement le même objet en mémoire (utilise ==).

```java
    @Test
    public void memeResultat() {
        // Cas 1:
        String a = "test";
        String b = "test";
        assertEquals(a, b);  // Passe ✓
        assertSame(a, b);    // Passe ✓ (pool de String) car Java réutilise le même objet
    }

    @Test
    public void resultatDifferent() {
        // Cas 2: Nouveaux objets (résultat différent)
        String a = new String("test");
        String b = new String("test");
        assertEquals(a, b);  // Passe ✓ (même contenu)
        assertSame(a, b);    // Échoue ✗ (objets différents)
    }
```

# 3 
fail() peut être utilisé pour :

- Marquer du code non implémenté

```java
    @Test
    public void testFeatureNonImplementee() {
        fail("Fonctionnalité à implémenter");
    }
```

- Ou encore Forcer l'échec dans des scénarios non désirés

```java
    @Test
    public void testBranchesInvalides() {
        String role = "admin";
        
        // Détecter des cas invalides
        if ("admin".equals(role)) {
            // OK
        } else if ("user".equals(role)) {
            // OK
        } else {
            fail("Role invalide: " + role);
        }
    }
```

# 4

L'utilisation de assertThrows dans JUnit 5 apporte plusieurs avantages par rapport à JUnit4

- Permet de tester plusieurs exceptions dans un même test
@Test(expected = XXX.class) ne permettait d'indiquer qu'une seule classe d'exception. Pour vérifier plusieurs types d'exceptions différentes dans un même test avec JUnit 4, il fallait utiliser @Rule avec ExpectedException ou créer des tests séparés.

- Plus précis sur la localisation de l'exception
@Test(expected) validait si l'exception était levée n'importe où dans le test. assertThrows permet de spécifier exactement quel code doit lever l'exception.

- Permet de vérifier le contenu de l'exception
En JUnit 4, la vérification du message ou du contenu de l'exception nécessitait l'utilisation de @Rule et ExpectedException. assertThrows permet de capturer simplement l'exception pour l'analyser.

- Code plus lisible
assertThrows rend explicite quel code doit lever l'exception, contrairement à @Test(expected) qui s'appliquait à toute la méthode de test.