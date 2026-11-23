package school.coda.remy_axel_ethan.projet_java.game.placement;

import org.junit.jupiter.api.Test;
import school.coda.remy_axel_ethan.projet_java.boat.Boat;
import school.coda.remy_axel_ethan.projet_java.boat.BoatType;
import school.coda.remy_axel_ethan.projet_java.tools.Case;

import static org.assertj.core.api.Assertions.assertThat;
import static school.coda.remy_axel_ethan.projet_java.game.placement.GridTestBuilder.aGrid;

class BoardRulesTest {

    @Test
    void shouldReturnTrueWhenAllHorizontalCasesAreFree() {
        // Arrange
        Case[][] grid = aGrid().build();

        // Act
        boolean result = BoardRules.isCasesFree(0, 0, 3, true, grid);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenHorizontalPathContainsOccupiedCase() {
        // Arrange
        Case[][] grid = aGrid()
                .withOccupiedCase(2, 0)
                .build();

        // Act
        boolean result = BoardRules.isCasesFree(0, 0, 3, true, grid);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenAllVerticalCasesAreFree() {
        // Arrange
        Case[][] grid = aGrid().build();

        // Act
        boolean result = BoardRules.isCasesFree(4, 2, 4, false, grid);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenVerticalPathContainsOccupiedCase() {
        // Arrange
        Case[][] grid = aGrid()
                .withOccupiedCase(4, 5)
                .build();

        // Act
        boolean result = BoardRules.isCasesFree(4, 2, 4, false, grid);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnFalseFromIsntCaseValideWhenCaseIsEmpty() {
        // Arrange
        Case emptyCase = new Case(0, 0, "player");

        // Act
        boolean result = BoardRules.isntCaseValide(emptyCase);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueFromIsntCaseValideWhenCaseIsOccupied() {
        // Arrange
        Case occupiedCase = new Case(0, 0, "player");
        occupiedCase.setOccupiedBy(new Boat(BoatType.values()[0]));

        // Act
        boolean result = BoardRules.isntCaseValide(occupiedCase);

        // Assert
        assertThat(result).isTrue();
    }
}