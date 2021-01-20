package model;

import utils.ConstantsUtils;

/**
 * Cette classe sert à représenter les objets grâce à une énumération
 */
public class Object {

    /**
     * Représente les deux types d'Objets posssibles (A et B) ainsi que l'absence d'Objet (NONE) dans tout le programme
     */
    public enum OBJECT_TYPE {
        TYPE_A,
        TYPE_B,
        NONE
    }

    /**
     * @param type le type d'objet dont on veut la représentation
     * @return une String représentant le type d'objet passé en paramètre, telle que définie dans le fichier de constantes
     */
    public static String getDisplayName(OBJECT_TYPE type) {
        switch (type){
            case TYPE_A:
                return ConstantsUtils.OBJ_TYPE_A;
            case TYPE_B:
                return ConstantsUtils.OBJ_TYPE_B;
            default:
                return " ";
        }
    }

}
