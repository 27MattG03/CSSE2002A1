package srg.ship;

public interface Damageable {
    public static final int DAMAGE_RATE = 5;
    public static final int REPAIR_THRESHOLD = 30;
    public static final int HEALTH_MULTIPLIER = 5;

    void damage();
    int getHealth();
    default boolean isBroken(){
        if (getHealth() <= REPAIR_THRESHOLD){
            return true;
        } else {
            return false;

        }
    }
    default boolean needsRepair(){
       if (getHealth() <= 0) {
           return true;
       } else {
           return false;
       }

    }
    void resetHealth();
    void setDamageRate(int newDamageRate);


}
