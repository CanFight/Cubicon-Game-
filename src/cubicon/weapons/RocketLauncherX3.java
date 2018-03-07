package cubicon.weapons;

import cubicon.Entity;
import cubicon.GameHandler;
import cubicon.weapons.projectiles.SeekingRocket;

/*
 * @author Niklas
 */
public class RocketLauncherX3 extends Weapon{

    private static final int CDM = 2000;
    
    public RocketLauncherX3(GameHandler gameHandler) {
        super(CDM, gameHandler);
    }
    public RocketLauncherX3(double dist,double angle,GameHandler gameHandler) {
        super(dist,angle,CDM, gameHandler);
    }
    
    @Override
    public void fire(double angle, Entity e) {
        if(super.noCooldown()){
            angle -= 0.5;
            for(int i = 0; i < 3; i++){
                angle += 0.52;
                super.getgameHandler().addEntity(new SeekingRocket(e.getLocX() + super.getOffsetDist() * Math.cos(super.getOffsetAngle() + e.getFacing()),
                    e.getLocY() + super.getOffsetDist() * Math.sin(super.getOffsetAngle() + e.getFacing()),angle,e.getTeam(),super.getgameHandler()));
            }super.setCdToMax();
        }
    }
    
}
