package Entities;

import Main.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pacman extends Entity {
    public int lives = 3;
    Direction direction;
    MainPanel mp;
    InputHandler ih;
    boolean isBlocked;
    public Rectangle pacmanHitbox;
    public int ghostDeathCount = 0;
    public int speed;

    BufferedImage pacmanfull_b, wreckingball_b;
    ImageIcon pacmanfull;
    public ImageIcon wreckingball;
    public PacmanState state;
    SpriteChoose currentSprite;

    public Pacman(MainPanel mp, InputHandler ih) {
        this.mp = mp;
        this.ih = ih;
        loadAndScaleImages();
        setDefaultValues();
        setSize(Data.TILE_SIZE, Data.TILE_SIZE);
        pacmanHitbox = new Rectangle(x, y, Data.TILE_SIZE, Data.TILE_SIZE);
        setOpaque(false);

    }

    private void animate() {
        Thread animate = new Thread(()-> {
            while (true) {
                try {
                    if (currentSprite == SpriteChoose.FIRST) {
                        Thread.sleep(250);
                        currentSprite = SpriteChoose.SECOND;
                    }
                    if (currentSprite == SpriteChoose.SECOND) {
                        Thread.sleep(250);
                        currentSprite = SpriteChoose.FIRST;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animate.start();

    }

    public void setDefaultValues() {
        speed = 3;
        x = Data.TILE_SIZE;
        y = Data.TILE_SIZE;
        direction = Direction.RIGHT;
        ih.inputDirection = Direction.RIGHT;
        setSize(Data.TILE_SIZE, Data.TILE_SIZE);
        setIcon(right1);
        setLocation(x, y);
        isBlocked = false;
        currentSprite = SpriteChoose.FIRST;
        state = PacmanState.NORMAL;
        animate();

    }

    public void draw() {
        if (state == PacmanState.NORMAL) {
            changeSprite();
        }
        if (state == PacmanState.WRECKINGBALL) {
            setIcon(wreckingball);
        }
        setBounds(x, y, Data.TILE_SIZE, Data.TILE_SIZE);
        pacmanHitbox.setBounds(x, y, Data.TILE_SIZE, Data.TILE_SIZE);


    }

    private void loadAndScaleImages() {
        try {
            right1_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pacmanright1.png"));
            right2_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pacmanright2.png"));
            left1_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pacmanleft1.png"));
            left2_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pacmanleft2.png"));
            up1_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pacmanup1.png"));
            up2_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pacmanup2.png"));
            down1_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pacmandown1.png"));
            down2_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pacmandown2.png"));
            pacmanfull_b = ImageIO.read(getClass().getResourceAsStream("/sprites/pacmanfull.png"));
            wreckingball_b = ImageIO.read(getClass().getResourceAsStream("/sprites/wreckingball.png"));

        } catch (IOException e) {
            System.out.println("pacman image load failed :(");
        }
        up1 = new ImageIcon(up1_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        up2 = new ImageIcon(up2_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        down1 = new ImageIcon(down1_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        down2 = new ImageIcon(down2_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        right1 = new ImageIcon(right1_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        right2 = new ImageIcon(right2_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        left1 = new ImageIcon(left1_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        left2 = new ImageIcon(left2_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        pacmanfull = new ImageIcon(pacmanfull_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
        wreckingball = new ImageIcon(wreckingball_b.getScaledInstance(Data.TILE_SIZE, Data.TILE_SIZE, 1));
    }

    private void changeSprite() {
        switch (direction) {
            case UP -> {
                if (currentSprite == SpriteChoose.FIRST) setIcon(up1);
                if (currentSprite == SpriteChoose.SECOND) setIcon(up2);
            }
            case DOWN -> {
                if (currentSprite == SpriteChoose.FIRST) setIcon(down1);
                if (currentSprite == SpriteChoose.SECOND) setIcon(down2);
            }
            case LEFT -> {
                if (currentSprite == SpriteChoose.FIRST) setIcon(left1);
                if (currentSprite == SpriteChoose.SECOND) setIcon(left2);
            }
            case RIGHT -> {
                if (currentSprite == SpriteChoose.FIRST) setIcon(right1);
                if (currentSprite == SpriteChoose.SECOND) setIcon(right2);
            }
        }
    }

    public void update() {
        if (x == -Data.TILE_SIZE) {
            isBlocked = true;
            x = Maze.width - 12;
            isBlocked = false;
        } else if (x == Maze.width) {
            isBlocked = true;
            x = -12;
            isBlocked = false;
        }


        if (!isBlocked) {
            if (canMove(ih.inputDirection)) {
                direction = ih.inputDirection;
            }
            if (canMove(direction)) {
                switch (direction) {
                    case UP -> y -= speed;
                    case DOWN -> y += speed;
                    case LEFT -> x -= speed;
                    case RIGHT -> x += speed;
                }
            }
        }
    }


    private boolean canMove(Direction nextDirection) {
        int[][] maze = Maze.maze;
        int newX = x;
        int newY = y;
        int hitboxSize = 6;


        switch (nextDirection) {
            case UP -> newY -= speed;
            case DOWN -> newY += speed;
            case LEFT -> newX -= speed;
            case RIGHT -> newX += speed;
        }
        Rectangle pacmanHitbox = new Rectangle(newX, newY, Data.TILE_SIZE, Data.TILE_SIZE);
        if (state == PacmanState.WRECKINGBALL) {
            pacmanHitbox = new Rectangle(newX, newY, Data.TILE_SIZE-speed/2, Data.TILE_SIZE-speed/2);
        }
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {

                if (maze[y][x] == 1 || maze[y][x] == 6) {
                    Rectangle wallHitbox = new Rectangle(x * Data.TILE_SIZE, y * Data.TILE_SIZE, Data.TILE_SIZE, Data.TILE_SIZE);
                    if (pacmanHitbox.intersects(wallHitbox)) {
                        return false;
                    }
                }
                if (maze[y][x] == 0) {
                    Rectangle itemHitbox = new Rectangle(x * Data.TILE_SIZE + (Data.TILE_SIZE / 2 - hitboxSize), y * Data.TILE_SIZE + (Data.TILE_SIZE / 2 - hitboxSize), hitboxSize, hitboxSize);
                    if (pacmanHitbox.intersects(itemHitbox)) {
                        maze[y][x] = 5;
                        MainPanel.points += 10;
                    }
                }
                if (maze[y][x] == 2) {
                    Rectangle itemHitbox = new Rectangle(x * Data.TILE_SIZE + (Data.TILE_SIZE / 2 - hitboxSize), y * Data.TILE_SIZE + (Data.TILE_SIZE / 2 - hitboxSize), hitboxSize, hitboxSize);
                    if (pacmanHitbox.intersects(itemHitbox)) {
                        maze[y][x] = 5;
                        MainPanel.points += 50;
                        pacmanAteBigCoin();
                    }
                }
                if (maze[y][x] == 3) {

                }

            }
        }
        return true;
    }

    private void pacmanAteBigCoin() {

        Thread countingThread = new Thread(() -> {
            try {
                ghostDeathCount = 0;
                for (Ghost g : mp.ghostArray) {
                    if (g.ghostState != GhostState.DEAD) {
                        g.ghostState = GhostState.FRIGHTENED;
                    }
                }
                Thread.sleep(4000);
                for (int i = 0; i < 6; i++) {
                    for (Ghost g : mp.ghostArray) {
                        if (g.ghostState != GhostState.DEAD) g.ghostState = GhostState.HALFFRIGHTENED;
                    }
                    Thread.sleep(250);
                    for (Ghost g : mp.ghostArray) {
                        if (g.ghostState != GhostState.DEAD) g.ghostState = GhostState.FRIGHTENED;
                    }
                    Thread.sleep(250);
                }
                for (Ghost g : mp.ghostArray) {
                    if (g.ghostState != GhostState.DEAD) g.ghostState = GhostState.NORMAL;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        countingThread.start();
    }

    private void checkCoins() {
        int[][] maze = Maze.maze;
        int hitboxSize = 6;
        Rectangle pacmanHitbox = new Rectangle(x, y, Data.TILE_SIZE, Data.TILE_SIZE);

        int arrX = x / Data.TILE_SIZE;
        int arrY = y / Data.TILE_SIZE;
        if (maze[arrY][arrX] == 0) {
            maze[arrY][arrX] = 5;
            mp.points += 10;
        }
        if (maze[arrY][arrX] == 2) {
            maze[arrY][arrX] = 5;
            mp.points += 50;
        }

    }
    public void playDeathAnimation() {
        Thread deathAnim = new Thread(() -> {
           try {
               setIcon(pacmanfull);
               Thread.sleep(500);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        });
        deathAnim.start();
    }
}