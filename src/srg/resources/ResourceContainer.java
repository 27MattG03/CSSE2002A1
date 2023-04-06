package srg.resources;

public class ResourceContainer extends Object{
    static final int MAXIMUM_CAPACITY = 10;
    private int amount;
    private ResourceType type;
    public ResourceContainer(ResourceType type, int amount) {
        this.amount = amount;
        if (!canStore(type)) {
            throw new IllegalArgumentException();
        } else {
            this.type = type;
        }


    }



    public boolean canStore(ResourceType type) {
        if (type == ResourceType.FUEL) {
            return false;
        } else {
            return true;
        }

    }
    public int getAmount() {
        return this.amount;
    }
    public void setAmount(int amount){
        this.amount = amount;
    }
    public ResourceType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return String.format("%s: %i",this.type.toString(), this.amount);
    }
    public String getShortName(){
        return this.type.toString();
    }
}
