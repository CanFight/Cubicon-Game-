package cubicon.weapons.projectiles;

import java.awt.image.BufferedImage;
import cubicon.Entity;
import cubicon.MainLoop;
import cubicon.GameHandler;

/*
 * @author Niklas
 */
public class PlasmaBall extends Projectile{

    //the following variables are huge part of what differs this projectile from others.
    
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final int RADIUS = 5;
    private static final int SPEED = 15;
    private static final int DAMAGE = 5;
    private static final int RANGE = 500;
    private static final BufferedImage MODEL = MainLoop.loadImage("PlasmaBall.png");
    
    public PlasmaBall(double locX, double locY, double angle, int team, GameHandler gameHandler) {
        super(locX, locY, WIDTH, HEIGHT, RADIUS, team, RANGE, gameHandler, MODEL);
        super.setSpeedX(SPEED * Math.cos(angle));
        super.setSpeedY(SPEED * Math.sin(angle));
        super.setImmortal(true);
        super.setHpM(1);
        super.setHp(1);
    }

    @Override
    public void onDeath() { //does nothing on dead.
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
