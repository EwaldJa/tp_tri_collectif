package model;


import utils.RandomUtils;
import utils.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {

    private int size_x, size_y, nb_Obj_A, nb_Obj_B, nb_Agent, agent_Range;
    private double k_Plus, k_Minus;

    private List<List<Cell>> world;
    private Map<Agent, Position> agents;

    public World(int sizeX, int sizeY, int nbObjA, int nbObjB, int nbAgent, int agentRange, double kPlus, double kMinus) {
        size_x = sizeX; size_y = sizeY; nb_Obj_A = nbObjA; nb_Obj_B = nbObjB; nb_Agent = nbAgent; agent_Range = agentRange; k_Plus = kPlus; k_Minus = kMinus;
    }

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
        try {
            if (world.get(north.getY()).get(north.getX()).getObjectType() == obj_type) { nbSameObj++; }
            if (world.get(south.getY()).get(south.getX()).getObjectType() == obj_type) { nbSameObj++; }
            if (world.get(east.getY()).get(east.getX()).getObjectType() == obj_type) { nbSameObj++; }
            if (world.get(west.getY()).get(west.getX()).getObjectType() == obj_type) { nbSameObj++; }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return nbSameObj;
    }

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

    public void addObjectCurrentCell(Agent agent, Object.OBJECT_TYPE pickedObj) {
        try {
            Position pos = agents.get(agent);
            world.get(pos.getY()).get(pos.getX()).addObject(pickedObj);
        }
            catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public int getSize_x() {
        return size_x;
    }

    public int getSize_y() {
        return size_y;
    }

    public int getNb_Obj_A() {
        return nb_Obj_A;
    }

    public int getNb_Obj_B() {
        return nb_Obj_B;
    }

    public int getNb_Agent() {
        return nb_Agent;
    }

    public double getK_Plus() {
        return k_Plus;
    }

    public double getK_Minus() {
        return k_Minus;
    }

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

    public void display() {
        System.out.println(toString());
    }

}
