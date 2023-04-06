package srg.ports;

import srg.exceptions.InsufficientCapcaityException;
import srg.exceptions.InsufficientResourcesException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;
import srg.ship.CargoHold;
import srg.ship.RoomTier;

public class Store extends SpacePort {
    private final int MAX_FUEL = 1000;
    private final int MAX_REPAIR_KIT = 10;
    private CargoHold cargoHold = new CargoHold(RoomTier.AVERAGE);

    public Store(String name, Position position) {

        super(name, position);

        try {
            cargoHold.storeResource(new ResourceContainer(ResourceType.REPAIR_KIT, MAX_REPAIR_KIT));
            cargoHold.storeResource(new FuelContainer(FuelGrade.TRITIUM,MAX_FUEL));
            cargoHold.storeResource(new FuelContainer(FuelGrade.HYPERDRIVE_CORE,MAX_FUEL));
        } catch (InsufficientCapcaityException error){

        }
    }
    public ResourceContainer purchase(String item,
                                      int amount)
            throws InsufficientResourcesException {
       return new ResourceContainer(ResourceType.REPAIR_KIT, amount);
    }

}
