package cubicon.effects;

import cubicon.Entity;
import cubicon.MainLoop;
import java.awt.image.BufferedImage;

/*
 * @author Niklas
 */
public class RepairEffect extends Effect {

    private static final int LIFE_TIME = 1500;
    private static final BufferedImage MODEL = MainLoop.loadImage("Repair.png");
    private Entity repairedEntity;

    public RepairEffect(int size, Entity e) {
        super(e.getLocX(), e.getLocY(), size, size, LIFE_TIME, MODEL);
        repairedEntity = e;
    }

    @Override
    public void update() {
        super.setFacingImage(MainLoop.rng.nextInt(6));//gives the this effects image a random rotation. 6 is close enouth to 2 PI.
        super.updateLifeTime();//updates the lifetime also checks so the effect is still "alive".
        super.setLocX(repairedEntity.getLocX());//couses this effect to be attached to a enity.
        super.setLocY(repairedEntity.getLocY());
    }

}
