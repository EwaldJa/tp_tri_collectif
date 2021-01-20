package model;

import utils.ConstantsUtils;
import utils.RandomUtils;

import java.util.*;

/**
 * Cette classe représente un agent qui évolue sur l'environnement pour trier les objets
 */
public class Agent {

    /**
     * Compte le nombre d'agents instanciés, pour avoir un ID unique
     */
    private static int agentNbCounter;

    /**
     * Id de l'agent
     */
    private int agentNb;
    /**
     * Environnement dans lequel évolue l'agent
     */
    private Environment environment;

    /**
     * Flag permettant de savoir sil il faut ou non utiliser la mémoire et le taux d'erreur
     */
    private boolean use_error;
    /**
     * Taux d'erreur de reconnaissance des objets
     */
    private double error_ratio;
    /**
     * Mémoire des objets déjà rencontrés
     */
    private List<Object.OBJECT_TYPE> memory;

    /**
     * Objet actuellement porté par l'agent
     */
    private Object.OBJECT_TYPE pickedObj;
    /**
     * Objet actuellement sur la case sur laquelle se situe l'agent
     */
    private Object.OBJECT_TYPE objOnCell;
    /**
     * valeur f calculé en fonction des objets de même type dans l'environnement, ou de la mémoire, selon le mode
     */
    private double obj_nearby_ratio_f;

    /**
     * Constructeur de l'Agent, initialise les variables utilisées dans le programme, initialise l'id unique
     * @param theEnvironment environnement dans lequel évolue l'Agent
     */
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

    /**
     * Perçoit l'environnement de l'Agent (objet sur la case actuelle), et calcule la valeur de f
     */
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

    /**
     * Exécute les actions possibles de l'Agent : tentative de dépôt si possible, tentative de prise si possible et si pas de dépôt, tentative de mouvement
     */
    public void action() {
        if(!dropObject()){ //Pour éviter de reprendre l'objet qu'on vient de poser
            pickObject(); }
        move();
    }

    /**
     * Fonction faisant déplacer l'Agent : tire les directions dans un ordre aléatoire, les teste toutes jusqu'à la première qui lui permet de se déplacer ou alors ne se déplace pas
     * @return true si l'Agent s'est déplacé, false sinon
     */
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

    /**
     * Fonction faisant la tentative de dépôt d'objet, si l'Agent a un objet à déposer, et si la case est vide
     * @return true si un dépôt a été fait, false sinon
     */
    private boolean dropObject() {
        if( (pickedObj != Object.OBJECT_TYPE.NONE) && (objOnCell == Object.OBJECT_TYPE.NONE) ) {
            double dropProba = Math.pow(( obj_nearby_ratio_f / (environment.getK_Minus() + obj_nearby_ratio_f) ), 2.0);
            if(dropProba >= RandomUtils.randDouble()) {
                environment.addObjectCurrentCell(this, pickedObj);
                pickedObj = Object.OBJECT_TYPE.NONE;
                return true; } }
        return false;
    }

    /**
     * Fonction faisant la tentative de récupération d'objet, si l'Agent ne porte pas d'objet, et si la case en contient un
     * @return true si une récupération a été faite, false sinon
     */
    private boolean pickObject() {
        if( (objOnCell != Object.OBJECT_TYPE.NONE) && (pickedObj == Object.OBJECT_TYPE.NONE) ) {
            double pickProba = ( environment.getK_Plus() / (environment.getK_Plus() + obj_nearby_ratio_f) );
            if(pickProba >= RandomUtils.randDouble()) {
                pickedObj = environment.removeObjectCurrentCell(this);
                objOnCell = Object.OBJECT_TYPE.NONE;
                return true; } }
        return false;
    }

    /**
     * Calcule la valeur de f, selon le mode utilisé (mode erreur+mémoire ou mode naïf)
     * @param nbSameObjNearby le nombre d'objets de même type dans l'environnement immédiat
     * @param obj_type le type d'objet auquel on s'intéresse
     * @return la valeur de f
     */
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

    /**
     * @return le nom (numéro d'identification) de l'Agent prêt à être affiché dans la console
     */
    public String getDisplayName() {
        return ((agentNb>9)?ConstantsUtils.AGENT_DISP + agentNb:ConstantsUtils.AGENT_DISP + "0" + agentNb);
    }

    /**
     * @return une copie de l'Agent
     */
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
