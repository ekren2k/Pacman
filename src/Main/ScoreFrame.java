package Main;

import javax.swing.*;
import java.awt.*;

public class ScoreFrame extends JFrame {
    ScoreFrame(){
        setTitle("Score");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static class MainFrame extends JFrame {
        public MainFrame(){
            setTitle("Pacman.jar");
            setLayout(new BorderLayout());
            setResizable(false);
            MainPanel mp = new MainPanel();
            add(mp, BorderLayout.CENTER);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
}
