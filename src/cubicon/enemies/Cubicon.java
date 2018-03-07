package cubicon.enemies;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import cubicon.MainLoop;
import cubicon.GameHandler;
import cubicon.weapons.*;

/*
 * @author Niklas
 */
public class Cubicon extends Enemy {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final int RADIUS = 100;
    private Weapon w1, w2, w3;

    private static final BufferedImage MODEL = MainLoop.loadImage("Cubicon.png");

    public Cubicon(double locX, double locY, GameHandler gameHandler) {
        super(locX, locY, WIDTH, HEIGHT, RADIUS, "Cubicon", gameHandler, MODEL);
        super.setHpM(1000);
        super.setHp(1000);
        super.setImmortal(false);
        super.setHpBar(true);
        super.setDrawName(true);
        speed = 1;
        w1 = new RocketLauncherX5(super.getgameHandler());
        w2 = new PlasmaCannon(-50,-50,super.getgameHandler());
        w3 = new InterceptorDock(+50,+50,super.getgameHandler());
    }

    @Override
    public void update() {
        seekTarget();
        if (target != null) {
            angleToTarget = Math.atan2(target.getLocY() - super.getLocY(), target.getLocX() - super.getLocX());
            followTarget();
            w1.fire(angleToTarget, this);
            w2.fire(angleToTarget, this);
            w3.fire(angleToTarget, this);
        }
        w1.lowerCooldown();
        w2.lowerCooldown();
        w3.lowerCooldown();
    }

}
