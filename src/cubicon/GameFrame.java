package cubicon;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
    
    //contains a short configuration of the jframe.
    
    public GameFrame(){
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setVisible(true);
    }
    
    public void refreshSize(){
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
}
