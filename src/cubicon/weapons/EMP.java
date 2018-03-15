package cubicon.weapons;

import cubicon.Entity;
import cubicon.GameHandler;
import cubicon.effects.EmpEffect;

/*
 * @author Niklas
 */
public class EMP extends Weapon {

    //the only difference between weapons are the fire method, and their cooldown.
    
    private static final int CDM = 1000;

    public EMP(GameHandler gameHandler) {
        super(CDM, gameHandler);
    }

    public EMP(int oX, int oY, GameHandler gameHandler) {
        super(oX, oY, CDM, gameHandler);
    }

    @Override
    public void fire(double angle, Entity e) {//Deals 1 damage to all nearby enities. Which basicly destroys all nearby rockets.
        if (super.noCooldown()) {
            for (Entity t : e.getgameHandler().getEntities()) {
                if (Math.hypot(e.getLocX() - t.getLocX(), e.getLocY() - t.getLocY()) <= 200) {
                        t.damage(1);
                }
            }
            e.getgameHandler().addEntity(new EmpEffect(e.getLocX(), e.getLocY()));//creates an effect at the position of the enitity that used this weapon.
            super.setCdToMax();
        }
    }

}
