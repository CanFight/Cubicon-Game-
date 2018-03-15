package cubicon.weapons;

import cubicon.Entity;
import cubicon.GameHandler;
import cubicon.effects.RepairEffect;

/*
 * @author Niklas
 */

public class Repair extends Weapon {

    //the only difference between weapons are the fire method, and their cooldown.
    
    private static final int CDM = 5000;
    
    public Repair(GameHandler gameHandler) {
        super(CDM, gameHandler);
    }

    public Repair(int oX, int oY, GameHandler gameHandler) {
        super(oX, oY, CDM, gameHandler);
    }

    @Override
    public void fire(double angle, Entity e) {//simply repairs the enitity that used this weapon, quite different from other weapons.
        if (super.noCooldown()) {
            e.setHp(e.getHp() + 50);
            if (e.getHp() > e.getHpM()) {
                e.setHp(e.getHpM());
            }
            e.getgameHandler().addEntity(new RepairEffect(e.getRadius() * 3, e));//creates an effect that gets attached to the ship that used this weapon.
            super.setCdToMax();
        }
    }
    
}
