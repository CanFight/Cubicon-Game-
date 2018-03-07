package cubicon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/*
 * @author Niklas
 */
public abstract class Entity {

    private double locX, locY, facing;
    private int width, height, radius, team;
    private BufferedImage image, model;
    private GameHandler gameHandler;
    private boolean alive;
    private int hp, hpM;
    private boolean immortal, hpBar;

    public Entity(double locX, double locY, int width, int height, int radius, int team, GameHandler gameHandler, BufferedImage model) {
        this.locX = locX;
        this.locY = locY;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.team = team;
        this.gameHandler = gameHandler;
        alive = true;
        hpBar = false;
        immortal = true;
        facing = 0;
        setModel(model, width, height);
        setFacingImage(facing);
    }

    public void onDeath() {//Can be used to activate effects on death
    }

    public void damage(int d) {
        if (!immortal) {
            hp -= d;
            if (hp <= 0) {
                alive = false;
            }
        }
    }

    public abstract void update();

    public boolean move(double x, double y) {
        if (gameHandler.isPointWalkable(x, y)) {
            locX = x;
            locY = y;
            return true;
        }
        return false;
    }

    public boolean checkCollide(Entity e) {
        return Math.hypot(locX - e.getLocX(), locY - e.getLocY()) < radius + e.getRadius();
    }

    public void draw(Graphics g) {
        if (model != null) {
            g.drawImage(image, (int) locX - image.getWidth() / 2, (int)locY - image.getHeight() / 2, null);
            if (hpBar) {
                g.setColor(Color.red);
                g.fillRect((int)locX - width / 2, (int)locY + height / 2, width, 3);
                g.setColor(Color.green);
                g.fillRect((int)locX - width / 2, (int) locY + height / 2, (int) ((double) width * (double) hp / (double) hpM), 3);
            }
        }
    }

    protected void setFacingImage(double angle) {
        if (model != null) {
            facing = angle;
            AffineTransform transform = new AffineTransform();
            transform.rotate(angle, model.getWidth() / 2, model.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            image = op.filter(model, null);
        }
    }

    protected void setModel(BufferedImage baseImg, int width, int height) {
        if (baseImg != null) {
            model = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            double imageSizeFixer = (double) width / (double) baseImg.getWidth();
            AffineTransform transform = new AffineTransform();
            transform.scale(imageSizeFixer, imageSizeFixer);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            model = op.filter(baseImg, null);
        }
    }

    public double getLocX() {
        return locX;
    }

    public double getLocY() {
        return locY;
    }

    public int getRadius() {
        return radius;
    }

    public int getTeam() {
        return team;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public GameHandler getgameHandler() {
        return gameHandler;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHpM() {
        return hpM;
    }

    public void setHpM(int hpM) {
        this.hpM = hpM;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }

    public boolean isHpBar() {
        return hpBar;
    }

    public void setHpBar(boolean hpBar) {
        this.hpBar = hpBar;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public double getFacing() {
        return facing;
    }

    public void setLocX(double locX) {
        this.locX = locX;
    }

    public void setLocY(double locY) {
        this.locY = locY;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
