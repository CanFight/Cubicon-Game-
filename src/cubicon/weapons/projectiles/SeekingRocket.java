package cubicon.weapons.projectiles;

import java.awt.image.BufferedImage;
import cubicon.Entity;
import cubicon.MainLoop;
import cubicon.GameHandler;
import cubicon.effects.ExplosionEffect;

/*
 * @author Niklas
 */
public class SeekingRocket extends Projectile {

    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private static final int RADIUS = 10;
    private static final int SPEED = 10;
    private static final int DAMAGE = 15;
    private static final int RANGE = 2000;
    private static final double ACCEL = 0.4;
    private static final BufferedImage MODEL = MainLoop.loadImage("Rocket.png");
    private Entity target;

    public SeekingRocket(double locX, double locY, double angle, int team, GameHandler gameHandler) {
        super(locX, locY, WIDTH, HEIGHT, RADIUS, team, RANGE, gameHandler, MODEL);
        super.setSpeedX(SPEED / 1.5 * Math.cos(angle));
        super.setSpeedY(SPEED / 1.5 * Math.sin(angle));
        super.setFacingImage(angle);
        super.setImmortal(false);
        super.setHpM(1);
        super.setHp(1);
    }

    @Override
    public void onDeath() {//creates a nice explosion effect on death.
        super.getgameHandler().addEntity(new ExplosionEffect(super.getLocX(), super.getLocY(), 75));
    }

    @Override
    public void toCollider(Entity e) {//damage the target we collideed with and set our state to dead.
        super.setAlive(false);
        e.damage(DAMAGE);
    }

    @Override
    public void update() {
        seekTarget();
        setSpeedTowardsTarget(target);
        super.moveP();
    }

    private void seekTarget() {//finds the closest ENEMY target.
        double closestTarget = Double.MAX_VALUE;
        for (Entity e : super.getgameHandler().getEntities()) {
            if (e.getTeam() != super.getTeam() && !e.isImmortal()) {
                double hypot = Math.hypot(super.getLocX() - e.getLocX(), super.getLocY() - e.getLocY());
                if (hypot <= closestTarget) {
                    closestTarget = hypot;
                    target = e;
                }
            }
        }
    }

    private void setSpeedTowardsTarget(Entity target) {//sets this projectiles XY speed based on its location towards the "targets" location.
        if (target != null) {
            double angle = Math.atan2(target.getLocY() - super.getLocY(), target.getLocX() - super.getLocX());
            super.setSpeedX(super.getSpeedX() + (ACCEL * Math.cos(angle)));
            super.setSpeedY(super.getSpeedY() + (ACCEL * Math.sin(angle)));
            angle = Math.atan2(super.getSpeedY(), super.getSpeedX());
            if (Math.hypot(super.getSpeedX(), super.getSpeedY()) > SPEED) {
                super.setSpeedX(SPEED * Math.cos(angle));
                super.setSpeedY(SPEED * Math.sin(angle));
            }
            super.setFacingImage(angle);//updates the image of this projectile to match its new vector.
        }
    }

}
