package Edibles;

import Entities.Pacman;
import Main.Data;
import Main.MainPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class Potion extends Edible {
    public Potion(Pacman pacman, MainPanel mp) {
        super(pacman, mp);
    }
    @Override
    protected void loadAndScaleImages() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/sprites/potion.png"));
            empty_b = ImageIO.read(getClass().getResourceAsStream("/tiles/empty.png"));
        } catch (IOException e) {
            System.out.println("potion image load error");
        }
        imageIcon = new ImageIcon(image.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        empty = new ImageIcon(empty_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
    }

    @Override
    public void addBoostEffect() {
        pacman.speed = 4;
    }
}
