package srg.ports;

import srg.exceptions.InsufficientCapcaityException;
import srg.exceptions.InsufficientResourcesException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;
import srg.ship.CargoHold;
import srg.ship.RoomTier;

/**
 * A class representing a space port that is a store.
 */
public class Store extends SpacePort {
    /**
     * Maximum capacity of a fuel container.
     */
    private final int maxFuel = 1000;
    /**
     * The maximum capacity of a resource container.
     */
    private final int maxRepairKit = 10;
    /**
     * The cargohold of this store.
     */
    private CargoHold cargoHold = new CargoHold(RoomTier.AVERAGE);

    /**
     * Constructs a Store object with a full Resource container of each type of resource.
     * @param name Unique name of the Store.
     * @param position Position of the Store represented by a Position object.
     */
    public Store(String name, Position position) {

        super(name, position);

        try {
            cargoHold.storeResource(new ResourceContainer(ResourceType.REPAIR_KIT, maxRepairKit));
            cargoHold.storeResource(new FuelContainer(FuelGrade.TRITIUM, maxFuel));
            cargoHold.storeResource(new FuelContainer(FuelGrade.HYPERDRIVE_CORE, maxFuel));
        } catch (InsufficientCapcaityException error) {
            error.getMessage();
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
