package Entities;

import Main.Data;
import Main.MainPanel;
import Main.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Ghost extends Entity {
    public int defaultSpeed;
    int ghostX, ghostY;
    MainPanel mp;
    BufferedImage frightened_b, halffrightened_b, dead_b, empty_b;
    ImageIcon frightened, halffrightened, dead, empty;
    Direction direction = Direction.UP;
    Direction desiredDirection = Direction.UP;
    Rectangle ghostHitbox;
    public boolean isBlocked;
    public boolean isFreezed = false;
    Pacman pacman;
    GhostState ghostState = GhostState.NORMAL;
    public Ghost(MainPanel mp, Pacman pacman) {
        this.mp = mp;
        this.pacman = pacman;
        Thread boostGenerator = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    generateBoost();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            });
        boostGenerator.start();
    }



    public abstract void setDefaultValues();

    protected abstract void loadAndScaleImages();

    public void draw() {
        if (ghostState == GhostState.FRIGHTENED) {
            setIcon(frightened);
        }
        if (ghostState == GhostState.HALFFRIGHTENED) {
            setIcon(halffrightened);
        }
        if (ghostState == GhostState.DEAD) {
            setIcon(dead);
        }
        if (ghostState == GhostState.NORMAL) {
            changeSprite();
        }
        setBounds(x, y, Data.TILE_SIZE, Data.TILE_SIZE);


    }

    public void update() {
        ghostHitbox = new Rectangle(x, y, Data.TILE_SIZE, Data.TILE_SIZE);
        if (pacman.pacmanHitbox.intersects(ghostHitbox) && ghostState == GhostState.NORMAL) {
            if (pacman.lives == 0) {
                pacman.state = PacmanState.DEAD;
                System.out.println("PACMAN DEAD");
            }
            if (pacman.lives > 0) {
                Thread pacmanRespawn = new Thread(()->{
                    try {
                        pacman.state = PacmanState.NORMAL;
                        pacman.setDefaultValues();
                        pacman.isBlocked = true;
                        pacman.lives--;
                        Thread.sleep(1000);
                        pacman.isBlocked = false;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                pacmanRespawn.start();

            }
        }
        if (pacman.pacmanHitbox.intersects(ghostHitbox)) {
            if (ghostState == GhostState.FRIGHTENED || ghostState == GhostState.HALFFRIGHTENED) {

                Thread deathTimer = new Thread(() -> {
                    try {
                        isBlocked = true;
                        ghostState = GhostState.DEAD;
                        pacman.ghostDeathCount++;
                        MainPanel.points += pacman.ghostDeathCount*200;
                        Thread.sleep(2000);
                        respawn();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                deathTimer.start();

            }

        }
        if (x == -Data.TILE_SIZE) {
            isBlocked = true;
            x = Maze.width - 12;
            isBlocked = false;
        } else if (x == Maze.width) {
            isBlocked = true;
            x = -12;
            isBlocked = false;
        }
        if (!isBlocked && !isFreezed) {
            if (canMove(desiredDirection)) {
                if (directionIsNotOpposite(desiredDirection)) {
                    direction = desiredDirection;
                } else desiredDirection = pickDirection();
            }
            if (canMove(direction)) {
                switch (direction) {
                    case UP -> y -= speed;
                    case DOWN -> y += speed;
                    case LEFT -> x -= speed;
                    case RIGHT -> x += speed;
                }
            }
            desiredDirection = pickDirection();
        }
    }

    private boolean canMove(Direction nextDirection) {
        int[][] maze = Maze.maze;
        int newX = x;
        int newY = y;
        switch (nextDirection) {
            case UP -> newY -= speed;
            case DOWN -> newY += speed;
            case LEFT -> newX -= speed;
            case RIGHT -> newX += speed;
        }
        Rectangle ghostHitbox = new Rectangle(newX, newY, Data.TILE_SIZE, Data.TILE_SIZE);
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {

                if (maze[y][x] == 1) {
                    Rectangle wallHitbox = new Rectangle(x * Data.TILE_SIZE, y * Data.TILE_SIZE, Data.TILE_SIZE, Data.TILE_SIZE);
                    if (ghostHitbox.intersects(wallHitbox)) {
                        return false;
                    }
                }
                if (maze[y][x] == 6) {
                    Rectangle wallHitbox = new Rectangle(x * Data.TILE_SIZE, y * Data.TILE_SIZE, Data.TILE_SIZE, Data.TILE_SIZE / 4);
                    if (ghostHitbox.intersects(wallHitbox)) {
                        if (nextDirection == Direction.DOWN) return false;
                        if (nextDirection == Direction.UP) return true;
                    }
                }
            }
        }
        return true;
    }

    private Direction pickDirection() {
        Random rand = new Random();
        int rand_int = rand.nextInt(4);
        switch (rand_int) {
            case 0 -> {
                return Direction.UP;
            }
            case 1 -> {
                return Direction.DOWN;
            }
            case 2 -> {
                return Direction.LEFT;
            }
            case 3 -> {
                return Direction.RIGHT;
            }
        }
        return Direction.UP;
    }
    public void respawn() {
        Thread cooldown = new Thread(() -> {
            try {

                x = Data.TILE_SIZE*9;
                y = Data.TILE_SIZE*9;
                Thread.sleep(2000);
                ghostState = GhostState.NORMAL;
                isBlocked = false;

                desiredDirection = Direction.UP;
                direction = Direction.UP;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        cooldown.start();

    }
    private void changeSprite() {
        switch (direction) {
            case UP -> setIcon(up1);
            case DOWN -> setIcon(down1);
            case LEFT -> setIcon(left1);
            case RIGHT -> setIcon(right1);
        }
    }
    private void generateBoost() {
           Random rand = new Random();
           int num = rand.nextInt(5);
            switch (num) {
                case 0 -> {
                    mp.bag.setLocation(x, y);
                    mp.bag.itemHitbox.setLocation(x,y);
                    mp.bag.setVisible(true);
                }
                case 1 -> {
                    mp.freeze.setLocation(x, y);
                    mp.freeze.itemHitbox.setLocation(x,y);
                    mp.freeze.setVisible(true);
                }
                case 2 -> {
                    mp.cherry.setLocation(x, y);
                    mp.cherry.itemHitbox.setLocation(x,y);
                    mp.cherry.setVisible(true);
                }
                case 3 -> {
                    mp.potion.setLocation(x, y);
                    mp.potion.itemHitbox.setLocation(x,y);
                    mp.potion.setVisible(true);
                }
                case 4 -> {
                    mp.totem.setLocation(x, y);
                    mp.totem.itemHitbox.setLocation(x,y);
                    mp.totem.setVisible(true);
                }
            }
    }

    private boolean directionIsNotOpposite(Direction desiredDirection) {
        switch (desiredDirection) {
            case UP -> {
                if (direction == Direction.DOWN) return false;
            }
            case DOWN -> {
                if (direction == Direction.UP) return false;
            }
            case LEFT -> {
                if (direction == Direction.RIGHT) return false;
            }
            case RIGHT -> {
                if (direction == Direction.LEFT) return false;
            }
        }
        return true;
    }
}


