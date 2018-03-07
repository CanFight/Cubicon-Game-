package cubicon.weapons;

import cubicon.Entity;
import cubicon.GameHandler;
import cubicon.enemies.Interceptor;

/*
 * @author Niklas
 */
public class InterceptorDock extends Weapon{

    private static final int CDM = 10000;
    
    public InterceptorDock(GameHandler gameHandler) {
        super(CDM, gameHandler);
    }
    public InterceptorDock(int oX,int oY,GameHandler gameHandler) {
        super(oX,oY,CDM, gameHandler);
    }
    
    @Override
    public void fire(double angle, Entity e) {
        if(super.noCooldown()){
            Interceptor t = new Interceptor(e.getLocX() + super.getOffsetDist() * Math.cos(super.getOffsetAngle() + e.getFacing()),
                    e.getLocY() + super.getOffsetDist() * Math.sin(super.getOffsetAngle() + e.getFacing()), e.getgameHandler());
            t.setTeam(e.getTeam());
            super.getgameHandler().addEntity(t);
            
            super.setCdToMax();
        }
    }
    
}
