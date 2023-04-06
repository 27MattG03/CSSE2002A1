package srg.ports;

import srg.ship.Room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShipYard extends SpacePort{
    private List <String> canUpgrade;
    public ShipYard(String name, Position position, List<String> canUpgrade) {
        super(name, position);
        this.canUpgrade = canUpgrade;
    }

    public void upgrade(Room room) {
        boolean in = false;
       for (String type : canUpgrade) {
           if (type.equals(room.getClass().getName())) {
               in = true;
           }
       }
       if (in) {
           room.upgrade();
       } else {
           throw new IllegalArgumentException();
       }
    }

    @Override
    public List<String> getActions() {
        Iterator<String> iter = canUpgrade.iterator();
        List<String> actions = new ArrayList<>();

        while (iter.hasNext()){
            String roomType = iter.next();
            actions.add(String.format("upgrade %s",roomType));
        }
        return actions;
    }
}
