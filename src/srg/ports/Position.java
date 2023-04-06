package srg.ports;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Position class.
 */
public class Position extends Object {
    /**
     * x coordinate
     */
    public final int x;
    /**
     * y coordinate
     */
    public final int y;
    /**
     * x coordinate
     */
    public final int z;

    /**
     * Constructs a position object.
     * @param x x-coord
     * @param y y-coord
     * @param z z-coord
     */
    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    /**
     * Gets the distance between 2 position object.
     * @param other The position that the distance will be calculated to.
     * @return Distance to the other position.
     */
    public int distanceTo(Position other) {
        int dist = (int) sqrt(pow((other.x - this.x), 2) + pow((other.y - this.y),2) + pow((other.z - this.z), 2));
        return dist;
    }

    /**
     * Gets a String representation of the pos object
     * @return A string representation of a position object.
     */
    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", x, y, z);
    }
}
