package model;

import utils.ConstantsUtils;

public class Object {

    public enum OBJECT_TYPE {
        TYPE_A,
        TYPE_B,
        NONE
    }


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
