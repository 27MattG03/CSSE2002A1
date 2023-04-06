package srg.ship;
import srg.exceptions.InsufficientResourcesException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.exceptions.InsufficientCapacityException;
import srg.resources.ResourceType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CargoHold extends Room {
    private final int MAX_CAPACITY_BASIC = 5;
    private final int MAX_CAPACITY_AVERAGE = 10;
    private final int MAX_CAPACITY_PRIME = 15;

    private int capacity;
    private List<ResourceContainer> resources = new ArrayList<ResourceContainer>();

    /**
     * Constructs a cargo hold of a certain room tier.
     * @param tier
     */
    public CargoHold(RoomTier tier) {
        super(tier);
        this.capacity = getMaximumCapacity();
    }

    /**
     *
     * @return The maximum capacity of a CargoHold based on its Tier
     */
    public int getMaximumCapacity() {
        switch (this.getTier()){
            case BASIC :
                return MAX_CAPACITY_BASIC;
            case AVERAGE:
                return MAX_CAPACITY_AVERAGE;
            case PRIME:
                return MAX_CAPACITY_PRIME;
            default:
                return 0;
        }
    }

    /**
     *
     * @return The remaining capacity of a CargoHold object.
     */
    public int getRemainingCapacity() {
        return this.capacity;
    }

    /**
     *
     * @return A list of the resource containers stored in a CargoHold.
     */
    public List<ResourceContainer> getResources(){
        return resources;
    }

    /**
     * Store a resource container in the CargoHold
     * @param resource the resource container.
     * @throws InsufficientCapacityException if there is no room for this resource.
     */
    public void storeResource(ResourceContainer resource) throws InsufficientCapacityException {
        if (this.resources.size() < getMaximumCapacity()) {
            this.resources.add(resource);
        }
        else {
            throw new InsufficientCapacityException();
        }
    }

    /**
     * Find the containers with a specific type of Resource
     * @param type The ResourceType to be found
     * @return A list of ResourceContainers with this type of Resource
     */
    public List<ResourceContainer> getResourceByType(ResourceType type){
        List<ResourceContainer> resourcesByType = new ArrayList<ResourceContainer>();
        for (ResourceContainer container: this.resources) {
            if(container.getType() == type){
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
    public List<ResourceContainer> getResourceByType(FuelGrade grade){
        List<ResourceContainer> fuelContainers = getResourceByType(ResourceType.FUEL);
        List<ResourceContainer> resourcesByType = new ArrayList<ResourceContainer>();
        for (ResourceContainer container: fuelContainers){
            FuelContainer fuelContainer = (FuelContainer)container;
            if (fuelContainer.getFuelGrade() == grade) {
                resourcesByType.add(container);
            }
        }
        return resourcesByType;
    }

    /**
     *
     * @param type
     * @return The total amount of a ResourceType in the CargoHold
     */
    public int getTotalAmountByType(ResourceType type) {
        List<ResourceContainer> resourceContainers = getResourceByType(type);
        int count = 0;
        for (ResourceContainer container: resourceContainers){
           count += container.getAmount();
        }
        return count;
    }

    /**
     *
     * @param grade
     * @return The total amount of a FuelGrade in the CargoHold
     */
    public int getTotalAmountByType(FuelGrade grade) {
        List<ResourceContainer> fuelContainers = getResourceByType(grade);
        int count = 0;
        for (ResourceContainer container: fuelContainers){
            count += container.getAmount();
        }
        return count;
    }

    /**
     * Consume a specific non-fuel resource.
     * @param type The type of resource to be consumed
     * @param amount The amount of resource to be consumed
     * @throws InsufficientResourcesException
     */
    public void consumeResource(ResourceType type,
                                int amount)
            throws InsufficientResourcesException {
        if (type == ResourceType.FUEL){
            throw new IllegalArgumentException();
        }
        if (getTotalAmountByType(type) < amount){
            throw new InsufficientResourcesException();
        }

        List<ResourceContainer> resourceContainers = getResourceByType(type);
        int count = 0;
        Iterator<ResourceContainer> iter = resourceContainers.iterator();

        while (iter.hasNext()){
            ResourceContainer current = iter.next();
            int amountRemain = amount - count;
            if (current.getAmount() < amountRemain){
                current.setAmount(current.getAmount()-amountRemain);
            }
            else {
                count += current.getAmount();
                this.resources.remove(current);
            }
        }

    }

    /**
     * Consume a specific fuel grade from the CargoHold
     * @param grade The grade of fuel to be consumed.
     * @param amount The amount of resource to be consumed.
     * @throws InsufficientResourcesException
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
