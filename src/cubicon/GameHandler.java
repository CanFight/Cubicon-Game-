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

    private ArrayList<Entity> entities; //the main array containing all enetities, from simple missiles to enemies and players.
    private ArrayList<Entity> newEntities; //an array containing new entities that should be added to the main array. (to avoid adding entities to the main array while we are looping through it)
    private ArrayList<Entity> deadEntities; //used to remove dead arrays, same thing here to avoid removing things while looping through the main array.
    private Entity player; //the player, we keep track of it so we can determine if he died.
    private Scenario currentScenario; //current scenario loaded.
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

    public void newGame(int x, int y, InputHandler inputHandler) {//resets everything. For a new start (usually the new start of a scenario.
        entities.clear();
        player = new Player(x / 2, y / 2, this, inputHandler);
        addEntity(player);
        MainLoop.soundHandler.stopMusic();
    }

    private boolean isPlayerAlive() {// returns true if the player is alive else false.
        if (player != null) {
            return player.isAlive();
        }
        return false;
    }

    public void startCampaign() { //loads the campaign file from within the jar, has to be loaded differently from the custom scenarios as this file lies within the jar itself. (so we always have something playable no mater what)
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

    public void startCustomScenario(String path) { //loads in the specified scenario and starts it up.
        currentScenario = Scenario.loadScenario(path);
        if (currentScenario != null) {
            currentScenario.startScenario();
            scenarioWon = false;
            scenarioLost = false;
            System.out.println("starting custom scenario");
        } else {
            System.err.println("failed starting custom scenario");
        }
    }

    public void addEntity(Entity e) {
        newEntities.add(e);
    }

    public void update() {
        deadEntities.clear();//clear the array of dead entities.
        entities.addAll(newEntities);//add all the new entities to the primary enteties array.
        newEntities.clear();
        aliveEnemies = 0;//reset the counter for number of alive enemies.
        for (Entity e : entities) {//update all the enemies.
            e.update();
            if (e.getTeam() == 2) {// team 2 == enemies, so if we find one, count it.
                aliveEnemies++;
            }
            if (!e.isAlive()) {//is the entitie dead, then we add it to the dead entities array for removal.
                deadEntities.add(e);
            }
        }
        for (Entity e : deadEntities) {//removes all dead entities from the primary array.
            e.onDeath();
            entities.remove(e);
        }
//        System.out.println("Enemies: " + aliveEnemies);
        checkCollide();
        checkForNewWave();
        if (!isPlayerAlive()) {//if the player is dead he loses the scenario.
            scenarioLost = true;
        }
    }

    private void checkForNewWave() {//checks if its time to start the next wave. (if number of hostile entities is 0, next wave starts)
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
        //draws all the entities
        for (Entity e : entities) {
            e.draw(g);
        }
        
        //draws different text based on things like losing, winning or next wave starting.
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

    public void checkCollide() { //checks if 2 entites of different teams has collided, mostly used by projectiles
        for (Entity e1 : entities) {
            for (Entity e2 : entities) {
                if (e1.getTeam() != e2.getTeam()) {
                    e1.checkCollide(e2);
                }
            }
        }
    }

    public ArrayList<Entity> getEntities() { //returns the primary array of all the eneteties.
        return entities;
    }

}
