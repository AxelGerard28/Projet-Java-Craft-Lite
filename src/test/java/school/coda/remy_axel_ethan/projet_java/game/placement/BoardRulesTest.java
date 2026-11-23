package school.coda.remy_axel_ethan.projet_java.game.placement;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static school.coda.remy_axel_ethan.projet_java.tools.Grille.GRID_SIZE;

class BoardRulesTest {

    @Test
    void shouldReturnTrueWhenHorizontalShipFitsInsideGrid() {
        // Arrange
        int x = 0;
        int y = 0;
        int shipSize = 3;

        // Act
        boolean result = BoardRules.isInGrid(x, y, shipSize, true);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnTrueWhenHorizontalShipReachesRightBorderExactly() {
        // Arrange
        int shipSize = 3;
        int x = GRID_SIZE - shipSize;
        int y = 0;

        // Act
        boolean result = BoardRules.isInGrid(x, y, shipSize, true);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenHorizontalShipExceedsRightBorder() {
        // Arrange
        int shipSize = 3;
        int x = GRID_SIZE - shipSize + 1;
        int y = 0;

        // Act
        boolean result = BoardRules.isInGrid(x, y, shipSize, true);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenVerticalShipReachesBottomBorderExactly() {
        // Arrange
        int shipSize = 4;
        int x = 0;
        int y = GRID_SIZE - shipSize;

        // Act
        boolean result = BoardRules.isInGrid(x, y, shipSize, false);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenVerticalShipExceedsBottomBorder() {
        // Arrange
        int shipSize = 4;
        int x = 0;
        int y = GRID_SIZE - shipSize + 1;

        // Act
        boolean result = BoardRules.isInGrid(x, y, shipSize, false);

        // Assert
        assertThat(result).isFalse();
    }
}