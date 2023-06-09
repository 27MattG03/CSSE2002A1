package srg.ship;

import srg.cli.given.IO;
import srg.cli.given.PurchaseCommand;
import srg.cli.given.ShipCommand;
import srg.exceptions.InsufficientCapcaityException;
import srg.exceptions.InsufficientResourcesException;
import srg.exceptions.NoPathException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;
import srg.ports.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a spaceship, which has a unique name,
 * a unique ID, a registered owner, a CargoHold and a NavigationRoom.
 * @version 1.0
 * @ass1
 */
public class Ship {
    /**
     * The name of the ship
     */
    private String name;
    /**
     * The owner of the ship
     */
    private String owner;
    /**
     * The ships unique id.
     */
    private String id;
    /**
     * The ships cargoHold
     */
    private CargoHold cargoHold;
    /**
     * The ships NavigationRoom.
     */
    private NavigationRoom navigationRoom;

    /**
     * Constructs a ship with resource containers of each resource type
     * @param name The name of the ship.
     * @param owner The owner of the ship
     * @param id The unique id of the ship.
     * @param cargoHoldTier The room tier of the cargo hold.
     * @param navigationRoomTier The room tier of the navigation room.
     * @param galaxyMap The galaxy map for the navigation room.
     */
    public Ship(String name,
                String owner,
                String id,
                RoomTier cargoHoldTier,
                RoomTier navigationRoomTier,
                List<SpacePort> galaxyMap) {
        this.name = name;
        this.owner = owner;
        this.id = id;
        this.cargoHold = new CargoHold(cargoHoldTier);
        this.navigationRoom = new NavigationRoom(navigationRoomTier, galaxyMap);
        try {
            cargoHold.storeResource(new ResourceContainer(ResourceType.REPAIR_KIT, 5));
            cargoHold.storeResource(new FuelContainer(FuelGrade.TRITIUM, 100));
            cargoHold.storeResource(new FuelContainer(FuelGrade.HYPERDRIVE_CORE, 5));
        } catch (InsufficientCapcaityException | IllegalArgumentException error) {
            error.getMessage();
        }
    }

    /**
     * This method is provided as it interfaces with the command line interface.
     *
     * @param ioHandler Handles IO
     * @param command   A command to the ship
     */
    public void performCommand(IO ioHandler, ShipCommand command) {
        try {
            processCommand(ioHandler, command);
        } catch (InsufficientResourcesException error) {
            ioHandler.writeLn("Unable to perform action due to broken component or "
                    + "insufficient resources."
                    + System.lineSeparator() + error.getMessage());
        } catch (IllegalArgumentException | NoPathException | InsufficientCapcaityException error) {
            ioHandler.writeLn(error.getMessage());
        }
    }

    /**
     * This method is provided as it interfaces with the command line interface.
     * @param ioHandler Handles IO
     * @param command   A command to the ship
     * @throws InsufficientResourcesException
     *      If an action cannot be performed due to a lack or resources or a broken Room.
     * @throws NoPathException
     *      If a specified SpacePort cannot be found, or cannot be reached.
     * @throws InsufficientCapcaityException
     *      If resources cannot be added because there is not enough capacity in the CargoHold.
     */
    public void processCommand(IO ioHandler, ShipCommand command)
            throws InsufficientResourcesException, NoPathException, InsufficientCapcaityException {
        switch (command.type) {
            case SHOW_ROOM -> {
                ioHandler.writeLn(getRoomByName(command.value).toString());
            }
            case FLY_TO -> {
                navigationRoom.flyTo(command.value, cargoHold);
            }
            case JUMP_TO -> {
                navigationRoom.jumpTo(command.value, cargoHold);
            }
            case REPAIR_ROOM -> {
                // Ignore whether CargoHold may be broken
                cargoHold.consumeResource(ResourceType.REPAIR_KIT, 1);
                getRoomByName(command.value).resetHealth();

            }
            case UPGRADE_ROOM -> {
                ShipYard shipYard = navigationRoom.getShipYard();
                if (shipYard == null) {
                    ioHandler.writeLn("Can only upgrade when docked at a ShipYard.");
                    return;
                }

                shipYard.upgrade(getRoomByName(command.value));
            }
            case PURCHASE_ITEM -> {
                PurchaseCommand purchaseCommand = (PurchaseCommand) command;
                Store store = navigationRoom.getStore();
                if (store == null) {
                    ioHandler.writeLn("Can only purchase items at a Store.");
                    return;
                }
                ResourceContainer resource = store.purchase(purchaseCommand.item,
                        purchaseCommand.amount);
                cargoHold.storeResource(resource);
            }
            case SHOW_PORT -> {
                ioHandler.writeLn(navigationRoom.getCurrentPort().toString());
                ioHandler.writeLn(String.join(System.lineSeparator(),
                        navigationRoom.getCurrentPort().getActions()));
            }
            case SHOW_ACTIONS -> {
                ioHandler.writeLn(String.join(System.lineSeparator(), getActions()));
            }

        }

    }

    /**
     * Returns the Room object from its name.
     * @param name the name to be found.
     * @return The Room object associated with this name.
     * @throws IllegalArgumentException If room cannot be found.
     */
    public Room getRoomByName(String name)
            throws IllegalArgumentException {
        switch (name) {
            case "NavigationRoom":
                return this.navigationRoom;

            case "CargoHold":
                return this.cargoHold;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Gets a list of actions that can be completed by the navigation room and cargo hold.
     * @return A list of actions.
     */
    public List<String> getActions() {
        List<String> actions = new ArrayList<String>();
        return actions;
    }

    /**
     * Gets a string representation of the ship
     * @return A string representation of the ship.
     */
    public String toString() {
        return "Hello World!";
    }
}
