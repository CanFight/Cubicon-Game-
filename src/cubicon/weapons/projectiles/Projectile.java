package cubicon.weapons.projectiles;

import java.awt.image.BufferedImage;
import cubicon.Entity;
import cubicon.GameHandler;

/*
 * @author Niklas
 */
public abstract class Projectile extends Entity{
    
    private double speedX, speedY, range;
    
    public Projectile(double locX, double locY, int width, int height, int radius, int team, double range, GameHandler gameHandler, BufferedImage model) {
        super(locX, locY, width, height, radius, team, gameHandler, model);
        this.range = range;
    }
    
    @Override
    public boolean checkCollide(Entity e){//did we colide with someone?
        if(super.checkCollide(e)){
            toCollider(e);
            return true;
        }
        return false;
    }
    
    public abstract void toCollider(Entity e);//what do we do to the entity we collided with?
    
    public void moveP(){//moves the projectile based on its speed
        super.move(super.getLocX() + speedX, super.getLocY() + speedY);
        range -= Math.hypot(speedX, speedY);
        if(range <= 0){
            super.setAlive(false);
        }
    }
    
    //getters and setters
    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }
    
}
