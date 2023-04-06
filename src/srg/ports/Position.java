package srg.ports;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Position extends Object {
    public final int x;
    public final int y;
    public final int z;

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     * @param other The position that the distance will be calculated to.
     * @return Distance to the other position.
     */
    public int distanceTo(Position other){
        int dist = (int) sqrt(pow((other.x-this.x),2)+pow((other.y-this.y),2)+pow((other.z-this.z),2));
        return dist;
    }

    /**
     *
     * @return A string representation of a position object.
     */
    @Override
    public String toString(){
        return String.format("(%d, %d, %d)", x, y, z);
    }
}
