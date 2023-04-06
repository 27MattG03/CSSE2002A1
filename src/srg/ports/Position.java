package srg.ports;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Position extends Object {
    public final int x;
    public final int y;
    public final int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public int distanceTo(Position other){
        int dist = (int) sqrt(pow((other.x-this.x),2)+pow((other.y-this.y),2)+pow((other.z-this.z),2));
        return dist;
    }
    @Override
    public String toString(){
        return String.format("(%d,%d,%d)", x, y, z);
    }
}
