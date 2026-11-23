package school.coda.remy_axel_ethan.projet_java.game.placement;

import org.junit.jupiter.api.Test;
import school.coda.remy_axel_ethan.projet_java.tools.Case;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static school.coda.remy_axel_ethan.projet_java.game.placement.GridTestBuilder.aGrid;

class AiPlacementTest {

    @Test
    void shouldPlaceAllBoatsAtPositionsProvidedByRandomStub() {
        // Arrange
        Case[][] grid = aGrid().build();
        Random stubRandom = new StubRandom(
                List.of(0, 0, 0, 1, 0, 2, 0, 3, 0, 4),
                List.of(true, true, true, true, true)
        );
        AiPlacement aiPlacement = new AiPlacement(grid, stubRandom);

        // Act
        aiPlacement.placeAllBoats();

        // Assert
        assertThat(List.of(grid[0][0], grid[0][1], grid[0][2], grid[0][3], grid[0][4]))
                .extracting(Case::getOccupiedBy)
                .doesNotContainNull();
    }

    @Test
    void shouldRetryWhenFirstRandomPositionIsAlreadyOccupied() {
        // Arrange
        Case[][] grid = aGrid()
                .withOccupiedCase(0, 0)
                .build();
        Random stubRandom = new StubRandom(
                List.of(0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5),
                List.of(true, true, true, true, true, true)
        );
        AiPlacement aiPlacement = new AiPlacement(grid, stubRandom);

        // Act
        aiPlacement.placeAllBoats();

        // Assert
        assertThat(grid[0][5].getOccupiedBy()).isNotNull();
    }

    private static class StubRandom extends Random {
        private final Queue<Integer> nextInts;
        private final Queue<Boolean> nextBooleans;

        StubRandom(List<Integer> nextInts, List<Boolean> nextBooleans) {
            this.nextInts = new ArrayDeque<>(nextInts);
            this.nextBooleans = new ArrayDeque<>(nextBooleans);
        }

        @Override
        public int nextInt(int bound) {
            return nextInts.remove();
        }

        @Override
        public boolean nextBoolean() {
            return nextBooleans.remove();
        }
    }
}