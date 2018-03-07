package cubicon.weapons;

import cubicon.Entity;
import cubicon.GameHandler;
import cubicon.effects.EmpEffect;

/*
 * @author Niklas
 */
public class EMP extends Weapon {

    private static final int CDM = 1000;

    public EMP(GameHandler gameHandler) {
        super(CDM, gameHandler);
    }

    public EMP(int oX, int oY, GameHandler gameHandler) {
        super(oX, oY, CDM, gameHandler);
    }

    @Override
    public void fire(double angle, Entity e) {
        if (super.noCooldown()) {
            for (Entity t : e.getgameHandler().getEntities()) {
                if (Math.hypot(e.getLocX() - t.getLocX(), e.getLocY() - t.getLocY()) <= 200) {
                        t.damage(1);
                }
            }
            e.getgameHandler().addEntity(new EmpEffect(e.getLocX(), e.getLocY()));
            super.setCdToMax();
        }
    }

}
