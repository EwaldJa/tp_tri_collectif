package model;

import utils.ConstantsUtils;
import utils.RandomUtils;

import java.util.*;

public class Agent {

    private static int agentNbCounter;

    private int agentNb;
    private World world;

    private List<Object.OBJECT_TYPE> memory;

    private Object.OBJECT_TYPE pickedObj;
    private Object.OBJECT_TYPE objOnCell;
    private double obj_nearby_ratio_f;

    public Agent(World theWorld) {
        agentNb = agentNbCounter++;
        world = theWorld;

        memory = new ArrayList<>();

        pickedObj = Object.OBJECT_TYPE.NONE;
        objOnCell = Object.OBJECT_TYPE.NONE;
        obj_nearby_ratio_f = 0.0;
    }

    public void perception() {
        objOnCell = world.getCurrentCellObjectType(this);
        memory.add(objOnCell);
        if(memory.size() > ConstantsUtils.AGENT_MEMORY_SIZE) { memory.remove(0); }
        if(pickedObj == Object.OBJECT_TYPE.NONE) {
            if (objOnCell != Object.OBJECT_TYPE.NONE){
                obj_nearby_ratio_f = calculateF(world.getSameObjNearbyNumber(objOnCell, this), objOnCell); } }
        else {
            obj_nearby_ratio_f = calculateF(world.getSameObjNearbyNumber(pickedObj, this), pickedObj); }
    }

    public void action() {
        if(!dropObject()){ //Pour éviter de reprendre l'objet qu'on vient de poser
            pickObject(); }
        move();
    }

    private boolean move() {
        boolean hasMoved = true;
        List<Direction> dirs = Direction.getALL_DIRECTIONS();
        Collections.shuffle(dirs);
        Iterator<Direction> itDir = dirs.iterator();
        try {
            while (!world.move(itDir.next(), this)); }
        catch (java.util.NoSuchElementException e) {
            hasMoved = false; }
        return hasMoved;
    }

    private boolean dropObject() {
        if( (pickedObj != Object.OBJECT_TYPE.NONE) && (objOnCell == Object.OBJECT_TYPE.NONE) ) {
            double dropProba = Math.pow(( obj_nearby_ratio_f / (world.getK_Minus() + obj_nearby_ratio_f) ), 2.0);
            if(dropProba >= RandomUtils.randDouble()) {
                world.addObjectCurrentCell(this, pickedObj);
                pickedObj = Object.OBJECT_TYPE.NONE;
                return true; } }
        return false;
    }

    private boolean pickObject() {
        if( (objOnCell != Object.OBJECT_TYPE.NONE) && (pickedObj == Object.OBJECT_TYPE.NONE) ) {
            double pickProba = ( world.getK_Plus() / (world.getK_Plus() + obj_nearby_ratio_f) );
            if(pickProba >= RandomUtils.randDouble()) {
                pickedObj = world.removeObjectCurrentCell(this);
                objOnCell = Object.OBJECT_TYPE.NONE;
                return true; } }
        return false;
    }

    private double calculateF(int nbSameObjNearby, Object.OBJECT_TYPE obj_type) {
        return nbSameObjNearby/ConstantsUtils.NB_NEARBY_CELLS;
    }


    public String getDisplayName() {
        return ((agentNb>9)?ConstantsUtils.AGENT_DISP + agentNb:ConstantsUtils.AGENT_DISP + "0" + agentNb);
    }

    public Agent clone() {
        Agent clone = new Agent(world);
        clone.agentNb = this.agentNb;
        clone.memory = this.memory;
        clone.pickedObj = this.pickedObj;
        clone.objOnCell = this.objOnCell;
        clone.obj_nearby_ratio_f = this.obj_nearby_ratio_f;
        return clone;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return agentNb == agent.agentNb;
    }

    @Override
    public int hashCode() {
        return Objects.hash(agentNb);
    }
}
