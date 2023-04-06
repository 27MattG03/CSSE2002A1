package srg.resources;

public class FuelContainer extends ResourceContainer {
    public static final int MAXIMUM_CAPACITY = 1000;
    private FuelGrade grade;

    public FuelContainer(FuelGrade grade, int amount) {
        super(ResourceType.FUEL, amount);
        this.grade = grade;
    }
    public FuelGrade getFuelGrade(){
        return this.grade;
    }
    @Override
    public boolean canStore(ResourceType type) {
        if (type == ResourceType.FUEL){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %s",super.toString(),this.grade.toString());
    }

    @Override
    public String getShortName() {
        return this.grade.toString();
    }
}
