package cubicon.effects;

import cubicon.Entity;
import cubicon.MainLoop;
import java.awt.image.BufferedImage;

/*
 * @author Niklas
 */
public abstract class Effect extends Entity{

    private int lifeTime;
    
    public Effect(double locX, double locY, int width, int height, int lifeTime, BufferedImage model) {
        super(locX, locY, width, height, 0, 0, null, model);
        this.lifeTime = lifeTime;
        
    }
    protected void updateLifeTime(){//updates the remaining time of the effect
        lifeTime -= 1000 / MainLoop.UPDATES_PER_SEC;
        if(lifeTime <= 0){
            super.setAlive(false);
        }
    }

    public int getLifeTime() {
        return lifeTime;
    }
    
}
