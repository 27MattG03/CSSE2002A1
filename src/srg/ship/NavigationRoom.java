package srg.ship;

import srg.exceptions.InsufficientResourcesException;
import srg.exceptions.NoPathException;
import srg.ports.ShipYard;
import srg.ports.SpacePort;
import srg.ports.Store;
import srg.resources.FuelGrade;

import java.util.ArrayList;
import java.util.List;

public class NavigationRoom extends Room{
    private List<SpacePort> galaxyMap;
    public SpacePort currentPort;
    public NavigationRoom(RoomTier roomTier, List<SpacePort> galaxyMap) {
        this.tier = roomTier;
        this.galaxyMap = galaxyMap;
        this.currentPort = galaxyMap.get(0);
    }
    public SpacePort getCurrentPort(){
        return this.currentPort;
    }
    public List<SpacePort> getPortsInFlyRange(){
        List<SpacePort> inFly = new ArrayList<>();
        for (SpacePort port : this.galaxyMap) {
            int dist = port.getPosition().distanceTo(currentPort.getPosition());
            if ( dist <= getMaximumFlyDistance() && dist > 0){
                inFly.add(port);
            }
        }
        return inFly;
    }
    public int getMaximumFlyDistance(){
        switch(this.tier) {
            case BASIC:
                return 200;
            case AVERAGE:
                return 400;
            case PRIME:
                return 600;
            default:
                return -1;
        }
    }
    public int getMaximumJumpDistance(){
        switch(this.tier) {
            case BASIC:
                return 500;
            case AVERAGE:
                return 750;
            case PRIME:
                return 1000;
            default:
                return -1;
        }
    }
    public List<SpacePort> getPortsInJumpRange(){
        List<SpacePort> inJump = new ArrayList<>();
        for (SpacePort port : this.galaxyMap) {
            int dist = port.getPosition().distanceTo(currentPort.getPosition());
            if ( dist <= getMaximumJumpDistance() && dist > 0){
                inJump.add(port);
            }
        }
        return inJump;
    }
    public List<String> getActions(){
        List<String> actions = new ArrayList<String>();
        List<SpacePort> inFly = getPortsInJumpRange();
        List<SpacePort> inJump = getPortsInJumpRange();
        for (SpacePort port: inFly){
            actions.add(String.format("fly to \"%s\": %s at %s [COST: %i TRITIUM FUEL",port.getName(),
                    port.getClass().getName(), port.getPosition().toString(), getFuelNeeded(port)));
        }
        for (SpacePort port: inJump){
            actions.add(String.format("jump to \"%s\" [COST: 1 HYPERDRIVE CORE]", port.getName()));
        }
        return actions;
    }
    public int getFuelNeeded(SpacePort port) {
        return port.getPosition().distanceTo(currentPort.getPosition());
    }
    public ShipYard getShipYard(){
        ShipYard shipYard;
        if (this.currentPort instanceof ShipYard){
            return (ShipYard) this.currentPort;
        }
        else {
            return null;
        }
    }
    public Store getStore() {
        Store shipYard;
        if (this.currentPort instanceof Store) {
            return (Store) this.currentPort;
        }
        else {
            return null;
        }
    }
    public void flyTo(String portName,
                      CargoHold cargoHold)
            throws InsufficientResourcesException,
            NoPathException {
        SpacePort desiredPort = getSpacePortFromName(portName);
        SpacePort inRangePort = null;
        for (SpacePort port : getPortsInFlyRange()){
            if (port == desiredPort){
                inRangePort = desiredPort;
            }
        }
        if (inRangePort == null){
            throw new NoPathException();
        }
        int required  = getFuelNeeded(desiredPort);
        cargoHold.consumeResource(FuelGrade.TRITIUM,required);
        this.currentPort = inRangePort;
    }
    public void jumpTo(String portName,
                      CargoHold cargoHold)
            throws InsufficientResourcesException,
            NoPathException {
        SpacePort desiredPort = getSpacePortFromName(portName);
        SpacePort inRangePort = null;
        for (SpacePort port : getPortsInJumpRange()){
            if (port == desiredPort){
                inRangePort = desiredPort;
            }
        }
        if (inRangePort == null){
            throw new NoPathException();
        }
        cargoHold.consumeResource(FuelGrade.HYPERDRIVE_CORE,1);
        this.currentPort = inRangePort;
    }
    public SpacePort getSpacePortFromName(String name)
            throws NoPathException {
        SpacePort portFromName = null;
        for (SpacePort port : this.galaxyMap) {
            if (port.getName() == name){
                portFromName = port;
            }
        }
        if (portFromName != null){
            return portFromName;
        }
        else {
            throw new NoPathException();
        }
    }
}
