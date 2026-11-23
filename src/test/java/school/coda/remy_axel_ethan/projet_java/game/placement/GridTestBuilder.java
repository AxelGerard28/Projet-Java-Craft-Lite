package school.coda.remy_axel_ethan.projet_java.game.placement;

import school.coda.remy_axel_ethan.projet_java.boat.Boat;
import school.coda.remy_axel_ethan.projet_java.boat.BoatType;
import school.coda.remy_axel_ethan.projet_java.tools.Case;

import static school.coda.remy_axel_ethan.projet_java.tools.Grille.GRID_SIZE;

class GridTestBuilder {

    private final Case[][] grid;

    private GridTestBuilder() {
        this.grid = new Case[GRID_SIZE][GRID_SIZE];
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                grid[x][y] = new Case(x, y, "player");
            }
        }
    }

    static GridTestBuilder aGrid() {
        return new GridTestBuilder();
    }

    GridTestBuilder withOccupiedCase(int x, int y) {
        grid[x][y].setOccupiedBy(new Boat(BoatType.values()[0]));
        return this;
    }

    Case[][] build() {
        return grid;
    }
}