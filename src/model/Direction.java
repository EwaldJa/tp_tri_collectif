package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente les directions possibles de déplacement sur la grille de l'environnement
 * Il est possible d'ajouter 4 directions au projet en décommentant les lignes de code prévues à cet effet dans cette classe et dans Environnement, ainsi qu'en changeant une valeur dans ConstantsUtils
 */
public class Direction {

    /**
     * Instanciation des directions cardinales constantes utilisées dans le projet
     */
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

    /**
     * Définition des direction cardinales constantes utilisées dans ce projet
     */
    private static Direction NORTH, SOUTH, EAST, WEST;

    /* Supprimer les commentaires pour activer les déplacements en diagonale
    private static Direction NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST;
    */

    /**
     * Liste de toutes les Directions cardinales utilisées dans le projet
     */
    private static List<Direction> ALL_DIRECTIONS;

    /**
     * Coordonnées représentant la direction sur les axes Est-Ouest (x) et Nord-Sud (y)
     */
    private int x, y;

    /**
     * Crée une direction sans coordonnées
     */
    public Direction(){
        x=0;
        y=0;
    }

    /**
     * Instancie une direction spécifique avec les valeurs données
     * @param x_axis coordonnée de la direction sur l'axe Est-Ouest
     * @param y_axis coordonnée de la direction sur l'axe Nord-Surd
     */
    public Direction(int x_axis, int y_axis) {
        x = x_axis;
        y = y_axis;
    }

    /**
     * @return la liste des Directions cardinales utilisées dans le projet
     */
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

    /**
     * @return la Direction correspondant au Nord
     */
    public static Direction getNORTH() {
        return NORTH;
    }

    /**
     * @return la Direction correspondant au Sud
     */
    public static Direction getSOUTH() {
        return SOUTH;
    }

    /**
     * @return la Direction correspondant à l'Est
     */
    public static Direction getEAST() {
        return EAST;
    }

    /**
     * @return la Direction correspondant à l'Ouest
     */
    public static Direction getWEST() {
        return WEST;
    }

    /**
     * @return la Direction correspondant au Nord-Est
     */
    /* Supprimer les commentaires pour activer les déplacements en diagonale
    public static Direction getNORTH_EAST() {
        return NORTH_EAST;
    }*/

    /**
     * @return la Direction correspondant au Nord-Ouest
     */
    /* Supprimer les commentaires pour activer les déplacements en diagonale
    public static Direction getNORTH_WEST() {
        return NORTH_WEST;
    }*/

    /**
     * @return la Direction correspondant au Sud-Est
     */
    /* Supprimer les commentaires pour activer les déplacements en diagonale
    public static Direction getSOUTH_EAST() {
        return SOUTH_EAST;
    }*/

    /**
     * @return la Direction correspondant au Sud-Ouest
     */
    /* Supprimer les commentaires pour activer les déplacements en diagonale
    public static Direction getSOUTH_WEST() {
        return SOUTH_WEST;
    }*/

    /**
     * @return la coordonnée sur l'axe Est-Ouest de la Direction
     */
    public int getX() {
        return x;
    }

    /**
     * @param x la nouvelle coordonnée sur l'axe Est-Ouest de la Direction
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return la coordonnée sur l'axe Nord-Sud de la Direction
     */
    public int getY() {
        return y;
    }

    /**
     * @param y la nouvelle coordonnée sur l'axe Nord-Sud de la Direction
     */
    public void setY(int y) {
        this.y = y;
    }
}
