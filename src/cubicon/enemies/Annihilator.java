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
public class Annihilator extends Enemy {

    private static final int WIDTH = 220;
    private static final int HEIGHT = 220;
    private static final int RADIUS = 100;
    private ArrayList<Weapon> weapons;
    private double speedX, speedY, accel, circlingAngle, circleRotation;

    private static final BufferedImage MODEL = MainLoop.loadImage("Annihilator.png");

    public Annihilator(double locX, double locY, GameHandler gameHandler) {
        super(locX, locY, WIDTH, HEIGHT, RADIUS, "Annihilator", gameHandler, MODEL);
        super.setHpM(500);
        super.setHp(500);
        super.setImmortal(false);
        super.setHpBar(true);
        super.setDrawName(true);
        weapons = new ArrayList();
        weapons.add(new DarkMatterCannon(20,1.57,super.getgameHandler()));
        weapons.add(new DarkMatterCannon(-20,1.57,super.getgameHandler()));
        weapons.add(new RocketLauncherX3(50,1.57,super.getgameHandler()));
        weapons.add(new RocketLauncherX3(-50,1.57,super.getgameHandler()));
        weapons.add(new Repair(super.getgameHandler()));
        speed = 6;
        speedX = 0;
        speedY = 0;
        accel = 0.2;
        if (MainLoop.rng.nextBoolean()) {
            circleRotation = 0.03;
        } else {
            circleRotation = -0.03;
        }
        circlingAngle = (double)MainLoop.rng.nextInt(63) / 10.0;
    }

    @Override
    public void onDeath() {
        super.getgameHandler().addEntity(new ExplosionEffect(super.getLocX(), super.getLocY(), 150));
    }

    @Override
    public void update() {
        seekTarget();
        if (target != null) {
            angleToTarget = Math.atan2(target.getLocY() - super.getLocY(), target.getLocX() - super.getLocX());
            for (Weapon w : weapons) {
                w.fire(angleToTarget, this);
            }
            moveTowardsTarget();
        }
        for (Weapon w : weapons) {
            w.lowerCooldown();
        }
        circlingAngle += circleRotation;
        if (circlingAngle > 6.3) {
            circlingAngle = 0;
        }
        if (circlingAngle < 0) {
            circlingAngle = 6.3;
        }

    }

    private void moveTowardsTarget() {
        double mLocX = target.getLocX() + 200 * Math.cos(circlingAngle);
        double mLocY = target.getLocY() + 200 * Math.sin(circlingAngle);
        double angle = Math.atan2(mLocY - super.getLocY(), mLocX - super.getLocX());
        speedX += accel * Math.cos(angle);
        speedY += accel * Math.sin(angle);
        if (Math.hypot(speedX, speedY) > speed) {
            double travelAngle = Math.atan2(speedY, speedX);
            speedX = speed * Math.cos(travelAngle);
            speedY = speed * Math.sin(travelAngle);
        }
        super.move(super.getLocX() + speedX, super.getLocY() + speedY);
        super.setFacingImage(Math.atan2(speedY, speedX));
    }
}
