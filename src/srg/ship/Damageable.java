package srg.ship;

public interface Damageable {
    public static final int DAMAGE_RATE = 5;
    public static final int REPAIR_THRESHOLD = 30;
    public static final int HEALTH_MULTIPLIER = 5;

    /**
     * Decrease the objects health.
     */
    void damage();
    /**
     *
     * @return The health of the object
     */
    int getHealth();

    /**
     * Check if the object is broken.
     * @return True if broken False otherwise.
     */
    default boolean isBroken(){
        if (getHealth() <= 0){
            return true;
        } else {
            return false;

        }
    }

    /**
     * Check if object needs Repair
     * @return True if health is less than repair threshold
     */
    default boolean needsRepair(){
       if (getHealth() <= REPAIR_THRESHOLD) {
           return true;
       } else {
           return false;
       }

    }

    /**
     * Reset the health of an object to full health.
     */
    void resetHealth();

    /**
     * Set the damage rate of an object.
     * @param newDamageRate the desired damage rate.
     */
    void setDamageRate(int newDamageRate);


}
