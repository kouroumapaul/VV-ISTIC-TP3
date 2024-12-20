# Detecting test smells with PMD

In folder [`pmd-documentation`](../pmd-documentation) you will find the documentation of a selection of PMD rules designed to catch test smells.
Identify which of the test smells discussed in classes are implemented by these rules.

Use one of the rules to detect a test smell in one of the following projects:

- [Apache Commons Collections](https://github.com/apache/commons-collections)
- [Apache Commons CLI](https://github.com/apache/commons-cli)
- [Apache Commons Math](https://github.com/apache/commons-math)
- [Apache Commons Lang](https://github.com/apache/commons-lang)

Discuss the test smell you found with the help of PMD and propose here an improvement.
Include the improved test code in this file.

## Answer

Nous avons vu en cours plusieurs tests smells : 

- UnitTestContainsTooManyAsserts : Detecte si un cas de tests contient plusieurs assertions (Ce qui ne doit pas être fait)
- UnitTestAssertionsShouldIncludeMessage : Detecte qu'une assertion inclue bien un message informatif

Nous avons utiliser la règle sur le projet `Common Collection`

- Voici un extrait des resultats pour la règle `UnitTestContainsTooManyAsserts` (Le resultat complet se trouve dans le fichier pmd-report)

```
.\commons-collections\src\test\java\org\apache\commons\collections4\AbstractArrayListTest.java:40:	UnitTestContainsTooManyAsserts:	Unit tests should not contain more than 1 assert(s).
.\commons-collections\src\test\java\org\apache\commons\collections4\AbstractArrayListTest.java:50:	UnitTestContainsTooManyAsserts:	Unit tests should not contain more than 1 assert(s).
.\commons-collections\src\test\java\org\apache\commons\collections4\AbstractLinkedListTest.java:86:	UnitTestContainsTooManyAsserts:	Unit tests should not contain more than 1 assert(s).
```
Pour resoudre, il faut juste utiliser une seule assertion par cas de tests 


- Pour la règle `UnitTestAssertionsShouldIncludeMessage` pmd check n'a donné aucun resultat, ce qui signe que toutes les assertions de tests dans Common Collection incluent des messages





