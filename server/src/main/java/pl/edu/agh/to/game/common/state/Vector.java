package pl.edu.agh.to.game.common.state;

public class Vector {
    int x;
    int y;

    public Vector() {
        this.x = this.y = 0;
    }

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Vector setX(int x) {
        this.x = x;
        return this;
    }

    public Vector setY(int y) {
        this.y = y;
        return this;
    }

    public Vector add(Vector augend) {
        return new Vector(x + augend.getX(), y + augend.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;

        Vector vector = (Vector) o;

        return getX() == vector.getX() && getY() == vector.getY();

    }

    @Override
    public String toString() {
        return "[" + getX() + ", " + getY() + "]";
    }
}
