package srg.ports;

import srg.exceptions.InsufficientCapacityException;
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

    /**
     * Constructs a Store object with a full Resource container of each type of resource.
     * @param name Unique name of the Store.
     * @param position Position of the Store represented by a Position object.
     */
    public Store(String name, Position position) {

        super(name, position);

        try {
            cargoHold.storeResource(new ResourceContainer(ResourceType.REPAIR_KIT, MAX_REPAIR_KIT));
            cargoHold.storeResource(new FuelContainer(FuelGrade.TRITIUM,MAX_FUEL));
            cargoHold.storeResource(new FuelContainer(FuelGrade.HYPERDRIVE_CORE,MAX_FUEL));
        } catch (InsufficientCapacityException error){

        }
    }

    /**
     * Purchase an item from this store.
     * @param item The desired items name e.g. TRITIUM
     * @param amount The amount of the desired item required
     * @return A resource container with the desired resource.
     * @throws InsufficientResourcesException if the store does not have enough of the desired item.
     */
    public ResourceContainer purchase(String item,
                                      int amount)
            throws InsufficientResourcesException {
       return new ResourceContainer(ResourceType.REPAIR_KIT, amount);
    }


}
