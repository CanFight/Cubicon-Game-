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

    //the following variables are huge part of what differs this projectile from others.
    
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
    public void onDeath() {//creates an explosion effect where this entity dies.
        super.getgameHandler().addEntity(new ExplosionEffect(super.getLocX(), super.getLocY(), 25));
    }

    @Override
    public void toCollider(Entity e) {//deals damage to the enity we collide with, also sets this enitites state to dead.
        if(!e.isImmortal()){
            super.setAlive(false);
        }
        e.damage(DAMAGE);
    }

    @Override
    public void update() {//when the update method is called we update this projectiles position with the move method.
        super.moveP();
    }
    
}
