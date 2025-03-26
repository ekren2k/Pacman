package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Maze extends JPanel {
    BufferedImage wall_b;
    BufferedImage empty_b;
    BufferedImage smallCoin_b;
    BufferedImage bigCoin_b;
    BufferedImage upgrade_b;
    BufferedImage ghostWall_b;
    ImageIcon wall;
    ImageIcon empty;
    ImageIcon smallCoin;
    ImageIcon bigCoin;
    ImageIcon ghostWall;
    ImageIcon upgrade;

    public static int[][] maze;
    public static int width;
    public int height;
    public int rows, columns;
    JLabel[][] mazeLabels;
    public Maze() {
        maze = Data.maze;
        rows = Data.rows;
        columns = Data.columns;
        width = rows * Data.TILE_SIZE;
        height = columns * Data.TILE_SIZE;
        mazeLabels = new JLabel[columns][rows];
        setLayout(new GridLayout(rows, columns));
        setSize(width, height);
        fillWithLabels(columns, rows);
        loadImageIconsAndScaleThem();
        draw();
        setVisible(true);
    }
    public void update() {

    }
    private void fillWithLabels(int columns, int rows) {
        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {
                mazeLabels[y][x] = new JLabel();
                add(mazeLabels[y][x]);
            }
        }
    }
    public void removeSmallCoin() {

    }
    public void draw() {
        setBackground(Color.BLACK);
        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {
                updateLabelIcons(x, y);
            }
        }

    }
    public void updateLabelIcons(int x, int y) {
                switch (maze[y][x]) {
                    case 0 -> {
                        mazeLabels[y][x].setIcon(smallCoin);
                    }

                    case 1 -> {
                        mazeLabels[y][x].setIcon(wall);

                    }
                    case 2 -> {
                        mazeLabels[y][x].setIcon(bigCoin);
                    }
                    case 5 -> {
                        mazeLabels[y][x].setIcon(empty);
                    }
                    case 6 -> {
                        mazeLabels[y][x].setIcon(ghostWall);
                    }

                }
        }

    private void loadImageIconsAndScaleThem() {
        try {
            wall_b = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            empty_b = ImageIO.read(getClass().getResourceAsStream("/tiles/empty.png"));
            smallCoin_b = ImageIO.read(getClass().getResourceAsStream("/sprites/smallcoin.png"));
            bigCoin_b = ImageIO.read(getClass().getResourceAsStream("/sprites/bigcoin.png"));
            ghostWall_b = ImageIO.read(getClass().getResourceAsStream("/tiles/ghostwall.png"));
        }
        catch (IOException e) {
            System.out.println("Error loading images");
            e.printStackTrace();
        }
        wall = new ImageIcon(wall_b.getScaledInstance(Data.TILE_SIZE,Data.TILE_SIZE,1));
        empty = new ImageIcon(empty_b.getScaledInstance(Data.TILE_SIZE,Data.TILE_SIZE,1));
        smallCoin = new ImageIcon(smallCoin_b.getScaledInstance(Data.TILE_SIZE,Data.TILE_SIZE,1));
        bigCoin = new ImageIcon(bigCoin_b.getScaledInstance(Data.TILE_SIZE,Data.TILE_SIZE,1));
        ghostWall = new ImageIcon(ghostWall_b.getScaledInstance(Data.TILE_SIZE,Data.TILE_SIZE,1));

    }
}
