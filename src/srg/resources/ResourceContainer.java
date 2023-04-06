package srg.resources;

public class ResourceContainer extends Object{
    public static final int MAXIMUM_CAPACITY = 10;
    private int amount;
    private ResourceType type;

    /**
     * Constructs a ResourceContainer object throws an IllegalArgumentException if the Resource cannot be stored
     * @param type The type of resource to be stored
     * @param amount The amount of this Resource to be stored (Precondition: The amount ot be Stored is less than
     *               the Maximum Capacity).
     */
    public ResourceContainer(ResourceType type, int amount) throws IllegalArgumentException {
        this.amount = amount;
        if (!canStore(type)) {
            throw new IllegalArgumentException();
        } else {
            this.type = type;
        }


    }

    /**
     * Check if a Resource can be stored in this resource container.
     * @param type The type to be checked
     * @return False if the type is FUEL otherwise return True
     */
    public boolean canStore(ResourceType type) {
        if (type == ResourceType.FUEL) {
            return false;
        } else {
            return true;
        }

    }

    /**
     *
     * @return The amount stored in this resource container.
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Set the amount of material in a container.
     * @param amount the amount of material to be stored (Precondition: must be less than the Maximum Capacity)
     */
    public void setAmount(int amount){
        this.amount = amount;
    }

    /**
     *
     * @return The type of material in this container.
     */
    public ResourceType getType() {
        return this.type;
    }

    /**
     *
     * @return A string representation of the ResourceContainer object
     */
    @Override
    public String toString() {
        return String.format("%s: %s",this.type.toString(), this.amount);
    }

    /**
     *
     * @return A shorter string representation of the ResourceContainer object i.e. the ResourceType being stored.
     */
    public String getShortName(){
        return this.type.toString();
    }
}
