package model;

import utils.ConstantsUtils;
import utils.RandomUtils;

import java.util.*;

public class Agent {

    private static int agentNbCounter;

    private int agentNb;
    private Environment environment;

    private boolean use_error;
    private double error_ratio;
    private List<Object.OBJECT_TYPE> memory;

    private Object.OBJECT_TYPE pickedObj;
    private Object.OBJECT_TYPE objOnCell;
    private double obj_nearby_ratio_f;

    public Agent(Environment theEnvironment) {
        agentNb = agentNbCounter++;
        environment = theEnvironment;

        use_error = environment.useError();
        error_ratio = environment.getError_ratio();
        memory = new ArrayList<>();

        pickedObj = Object.OBJECT_TYPE.NONE;
        objOnCell = Object.OBJECT_TYPE.NONE;
        obj_nearby_ratio_f = 0.0;
    }

    public void perception() {
        objOnCell = environment.getCurrentCellObjectType(this);
        memory.add(objOnCell);
        if(memory.size() > ConstantsUtils.AGENT_MEMORY_SIZE) { memory.remove(0); }
        if(pickedObj == Object.OBJECT_TYPE.NONE) {
            if (objOnCell != Object.OBJECT_TYPE.NONE){
                obj_nearby_ratio_f = calculateF(environment.getSameObjNearbyNumber(objOnCell, this), objOnCell); } }
        else {
            obj_nearby_ratio_f = calculateF(environment.getSameObjNearbyNumber(pickedObj, this), pickedObj); }
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
            while (!environment.move(itDir.next(), this)); }
        catch (java.util.NoSuchElementException e) {
            hasMoved = false; }
        return hasMoved;
    }

    private boolean dropObject() {
        if( (pickedObj != Object.OBJECT_TYPE.NONE) && (objOnCell == Object.OBJECT_TYPE.NONE) ) {
            double dropProba = Math.pow(( obj_nearby_ratio_f / (environment.getK_Minus() + obj_nearby_ratio_f) ), 2.0);
            if(dropProba >= RandomUtils.randDouble()) {
                environment.addObjectCurrentCell(this, pickedObj);
                pickedObj = Object.OBJECT_TYPE.NONE;
                return true; } }
        return false;
    }

    private boolean pickObject() {
        if( (objOnCell != Object.OBJECT_TYPE.NONE) && (pickedObj == Object.OBJECT_TYPE.NONE) ) {
            double pickProba = ( environment.getK_Plus() / (environment.getK_Plus() + obj_nearby_ratio_f) );
            if(pickProba >= RandomUtils.randDouble()) {
                pickedObj = environment.removeObjectCurrentCell(this);
                objOnCell = Object.OBJECT_TYPE.NONE;
                return true; } }
        return false;
    }

    private double calculateF(int nbSameObjNearby, Object.OBJECT_TYPE obj_type) {
        if (!use_error){
            return nbSameObjNearby/ConstantsUtils.NB_NEARBY_CELLS; }
        else {
            int nbObjA = 0, nbObjB = 0, nbObj = 0;
            Iterator<Object.OBJECT_TYPE> it = memory.iterator();
            while (it.hasNext()) {
                Object.OBJECT_TYPE next = it.next();
                if(next == Object.OBJECT_TYPE.TYPE_A) {
                    nbObjA++; }
                else if(next == Object.OBJECT_TYPE.TYPE_B) {
                    nbObjB++; }
                nbObj++; }
            if(obj_type == Object.OBJECT_TYPE.TYPE_A) {
                return ( (nbObjA + (nbObjB * error_ratio) ) / nbObj ); }
            else {
                return ( (nbObjB + (nbObjA * error_ratio) ) / nbObj ); } }
    }


    public String getDisplayName() {
        return ((agentNb>9)?ConstantsUtils.AGENT_DISP + agentNb:ConstantsUtils.AGENT_DISP + "0" + agentNb);
    }

    public Agent clone() {
        Agent clone = new Agent(environment);
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
