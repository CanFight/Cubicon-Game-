package cubicon;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.io.ObjectInputStream;

/*
 * @author Niklas
 */
public class GameHandler {

    private ArrayList<Entity> entities;
    private ArrayList<Entity> newEntities;
    private ArrayList<Entity> deadEntities;
    private Entity player;
    private Scenario currentScenario;
    private int aliveEnemies, newWaveTimer;
    private boolean newWaveTimerOn, scenarioWon, scenarioLost;
    private GameFrame frame;

    public GameHandler(GameFrame frame) {
        this.frame = frame;
        entities = new ArrayList();
        newEntities = new ArrayList();
        deadEntities = new ArrayList();
        aliveEnemies = 0;
    }

    public void newGame(int x, int y, InputHandler inputHandler) {
        entities.clear();
        player = new Player(x / 2, y / 2, this, inputHandler);
        addEntity(player);
        MainLoop.soundHandler.stopMusic();
        MainLoop.soundHandler.playMusicJL("Resources/Sound/Boss1.mp3");
    }

    private boolean isPlayerAlive() {
        if (player != null) {
            return player.isAlive();
        }
        return false;
    }

    public void startCampaign() {
        try {
            ObjectInputStream in = new ObjectInputStream(this.getClass().getResourceAsStream("Resources/Scenarios/Campaign.scenario"));
            currentScenario = (Scenario) in.readObject();
            currentScenario.startScenario();
            scenarioWon = false;
            scenarioLost = false;
        } catch (Exception e) {
            System.err.println("Failed loading campaign file.");
            e.printStackTrace();
        }
    }
    
    public void startCustomScenario(String path){
        currentScenario = Scenario.loadScenario(path);
        if(currentScenario != null){
            currentScenario.startScenario();
            scenarioWon = false;
            scenarioLost = false;
            System.out.println("starting custom scenario");
        }else{
            System.err.println("failed starting custom scenario");
        }
    }

    public boolean isPointWalkable(double x, double y) {
        return true;
    }

    public void addEntity(Entity e) {
        newEntities.add(e);
    }

    public void update() {
        deadEntities.clear();
        entities.addAll(newEntities);
        newEntities.clear();
        aliveEnemies = 0;
        for (Entity e : entities) {
            e.update();
            if (e.getTeam() == 2) {
                aliveEnemies++;
            }
            if (!e.isAlive()) {
                deadEntities.add(e);
            }
        }
        for (Entity e : deadEntities) {
            e.onDeath();
            entities.remove(e);
        }
//        System.out.println("Enemies: " + aliveEnemies);
        checkCollide();
        checkForNewWave();
        if(!isPlayerAlive()){
            scenarioLost = true;
        }
    }

    private void checkForNewWave() {
        if (aliveEnemies == 0 && newWaveTimer <= 0 && newWaveTimerOn == false) {
            if (currentScenario.isScenarioOver()) {
                scenarioWon = true;
                return;
            } else {
                newWaveTimer = 3000;
                newWaveTimerOn = true;
                currentScenario.startWaveMusic(currentScenario.getCurrentWave() + 1);
            }
        }
        if (newWaveTimer > 0 && newWaveTimerOn == true) {
            newWaveTimer -= 1000 / MainLoop.UPDATES_PER_SEC;
        }
        if (newWaveTimer <= 0 && newWaveTimerOn == true) {
            currentScenario.startNextWave(entities, this);
            newWaveTimerOn = false;
        }
    }

    public void draw(Graphics g) {
        for (Entity e : entities) {
            e.draw(g);
        }
        if (scenarioWon) {
            g.setColor(Color.magenta);
            g.setFont(new Font("LucidaSans", Font.BOLD, 40));
            g.drawString(currentScenario.getName() + " Won!", frame.getWidth() / 2 - g.getFontMetrics().stringWidth(currentScenario.getName() + " Won!") / 2, frame.getHeight() / 2);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString("(Press ESC to return to menu)", frame.getWidth() / 2 - g.getFontMetrics().stringWidth("(Press ESC to return to menu)") / 2, frame.getHeight() / 2 + 40);
        }
        if (scenarioLost) {
            g.setColor(Color.RED);
            g.setFont(new Font("LucidaSans", Font.BOLD, 40));
            g.drawString("DEFEAT", frame.getWidth() / 2 - g.getFontMetrics().stringWidth("DEFEAT") / 2, frame.getHeight() / 2);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString("(Press ESC to return to menu)", frame.getWidth() / 2 - g.getFontMetrics().stringWidth("(Press ESC to return to menu)") / 2, frame.getHeight() / 2 + 40);
        }
        if (newWaveTimerOn && !scenarioWon && !scenarioLost) {
            g.setColor(Color.cyan);
            g.setFont(new Font("LucidaSans", Font.BOLD, 30));
            g.drawString(currentScenario.getName(), frame.getWidth() / 2 - g.getFontMetrics().stringWidth(currentScenario.getName()) / 2, frame.getHeight() / 2);
            g.setFont(new Font("LucidaSans", Font.BOLD, 20));
            g.drawString("Wave " + (currentScenario.getCurrentWave() + 2), frame.getWidth() / 2 - g.getFontMetrics().stringWidth("Wave " + (currentScenario.getCurrentWave() + 1)) / 2, frame.getHeight() / 2 + 40);
        }
    }

    public void checkCollide() {
        for (Entity e1 : entities) {
            for (Entity e2 : entities) {
                if (e1.getTeam() != e2.getTeam()) {
                    e1.checkCollide(e2);
                }
            }
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

}
