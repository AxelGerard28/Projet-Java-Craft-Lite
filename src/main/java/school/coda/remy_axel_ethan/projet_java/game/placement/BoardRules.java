package school.coda.remy_axel_ethan.projet_java.game.placement;

import school.coda.remy_axel_ethan.projet_java.tools.Case;
import static school.coda.remy_axel_ethan.projet_java.tools.Grille.GRID_SIZE;

public class BoardRules {

    private BoardRules() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isInGrid(int x, int y, int size, boolean isHorizontal) {
        int startCoordinate = isHorizontal ? x : y;
        return (startCoordinate + size) <= GRID_SIZE;
    }

    public static boolean isCasesFree(int x, int y, int size, boolean isHorizontal, Case[][] cases) {
        for (int offset = 0; offset < size; offset++) {
            Case target = caseAt(cases, x, y, offset, isHorizontal);
            if (isCaseOccupied(target)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCaseOccupied(Case target) {
        return target.getOccupiedBy() != null;
    }

    public static boolean isntCaseValide(Case target) {
        return isCaseOccupied(target);
    }

    private static Case caseAt(Case[][] cases, int x, int y, int offset, boolean isHorizontal) {
        int targetX = isHorizontal ? x + offset : x;
        int targetY = isHorizontal ? y : y + offset;
        return cases[targetX][targetY];
    }
}