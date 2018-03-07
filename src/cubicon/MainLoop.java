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

    public static int UPDATES_PER_SEC = 60;
    private static final int UPDATES_PER_SEC_BASE = 60;
    public static double GAME_SPEED_FIX = 1.0;
    public static int FRAMES_PER_SEC = 60;
    private int framesC, updatesC;
    private double nsPerFrame, nsPerUpdate;
    private boolean isRunning;

    private MainMenu menu;
    private boolean inMenu;
    private InputHandler inputHandler;
    private ScenarioMaker scenarioMaker;
    public static SoundHandler soundHandler; //we only need one in the program as its only purpose is to play sounds and music.
    private static BufferedImage placeHolderImg = loadImage("PlaceHolder.png");
    private GameFrame frame;
    private GameHandler gameHandler;
    private Player player;
    public static Random rng;
    public static ArrayList<Enemy> enemyTypes;

    public void init() {
        GAME_SPEED_FIX = UPDATES_PER_SEC_BASE / UPDATES_PER_SEC;

        rng = new Random();
        enemyTypes = new ArrayList();
        enemyTypes.add(new Cubicon(0, 0, null));
        enemyTypes.add(new Interceptor(0, 0, null));
        enemyTypes.add(new Destroyer(0, 0, null));
        enemyTypes.add(new Annihilator(0, 0, null));
        soundHandler = new SoundHandler();
        inputHandler = new InputHandler();
        frame = new GameFrame();
        gameHandler = new GameHandler(frame);
        isRunning = true;
        menu = new MainMenu(frame.getWidth(), frame.getHeight(), inputHandler);
        scenarioMaker = new ScenarioMaker();
        inMenu = true;
        frame.addMouseListener(inputHandler);
        frame.addMouseMotionListener(inputHandler);
        frame.addKeyListener(inputHandler);

//        gameHandler.addEntity(player);
        soundHandler.playMusicJL("Resources/Sound/Boss1.mp3");
        new Thread(this).start();
    }

    @Override
    public void run() {

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
        dispose();
    }

    private void update() {
        if (inputHandler.isButtonESC()) {
            inMenu = true;
            menu.setStartGame(false);
            frame.refreshSize();
            menu.updateScreenDim(frame.getWidth(), frame.getHeight());
        }
        if (menu.startScenarioMaker()) {
            scenarioMaker.setVisible(true);
            menu.setStartScenarioMaker(false);
        }
        if (menu.isCustomScenarioPicked()) {
            gameHandler.startCustomScenario(menu.getCustomScenarioPath());
            menu.setCustomScenarioPath("");
            menu.setCustomScenarioPicked(false);
            inMenu = false;
            gameHandler.newGame(frame.getWidth(), frame.getHeight(), inputHandler);
        }
        if (inMenu) {
            menu.update();
            menu.moveStarsSpawnTowards(inputHandler.getMouseX(), inputHandler.getMouseY());
            if (menu.startGame()) {
                inMenu = false;
                gameHandler.newGame(frame.getWidth(), frame.getHeight(), inputHandler);
                gameHandler.startCampaign();
            }
        } else {
            gameHandler.update();
        }
    }

    private void dispose() {
        System.exit(0);
    }

    private void render() {

        BufferStrategy bs = frame.getBufferStrategy();
        if (bs == null) {
            frame.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        if (inMenu) {
            menu.draw(g);
        } else {
            gameHandler.draw(g);
        }
        g.setColor(Color.blue);
        g.setFont(new Font("LucidaSans", Font.PLAIN, 15));
        g.drawString("FPS: " + framesC, 25, 25);
        g.drawString("Updates: " + updatesC, 25, 50);
        g.dispose();
        bs.show();
        Toolkit.getDefaultToolkit().sync();//so linux users can enjoy the graphics :D
    }

    public static Enemy getEnemyByEnemySavable(int x, int y, int type, GameHandler gh) {
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

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(cubicon.MainLoop.class.getResource("Resources/Textures/" + path));
        } catch (Exception e) {
            System.out.println("Failed loading: " + path);
            return placeHolderImg;
        }
    }

}
