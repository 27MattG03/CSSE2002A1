package srg.ports;


import java.util.ArrayList;
import java.util.List;

public class SpacePort extends Object {
    protected String name;
    protected Position position;

    /**
     * Constructs a SpacePort with a unique name at a specific position
     * @param name
     * @param position
     */
    public SpacePort(String name, Position position){
        this.name = name;
        this.position = position;
    }

    /**
     *
     * @return The name of the SpacePort
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return A string representation of the SpacePort object of the form
     * "PORT: \"SpacePort name\" SpacePort class name at position"
     */
    @Override
    public String toString() {

        return String.format("PORT: \"%s\" %s at %s", name,this.getClass().getSimpleName(), position.toString());
    }

    /**
     *
     * @return A list of the actions that can be performed by this SpacePort
     */
    public List<String> getActions() {
        List<String> actions = new ArrayList<String>();
        return actions;
    }

    /**
     *
     * @return The position of this SpacePort represented by a Position object.
     */
    public Position getPosition(){
        return this.position;
    }
}
