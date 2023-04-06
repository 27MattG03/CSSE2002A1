package srg.resources;

public class FuelContainer extends ResourceContainer {
    public static final int MAXIMUM_CAPACITY = 1000;
    private FuelGrade grade;

    /**
     * Constructs a fuel container that stores a certain grade of Fuel.
     * @param grade The grade of fuel to be Stored.
     * @param amount The amount of fuel to be Stored (Precondition: Less than MAXIMUM_CAPACITY)
     */
    public FuelContainer(FuelGrade grade, int amount) {
        super(ResourceType.FUEL, amount);
        this.grade = grade;
    }

    /**
     * Gets the fuel Grade.
     * @return The fuel grade of this container as a FuelGrade enum
     */
    public FuelGrade getFuelGrade() {
        return this.grade;
    }

    /**
     * Check whether the resource type can be stored.
     * @param type They type to be checked
     * @return True if resource type is FUEL otherwise return False
     */
    @Override
    public boolean canStore(ResourceType type) {
        if (type == ResourceType.FUEL) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets a string rep of a Fuel Container.
     * @return A string representation of the FuelContainer object
     */
    @Override
    public String toString() {
        return String.format("%s - %s", super.toString(), this.grade.toString());
    }
    /**
     * Gets a shorter representation of a fuel container.
     * @return A shorter representation of FuelContainer i.e. the Fuel Grade it stores
     */

    @Override
    public String getShortName() {
        return this.grade.toString();
    }
}
