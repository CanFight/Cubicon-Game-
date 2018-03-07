package cubicon.weapons.projectiles;

import java.awt.image.BufferedImage;
import cubicon.Entity;
import cubicon.MainLoop;
import cubicon.GameHandler;
import cubicon.effects.ExplosionEffect;

/*
 * @author Niklas
 */
public class DarkMatter extends Projectile{

    private static final int WIDTH = 25;
    private static final int HEIGHT = 25;
    private static final int RADIUS = 15;
    private static final int SPEED = 25;
    private static final int DAMAGE = 10;
    private static final int RANGE = 1000;
    private static final BufferedImage model = MainLoop.loadImage("DarkMatter.png");
    
    public DarkMatter(double locX, double locY, double angle, int team, GameHandler gameHandler) {
        super(locX, locY, WIDTH, HEIGHT, RADIUS, team, RANGE, gameHandler, model);
        super.setSpeedX(SPEED * Math.cos(angle));
        super.setSpeedY(SPEED * Math.sin(angle));
        super.setImmortal(true);
        super.setHpM(1);
        super.setHp(1);
    }

    @Override
    public void onDeath() {
        super.getgameHandler().addEntity(new ExplosionEffect(super.getLocX(), super.getLocY(), 25));
    }

    @Override
    public void toCollider(Entity e) {
        if(!e.isImmortal()){
            super.setAlive(false);
        }
        e.damage(DAMAGE);
    }

    @Override
    public void update() {
        super.moveP();
    }
    
}
