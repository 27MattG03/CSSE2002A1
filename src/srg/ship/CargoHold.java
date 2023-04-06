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


public class CargoHold extends Room {
    private final int MAX_CAPACITY_BASIC = 5;
    private final int MAX_CAPACITY_AVERAGE = 10;
    private final int MAX_CAPACITY_PRIME = 15;

    private int capacity;
    private List<ResourceContainer> resources = new ArrayList<ResourceContainer>();
    public CargoHold(RoomTier tier) {
        super(tier);
        this.capacity = getMaximumCapacity();
    }
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
    public int getRemainingCapacity() {

        return this.capacity;
    }
    public List<ResourceContainer> getResources(){
        return resources;
    }
    public void storeResource(ResourceContainer resource) throws InsufficientCapcaityException {
        if (this.resources.size() < getMaximumCapacity()) {
            this.resources.add(resource);
        }
        else {
            throw new InsufficientCapcaityException();
        }
    }
    public List<ResourceContainer> getResourceByType(ResourceType type){
        List<ResourceContainer> resourcesByType = new ArrayList<ResourceContainer>();
        for (ResourceContainer container: this.resources) {
            if(container.getType() == type){
            resourcesByType.add(container);
            }

        }
        return resourcesByType;
    }
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
    public int getTotalAmountByType(ResourceType type) {
        List<ResourceContainer> resourceContainers = getResourceByType(type);
        int count = 0;
        for (ResourceContainer container: resourceContainers){
           count += container.getAmount();
        }
        return count;
    }
    public int getTotalAmountByType(FuelGrade grade) {
        List<ResourceContainer> fuelContainers = getResourceByType(grade);
        int count = 0;
        for (ResourceContainer container: fuelContainers){
            count += container.getAmount();
        }
        return count;
    }
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
