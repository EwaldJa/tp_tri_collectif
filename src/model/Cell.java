package model;

import utils.exceptions.EmptyCellException;
import utils.exceptions.FullCellException;

public class Cell {

    private Position pos;

    private Object.OBJECT_TYPE object;
    private Agent agent;

    public Cell(int x_coord, int y_coord, Environment w) {
        pos = new Position(x_coord, y_coord, w);
        agent = null;
        object = Object.OBJECT_TYPE.NONE;
    }

    public Position getPos() {
        return pos;
    }

    public int getX() {
        return pos.getX();
    }

    public int getY() {
        return pos.getY();
    }

    public Object.OBJECT_TYPE removeObject() {
        if(object != Object.OBJECT_TYPE.NONE) {
            Object.OBJECT_TYPE res = object;
            object = Object.OBJECT_TYPE.NONE;
            return res; }
        else {
            throw new EmptyCellException(String.format("Cell %s does not contain an Object", pos.toString())); }
    }

    public void addObject(Object.OBJECT_TYPE newObj) {
        if(object == Object.OBJECT_TYPE.NONE) {
            object = newObj; }
        else {
            throw new FullCellException(String.format("Cell %s already contains an Object", pos.toString())); }
    }

    public Agent removeAgent() {
        if(agent != null) {
            Agent res = agent.clone();
            agent = null;
            return res; }
        else {
            throw new EmptyCellException(String.format("Cell %s does not contain an Agent", pos.toString())); }
    }

    public void addAgent(Agent newAgent) {
        if(agent == null) {
            agent = newAgent; }
        else {
            throw new FullCellException(String.format("Cell %s already contains an Agent", pos.toString())); }
    }

    public Object.OBJECT_TYPE getObjectType() {
        return object;
    }

    public boolean hasAgent() {
        return (agent != null);
    }

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

