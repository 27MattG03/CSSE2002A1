package srg.ship;

import srg.exceptions.InsufficientResourcesException;
import srg.exceptions.NoPathException;
import srg.ports.ShipYard;
import srg.ports.SpacePort;
import srg.ports.Store;
import srg.resources.FuelGrade;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the navigation room on a ship.
 */
public class NavigationRoom extends Room {
    /**
     * This NavigationRoom's galaxy map
     */
    public List<SpacePort> galaxyMap;
    /**
     * The current port of the ship.
     */
    private SpacePort currentPort;

    /**
     * Constructs a NavigationRoom of a certain tier and a specific galaxy map.
     * @param roomTier The tier of the navigation room
     * @param galaxyMap The galaxy map of this navigation room.
     */
    public NavigationRoom(RoomTier roomTier, List<SpacePort> galaxyMap) {
        super(roomTier);
        this.galaxyMap = galaxyMap;
        this.currentPort = galaxyMap.get(0);
    }

    /**
     * Get the current port of the ship
     * @return The current port
     */
    public SpacePort getCurrentPort() {
        return this.currentPort;
    }

    /**
     * Finds the port in fly range.
     * @return A list of all the ports in fly range.
     */
    public List<SpacePort> getPortsInFlyRange() {
        List<SpacePort> inFly = new ArrayList<>();
        for (SpacePort port : this.galaxyMap) {
            int dist = port.getPosition().distanceTo(currentPort.getPosition());
            if (dist <= getMaximumFlyDistance() && dist > 0) {
                inFly.add(port);
            }
        }
        return inFly;
    }

    /**
     * Gets the maximum flight distance based on room tier
     * @return The maximum fly distance.
     */
    public int getMaximumFlyDistance() {
        return switch (getTier()) {
            case BASIC -> 200;
            case AVERAGE -> 400;
            case PRIME -> 600;
        };
    }

    /**
     * Gets the maximum jump distance of a ship based on it tier.
     * @return The maximum jump distance.
     */
    public int getMaximumJumpDistance() {
        return switch (getTier()) {
            case BASIC -> 500;
            case AVERAGE -> 750;
            case PRIME -> 1000;
        };
    }

    /**
     * Gets the ports in jump range of a ship.
     * @return A list of the ports in jump range.
     */
    public List<SpacePort> getPortsInJumpRange() {
        List<SpacePort> inJump = new ArrayList<>();
        for (SpacePort port : this.galaxyMap) {
            int dist = port.getPosition().distanceTo(currentPort.getPosition());
            if (dist <= getMaximumJumpDistance() && dist > 0) {
                inJump.add(port);
            }
        }
        return inJump;
    }

    /**
     * Returns a list of the possible actions for the ship i.e. what ports it can jump or fly to.
     * @return A list of a navigation rooms actions.
     */
    public List<String> getActions() {
        List<String> actions = new ArrayList<String>();
        List<SpacePort> inFly = getPortsInJumpRange();
        List<SpacePort> inJump = getPortsInJumpRange();
        for (SpacePort port : inFly) {
            actions.add(String.format("fly to \"%s\": %s at %s [COST: %s TRITIUM FUEL]",
                    port.getName(),
                    port.getClass().getName(), port.getPosition().toString(), getFuelNeeded(port)));
        }
        for (SpacePort port : inJump) {
            actions.add(String.format("jump to \"%s\" [COST: 1 HYPERDRIVE CORE]", port.getName()));
        }
        return actions;
    }

    /**
     * Get the amount of fuel need to fly to a specific port.
     * @param port The port to fly to.
     * @return The amount of fuel required to fly to this port.
     */
    public int getFuelNeeded(SpacePort port) {
        return port.getPosition().distanceTo(currentPort.getPosition());
    }

    /**
     * Gets whether the current port is a ShipYard
     * @return The current port object (cast to a ShipYard), null otherwise.
     */
    public ShipYard getShipYard() {
        if (this.currentPort instanceof ShipYard) {
            return (ShipYard) this.currentPort;
        } else {
            return null;
        }
    }

    /**
     * Gets whether the current port is a Store
     * @return The current port object (cast to a Store), null otherwise.
     */
    public Store getStore() {
        if (this.currentPort instanceof Store) {
            return (Store) this.currentPort;
        } else {
            return null;
        }
    }

    /**
     * Fly's the ship to a different port.
     * @param portName The port to fly to
     * @param cargoHold The CargoHold of this ship.
     * @throws InsufficientResourcesException If not enough fuel
     * @throws NoPathException If port not in range or non-existant.
     */
    public void flyTo(String portName,
                      CargoHold cargoHold)
            throws InsufficientResourcesException,
            NoPathException {
        SpacePort desiredPort = getSpacePortFromName(portName);
        SpacePort inRangePort = null;
        for (SpacePort port : getPortsInFlyRange()) {
            if (port == desiredPort) {
                inRangePort = desiredPort;
            }
        }
        if (inRangePort == null) {
            throw new NoPathException();
        }
        int required  = getFuelNeeded(desiredPort);
        cargoHold.consumeResource(FuelGrade.TRITIUM, required);
        this.currentPort = inRangePort;
    }

    /**
     * Jump to a specific port.
     * @param portName The name of the port to jump to.
     * @param cargoHold The CargoHold of the ship
     * @throws InsufficientResourcesException If insufficient fuel.
     * @throws NoPathException If portName cannot be found.
     */
    public void jumpTo(String portName,
                      CargoHold cargoHold)
            throws InsufficientResourcesException,
            NoPathException {
        SpacePort desiredPort = getSpacePortFromName(portName);
        SpacePort inRangePort = null;
        for (SpacePort port : getPortsInJumpRange()) {
            if (port == desiredPort) {
                inRangePort = desiredPort;
            }
        }
        if (inRangePort == null) {
            throw new NoPathException();
        }
        cargoHold.consumeResource(FuelGrade.HYPERDRIVE_CORE, 1);
        this.currentPort = inRangePort;
    }

    /**
     * Gets a SpacePort from its name
     * @param name The name of the desired SpacePort.
     * @return The desired SpacePort.
     * @throws NoPathException If SpacePort cannot be found.
     */
    public SpacePort getSpacePortFromName(String name)
            throws NoPathException {
        SpacePort portFromName = null;
        for (SpacePort port : this.galaxyMap) {
            if (port.getName().equals(name)) {
                portFromName = port;
            }
        }
        if (portFromName != null) {
            return portFromName;
        } else {
            throw new NoPathException();
        }
    }
}
