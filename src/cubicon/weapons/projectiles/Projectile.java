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
    public boolean checkCollide(Entity e){
        if(super.checkCollide(e)){
            toCollider(e);
            return true;
        }
        return false;
    }
    
    public abstract void toCollider(Entity e);
    
    public void moveP(){
        if(!super.move(super.getLocX() + speedX, super.getLocY() + speedY)){
            super.setAlive(false);
        }
        range -= Math.hypot(speedX, speedY);
        if(range <= 0){
            super.setAlive(false);
        }
    }
    
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
