package Edibles;

import Entities.PacmanState;
import Entities.Pacman;
import Main.Data;
import Main.MainPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class Totem extends Edible {
    public Totem(Pacman pacman, MainPanel mp) {
        super(pacman, mp);
    }
    @Override
    protected void loadAndScaleImages() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/sprites/totem.png"));
            empty_b = ImageIO.read(getClass().getResourceAsStream("/tiles/empty.png"));
        } catch (IOException e) {
            System.out.println("totem image load error");
        }
        imageIcon = new ImageIcon(image.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        empty = new ImageIcon(empty_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
    }
    @Override
    public void addBoostEffect() {
        Thread wreckingballCounter = new Thread(() -> {
           pacman.state = PacmanState.WRECKINGBALL;
           try {
               Thread.sleep(5000);
               pacman.state = PacmanState.NORMAL;
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        });
        wreckingballCounter.start();
    }
}
