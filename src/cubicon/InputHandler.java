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

    private boolean mousePressLeft, mousePressRight;
    private int mouseX, mouseY;
    private boolean button1P, button2P, button3P, button4P, buttonESC;

    public InputHandler() {
        mouseX = 0;
        mouseY = 0;
        mousePressLeft = false;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
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
    public void mouseReleased(MouseEvent me) {
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
    public void mouseDragged(MouseEvent me) {
        setMouseX(me.getX());
        setMouseY(me.getY());
        setMousePressLeft(true);
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        setMouseX(me.getX());
        setMouseY(me.getY());
        setMousePressLeft(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
                button1P = true;
                break;
            case KeyEvent.VK_W:
                button2P = true;
                break;
            case KeyEvent.VK_E:
                button3P = true;
                break;
            case KeyEvent.VK_R:
                button4P = true;
                break;
            case KeyEvent.VK_ESCAPE:
                buttonESC = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
                button1P = false;
                break;
            case KeyEvent.VK_W:
                button2P = false;
                break;
            case KeyEvent.VK_E:
                button3P = false;
                break;
            case KeyEvent.VK_R:
                button4P = false;
                break;
            case KeyEvent.VK_ESCAPE:
                buttonESC = false;
                break;
        }
    }

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

    public boolean isButton1P() {
        return button1P;
    }

    public boolean isButton2P() {
        return button2P;
    }

    public boolean isButton3P() {
        return button3P;
    }

    public boolean isButton4P() {
        return button4P;
    }

    public boolean isButtonESC() {
        return buttonESC;
    }

}
