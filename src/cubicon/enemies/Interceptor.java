package cubicon.enemies;

import java.awt.image.BufferedImage;
import cubicon.MainLoop;
import cubicon.GameHandler;
import cubicon.effects.ExplosionEffect;
import cubicon.weapons.*;

/*
 * @author Niklas
 */
public class Interceptor extends Enemy {

    //
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int RADIUS = 30;
    private static final int SPEED = 12;
    private static final int CIRCLING_OFFSET = 200;
    private static final double ACCELERATION = 0.5;
    private static final double CIRCLING_ROT_SPEED = 0.06;
    private Weapon w1;

    private static final BufferedImage MODEL = MainLoop.loadImage("Interceptor.png");

    public Interceptor(double locX, double locY, GameHandler gameHandler) {
        super(locX, locY, WIDTH, HEIGHT, RADIUS, CIRCLING_OFFSET, SPEED, ACCELERATION, CIRCLING_ROT_SPEED, "Interceptor", gameHandler, MODEL);
        super.setHpM(30);
        super.setHp(30);
        super.setImmortal(false);
        super.setHpBar(true);
        w1 = new PlasmaCannon(super.getgameHandler());//this ship only has one weapon therefor we dont use an array.
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
            w1.fire(angleToTarget, this);//fires all weapons towards the target.
            updateTargetOffsetCircle();
            moveTowardsTarget();
        }
        w1.lowerCooldown();
        super.setFacingImage(Math.atan2(super.getSpeedY(), super.getSpeedX()));
    }
}
