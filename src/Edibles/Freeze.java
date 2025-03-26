package Edibles;

import Entities.Ghost;
import Entities.Pacman;
import Main.Data;
import Main.MainPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class Freeze extends Edible {
    public Freeze(Pacman pacman, MainPanel mp) {
        super(pacman, mp);
    }
    @Override
    protected void loadAndScaleImages() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/sprites/freeze.png"));
            empty_b = ImageIO.read(getClass().getResourceAsStream("/tiles/empty.png"));
        } catch (IOException e) {
            System.out.println("freeze image load error");
        }
        imageIcon = new ImageIcon(image.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        empty = new ImageIcon(empty_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
    }
    @Override
    public void addBoostEffect() {
        Thread freezeTimer = new Thread(() -> {

            try {
                for (Ghost g : mp.ghostArray) {
                    g.isFreezed = true;
                }
                Thread.sleep(5000);
                for (Ghost g : mp.ghostArray) {
                    g.isFreezed = false;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        freezeTimer.start();

    }
}
