package model;

import java.util.ArrayList;
import java.util.List;

public class Direction {

    static {
        NORTH = new Direction(0, -1);
        SOUTH = new Direction(0, +1);
        EAST = new Direction(+1, 0);
        WEST = new Direction(-1, 0);
        /* Supprimer les commentaires pour activer les déplacements en diagonale
        NORTH_EAST = new Direction(+1, -1);
        NORTH_WEST = new Direction(-1, -1);
        SOUTH_EAST = new Direction(+1, +1);
        SOUTH_WEST = new Direction(-1, +1);
        */
    }

    private static Direction NORTH, SOUTH, EAST, WEST;
    /* Supprimer les commentaires pour activer les déplacements en diagonale
    private static Direction NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST;
    */
    private static List<Direction> ALL_DIRECTIONS;

    private int x, y;

    public Direction(){
        x=0;
        y=0;
    }

    public Direction(int x_axis, int y_axis) {
        x = x_axis;
        y = y_axis;
    }

    public static List<Direction> getALL_DIRECTIONS() {
        if (ALL_DIRECTIONS == null) {
            ALL_DIRECTIONS = new ArrayList<>();
            ALL_DIRECTIONS.add(NORTH);
            ALL_DIRECTIONS.add(SOUTH);
            ALL_DIRECTIONS.add(EAST);
            ALL_DIRECTIONS.add(WEST);
            /* Supprimer les commentaires pour activer les déplacements en diagonale
            ALL_DIRECTIONS.add(NORTH_EAST);
            ALL_DIRECTIONS.add(NORTH_WEST);
            ALL_DIRECTIONS.add(SOUTH_EAST);
            ALL_DIRECTIONS.add(SOUTH_WEST);
            */
        }
        return ALL_DIRECTIONS;
    }

    public static Direction getNORTH() {
        return NORTH;
    }

    public static Direction getSOUTH() {
        return SOUTH;
    }

    public static Direction getEAST() {
        return EAST;
    }

    public static Direction getWEST() {
        return WEST;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
