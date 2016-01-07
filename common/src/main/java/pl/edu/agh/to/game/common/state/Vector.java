package pl.edu.agh.to.game.common.state;

import java.io.Serializable;

public class Vector implements Serializable {
    private static final long serialVersionUID = 6192370920369386661L;

    private int x;
    private int y;

    public Vector() {
        this(0, 0);
    }

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public Vector setX(int x) {
        return new Vector(x, this.y);
    }

    public int getY() {
        return y;
    }

    public Vector setY(int y) {
        return new Vector(this.x, y);
    }

    public Vector add(Vector augend) {
        return new Vector(x + augend.getX(), y + augend.getY());
    }

    public Vector sub(Vector augend) {
        return new Vector(x - augend.getX(), y - augend.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;

        Vector vector = (Vector) o;

        if (x != vector.x) return false;
        return y == vector.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
    
    @Override
    public String toString() {
        return "[" + getX() + ", " + getY() + "]";
    }
}
