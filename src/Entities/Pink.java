
package Entities;

import Main.Data;
import Main.MainPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class Pink extends Ghost {
    MainPanel mp;

    Direction direction, desiredDirection;
    boolean isBlocked;
    public Pink(MainPanel mp, Pacman pacman) {
        super(mp, pacman);
        defaultSpeed = 4;
        this.pacman = pacman;
        this.mp = mp;
        setOpaque(false);
        loadAndScaleImages();
        setDefaultValues();
        setBounds(x, y, Data.TILE_SIZE, Data.TILE_SIZE);
    }
    @Override
    public void setDefaultValues() {
        speed = defaultSpeed;
        x = Data.TILE_SIZE*9;
        y = Data.TILE_SIZE*9;
        setIcon(right1);
        ghostState = GhostState.NORMAL;
        direction = Direction.UP;
        desiredDirection = direction.UP;
        ghostX = x/Data.TILE_SIZE;
        ghostY = y/Data.TILE_SIZE;
        isBlocked = false;

    }
    @Override
    protected void loadAndScaleImages(){
        try {
            right1_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pinkright1.png"));
            left1_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pinkleft1.png"));
            down1_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pinkdown1.png"));
            up1_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pinkup1.png"));
            frightened_b = ImageIO.read(getClass().getResourceAsStream("/sprites/ghostfrightened.png"));
            halffrightened_b = ImageIO.read(getClass().getResourceAsStream("/sprites/halffrightened.png"));
            dead_b = ImageIO.read(getClass().getResourceAsStream("/sprites/dead.png"));
            empty_b = ImageIO.read(getClass().getResourceAsStream("/tiles/empty.png"));
        }
        catch(IOException e){
            System.out.println("pink image not found ;(");
        }
        up1 = new ImageIcon(up1_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        down1 = new ImageIcon(down1_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        right1 = new ImageIcon(right1_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        left1 = new ImageIcon(left1_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        frightened = new ImageIcon(frightened_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        halffrightened = new ImageIcon(halffrightened_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        dead = new ImageIcon(dead_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        empty = new ImageIcon(empty_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
    }
}

