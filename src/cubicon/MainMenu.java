package cubicon;

import static cubicon.MainLoop.loadImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * @author Niklas
 */
public class MainMenu { //The main menu in the game.

    private int screenWidth, screenHeight;
    private Star[] stars; //an array of objects used to create a nice background effect

    private InputHandler inputHandler;
    private boolean startCampaing, startScenarioMaker, customScenarioPicked, scenarioPickerOpen;
    private String customScenarioPath = "";
    private double spawnX, spawnY;

    private BufferedImage title = loadImage("Title.png");
    private Rectangle[] buttons;

    public MainMenu(int screenWidth, int screenHeight, InputHandler inputHandler) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.inputHandler = inputHandler;
        startCampaing = false;
        scenarioPickerOpen = false;
        setUpUI();
        spawnX = screenWidth / 2; //where the stars comes from.
        spawnY = screenHeight / 2; 
        stars = new Star[300];//create all the stars.
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(this.screenWidth, this.screenHeight);
        }
    }

    private void setUpUI() {//sets up the 4 menu buttons campaing, custom scenario, scenario maker, and exit.
        buttons = new Rectangle[4];
        buttons[0] = new Rectangle(screenWidth / 2 - 150, screenHeight / 2 - 125, 300, 50);
        buttons[1] = new Rectangle(screenWidth / 2 - 150, screenHeight / 2 - 65, 300, 50);
        buttons[2] = new Rectangle(screenWidth / 2 - 150, screenHeight / 2 - 5, 300, 50);
        buttons[3] = new Rectangle(screenWidth / 2 - 150, screenHeight / 2 + 55, 300, 50);

    }

    public void checkButtonClick() {//check if any of the menu buttons where clicked.
        if (inputHandler.isMousePressLeft()) {
            if (buttons[0].contains(inputHandler.getMouseX(), inputHandler.getMouseY())) {
                startCampaing = true;
            }
            if (buttons[1].contains(inputHandler.getMouseX(), inputHandler.getMouseY())) {
                if (!isCustomScenarioPickerIsOpen()) {
                    setCustomScenarioPickerIsOpen(true);
                    openScenarioChooser();
                }
            }
            if (buttons[2].contains(inputHandler.getMouseX(), inputHandler.getMouseY())) {
                startScenarioMaker = true;
            }
            if (buttons[3].contains(inputHandler.getMouseX(), inputHandler.getMouseY())) {
                System.exit(0);
            }
        }
    }

    private synchronized boolean isCustomScenarioPickerIsOpen() {
        return scenarioPickerOpen;
    }

    private synchronized void setCustomScenarioPickerIsOpen(boolean b) {
        scenarioPickerOpen = b;
    }

    public void update() {
        checkButtonClick();
        for (Star s : stars) {
            s.update();
        }
    }

    public void draw(Graphics g) {
        for (Star s : stars) {
            s.draw(g);
        }
        g.setColor(Color.cyan);
        g.drawRect(buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height);
        g.drawRect(buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height);
        g.drawRect(buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height);
        g.drawRect(buttons[3].x, buttons[3].y, buttons[3].width, buttons[3].height);
        g.setFont(new Font("LucidaSans", Font.BOLD, 25));
        g.drawString("START CAMPAIGN", buttons[0].x + buttons[0].width / 2 - g.getFontMetrics().stringWidth("START CAMPAIGN") / 2, buttons[0].y + buttons[0].height / 2 + 12);
        g.drawString("CUSTOM SCENARIO", buttons[1].x + buttons[1].width / 2 - g.getFontMetrics().stringWidth("CUSTOM SCENARIO") / 2, buttons[1].y + buttons[1].height / 2 + 12);
        g.drawString("SCENARIO MAKER", buttons[2].x + buttons[2].width / 2 - g.getFontMetrics().stringWidth("SCENARIO MAKER") / 2, buttons[2].y + buttons[2].height / 2 + 12);
        g.drawString("EXIT", buttons[3].x + buttons[3].width / 2 - g.getFontMetrics().stringWidth("EXIT") / 2, buttons[3].y + buttons[3].height / 2 + 12);
        g.drawImage(title, screenWidth / 2 - 300, screenHeight / 2 - 400, 600, 200, null);

    }

    public void openMenu() {
        startCampaing = false;
    }

    public boolean startCampaign() {
        return startCampaing;
    }

    public boolean startScenarioMaker() {
        return startScenarioMaker;
    }

    public void setStartScenarioMaker(boolean b) {
        this.startScenarioMaker = b;
    }

    public void setStartCampaign(boolean b) {
        this.startCampaing = b;
    }

    public void updateScreenDim(int width, int height) {//in case we deside the size of the window changed.
        screenWidth = width;
        screenHeight = height;
        for (Star s : stars) {
            s.screenWidth = screenWidth;
            s.screenHeight = screenHeight;
        }
        for (Rectangle r : buttons) {
            r.x = screenWidth / 2 - r.width / 2;
        }
    }

    public void moveStarsSpawnTowards(int x, int y) { //moves the center of the background effect towards the specified point. (used for the background center to follow the mouse)
        double angle = Math.atan2(y - spawnY, x - spawnX);
        spawnX += 20.0 * Math.cos(angle);
        spawnY += 20.0 * Math.sin(angle);
        setStarsXY((int) spawnX, (int) spawnY);
    }

    public void setStarsXY(int x, int y) { //moves the center of the stars INSTANTLY to the specified point.
        for (Star s : stars) {
            s.setSpawn(x, y);
        }
    }

    private void openScenarioChooser() { //opens a standard swing java file chooser to pick custom scenario to play, runs on a seperate thread to not freeze the program while a scenario is being selected.
        Thread chooserThread = new Thread() {
            @Override
            public void run() {

                JFileChooser fileChooser = new JFileChooser(".");
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Scenario Files", "scenario", "scenario");
                fileChooser.setFileFilter(filter);

                int returnVal = fileChooser.showOpenDialog(null);
                String path = null;
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getName();
                }
                if (path != null) {
                    setCustomScenarioPath(path);
                    setCustomScenarioPicked(true);
                }
                setCustomScenarioPickerIsOpen(false);
            }
        };
        chooserThread.start();
    }

    public synchronized boolean isCustomScenarioPicked() {
        return customScenarioPicked;
    }

    public synchronized void setCustomScenarioPicked(boolean customScenarioPicked) {
        this.customScenarioPicked = customScenarioPicked;
    }

    public synchronized String getCustomScenarioPath() {
        return customScenarioPath;
    }

    public synchronized void setCustomScenarioPath(String customScenarioPath) {
        this.customScenarioPath = customScenarioPath;
    }

    private class Star { //to simple to create a seperate class for so we just make a class in a class, it is also ONLY used in this class.

        private double x, y, angle, dist, speed;
        private Color color;
        private int screenWidth, screenHeight, spawnX, spawnY;

        public Star(int screenWidth, int screenHeight) {
            this.screenHeight = screenHeight;
            this.screenWidth = screenWidth;
            spawnX = screenHeight / 2;
            spawnY = screenWidth / 2;
            reCreate();
        }

        private void update() {// moves and accelerates the star away from the center towards the borders of the screen creating a "warp effect"
            angle = Math.atan2(y - spawnY, x - spawnX);
            x += speed * Math.cos(angle);
            y += speed * Math.sin(angle);
            if (x < -50 || x > screenWidth + 50 || y < -50 || y > screenHeight + 50) {
                reCreate();
            }
            if (speed < 50) {
                speed *= 1.04;
            }
            if (dist < 50) {
                dist *= 1.07;
            }
        }

        public void setSpawn(int x, int y) { //moves the spawn (center)
            spawnX = x;
            spawnY = y;
        }

        private void reCreate() {//as we dont want to remove and create a new star (ineffective), we simply reuse this one by moving it back to the "center"
            angle = (double) MainLoop.rng.nextInt(63) / 10;
            color = new Color(MainLoop.rng.nextInt(155) + 100, MainLoop.rng.nextInt(155) + 100, MainLoop.rng.nextInt(155) + 100);
            
            x = spawnX + ((125 + MainLoop.rng.nextInt(600)) * Math.cos(angle));
            y = spawnY + ((125 + MainLoop.rng.nextInt(600)) * Math.sin(angle));
            dist = 4;
            speed = 4;
            angle = Math.atan2(y - spawnY, x - spawnX);
        }

        public void draw(Graphics g) { //draws the star
            g.setColor(color);
            g.fillOval((int) x - 2, (int) y - 2, 4, 4);
            g.drawLine((int) x, (int) y, (int) (x + (-dist * Math.cos(angle))), (int) (y + (-dist * Math.sin(angle))));
        }
    }

}
