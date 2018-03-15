package cubicon;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/*
 * @author Niklas
 */
public class InputHandler implements MouseListener, MouseMotionListener, KeyListener {

    //the setup of all the variables that saves the input data.
    private boolean mousePressLeft, mousePressRight;
    private int mouseX, mouseY;
    private boolean button1P, button2P, button3P, button4P, buttonESC, buttonH;

    public InputHandler() {
        mouseX = 0;
        mouseY = 0;
        mousePressLeft = false;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {//if the mouse is pressed check what key and sets its boolean to true.
        switch (me.getButton()) {
            case 1:
                setMousePressLeft(true);
                break;
            case 3:
                setMousePressRight(true);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {//if the mouse is released check what button and set its boolean to false.
        switch (me.getButton()) {
            case 1:
                setMousePressLeft(false);
                break;
            case 3:
                setMousePressRight(false);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {//tracks the moues's x and y coordinates.
        setMouseX(me.getX());
        setMouseY(me.getY());
        setMousePressLeft(true);
    }

    @Override
    public void mouseMoved(MouseEvent me) {//tracks the moues's x and y coordinates.
        setMouseX(me.getX());
        setMouseY(me.getY());
        setMousePressLeft(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {//a key on the keyboard was pressed check if its a relevant key in that case set its boolean to true.
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
                setButton1P(true);
                break;
            case KeyEvent.VK_W:
                setButton2P(true);
                break;
            case KeyEvent.VK_E:
                setButton3P(true);
                break;
            case KeyEvent.VK_R:
                setButton4P(true);
                break;
            case KeyEvent.VK_ESCAPE:
                setButtonESC(true);
                break;
                
            case KeyEvent.VK_H:
                setButtonH(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {//a key on the keyboard was released check if its a relevant key in that case set its boolean to false
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
                setButton1P(false);
                break;
            case KeyEvent.VK_W:
                setButton2P(false);
                break;
            case KeyEvent.VK_E:
                setButton3P(false);
                break;
            case KeyEvent.VK_R:
                setButton4P(false);
                break;
            case KeyEvent.VK_ESCAPE:
                setButtonESC(false);
                break;
            case KeyEvent.VK_G:
                setButtonH(false);
                break;
        }
    }
/*

    Below follows "a few"  getters and setters for all the values that represent the users interaction with the game.
    The reason they are synchronized is for example so the user dont happen to change the value of the mouseX at the same time as the game uses it.
    
*/
    public synchronized int getMouseX() {
        return mouseX;
    }

    public synchronized int getMouseY() {
        return mouseY;
    }

    private synchronized void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    private synchronized void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public synchronized boolean isMousePressLeft() {
        return mousePressLeft;
    }

    public synchronized boolean isMousePressRight() {
        return mousePressRight;
    }

    public synchronized void setMousePressLeft(boolean mousePressLeft) {
        this.mousePressLeft = mousePressLeft;
    }

    public synchronized void setMousePressRight(boolean mousePressRight) {
        this.mousePressRight = mousePressRight;
    }

    public synchronized boolean isButton1P() {
        return button1P;
    }

    public synchronized boolean isButton2P() {
        return button2P;
    }

    public synchronized boolean isButton3P() {
        return button3P;
    }

    public synchronized boolean isButton4P() {
        return button4P;
    }

    public synchronized void setButton1P(boolean button1P) {
        this.button1P = button1P;
    }

    public synchronized void setButton2P(boolean button2P) {
        this.button2P = button2P;
    }

    public synchronized void setButton3P(boolean button3P) {
        this.button3P = button3P;
    }

    public synchronized void setButton4P(boolean button4P) {
        this.button4P = button4P;
    }

    public synchronized void setButtonESC(boolean buttonESC) {
        this.buttonESC = buttonESC;
    }

    public synchronized boolean isButtonESC() {
        return buttonESC;
    }

    public synchronized boolean isButtonH() {
        return buttonH;
    }

    public synchronized void setButtonH(boolean buttonH) {
        this.buttonH = buttonH;
    }

}
