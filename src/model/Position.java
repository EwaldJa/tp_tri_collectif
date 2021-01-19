package model;

public class Position {

    private int x, y;
    private World world;

    public Position(int x_coord, int y_coord, World w) {
        world = w; setX(x_coord); setY(y_coord);
    }

    public Position addDirection(Direction dir) {
        addX(dir.getX());
        addY(dir.getY());
        return this;
    }

    public Position newAddDirection(Direction dir) {
        return new Position(x + dir.getX(), y + dir.getY(), world);
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
        x = (newX % world.getSize_x() + world.getSize_x()) % world.getSize_x();
        return x;
    }

    public int setY(int newY) {
        y = (newY % world.getSize_y() + world.getSize_y()) % world.getSize_y();
        return y;
    }

    public String toString() {
        return String.format("[%d, %d]", x, y);
    }
}
