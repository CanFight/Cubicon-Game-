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

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int RADIUS = 30;
    private Weapon w1;
    private double speedX, speedY, accel, circlingAngle, circleRotation;

    private static final BufferedImage MODEL = MainLoop.loadImage("Interceptor.png");

    public Interceptor(double locX, double locY, GameHandler gameHandler) {
        super(locX, locY, WIDTH, HEIGHT, RADIUS, "Interceptor", gameHandler, MODEL);
        super.setHpM(30);
        super.setHp(30);
        super.setImmortal(false);
        super.setHpBar(true);
        speed = 12;
        speedX = 0;
        speedY = 0;
        accel = 0.5;
        if (MainLoop.rng.nextBoolean()) {
            circleRotation = 0.06;
        } else {
            circleRotation = -0.06;
        }
        circlingAngle = (double)MainLoop.rng.nextInt(63) / 10.0;
        w1 = new PlasmaCannon(super.getgameHandler());
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
            w1.fire(angleToTarget, this);
            moveTowardsTarget();
        }
        w1.lowerCooldown();
        circlingAngle += circleRotation;
        if(circlingAngle > 6.3){
            circlingAngle = 0;
        }
        if(circlingAngle < 0){
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
