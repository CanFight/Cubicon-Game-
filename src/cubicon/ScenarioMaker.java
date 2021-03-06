package cubicon;

import cubicon.enemies.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/*
 * @author Niklas
 */
public class ScenarioMaker extends javax.swing.JFrame {

    private Scenario scenario; //the current scenario we are making/editing
    private int currentWave = -1, currentEnemy = -1; //tracks what wave and what enemy we are currently editing.
    private JFileChooser fileChooser;//used to select files that we want to save and load.
    
    public ScenarioMaker() { //the constructor of the class.
        fileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Scenario Files", "scenario", "scenario");
        fileChooser.setFileFilter(filter);
        
        initComponents();//this is the stuff that was made in the netbeans graphical interface for creating a swing based interface.
        setTitle("Scenario Maker");
        setResizable(false);
        setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);//if not done, closing the scenario maker would close the game. And thats bad.
        addWindowListener(new java.awt.event.WindowAdapter() {//instead of closing the entire program when closing the scenario maker we simply hide it.
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                setVisible(false);
            }
        });

        scenario = new Scenario();
        scenario.addWave();
        wavesList.removeAllItems();
        wavesList.addItem("Wave " + scenario.getNumberOfWaves());
        if (currentWave >= 0) {
            currentEnemy = scenario.getNumberOfEnemiesInWave(currentWave) - 1;
        } else {
            currentEnemy = -1;
        }
        setUpEnemyTypeList();
    }

    //below follows autogenerated code generated by netbeans. 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wavesList = new javax.swing.JComboBox();
        scenarioName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        addWaveButton = new javax.swing.JButton();
        removeWaveButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        waveEnemiesList = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        enemyTypes = new javax.swing.JComboBox();
        addEnemyButton = new javax.swing.JButton();
        removeEnemyButton = new javax.swing.JButton();
        enemyLocX = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        enemyLocY = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        waveMusicPath = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        wavesList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Wave 1" }));
        wavesList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wavesListActionPerformed(evt);
            }
        });

        scenarioName.setText("Just Another Scenario");
        scenarioName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scenarioNameActionPerformed(evt);
            }
        });

        jLabel2.setText("Scenario Name");

        jLabel3.setText("Waves");

        addWaveButton.setText("Add Wave");
        addWaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWaveButtonActionPerformed(evt);
            }
        });

        removeWaveButton.setText("Remove Wave");
        removeWaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeWaveButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Wave Enemies");

        waveEnemiesList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                waveEnemiesListActionPerformed(evt);
            }
        });

        jLabel4.setText("Enemy Types");

        addEnemyButton.setText("Add Enemy");
        addEnemyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEnemyButtonActionPerformed(evt);
            }
        });

        removeEnemyButton.setText("Remove Enemy");
        removeEnemyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeEnemyButtonActionPerformed(evt);
            }
        });

        enemyLocX.setText("0");
        enemyLocX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enemyLocXActionPerformed(evt);
            }
        });

        jLabel6.setText("Enemy Loc X");

        jLabel7.setText("Enemy Loc Y");

        enemyLocY.setText("0");
        enemyLocY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enemyLocYActionPerformed(evt);
            }
        });

        saveButton.setText("Save Scenario");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        loadButton.setText("Load Scenario");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Wave Music Path (Mp3)");

        waveMusicPath.setText("None");
        waveMusicPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                waveMusicPathActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(waveEnemiesList, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scenarioName, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(waveMusicPath, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(enemyLocX)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(enemyLocY, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(wavesList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(enemyTypes, 0, 148, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addWaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(removeWaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addEnemyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(removeEnemyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addComponent(loadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(loadButton))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(wavesList)
                        .addComponent(addWaveButton)
                        .addComponent(removeWaveButton))
                    .addComponent(scenarioName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(waveEnemiesList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enemyTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addEnemyButton)
                    .addComponent(removeEnemyButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(enemyLocX, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(waveMusicPath, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enemyLocY, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void wavesListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wavesListActionPerformed
        currentWave = wavesList.getSelectedIndex();//changes the current wave that is being edited to the one selected in the combobox
        if (currentWave >= 0) {
            currentEnemy = scenario.getNumberOfEnemiesInWave(currentWave) - 1;
        } else {
            currentEnemy = -1;
        }
        refreshEnemiesList();
    }//GEN-LAST:event_wavesListActionPerformed

    private void scenarioNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scenarioNameActionPerformed
        scenario.setName(scenarioName.getText());//changes the name of the scenario
    }//GEN-LAST:event_scenarioNameActionPerformed

    private void addWaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addWaveButtonActionPerformed
        scenario.addWave();//adds a new wave to the scenario
        wavesList.addItem("Wave " + scenario.getNumberOfWaves());
        currentWave++;
        wavesList.setSelectedIndex(currentWave);
    }//GEN-LAST:event_addWaveButtonActionPerformed

    private void removeWaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeWaveButtonActionPerformed
        if (currentWave >= 0) {//removes the currently selected wave from the scenario.
            scenario.removeWave(currentWave);
            currentWave--;
            refreshWavesList();
        }
    }//GEN-LAST:event_removeWaveButtonActionPerformed

    private void addEnemyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEnemyButtonActionPerformed
        if (currentWave >= 0) {//adds an enemy of the selected type to the scenario
            scenario.addEnemyToWave(currentWave, enemyTypes.getSelectedIndex());
            refreshEnemiesList();
            currentEnemy++;
            waveEnemiesList.setSelectedIndex(currentEnemy);
            refreshEnemySetUp();
        }
    }//GEN-LAST:event_addEnemyButtonActionPerformed

    private void removeEnemyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeEnemyButtonActionPerformed
        if (currentEnemy >= 0 && currentWave >= 0) {//removes the selected enemy from the scenario
            scenario.removeEnemy(currentWave, currentEnemy);
            waveEnemiesList.removeItemAt(currentEnemy);
            currentEnemy--;
        }
    }//GEN-LAST:event_removeEnemyButtonActionPerformed

    private void waveEnemiesListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_waveEnemiesListActionPerformed
        currentEnemy = waveEnemiesList.getSelectedIndex();//sets the currently selected enemy to the one selected in the combobox
        refreshEnemySetUp();
    }//GEN-LAST:event_waveEnemiesListActionPerformed

    private void enemyLocXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enemyLocXActionPerformed
        if (currentEnemy >= 0 && currentWave >= 0) {//changes the starting X position of the currently selected enemy
            try {
                int x = Integer.parseInt(enemyLocX.getText());
                scenario.setWaveEnemyLocX(currentWave, currentEnemy, x);
                refreshEnemiesList();
                System.out.println("X: " + x);
            } catch (Exception e) {
            }
        }

    }//GEN-LAST:event_enemyLocXActionPerformed

    private void enemyLocYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enemyLocYActionPerformed
        if (currentEnemy >= 0 && currentWave >= 0) {//changes the starting Y position of the currently selected enemy
            try {
                int y = Integer.parseInt(enemyLocY.getText());
                System.out.println("Y: " + y);
                scenario.setWaveEnemyLocY(currentWave, currentEnemy, y);
                refreshEnemiesList();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_enemyLocYActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        int returnVal = fileChooser.showSaveDialog(null);//saves the scenario to a path selected by the filechooser
        String path = "null";
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getName();
        }
        if(path != "null"){
            Scenario.saveScenario(path + ".scenario", scenario);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        int returnVal = fileChooser.showOpenDialog(null);//loads a scenario to be edited with the filepath being picked with the filechooser.
        String path = "null";
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getName();
        }
        Scenario s = Scenario.loadScenario(path);
        if (s != null) {
            scenario = s;
            scenarioName.setText(scenario.getName());
            refreshWavesList();
            System.out.println("scenario loaded");
        }
    }//GEN-LAST:event_loadButtonActionPerformed

    private void waveMusicPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_waveMusicPathActionPerformed
        if (currentWave >= 0) {//changes the music to be played during the selected wave.
            scenario.setWaveMusicPath(currentWave, waveMusicPath.getText());
        }
    }//GEN-LAST:event_waveMusicPathActionPerformed

    private void refreshWavesList() {//refreshes the combobox containing the waves
        wavesList.removeAllItems();
        for (int i = 0; i < scenario.getNumberOfWaves(); i++) {
            wavesList.addItem("Wave " + (i + 1));
        }
    }

    private void refreshEnemiesList() {//refreshes the combobox containing the enemies
        if (currentWave >= 0) {
            int i = currentEnemy;
            waveEnemiesList.removeAllItems();
            for (int j = 0; j < scenario.getNumberOfEnemiesInWave(currentWave); j++) {
                waveEnemiesList.addItem(MainLoop.enemyTypes.get(scenario.getEnemyType(currentWave, j)).getName()
                        + " (" + scenario.getWaveEnemyLocX(currentWave, j) + ", " + scenario.getWaveEnemyLocY(currentWave, j) + ")");
            }
            waveEnemiesList.setSelectedIndex(i);
            waveMusicPath.setText(scenario.getWaveMusicPath(currentWave));
        }

    }

    private void refreshEnemySetUp() {//refreshes the 2 textfields that contains the setup for enemy XY position
        if (currentEnemy >= 0) {
            enemyLocX.setText("" + scenario.getWaveEnemyLocX(currentWave, currentEnemy));
            enemyLocY.setText("" + scenario.getWaveEnemyLocY(currentWave, currentEnemy));
        }
    }

    private void setUpEnemyTypeList() {//adds all enemy types to the combo box where you pick what enemy you wish to add
        enemyTypes.removeAllItems();
        for (Enemy e : MainLoop.enemyTypes) {
            enemyTypes.addItem(e.getName());

        }
    }
    
    //more stuff generated by netbeans.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEnemyButton;
    private javax.swing.JButton addWaveButton;
    private javax.swing.JTextField enemyLocX;
    private javax.swing.JTextField enemyLocY;
    private javax.swing.JComboBox enemyTypes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton removeEnemyButton;
    private javax.swing.JButton removeWaveButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField scenarioName;
    private javax.swing.JComboBox waveEnemiesList;
    private javax.swing.JTextField waveMusicPath;
    private javax.swing.JComboBox wavesList;
    // End of variables declaration//GEN-END:variables
}
