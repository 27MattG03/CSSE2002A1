package srg.ship;

import srg.exceptions.InsufficientResourcesException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.exceptions.InsufficientCapcaityException;
import srg.resources.ResourceType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class that represents a CargoHold to store resources in.
 */
public class CargoHold extends Room {
    /**
     * The remaining capacity of a cargo hold.
     */
    private int capacity;
    /**
     * A list of the resource containers in a cargo hold
     */
    private List<ResourceContainer> resources = new ArrayList<ResourceContainer>();

    /**
     * Constructs a cargo hold of a certain room tier.
     * @param tier The desired room tier.
     */
    public CargoHold(RoomTier tier) {
        super(tier);
        this.capacity = getMaximumCapacity();
    }

    /**
     * Gets the maximum capacity of this cargo hold
     * @return The maximum capacity of a CargoHold based on its Tier
     */
    public int getMaximumCapacity() {
        switch (this.getTier()) {
            case BASIC :
                return 5;
            case AVERAGE:
                return 10;
            case PRIME:
                return 15;
            default:
                return 0;
        }
    }

    /**
     * Gets the remaining capacity of a cargo hold.
     * @return The remaining capacity of a CargoHold object.
     */
    public int getRemainingCapacity() {
        return this.capacity;
    }

    /**
     * Gets the resources stored in a cargo hold.
     * @return A list of the resource containers stored in a CargoHold.
     */
    public List<ResourceContainer> getResources() {
        return this.resources;
    }

    /**
     * Store a resource container in the CargoHold
     * @param resource the resource container.
     * @throws InsufficientCapcaityException if there is no room for this resource.
     */
    public void storeResource(ResourceContainer resource) throws InsufficientCapcaityException {
        if (this.resources.size() < getMaximumCapacity()) {
            this.resources.add(resource);
        } else {
            throw new InsufficientCapcaityException();
        }
    }

    /**
     * Find the containers with a specific type of Resource
     * @param type The ResourceType to be found
     * @return A list of ResourceContainers with this type of Resource
     */
    public List<ResourceContainer> getResourceByType(ResourceType type) {
        List<ResourceContainer> resourcesByType = new ArrayList<ResourceContainer>();
        for (ResourceContainer container : this.resources) {
            if (container.getType() == type) {
                resourcesByType.add(container);
            }
        }
        return resourcesByType;
    }

    /**
     * Find the containers with a specific grade of Fuel
     * @param grade The grade to be found.
     * @return A list of ResourceContainers with this grade of Fuel
     */
    public List<ResourceContainer> getResourceByType(FuelGrade grade) {
        List<ResourceContainer> fuelContainers = getResourceByType(ResourceType.FUEL);
        List<ResourceContainer> resourcesByType = new ArrayList<ResourceContainer>();
        for (ResourceContainer container : fuelContainers) {
            FuelContainer fuelContainer = (FuelContainer) container;
            if (fuelContainer.getFuelGrade() == grade) {
                resourcesByType.add(container);
            }
        }
        return resourcesByType;
    }

    /**
     * Gets the total amount of a certain type of resource
     * @param type The type of resource to be found
     * @return The total amount of a ResourceType in the CargoHold
     */
    public int getTotalAmountByType(ResourceType type) {
        List<ResourceContainer> resourceContainers = getResourceByType(type);
        int count = 0;
        for (ResourceContainer container : resourceContainers) {
            count += container.getAmount();
        }
        return count;
    }

    /**
     * Gets the total amount stored of a certain grade of fuel.
     * @param grade The grade to be found.
     * @return The total amount of a FuelGrade in the CargoHold
     */
    public int getTotalAmountByType(FuelGrade grade) {
        List<ResourceContainer> fuelContainers = getResourceByType(grade);
        int count = 0;
        for (ResourceContainer container : fuelContainers) {
            count += container.getAmount();
        }
        return count;
    }

    /**
     * Consume a specific non-fuel resource.
     * @param type The type of resource to be consumed
     * @param amount The amount of resource to be consumed
     * @throws InsufficientResourcesException If insufficient resources.
     */
    public void consumeResource(ResourceType type,
                                int amount)
            throws InsufficientResourcesException {
        if (type == ResourceType.FUEL) {
            throw new IllegalArgumentException();
        }
        if (getTotalAmountByType(type) < amount) {
            throw new InsufficientResourcesException();
        }

        List<ResourceContainer> resourceContainers = getResourceByType(type);
        int count = 0;
        Iterator<ResourceContainer> iter = resourceContainers.iterator();

        while (iter.hasNext()) {
            ResourceContainer current = iter.next();
            int amountRemain = amount - count;
            if (current.getAmount() < amountRemain) {
                current.setAmount(current.getAmount() - amountRemain);
            } else {
                count += current.getAmount();
                this.resources.remove(current);
            }
        }

    }

    /**
     * Consume a specific fuel grade from the CargoHold
     * @param grade The grade of fuel to be consumed.
     * @param amount The amount of resource to be consumed.
     * @throws InsufficientResourcesException if insufficient resources.
     */
    public void consumeResource(FuelGrade grade,
                                int amount)
            throws InsufficientResourcesException {
        if (getTotalAmountByType(grade) < amount) {
            throw new InsufficientResourcesException();
        }

        List<ResourceContainer> resourceContainers = getResourceByType(grade);
        int count = 0;
        Iterator<ResourceContainer> iter = resourceContainers.iterator();
        while (iter.hasNext()) {
            ResourceContainer current = iter.next();
            int amountRemain = amount - count;
            if (current.getAmount() < amountRemain) {
                current.setAmount(current.getAmount() - amountRemain);
            } else {
                count += current.getAmount();
                this.resources.remove(current);
            }
        }
    }
}
