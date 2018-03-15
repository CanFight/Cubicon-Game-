package cubicon.enemies;

import java.awt.image.BufferedImage;
import cubicon.MainLoop;
import cubicon.GameHandler;
import cubicon.effects.ExplosionEffect;
import cubicon.weapons.*;
import java.util.ArrayList;

/*
 * @author Niklas
 */
public class Cubicon extends Enemy {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final int RADIUS = 100;
    private static final int SPEED = 2;
    private static final int CIRCLING_OFFSET = 400;
    private static final double ACCELERATION = 0.3;
    private static final double CIRCLING_ROT_SPEED = 0.01;
    private ArrayList<Weapon> weapons;

    private static final BufferedImage MODEL = MainLoop.loadImage("Cubicon.png");

    public Cubicon(double locX, double locY, GameHandler gameHandler) {
        super(locX, locY, WIDTH, HEIGHT, RADIUS, CIRCLING_OFFSET, SPEED, ACCELERATION, CIRCLING_ROT_SPEED, "Cubicon", gameHandler, MODEL);
        super.setHpM(1000);
        super.setHp(1000);
        super.setImmortal(false);
        super.setHpBar(true);
        super.setDrawName(true);
        weapons = new ArrayList();//Adds all the ships weapons to an array so we can loop through it easly when we use them.
        weapons.add(new RocketLauncherX5(super.getgameHandler()));
        weapons.add(new PlasmaCannon(-50,-50,super.getgameHandler()));
        weapons.add(new InterceptorDock(+50,+50,super.getgameHandler()));
    }

    @Override
    public void onDeath() { //creates an explosion on death.
        super.getgameHandler().addEntity(new ExplosionEffect(super.getLocX(), super.getLocY(), 300));
    }
    
    @Override
    public void update() {//finds a target follows it, attacks it.
        seekTarget();
        if (target != null) {
            angleToTarget = Math.atan2(target.getLocY() - super.getLocY(), target.getLocX() - super.getLocX());
            updateTargetOffsetCircle();
            moveTowardsTarget();
            for (Weapon w : weapons) {//fires all weapons towards the target.
                w.fire(angleToTarget, this);
            }
        }
        for (Weapon w : weapons) {
            w.lowerCooldown();
        }
    }

}
