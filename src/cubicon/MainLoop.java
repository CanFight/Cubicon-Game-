package cubicon;

import cubicon.enemies.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

public class MainLoop implements Runnable {

    //Below follows the configuration of the main loop.
    public static int UPDATES_PER_SEC = 60;
    public static int FRAMES_PER_SEC = 60;
    private int framesC, updatesC;
    private double nsPerFrame, nsPerUpdate;
    private boolean isRunning;

    private MainMenu menu; //Menu object.
    private boolean inMenu, showCommands;
    private InputHandler inputHandler;
    private ScenarioMaker scenarioMaker; //Used to create custom scenarios. Made with NETBEANS GUI SWING creator which is the reason big parts of it looks horrible. However it does contains quite a lot of code written by me.
    public static SoundHandler soundHandler; //we only need one in the program as its only purpose is to play sounds and music.
    private static BufferedImage placeHolderImg = loadImage("PlaceHolder.png"); //loads the placeholder image that is used incase some image fails to load. (more common when using resources outside the jar)
    private GameFrame frame; //the component we draw the game on.
    private GameHandler gameHandler; //Keeps track of all the entities and the "Scenario at hand". (Scenarios are basicly an array of events that happens in the game that can be customly configured).
    public static Random rng; //Random number generator used from varius parts of the program. No need for more then one so we create a static one here.
    public static ArrayList<Enemy> enemyTypes; //a list of all enemie TYPES in the game. Used by the scenario class and scenario maker class. Main reason for it being here is so that we dont have to edit the
                                               // to other classes to simply add an enemie. However any new enemies needs to be added to this list.

    public void init() {//initial configuration and construction of the game.

        rng = new Random();
        //setup all the enemie types.
        enemyTypes = new ArrayList();
        enemyTypes.add(new Cubicon(0, 0, null));
        enemyTypes.add(new Interceptor(0, 0, null));
        enemyTypes.add(new Destroyer(0, 0, null));
        enemyTypes.add(new Annihilator(0, 0, null));
        
        frame = new GameFrame();
        soundHandler = new SoundHandler(); //used to play sounds in the game. Will probebly not be used in the submiten version due to royalte rights on alot of music (Safety first) :)
        inputHandler = new InputHandler(); //handlers mouse and keyboard input
        gameHandler = new GameHandler(frame);
        menu = new MainMenu(frame.getWidth(), frame.getHeight(), inputHandler);
        scenarioMaker = new ScenarioMaker();
        
        //add all listeners to the frame
        frame.addMouseListener(inputHandler);
        frame.addMouseMotionListener(inputHandler);
        frame.addKeyListener(inputHandler);
        
        showCommands = true;
        isRunning = true;
        inMenu = true;

        //soundHandler.playMusicJL("Resources/Sound/Boss1.mp3"); //Not used in final submission due to copy right problems.
        new Thread(this).start();
    }

    @Override
    public void run() {

        // a basic main loop that runs the program.
        nsPerUpdate = 1000000000.0 / UPDATES_PER_SEC;
        nsPerFrame = 1000000000.0 / FRAMES_PER_SEC;

        long lastTime = System.nanoTime();
        double unprocessedTime = 0;
        double unprocessedTimeFPS = 0;

        int frames = 0;
        int updates = 0;

        long frameCounter = System.currentTimeMillis();

        while (isRunning) {

            long currentTime = System.nanoTime();
            long passedTime = currentTime - lastTime;
            lastTime = currentTime;
            unprocessedTime += passedTime;
            unprocessedTimeFPS += passedTime;

            if (unprocessedTime >= nsPerUpdate) {
                unprocessedTime = 0;
                updates++;
                update();
            }

            if (unprocessedTimeFPS >= nsPerFrame) {
                unprocessedTimeFPS = 0;
                render();
                frames++;
            }

            if (System.currentTimeMillis() - frameCounter >= 1000) {
                framesC = frames;
                updatesC = updates;
                frames = 0;
                updates = 0;
                frameCounter += 1000;
            }

        }
        dispose(0);
    }

    private void update() {//called upon by the main loop, here let different parts of the program update themselves.
        if (inputHandler.isButtonESC()) { //check if the player wishes to return to the main menu.
            inMenu = true;
            menu.setStartCampaign(false);
            frame.refreshSize();
            menu.updateScreenDim(frame.getWidth(), frame.getHeight()); //incase the player resized his window or w/e (implemented due to problems when running on linux)
        }
        if (menu.startScenarioMaker()) {//simply opens the scenario maker.
            scenarioMaker.setVisible(true);
            menu.setStartScenarioMaker(false);
        }
        if (menu.isCustomScenarioPicked()) {//did we select a custom scenario, in that case start it.
            gameHandler.newGame(frame.getWidth(), frame.getHeight(), inputHandler);
            gameHandler.startCustomScenario(menu.getCustomScenarioPath());
            menu.setCustomScenarioPath("");
            menu.setCustomScenarioPicked(false);
            inMenu = false;
        }
        if (inMenu) {//incase we are in the menu update it.
            menu.update();
            menu.moveStarsSpawnTowards(inputHandler.getMouseX(), inputHandler.getMouseY()); //moves the background effects center towards the mouse.
            if (menu.startCampaign()) {//do we want to start the campaing?
                inMenu = false;
                gameHandler.newGame(frame.getWidth(), frame.getHeight(), inputHandler);
                gameHandler.startCampaign();
            }
        } else {
            gameHandler.update(); //otherwise we are in the game then update that.
        }
        if(inputHandler.isButtonH()){
            inputHandler.setButtonH(false); //set the button to false so it doesnt keep running this. (basicly so this wont run multiple times per button press)
            showCommands = !showCommands; //sets show command boolean to the opposit of what it was. So we can toggle if we want to see commands or not.
        }
    }

    private void dispose(int i) {
        System.exit(i);//used to exit the game.
    }

    private void render() {

        //creates a buffer strategy becouse flickering is annoying.
        BufferStrategy bs = frame.getBufferStrategy();
        if (bs == null) {
            frame.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();//aquire the graphics from the buffer strategy
        g.setColor(Color.black); 
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());//draw a black background to the screen (basicly draw over all the old stuff too
        if (inMenu) { //if we are in the menu draw it.
            menu.draw(g);
        } else {
            gameHandler.draw(g); //else we are in the game, then draw it.
        }
        g.setColor(Color.blue);
        g.setFont(new Font("LucidaSans", Font.PLAIN, 15));
        g.drawString("FPS: " + framesC, 25, 25); //print out the fps (simply to see preformance)
        if (showCommands) { //shows the commands if the player has that toggled on.
            drawCommandsInfo(g);
        }
        g.dispose();
        bs.show();
        Toolkit.getDefaultToolkit().sync();//so linux users can enjoy the graphics, and actually play the game.
    }

    private void drawCommandsInfo(Graphics g) {//draws the commands
        g.setColor(Color.green);
        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        g.drawString("Commands (Press H to Hide/Show)", 30, 125);
        g.setFont(new Font("LucidaSans", Font.BOLD, 15));
        g.drawString("Hold Left Mouse Button to accelerate towards the mouse", 30, 165);
        g.drawString("Q - Plasma Cannons", 30, 185);
        g.drawString("W - Rocket Launcher", 30, 205);
        g.drawString("E - Repair", 30, 225);
        g.drawString("R - EMP", 30, 245);
    }

    public static Enemy getEnemyByEnemySavable(int x, int y, int type, GameHandler gh) {//used to aquire the different enemy types. Returns a new enemy based on the "integer" (type) representing it in the array.
        for (int i = 0; i < enemyTypes.size(); i++) {
            switch (type) {
                case 0:
                    return new Cubicon(x, y, gh);
                case 1:
                    return new Interceptor(x, y, gh);
                case 2:
                    return new Destroyer(x, y, gh);
                case 3:
                    return new Annihilator(x, y, gh);
            }
        }
        return null;
    }

    public static BufferedImage loadImage(String path) {//can be called from anywhere in the game to load textures from inside the jars resource folder.
        try {
            return ImageIO.read(cubicon.MainLoop.class.getResource("Resources/Textures/" + path));
        } catch (Exception e) {
            System.out.println("Failed loading: " + path);
            return placeHolderImg;
        }
    }

}
