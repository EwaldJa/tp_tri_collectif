package utils;

public class ConstantsUtils {

    public static final double K_PLUS = 0.1, K_MINUS = 0.3;
    public static final double NB_NEARBY_CELLS = 4.0; //Au cas où on veuille autoriser les déplacements en diagonales, auquel cas il faudra passer à 8.0, et supprimer les commentaires dans la classe Direction;
    public static final int NB_COLUMNS = 50, NB_LINES = 50;
    public static final int NB_AGENT = 20, AGENT_RANGE = 1, AGENT_MEMORY_SIZE = 10;
    public static final int NB_OBJ_A = 200, NB_OBJ_B = 200;

    public static final String OBJ_TYPE_A = "A", OBJ_TYPE_B = "B";
    public static final String AGENT_DISP = "X";

    public static final boolean USE_ERROR = true;
    public static final double ERROR_RATIO = 0.15;
}
