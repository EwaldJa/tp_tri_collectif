package utils;

public class ConstantsUtils {

    public static final double K_PLUS = 0.1, K_MINUS = 0.3;
    public static final double NB_NEARBY_CELLS = 4.0; //Au cas où on veuille autoriser les déplacements en diagonales, auquel cas il faudra passer à 8.0, et supprimer les commentaires dans les classes Direction et Environnement;
    public static final int NB_COLUMNS = 50, NB_LINES = 50;
    public static final int NB_AGENT = 20, AGENT_RANGE = 1, AGENT_MEMORY_SIZE = 10;
    public static final int NB_OBJ_A = 200, NB_OBJ_B = 200;

    public static final String OBJ_TYPE_A = "A", OBJ_TYPE_B = "B";
    public static final String AGENT_DISP = "X";

    public static final boolean USE_ERROR = true;
    public static final double ERROR_RATIO = 0.15;
}
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