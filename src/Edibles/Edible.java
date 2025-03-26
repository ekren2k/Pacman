package Edibles;

import Entities.Pacman;
import Main.Data;
import Main.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Edible extends JLabel {
    ImageIcon imageIcon, empty;
    BufferedImage image, empty_b;
    MainPanel mp;
    public Rectangle itemHitbox;
    public int x, y;
    protected int hitboxSize;
    Pacman pacman;
    public Edible(Pacman pacman, MainPanel mp) {
        this.mp = mp;
        this.pacman = pacman;
        hitboxSize = 6;
        x = 0;
        y = 0;
        loadAndScaleImages();
        setBounds(x, x, Data.TILE_SIZE, Data.TILE_SIZE);
        setIcon(imageIcon);
        itemHitbox = new Rectangle(x * Data.TILE_SIZE + (Data.TILE_SIZE / 2 - hitboxSize), y * Data.TILE_SIZE + (Data.TILE_SIZE / 2 - hitboxSize), hitboxSize, hitboxSize);
        setOpaque(false);
        setVisible(false);
    }
    protected void loadAndScaleImages() {

    }
    public void addBoostEffect() {

    }
    public void update() {
        checkPacman();
    }
    public void checkPacman() {
        if (pacman.pacmanHitbox.intersects(itemHitbox)) {
            setVisible(false);
            addBoostEffect();
            setLocation(0, 0);
            itemHitbox.setLocation(0,0);
        }
    }
}
