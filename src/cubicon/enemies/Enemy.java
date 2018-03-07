package cubicon.enemies;

import java.awt.image.BufferedImage;
import cubicon.Entity;
import cubicon.GameHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/*
 * @author Niklas
 */
public abstract class Enemy extends Entity {

    protected Entity target;
    protected double angleToTarget = 0, speed;
    private String name;
    private boolean drawName;

    public Enemy(double locX, double locY, int width, int height, int radius, String name, GameHandler gameHandler, BufferedImage model) {
        super(locX, locY, width, height, radius, 2, gameHandler, model);
        this.name = name;
        drawName = false;
    }

    protected void seekTarget() {
        target = null;
        double closestTarget = Double.MAX_VALUE;
        for (Entity e : super.getgameHandler().getEntities()) {
            if (e.getTeam() != super.getTeam() && !e.isImmortal()) {
                double hypot = Math.hypot(super.getLocX() - e.getLocX(), super.getLocY() - e.getLocY());
                if (hypot <= closestTarget) {
                    closestTarget = hypot;
                    target = e;
                }
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (drawName) {
            g.setColor(Color.red);
            g.setFont(new Font("LucidaSans", Font.BOLD, (super.getWidth() / 10)));
            g.drawString("[" + name + "]", (int) super.getLocX() - 50, (int) super.getLocY() + super.getHeight() / 2 - 5);
        }
    }

    protected void followTarget() {
        super.move(super.getLocX() + speed * Math.cos(angleToTarget), super.getLocY() + speed * Math.sin(angleToTarget));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDrawName(boolean drawName) {
        this.drawName = drawName;
    }

}
