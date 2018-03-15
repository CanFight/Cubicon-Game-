package cubicon;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import cubicon.weapons.*;
/*
 * @author Niklas
 */
public class Player extends Entity{

    private static final BufferedImage playerModel = MainLoop.loadImage("SpaceShip.png");//loads the model for the players ship!
    private InputHandler inputHandler;//so the player knows what to do
    private double facingAngle, accel, speedX, speedY,speedM;
    private ArrayList<Weapon> weapons; //an array containing all the players weapons.
    
    public Player(int locX, int locY, GameHandler gameHandler, InputHandler inputHandler) {//construction and a little bit of setup
        super(locX, locY, 50, 50, 25, 1, gameHandler, playerModel);
        this.inputHandler = inputHandler;
        speedM = 8;
        accel = 0.4;
        weapons = new ArrayList();
        weapons.add(new PlasmaCannon(15,1.57,super.getgameHandler()));
        weapons.add(new PlasmaCannon(-15,1.57,super.getgameHandler()));
        weapons.add(new RocketLauncherX3(super.getgameHandler()));
        weapons.add(new Repair(super.getgameHandler()));
        weapons.add(new EMP(super.getgameHandler()));
        super.setHpBar(true);
        super.setImmortal(false);
        super.setHpM(100);
        super.setHp(100);
    }

    @Override
    public void update() {
        facingAngle = Math.atan2(inputHandler.getMouseY() - super.getLocY(), inputHandler.getMouseX() - super.getLocX());//calculate the angle between the player and the mouse.
        super.setFacingImage(facingAngle);//rotates the model accordingly
        if(inputHandler.isMousePressLeft()){//does the player wish to move? In that case accelerate him towards the mouse.
            if(Math.hypot(inputHandler.getMouseY() - super.getLocY(), inputHandler.getMouseX() - super.getLocX()) > speedM){//checks so the distance between the curson and the player isnt to small, to avoid flickering.
                accel();//update the acceleration
            }
        }
        super.move(super.getLocX() + speedX, super.getLocY() + speedY);
        for(Weapon w: weapons){//updates the cooldown of all the weapons.
            w.lowerCooldown();
        }
        fireWeapons();
    }
    
    private void fireWeapons(){
        if(inputHandler.isButton1P()){
            weapons.get(0).fire(facingAngle, this);
            weapons.get(1).fire(facingAngle, this);
        }
        if(inputHandler.isButton2P()){
            weapons.get(2).fire(facingAngle, this);
        }
        if(inputHandler.isButton3P()){
            weapons.get(3).fire(facingAngle, this);
        }
        if(inputHandler.isButton4P()){
            weapons.get(4).fire(facingAngle, this);
        }
    }
    
    private void accel(){
        speedX += accel * Math.cos(facingAngle);
        speedY += accel * Math.sin(facingAngle);
        if(Math.hypot(speedX, speedY) > speedM){
            double travelAngle = Math.atan2(speedY, speedX);
            speedX = speedM * Math.cos(travelAngle);
            speedY = speedM * Math.sin(travelAngle);
        }
    }
    
}
