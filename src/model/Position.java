package model;

public class Position {

    private int x, y;
    private Environment environment;

    public Position(int x_coord, int y_coord, Environment w) {
        environment = w; setX(x_coord); setY(y_coord);
    }

    public Position addDirection(Direction dir) {
        addX(dir.getX());
        addY(dir.getY());
        return this;
    }

    public Position newAddDirection(Direction dir) {
        return new Position(x + dir.getX(), y + dir.getY(), environment);
    }

    public int addX(int toAdd) {
        return setX(x+toAdd);
    }

    public int addY(int toAdd) {
        return setY(y+toAdd);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int setX(int newX) {
        x = (newX % environment.getSize_x() + environment.getSize_x()) % environment.getSize_x();
        return x;
    }

    public int setY(int newY) {
        y = (newY % environment.getSize_y() + environment.getSize_y()) % environment.getSize_y();
        return y;
    }

    public String toString() {
        return String.format("[%d, %d]", x, y);
    }
}
