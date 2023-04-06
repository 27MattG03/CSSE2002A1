package srg.ports;


import java.util.ArrayList;
import java.util.List;

public class SpacePort extends Object {
    protected String name;
    protected Position position;




    public SpacePort(String name, Position position){
        this.name = name;
        this.position = position;
    }
    public String getName() {
        return this.name;
    }
    @Override
    public String toString() {

        return String.format("PORT: \"%s\" %s at %s", name,this.getClass().getSimpleName(), position.toString());
    }
    public List<String> getActions() {
        List<String> actions = new ArrayList<String>();
        return actions;
    }
    public Position getPosition(){
        return this.position;
    }
}
