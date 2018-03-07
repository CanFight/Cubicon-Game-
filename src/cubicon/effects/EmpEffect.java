package cubicon.effects;

import cubicon.MainLoop;
import java.awt.image.BufferedImage;

/*
 * @author Niklas
 */
public class EmpEffect extends Effect {

    private static final int LIFE_TIME = 300;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    private static final BufferedImage MODEL = MainLoop.loadImage("Emp.png");

    public EmpEffect(double locX, double locY) {
        super(locX, locY, WIDTH, HEIGHT, LIFE_TIME, MODEL);
    }

    @Override
    public void update() {
        super.setFacingImage(MainLoop.rng.nextInt(6));
        super.updateLifeTime();
    }

}
