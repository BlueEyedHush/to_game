package pl.edu.agh.to.game.common.state;

public class Vector {
    int x;
    int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        this.x = this.y = 0;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Vector setX(int x) {
        return null;
    }

    public Vector setY(int y) {
        return null;
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
}
