package cubicon.weapons;

import cubicon.Entity;
import cubicon.GameHandler;
import cubicon.weapons.projectiles.PlasmaBall;

/*
 * @author Niklas
 */
public class PlasmaCannon extends Weapon{

    //the only difference between weapons are the fire method, and their cooldown.
    
    private static final int CDM = 200;
    
    public PlasmaCannon(GameHandler gameHandler) {
        super(CDM, gameHandler);
    }
    public PlasmaCannon(double oD,double oA,GameHandler gameHandler) {
        super(oD,oA,CDM, gameHandler);
    }
    
    @Override
    public void fire(double angle, Entity e) { //fires a "ball of plasma" towards the specified angle.
        if(super.noCooldown()){
            super.getgameHandler().addEntity(new PlasmaBall(e.getLocX() + super.getOffsetDist() * Math.cos(super.getOffsetAngle() + e.getFacing()),
                    e.getLocY() + super.getOffsetDist() * Math.sin(super.getOffsetAngle() + e.getFacing()),angle,e.getTeam(),super.getgameHandler()));
            super.setCdToMax();
        }
    }
    
}
