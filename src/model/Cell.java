package model;

import utils.exceptions.EmptyCellException;
import utils.exceptions.FullCellException;

/**
 * Cette classe représente un emplacement de l'environnement dans lequel évoluent les Agents, elle peut contenir un Agent et un Objet à la fois
 */
public class Cell {

    /**
     * Représente la position de la cellule dans l'environnement
     */
    private Position pos;

    /**
     * Représente le type d'objet présent sur la case
     */
    private Object.OBJECT_TYPE object;
    /**
     * L'Agent actuellement sur la case
     */
    private Agent agent;

    /**
     * Construit une case avec une position, sans Agent et sans Objet
     * @param x_coord position sur l'axe x de la case dans l'environnement
     * @param y_coord position sur l'axe y de la case dans l'environnement
     * @param w l'environnement dans lequel se situe la case
     */
    public Cell(int x_coord, int y_coord, Environment w) {
        pos = new Position(x_coord, y_coord, w);
        agent = null;
        object = Object.OBJECT_TYPE.NONE;
    }

    /**
     * @return la position de la cellule
     */
    public Position getPos() {
        return pos;
    }

    /**
     * @return la position sur l'axe des abscisses de la cellule dans l'environnement
     */
    public int getX() {
        return pos.getX();
    }

    /**
     * @return la position sur l'axe des ordonnées de la cellule dans l'environnement
     */
    public int getY() {
        return pos.getY();
    }

    /**
     * Enlève l'objet qui est actuellement sur la cellule s'il y en a un
     * @return l'objet enlevé
     */
    public Object.OBJECT_TYPE removeObject() {
        if(object != Object.OBJECT_TYPE.NONE) {
            Object.OBJECT_TYPE res = object;
            object = Object.OBJECT_TYPE.NONE;
            return res; }
        else {
            throw new EmptyCellException(String.format("Cell %s does not contain an Object", pos.toString())); }
    }

    /**
     * Ajoute un objet à la case si possible
     * @param newObj l'objet à ajouter
     */
    public void addObject(Object.OBJECT_TYPE newObj) {
        if(object == Object.OBJECT_TYPE.NONE) {
            object = newObj; }
        else {
            throw new FullCellException(String.format("Cell %s already contains an Object", pos.toString())); }
    }

    /**
     * Enlève l'Agent actuellement sur la case s'il y en a un
     * @return un clone de l'Agent, pour éviter tout problème de pointeurs
     */
    public Agent removeAgent() {
        if(agent != null) {
            Agent res = agent.clone();
            agent = null;
            return res; }
        else {
            throw new EmptyCellException(String.format("Cell %s does not contain an Agent", pos.toString())); }
    }

    /**
     * Ajoute un agent à la case si possible
     * @param newAgent l'Agent à ajouter
     */
    public void addAgent(Agent newAgent) {
        if(agent == null) {
            agent = newAgent; }
        else {
            throw new FullCellException(String.format("Cell %s already contains an Agent", pos.toString())); }
    }

    /**
     * @return le type d'objet sur la case (A, B ou NONE)
     */
    public Object.OBJECT_TYPE getObjectType() {
        return object;
    }

    /**
     * @return true si la case contient un agent, false sinon
     */
    public boolean hasAgent() {
        return (agent != null);
    }

    /**
     * @return une String représentant la case et son contenu (éventuel Agent et/ou éventuel Objet)
     */
    public String getDisplayName(){
        if (agent == null) {
            return "  " + Object.getDisplayName(object) + "  "; }
        else {
            if (object == Object.OBJECT_TYPE.NONE) {
                return " " + agent.getDisplayName() + " "; }
            else {
                return agent.getDisplayName() + " " + Object.getDisplayName(object); } }
    }
}

