package ufrn.imd.modelo;

import java.util.Objects;

/**
 * Representa as coordenadas de um plano cartesiano.
 *
 * @version 1.1
 * @since 1.1
 */

public class Coordenada {
    private int x;
    private int y;

    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Coordenada)) {
            return false;
        }
        Coordenada coordenada = (Coordenada) o;
        return x == coordenada.x && y == coordenada.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
