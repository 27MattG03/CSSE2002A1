package srg.ship;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a generic room on the ship.
 */
public class Room extends Object implements Damageable {
    /**
     * The damage rate of the room
     */
    private int damageRate;
    /**
     * The current health of the room.
     */
    private int health;
    /**
     * The maximum possible health of the room.
     */
    private int maxHealth;
    /**
     * The current tier of the room
     */
    private RoomTier tier;
    /**
     * The name of the room.
     */
    private String name;

    /**
     * Constructs a room of Tier BASIC
     */
    public Room() {
        this(RoomTier.BASIC);
    }

    /**
     * Constructs a room of a certain tier.
     * @param tier the tier of the room
     */
    public Room(RoomTier tier) {
        this.name = "Room";
        this.tier = tier;
        this.damageRate = tier.damageMultiplier * DAMAGE_RATE;
        this.maxHealth = HEALTH_MULTIPLIER * tier.healthMultiplier;
        this.health = this.maxHealth;
    }

    /**
     * Damages a room.
     */
    @Override
    public void damage() {
        this.health = this.health - damageRate;
    }

    /**
     * Gets the health of a room.
     * @return The health of the room.
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Resets the Health to max.
     */
    @Override
    public void resetHealth() {
        this.health = this.maxHealth;

    }

    /**
     * Sets the damage rate of the room,
     * @param newDamageRate the desired damage rate.
     */
    @Override
    public void setDamageRate(int newDamageRate) {
        this.damageRate = newDamageRate;

    }

    /**
     * Gets the tier of the room.
     * @return The tier of the room.
     */
    public RoomTier getTier() {
        return this.tier;
    }

    /**
     * Creates a string representation of a ROOM object.
     * @return A string representation of the room.
     */
    @Override
    public String toString() {
        return String.format("ROOM: %s (%s) health: %i%, needs repair: %s",
                this.getClass().getName(), this.tier.name(),
                this.health, this.needsRepair());

    }

    /**
     * Gets a list of available actions for the room.
     * @return An empty list for generic room.
     */
    public List<String> getActions() {
        List<String> actions =  new ArrayList<>();
        return actions;
    }

    /**
     * Upgrades a room. If prime resets health.
     */
    public void upgrade() {
        switch (this.tier) {
            case BASIC :
                this.tier = RoomTier.AVERAGE;
                this.damageRate = tier.damageMultiplier * DAMAGE_RATE;
                this.maxHealth = tier.healthMultiplier * HEALTH_MULTIPLIER;
                break;
            case AVERAGE:
                this.tier = RoomTier.PRIME;
                this.damageRate = tier.damageMultiplier * DAMAGE_RATE;
                this.maxHealth = tier.healthMultiplier * HEALTH_MULTIPLIER;
                break;
            case PRIME:
                resetHealth();
                break;
        }
    }
}
