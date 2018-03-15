package cubicon.enemies;

import java.awt.image.BufferedImage;
import cubicon.Entity;
import cubicon.GameHandler;
import cubicon.MainLoop;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/*
 * @author Niklas
 */
public abstract class Enemy extends Entity {

    protected Entity target;
    protected double angleToTarget = 0;
    private String name;
    private boolean drawName;
    private int circlingOffset;
    private double speed, speedX, speedY, accel, circlingAngle, circleRotSpeed;

    public Enemy(double locX, double locY, int width, int height, int radius, int circlingOffset, int speed, double accel, double circleRotSpeed, String name, GameHandler gameHandler, BufferedImage model) {
        super(locX, locY, width, height, radius, 2, gameHandler, model);
        this.name = name;
        drawName = false;
        this.circlingOffset = circlingOffset;
        this.speed = speed;
        this.accel = accel;
        speedX = 0;
        speedY = 0;
        
        circlingAngle = (double)MainLoop.rng.nextInt(63) / 10.0;//starts the rotation on a random point. 63/10 is almost 2 PI, which is a full circle in radians.
        
        if (MainLoop.rng.nextBoolean()) {//so all enemies doesnt rotate in the same direction.
            this.circleRotSpeed = circleRotSpeed;
        } else {
            this.circleRotSpeed = circleRotSpeed * -1;
        }
    }

    protected void seekTarget() {//finds the closest enemy target target
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
    public void draw(Graphics g) {//if we wish to draw this enemies name we do so, everything else is drawn by the enitity class.
        super.draw(g);
        if (drawName) {
            g.setColor(Color.red);
            g.setFont(new Font("LucidaSans", Font.BOLD, (super.getWidth() / 10)));
            g.drawString("[" + name + "]", (int) super.getLocX() - 50, (int) super.getLocY() + super.getHeight() / 2 - 5);
        }
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
    
    protected void updateTargetOffsetCircle(){//a part of the irregular movement.
        circlingAngle += circleRotSpeed;
        if(circlingAngle > 6.3){
            circlingAngle = 0;
        }
        if(circlingAngle < 0){
            circlingAngle = 6.3;
        }
    }
    
    protected void moveTowardsTarget() {//used to move this enemy a bit "unregular" while still following its target, makes it alot harder to fight.
        double mLocX = target.getLocX() + circlingOffset * Math.cos(circlingAngle);
        double mLocY = target.getLocY() + circlingOffset * Math.sin(circlingAngle);
        double angle = Math.atan2(mLocY - super.getLocY(), mLocX - super.getLocX());
        speedX += accel * Math.cos(angle);
        speedY += accel * Math.sin(angle);
        if (Math.hypot(speedX, speedY) > speed) {
            double travelAngle = Math.atan2(speedY, speedX);
            speedX = speed * Math.cos(travelAngle);
            speedY = speed * Math.sin(travelAngle);
        }
        super.move(super.getLocX() + speedX, super.getLocY() + speedY);
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

}
