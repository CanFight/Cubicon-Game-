package cubicon.weapons;

import cubicon.MainLoop;
import cubicon.Entity;
import cubicon.GameHandler;

/*
 * @author Niklas
 */
public abstract class Weapon {
    
    //weapons can use offsets where the are attached on a ship. Basicly means the projectile will not be fired from the center of the enitity its attached to.
    
    private int cooldown,cooldownM;
    private double offsetAngle, offsetDist;
    private GameHandler gameHandler;//so we know where to add projectiles we fire.
    
    public Weapon(int cooldownM, GameHandler gameHandler){ //used if we dont use an offset
        this.cooldownM = cooldownM;
        this.gameHandler = gameHandler;
        offsetAngle = 0;
        offsetDist = 0;
        cooldown = this.cooldownM;
    }
    
    public Weapon(double oD, double oA, int cooldownM, GameHandler gameHandler){//used if we do use an offset
        this.cooldownM = cooldownM;
        this.gameHandler = gameHandler;
        offsetAngle = oA;
        offsetDist = oD;
        cooldown = this.cooldownM;
    }
    
    public abstract void fire(double angle, Entity e);//allows us to treat all weapons as if they where the same. Even though there is a rather big difference between them.
    
    public void lowerCooldown(){//lowers the cooldown towards 0.
        if(cooldown > 0){
            cooldown -= 1000 / MainLoop.UPDATES_PER_SEC;
        }
    }

    public GameHandler getgameHandler() {
        return gameHandler;
    }

    public void setCdToMax(){
        cooldown = cooldownM;
    }
    
    public boolean noCooldown(){
        return cooldown <= 0;
    }

    public double getOffsetAngle() {
        return offsetAngle;
    }

    public double getOffsetDist() {
        return offsetDist;
    }
    
}
