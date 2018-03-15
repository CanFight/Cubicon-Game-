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
public class Destroyer extends Enemy {

    private static final int WIDTH = 160;
    private static final int HEIGHT = 160;
    private static final int RADIUS = 75;
    private static final int SPEED = 7;
    private static final int CIRCLING_OFFSET = 200;
    private static final double ACCELERATION = 0.2;
    private static final double CIRCLING_ROT_SPEED = 0.03;
    private ArrayList<Weapon> weapons;

    private static final BufferedImage MODEL = MainLoop.loadImage("Destroyer.png");

    public Destroyer(double locX, double locY, GameHandler gameHandler) {
        super(locX, locY, WIDTH, HEIGHT, RADIUS, CIRCLING_OFFSET, SPEED, ACCELERATION, CIRCLING_ROT_SPEED, "Destroyer", gameHandler, MODEL);
        super.setHpM(300);
        super.setHp(300);
        super.setImmortal(false);
        super.setHpBar(true);
        super.setDrawName(true);
        weapons = new ArrayList();//Adds all the ships weapons to an array so we can loop through it easly when we use them.
        weapons.add(new PlasmaCannon(20,1.57,super.getgameHandler()));
        weapons.add(new PlasmaCannon(-20,1.57,super.getgameHandler()));
        weapons.add(new RocketLauncherX3(super.getgameHandler()));
        weapons.add(new Repair(super.getgameHandler()));
    }

    @Override
    public void onDeath() { //creates an explosion on death.
        super.getgameHandler().addEntity(new ExplosionEffect(super.getLocX(), super.getLocY(), 150));
    }

    @Override
    public void update() {//finds a target follows it, attacks it. Also sets the rotation of image to match the angle we are traveling.
        seekTarget();
        if (target != null) {
            angleToTarget = Math.atan2(target.getLocY() - super.getLocY(), target.getLocX() - super.getLocX());
            for (Weapon w : weapons) {//fires all weapons towards the target.
                w.fire(angleToTarget, this);
            }
            updateTargetOffsetCircle();
            moveTowardsTarget();
        }
        for (Weapon w : weapons) {//lowers the cooldown of all weapons.
            w.lowerCooldown();
        }
        super.setFacingImage(Math.atan2(super.getSpeedY(), super.getSpeedX()));
    }
}
