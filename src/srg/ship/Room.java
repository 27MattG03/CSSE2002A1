package srg.ship;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

public class Room extends Object implements Damageable {
    private int damageRate;
    private int health;
    private int maxHealth;
    protected RoomTier tier;
    private String name;
    /**
     * Constructs a room of Tier BASIC
     */
    public Room() {
        this(RoomTier.BASIC);

    }

    /**
     * Constructs a room of a certain tier.
     * @param tier
     */
    public Room(RoomTier tier) {
        this.name = "Room";
        this.tier = tier;
        this.damageRate = tier.damageMultiplier*DAMAGE_RATE;
        this.maxHealth = HEALTH_MULTIPLIER*tier.healthMultiplier;
        this.health = this.maxHealth;



    }
    @Override
    public void damage() {
        this.health = this.health - damageRate;
    }

    @Override
    public int getHealth() {

       return this.health;
    }

    @Override
    public void resetHealth() {
        this.health = this.maxHealth;

    }

    @Override
    public void setDamageRate(int newDamageRate) {
        this.damageRate = newDamageRate;

    }
    public RoomTier getTier(){
        return this.tier;
    }
    @Override
    public String toString() {
        return String.format("ROOM: %s (%s) health: %i%, needs repair: %s", this.getClass().getName(), this.tier.name(),
                this.health, this.needsRepair());

    }
    public List<String> getActions() {
        List<String> actions =  new ArrayList<>();
        return actions;
    }
    public void upgrade(){
        switch(this.tier){
            case BASIC :
                this.tier = RoomTier.AVERAGE;
                break;
            case AVERAGE:
                this.tier = RoomTier.PRIME;
                break;
            case PRIME:
                resetHealth();
                break;
        }
    }
}
