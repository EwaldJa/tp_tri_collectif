package model;

public class Position {

    /**
     * Entiers représentant les deux coordonnées de cette position
     */
    private int x, y;
    /**
     * Environnement dans lequel cette position est représentée
     */
    private Environment environment;

    /**
     * Instancie une position avec des coordonnées et un environnement dans lequel elle est représentée
     * @param x_coord la coordonnée sur l'axe des abscisses
     * @param y_coord la coordonnée sur l'axe des ordonnées
     * @param w l'environnement
     */
    public Position(int x_coord, int y_coord, Environment w) {
        environment = w; setX(x_coord); setY(y_coord);
    }

    /**
     * Ajoute les coordonnées correspondant à une direction à cette position
     * @param dir la direction à ajouter
     * @return cette Position, mise à jour
     */
    public Position addDirection(Direction dir) {
        addX(dir.getX());
        addY(dir.getY());
        return this;
    }

    /**
     * Crée une nouvelle Position avec les mêmes corrdonnées que la direction actuelle puis lui ajoute
     * @param dir la direction à ajouter
     * @return la nouvelle Position
     */
    public Position newAddDirection(Direction dir) {
        return new Position(x + dir.getX(), y + dir.getY(), environment);
    }

    /**
     * Augmente la coordonnée des abscisses de cette Position, de manière à ce que le monde soit un globe (quand on arrive tout à l'Est par exemple et qu'on continue, on se retrouve tout à l'Ouest)
     * @param toAdd la valeur à ajouter à la coordonnée des abscisses de cette Position
     * @return la nouvelle valeur de la coordonnée des abscisses de cette Position, après mise en forme en mode globe
     */
    public int addX(int toAdd) {
        return setX(x+toAdd);
    }

    /**
     * Augmente la coordonnée des ordonnées de cette Position, de manière à ce que le monde soit un globe (quand on arrive tout au Nord par exemple et qu'on continue, on se retrouve tout au Sud)
     * @param toAdd la valeur à ajouter à la coordonnée des ordonnées de cette Position
     * @return la nouvelle valeur de la coordonnée des ordonnées de cette Position, après mise en forme en mode globe
     */
    public int addY(int toAdd) {
        return setY(y+toAdd);
    }

    /**
     * @return la valeur de la coordonnée des abscisses de cette Position
     */
    public int getX() {
        return x;
    }

    /**
     * @return la valeur de la coordonnée des ordonnées de cette Position
     */
    public int getY() {
        return y;
    }

    /**
     * Défini la coordonnée des abscisses de cette Position, de manière à ce que le monde soit un globe (quand on arrive tout à l'Est par exemple et qu'on continue, on se retrouve tout à l'Ouest)
     * @param newX la nouvelle valeur à définir pour la coordonnée des abscisses de cette Position
     * @return la nouvelle valeur de la coordonnée des abscisses de cette Position, après mise en forme en mode globe
     */
    public int setX(int newX) {
        x = (newX % environment.getSize_x() + environment.getSize_x()) % environment.getSize_x();
        return x;
    }

    /**
     * Défini la coordonnée des ordonnées de cette Position, de manière à ce que le monde soit un globe (quand on arrive tout au Nord par exemple et qu'on continue, on se retrouve tout au Sud)
     * @param newY la nouvelle valeur à définir pour la coordonnée des ordonnées de cette Position
     * @return la nouvelle valeur de la coordonnée des ordonnées de cette Position, après mise en forme en mode globe
     */
    public int setY(int newY) {
        y = (newY % environment.getSize_y() + environment.getSize_y()) % environment.getSize_y();
        return y;
    }

    /**
     * @return une String représentant cette position et permettant de l'afficher dans une console par exemple
     */
    public String toString() {
        return String.format("[%d, %d]", x, y);
    }
}
