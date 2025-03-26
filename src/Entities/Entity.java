package Entities;

import javax.swing.*;
import java.awt.image.BufferedImage;

public abstract class Entity extends JLabel {
    public int x, y;
    Direction direction;

    public int speed;


    BufferedImage right1_b, right2_b, up1_b, up2_b, left1_b, left2_b, down1_b, down2_b;
    ImageIcon up1, down1, left1, right1, up2, down2, left2, right2;

/*    public int spriteUpdateCounter = 0;
    public int spriteUpdateRate;
    public SpriteChoose spriteChoose;*/
}
