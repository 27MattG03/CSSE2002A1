package srg.ports;


import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a generic spaceport.
 */
public class SpacePort extends Object {
    /**
     * The name of the spaceport.
     */
    private String name;
    /**
     * The position of the spaceport.
     */
    private Position position;

    /**
     * Constructs a SpacePort with a unique name at a specific position
     * @param name The name of the spaceport.
     * @param position The position of the space port.
     */
    public SpacePort(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    /**
     * Gets the name of the spaceport.
     * @return The name of the SpacePort
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets a string representation of the SpacePort
     * @return A string representation of the SpacePort object of the form
     * "PORT: \"SpacePort name\" SpacePort class name at position"
     */
    @Override
    public String toString() {
        return String.format("PORT: \"%s\" %s at %s", getName(),
                this.getClass().getSimpleName(), position.toString());
    }

    /**
     * Get the actions that this space port can complete
     * @return A list of the actions that can be performed by this SpacePort.
     */
    public List<String> getActions() {
        List<String> actions = new ArrayList<String>();
        return actions;
    }

    /**
     * Get the position of the space port.
     * @return The position of this SpacePort represented by a Position object.
     */
    public Position getPosition() {
        return this.position;
    }
}
