package za.ac.vhuthu.sagrada;

import java.io.Serializable;

public class MyPoint implements Serializable {
    private static final long serialVersionUID = 123L;
    public int x;
    public int y;
    public MyPoint() {}

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Offset the point's coordinates by dx, dy
     */
    public final void offset(int dx, int dy) {
        x += dx;
        y += dy;
    }
}
