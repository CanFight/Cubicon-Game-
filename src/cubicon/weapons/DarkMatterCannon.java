package cubicon.weapons;

import cubicon.Entity;
import cubicon.GameHandler;
import cubicon.weapons.projectiles.DarkMatter;

/*
 * @author Niklas
 */
public class DarkMatterCannon extends Weapon{

    private static final int CDM = 400;
    
    public DarkMatterCannon(GameHandler gameHandler) {
        super(CDM, gameHandler);
    }
    public DarkMatterCannon(double oD,double oA,GameHandler gameHandler) {
        super(oD,oA,CDM, gameHandler);
    }
    
    @Override
    public void fire(double angle, Entity e) {
        if(super.noCooldown()){
            super.getgameHandler().addEntity(new DarkMatter(e.getLocX() + super.getOffsetDist() * Math.cos(super.getOffsetAngle() + e.getFacing()),
                    e.getLocY() + super.getOffsetDist() * Math.sin(super.getOffsetAngle() + e.getFacing()),angle,e.getTeam(),super.getgameHandler()));
            super.setCdToMax();
        }
    }
    
}
