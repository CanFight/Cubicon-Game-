package cubicon.weapons;

import cubicon.MainLoop;
import cubicon.Entity;
import cubicon.GameHandler;

/*
 * @author Niklas
 */
public abstract class Weapon {
    
    private int cooldown,cooldownM;
    private double offsetAngle, offsetDist;
    private GameHandler gameHandler;
    
    public Weapon(int cooldownM, GameHandler gameHandler){
        this.cooldownM = cooldownM;
        this.gameHandler = gameHandler;
        offsetAngle = 0;
        offsetDist = 0;
        cooldown = this.cooldownM;
    }
    
    public Weapon(double oD, double oA, int cooldownM, GameHandler gameHandler){
        this.cooldownM = cooldownM;
        this.gameHandler = gameHandler;
        offsetAngle = oA;
        offsetDist = oD;
        cooldown = this.cooldownM;
    }
    
    public abstract void fire(double angle, Entity e);
    
    public void lowerCooldown(){
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
