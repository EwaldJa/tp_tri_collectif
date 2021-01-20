package model;


import utils.RandomUtils;
import utils.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe représente l'environnement du projet
 * Il est possible d'ajouter 4 directions au projet en décommentant les lignes de code prévues à cet effet dans cette classe et dans Direction, ainsi qu'en changeant une valeur dans ConstantsUtils
 */
public class Environment {

    /**
     * Valeurs utilisées dans l'environnement
     */
    private int size_x, size_y, nb_Obj_A, nb_Obj_B, nb_Agent, agent_Range;
    private double k_Plus, k_Minus, error_ratio;
    private boolean use_error;

    /**
     * Grille représentant l'environnement
     */
    private List<List<Cell>> world;
    /**
     * Les agents associés à leurs positions respectives
     */
    private Map<Agent, Position> agents;

    /**
     * Construit un environnement avec toutes les valeurs qui seront utilisées
     * @param sizeX la taille sur l'axe Est-Ouest de l'environnement
     * @param sizeY la taille sur l'axe Nord-Sud de l'environnement
     * @param nbObjA le nombre d'objets de type A à ajouter sur le monde
     * @param nbObjB le nombre d'objets de type B à ajouter sur le monde
     * @param nbAgent le nombre d'agents à ajouter sur le monde pour trier
     * @param agentRange la distance (nombre de case) que parcourent les agents à chaque déplacement
     * @param kPlus constante utilisée dans le calcule de la probabilité de prise de l'objet situé sur la case occupée par un agent
     * @param kMinus constante utilisée dans le calcule de la probabilité de dépôt de l'objet porté par un agent sur la case qu'il occupe
     * @param useError flag définissant l'utilisation du mode mémoire+taux d'erreur ou du mode naïf
     * @param errorRatio taux d'erreur dans la reconnaissance des objets
     */
    public Environment(int sizeX, int sizeY, int nbObjA, int nbObjB, int nbAgent, int agentRange, double kPlus, double kMinus, boolean useError, double errorRatio) {
        size_x = sizeX; size_y = sizeY; nb_Obj_A = nbObjA; nb_Obj_B = nbObjB; nb_Agent = nbAgent; agent_Range = agentRange; k_Plus = kPlus; k_Minus = kMinus; use_error = useError; error_ratio = errorRatio;
    }

    /**
     * Initialise l'environnement, place les objets et les agents
     * @return la liste des agents
     */
    public List<Agent> init() {
        /*Initialize the map*/
        world = new ArrayList<>();
        for (int lines = 0; lines < size_y; lines++) {
            List<Cell> line = new ArrayList<>();
            for(int columns = 0; columns < size_x; columns++) {
                line.add(new Cell(columns, lines, this)); }
            world.add(line); }

        /*Add randomly the objects and agents*/
        int nbObjAToAdd = nb_Obj_A, nbObjBToAdd = nb_Obj_B, nbAgentToAdd = nb_Agent;
        int randX, randY;

        while(nbObjAToAdd > 0) {
            randX = RandomUtils.randIntMavVal(size_x);
            randY = RandomUtils.randIntMavVal(size_y);
            Cell c = world.get(randY).get(randX);
            if (c.getObjectType() == Object.OBJECT_TYPE.NONE) {
                c.addObject(Object.OBJECT_TYPE.TYPE_A);
                nbObjAToAdd--; } }

        while(nbObjBToAdd > 0) {
            randX = RandomUtils.randIntMavVal(size_x);
            randY = RandomUtils.randIntMavVal(size_y);
            Cell c = world.get(randY).get(randX);
            if (c.getObjectType() == Object.OBJECT_TYPE.NONE) {
                c.addObject(Object.OBJECT_TYPE.TYPE_B);
                nbObjBToAdd--; } }

        agents = new HashMap<>();
        while(nbAgentToAdd > 0) {
            randX = RandomUtils.randIntMavVal(size_x);
            randY = RandomUtils.randIntMavVal(size_y);
            Cell c = world.get(randY).get(randX);
            if (!c.hasAgent()) {
                Agent a = new Agent(this);
                c.addAgent(a);
                agents.put(a, new Position(randX, randY, this));
                nbAgentToAdd--; } }

        return new ArrayList<>(agents.keySet());
    }

    /**
     * Déplace un Agent dans la Direction précisée, si possible
     * @param dir la Direction dans laquelle déplacer l'Agent
     * @param agent l'Agent à déplacer
     * @return true si le déplacement a été fait, false sinon
     */
    public boolean move(Direction dir, Agent agent) {
        Position pos = agents.get(agent), nextPos = new Position(pos.getX(), pos.getY(), this);
        for(int i = 0; i < agent_Range; i++) {
            nextPos.addDirection(dir);
            if (world.get(nextPos.getY()).get(nextPos.getX()).hasAgent()){
                return false; } }
        world.get(nextPos.getY()).get(nextPos.getX()).addAgent(world.get(pos.getY()).get(pos.getX()).removeAgent());
        agents.put(agent, nextPos);
        return true;
    }

    /**
     * Calcule le nombre d'objets de même type dans l'environnement immédiat de l'Agent
     * @param obj_type le type d'objet
     * @param agent l'agent
     * @return le nombre d'objet de même type dans l'environnement immédiat de l'agent (cases immédiatement autour de l'agent)
     */
    public int getSameObjNearbyNumber(Object.OBJECT_TYPE obj_type, Agent agent) {
        if (obj_type == Object.OBJECT_TYPE.NONE) {
            throw new InvalidArgumentException("Object type given to 'getSameObjNearbyNumber' method is of type NONE"); }
        Position pos = agents.get(agent);
        int x = pos.getX(), y = pos.getY();
        int nbSameObj = 0;
        Position north = pos.newAddDirection(Direction.getNORTH());
        Position south = pos.newAddDirection(Direction.getSOUTH());
        Position east = pos.newAddDirection(Direction.getEAST());
        Position west = pos.newAddDirection(Direction.getWEST());
        /* Supprimer les commentaires pour activer les déplacements en diagonale
        Position north_east = pos.newAddDirection(Direction.getNORTH_EAST());
        Position north_west = pos.newAddDirection(Direction.getNORTH_WEST());
        Position south_east = pos.newAddDirection(Direction.getSOUTH_EAST());
        Position south_west = pos.newAddDirection(Direction.getSOUTH_WEST());
        */
        try {
            if (world.get(north.getY()).get(north.getX()).getObjectType() == obj_type) { nbSameObj++; }
            if (world.get(south.getY()).get(south.getX()).getObjectType() == obj_type) { nbSameObj++; }
            if (world.get(east.getY()).get(east.getX()).getObjectType() == obj_type) { nbSameObj++; }
            if (world.get(west.getY()).get(west.getX()).getObjectType() == obj_type) { nbSameObj++; }
            /* Supprimer les commentaires pour activer les déplacements en diagonale
            if (world.get(north_east.getY()).get(north_east.getX()).getObjectType() == obj_type) { nbSameObj++; }
            if (world.get(north_west.getY()).get(north_west.getX()).getObjectType() == obj_type) { nbSameObj++; }
            if (world.get(south_east.getY()).get(south_east.getX()).getObjectType() == obj_type) { nbSameObj++; }
            if (world.get(south_west.getY()).get(south_west.getX()).getObjectType() == obj_type) { nbSameObj++; }
             */
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return nbSameObj;
    }

    /**
     * @param agent l'agent dont il est question
     * @return le type d'objet (A, B ou NONE) présent sur la case sur laquelle se trouve l'Agent
     */
    public Object.OBJECT_TYPE getCurrentCellObjectType(Agent agent) {
        Object.OBJECT_TYPE type = Object.OBJECT_TYPE.NONE;
        try {
            Position pos = agents.get(agent);
            type =  world.get(pos.getY()).get(pos.getX()).getObjectType();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * Ajoute un Objet à la case sur laquelle se trouve l'Agent
     * @param agent l'Agent dont il est question
     * @param pickedObj le type d'objet porté par l'agent
     */
    public void addObjectCurrentCell(Agent agent, Object.OBJECT_TYPE pickedObj) {
        try {
            Position pos = agents.get(agent);
            world.get(pos.getY()).get(pos.getX()).addObject(pickedObj);
        }
            catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retire un objet de la case sur laquelle se trouve l'Age,t
     * @param agent l'Agent dont il est question
     * @return l'objet retiré de la case
     */
    public Object.OBJECT_TYPE removeObjectCurrentCell(Agent agent) {
        Object.OBJECT_TYPE type = Object.OBJECT_TYPE.NONE;
        try {
            Position pos = agents.get(agent);
            type = world.get(pos.getY()).get(pos.getX()).removeObject(); }
        catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * @return la taille sur l'axe Est-Ouest de l'environnement
     */
    public int getSize_x() {
        return size_x;
    }

    /**
     * @return la taille sur l'axe Nord-Sud de l'environnement
     */
    public int getSize_y() {
        return size_y;
    }

    /**
     * @return le nombre d'objets de type A à ajouter sur le monde
     */
    public int getNb_Obj_A() {
        return nb_Obj_A;
    }

    /**
     * @return le nombre d'objets de type B à ajouter sur le monde
     */
    public int getNb_Obj_B() {
        return nb_Obj_B;
    }

    /**
     * @return le nombre d'agents à ajouter sur le monde pour trier
     */
    public int getNb_Agent() {
        return nb_Agent;
    }

    /**
     * @return la distance (nombre de case) que parcourent les agents à chaque déplacement
     */
    public int getAgent_Range() {
        return agent_Range;
    }

    /**
     * @return constante utilisée dans le calcule de la probabilité de prise de l'objet situé sur la case occupée par un agent
     */
    public double getK_Plus() {
        return k_Plus;
    }

    /**
     * @return constante utilisée dans le calcule de la probabilité de dépôt de l'objet porté par un agent sur la case qu'il occupe
     */
    public double getK_Minus() {
        return k_Minus;
    }

    /**
     * @return flag définissant l'utilisation du mode mémoire+taux d'erreur ou du mode naïf
     */
    public boolean useError() {
        return use_error;
    }

    /**
     * @return taux d'erreur dans la reconnaissance des objets
     */
    public double getError_ratio() {
        return error_ratio;
    }

    /**
     * @return une représentation de l'environnement, des agents, et des objets, sous forme de grille
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int lines = 0; lines < size_y; lines++) {
            List<Cell> line = new ArrayList<>();
            for(int columns = 0; columns < size_x; columns++) {
                sb.append("|").append(world.get(lines).get(columns).getDisplayName()); }
            sb.append("|\n"); }
        return sb.toString();
    }

    /**
     * Affiche la représentation de l'environnement sur System.out
     */
    public void display() {
        System.out.println(toString());
    }

}
