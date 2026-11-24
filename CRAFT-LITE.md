# CRAFT LITE : Projet_Java

## 1. Lancement
GERARD Axel  
Commande pour lancer les tests : `mvn test`

## 2. Diagnostic (tag `lite-start`)
- **Issues SonarQube for IDE :** 19 issues
- **Classe la plus complexe / touchée :** `GameController.java` (10 issues)

| # | Smell | Localisation | Pourquoi c'est un problème |
|---|-------|--------------|----------------------------|
| 1 | Code commenté, constructeur privé manquant (`java:S1118`) et `isntCaseValide` à logique inversée avec `IO.println` | `game/placement/BoardRules.java` (l. 6-7, `isntCaseValide`) | Code mort, classe utilitaire instanciable, nom négatif contre-intuitif et couplage avec la console. |
| 2 | `GRID_SIZE` `public static` non `final` (`java:S1444`) et constructeur privé manquant (`java:S1118`) | `tools/Grille.java` (l. 7, l. 9) | État global modifiable partout, risque de casser les règles de placement et l'isolation des tests. |
| 3 | Champ `aiCases` inutilisé (`java:S1068`) et littéral `"player"` dupliqué 3 fois | `game/GameController.java` (l. 72, l. 87) | Code mort et duplication qui alourdissent la maintenance. |

**Smell choisi :** Smell #1 (`BoardRules.java`), car c'est une classe métier (hors interface graphique) où les tests unitaires ont le plus de valeur.

## 3. Tests écrits
Tests écrits en AAA avec AssertJ et un Test Data Builder (`GridTestBuilder.java`).

- **`BoardRulesTest`** (classe `BoardRules`) :
    - `shouldReturnTrueWhenAllHorizontalCasesAreFree`
    - `shouldReturnFalseWhenHorizontalPathContainsOccupiedCase`
    - `shouldReturnTrueWhenAllVerticalCasesAreFree`
    - `shouldReturnFalseWhenVerticalPathContainsOccupiedCase`
    - `shouldReturnFalseFromIsntCaseValideWhenCaseIsEmpty`
    - `shouldReturnTrueFromIsntCaseValideWhenCaseIsOccupied`
    - **Test paramétré (`@ParameterizedTest` + `@CsvSource`, 6 cas) :** `shouldCheckIfShipFitsInGrid`

- **`AiPlacementTest`** (classe `AiPlacement`) :
    - `shouldPlaceAllBoatsAtPositionsProvidedByRandomStub`
    - `shouldRetryWhenFirstRandomPositionIsAlreadyOccupied`
    - **Test double :** `StubRandom` (Stub de `java.util.Random`) injecté via le constructeur `AiPlacement(Case[][], Random)` pour isoler l'aléatoire.

## 4. Refactoring réalisé
- **Techniques :** suppression du code commenté, ajout d'un constructeur privé, *Extract Method* (`caseAt`), création de `isCaseOccupied` sans `IO.println` (délégation conservée dans `isntCaseValide`), simplification de `isInGrid`.
- **Tests qui le sécurisent :** `BoardRulesTest` et `AiPlacementTest` (écrits avant le refactoring dans `a80242f`, `8e1636f`, `9cb2128`, `7f89082`).
- **Commits concernés :**
    - `60f28d6` `refactor(board-rules): add private constructor and remove commented-out code`
    - `53e6e90` `refactor(board-rules): extract case lookup and decouple occupancy check`

Avant (`BoardRules.java`) :
```java
public static boolean isCasesFree(int x, int y, int size, boolean isHorizontal, Case[][] cases) {
    for (int i = 0; i < size; i++) {
        int checkX = isHorizontal ? x + i : x;
        int checkY = isHorizontal ? y : y + i;
        Case checkTarget = cases[checkX][checkY];
        if (isntCaseValide(checkTarget)) return false;
    }
    return true;
}

public static boolean isntCaseValide(Case target) {
    if (target.getOccupiedBy() != null) {
        IO.println("Au moins l'une des cases est déjà occupée !");
        return true;
    }
    return false;
}
```

Après (`BoardRules.java`) :
```java
public static boolean isCasesFree(int x, int y, int size, boolean isHorizontal, Case[][] cases) {
    for (int offset = 0; offset < size; offset++) {
        Case target = caseAt(cases, x, y, offset, isHorizontal);
        if (isCaseOccupied(target)) return false;
    }
    return true;
}

public static boolean isCaseOccupied(Case target) {
    return target.getOccupiedBy() != null;
}

public static boolean isntCaseValide(Case target) {
    return isCaseOccupied(target);
}
```

## 5. Ce qui reste à faire
- **Smells non traités :** Smell #2 (`Grille.java` : `GRID_SIZE` en `final` + constructeur privé) et Smell #3 (`GameController.java` : champ `aiCases` et constante `"player"`).
- **Bug découvert :** `BoardRules.isInGrid` ne rejette pas les coordonnées négatives (`x < 0` ou `y < 0`) ni un dépassement sur l'autre axe.
- **Avec 1h de plus :** corriger ce bug avec des tests de régression, supprimer `isntCaseValide` une fois ses appels migrés vers `isCaseOccupied`, et tester les autres classes métier (`Boat`, gestion des attaques).