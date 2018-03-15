package cubicon.effects;

import cubicon.MainLoop;
import java.awt.image.BufferedImage;

/*
 * @author Niklas
 */
public class ExplosionEffect extends Effect {

    private static final int LIFE_TIME = 300;
    private static final BufferedImage MODEL = MainLoop.loadImage("Explosion.png");

    public ExplosionEffect(double locX, double locY, int size) {
        super(locX, locY, size, size, LIFE_TIME, MODEL);
    }

    @Override
    public void update() {
        super.setFacingImage(MainLoop.rng.nextInt(6));//gives the this effects image a random rotation. 6 is close enouth to 2 PI.
        super.updateLifeTime();//updates the lifetime also checks so the effect is still "alive".
    }

}
