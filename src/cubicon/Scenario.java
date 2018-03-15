package cubicon;

import cubicon.enemies.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * @author Niklas

 An object ment to be saved as a file so it can be read by the program and used to detirmin what enemies to spawn.

 */
public class Scenario implements Serializable {

    static final long serialVersionUID = -687991492884005033L;
    
    private ArrayList<Wave> waves;
    private String name = "Just Another Scenario";
    private int currentWave = 0;
    
    public Scenario() {
        waves = new ArrayList();
    }

    public void startScenario() {
        currentWave = -1;
    }

    public void startNextWave(ArrayList<Entity> entityList, GameHandler gh) {
        currentWave++;
        if (currentWave < waves.size()) {
            for (EnemySavable e : waves.get(currentWave).enemies) {
                Enemy t = MainLoop.getEnemyByEnemySavable(e.x, e.y, e.type, gh);
                if (t != null) {
                    entityList.add(t);
                }
            }
        }
    }

    public boolean isScenarioOver() {
        return currentWave >= waves.size() - 1;
    }

    public void startWaveMusic(int w) {
        if (w >= 0 && w < waves.size() && currentWave >= 0) {
            if (!waves.get(currentWave).musicPath.equals("")) {
                MainLoop.soundHandler.stopMusic();
                MainLoop.soundHandler.playMusicJL(waves.get(currentWave).musicPath);
            }
        }
    }

    public void addWave() {
        waves.add(new Wave());
    }

    public void removeWave(int i) {
        waves.remove(i);
    }

    public int getNumberOfWaves() {
        return waves.size();
    }

    public void addEnemyToWave(int i, int type) {
        if (i < waves.size()) {
            waves.get(i).addEnemy(0, 0, type);
        }
    }

    public void getWaveEnemies(int i) {
        if (i < waves.size()) {
//            return waves.get(i).getEnemies();
        }
//        return null;
    }

    public void setWaveEnemyLocX(int w, int e, int x) {
        if (w < waves.size()) {
            if (e < waves.get(w).enemies.size()) {
                waves.get(w).enemies.get(e).x = x;
            }
        }
    }

    public void setWaveEnemyLocY(int w, int e, int y) {
        if (w < waves.size()) {
            if (e < waves.get(w).enemies.size()) {
                waves.get(w).enemies.get(e).y = y;
            }
        }
    }

    public int getWaveEnemyLocX(int w, int e) {
        if (w < waves.size()) {
            if (e < waves.get(w).enemies.size()) {
                return waves.get(w).enemies.get(e).x;
            }
        }
        return 0;
    }

    public int getWaveEnemyLocY(int w, int e) {
        if (w < waves.size()) {
            if (e < waves.get(w).enemies.size()) {
                return waves.get(w).enemies.get(e).y;
            }
        }
        return 0;
    }

    public void removeEnemy(int w, int e) {
        if (w < waves.size()) {
            if (e < waves.get(w).enemies.size()) {
                waves.get(w).enemies.remove(e);
            }
        }
    }

    public int getEnemyType(int w, int e) {
        if (w < waves.size()) {
            if (e < waves.get(w).enemies.size()) {
                return waves.get(w).enemies.get(e).type;
            }
        }
        return -1;
    }

    public int getNumberOfEnemiesInWave(int w) {
        if (w < waves.size()) {
            return waves.get(w).enemies.size();
        }
        return 0;
    }

    public void setWaveMusicPath(int w, String path) {
        if (w < waves.size()) {
            waves.get(w).musicPath = path;
        }
    }

    public String getWaveMusicPath(int w) {
        if (w < waves.size()) {
            return waves.get(w).musicPath;
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public int getCurrentWave() {
        return currentWave;
    }

    private class Wave implements Serializable {

        private ArrayList<EnemySavable> enemies;
        private String musicPath;

        public Wave() {
            enemies = new ArrayList();
            musicPath = "";
        }

        public void addEnemy(int x, int y, int type) {
            enemies.add(new EnemySavable(x, y, type));
        }

    }

    private class EnemySavable implements Serializable {

        private int x, y, type;

        public EnemySavable(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }

    public static void saveScenario(String path, Scenario s) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(s);
            System.out.println("saved");
        } catch (Exception e) {
            System.err.println("failed saving scenario");
            e.printStackTrace();
        }
    }

    public static Scenario loadScenario(String path) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            return (Scenario) in.readObject();
        } catch (Exception e) {
            System.err.println("failed loading scenario");
            e.printStackTrace();
            return null;
        }

    }
    
}
