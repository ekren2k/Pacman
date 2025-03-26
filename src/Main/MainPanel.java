package Main;

import javax.swing.*;
import java.awt.*;


import Edibles.*;

import Entities.*;




public class MainPanel extends JPanel implements Runnable {


    public static int points = 0;
    Thread mainThread;
    InputHandler inputHandler = new InputHandler();
    public Maze maze;
    Pacman pacman;
    Pink pink;
    Red red;
    Blue blue;
    Orange orange;
    public Bag bag;
    public Freeze freeze;
    public Totem totem;
    public Cherry cherry;
    public Potion potion;
    public Ghost[] ghostArray;
    public Edible[] edibleArray;
    public BorderLayout layout;
    public boolean running = true;
;


    public MainPanel(){

        addKeyListener(inputHandler);
        pacman = new Pacman(this, inputHandler);
        bag = new Bag(pacman, this);
        freeze = new Freeze(pacman, this);
        totem = new Totem(pacman, this);
        potion = new Potion(pacman, this);
        cherry = new Cherry(pacman, this);
        edibleArray = new Edible[]{bag, freeze, totem, potion, cherry};
        for (Edible edible : edibleArray) {
            add(edible, layout.CENTER);
        }

        pink = new Pink(this, pacman);
        red = new Red(this, pacman);
        blue = new Blue(this, pacman);
        orange = new Orange(this, pacman);
        ghostArray = new Ghost[]{pink,red,blue,orange};
        for (Ghost ghost : ghostArray) {
            add(ghost, layout.CENTER);
        }
        maze = new Maze();


        layout = new BorderLayout();
        setLayout(layout);
        add(pacman, layout.CENTER);
        add(maze, layout.CENTER);



        startMainThread();


        setFocusable(true);
        setVisible(true);
    }
    private void startMainThread() {
        mainThread = new Thread(this);
        mainThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1_000_000_000 / Data.FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        int frames = 0;
        long lastCheck = System.nanoTime();
        while (running) {
            if (pacman.state == PacmanState.DEAD) {
                    running = false;
                    pacman.playDeathAnimation();
                    ScoreFrame scoreFrame = new ScoreFrame();
            } else {
                Toolkit.getDefaultToolkit().sync();
                update();
                render();

                try {
                    frames++;
                    double remainingTime = nextDrawTime - System.nanoTime();
                    remainingTime = remainingTime / 1_000_000;
                    if (remainingTime < 0) {
                        remainingTime = 0;
                    }
                    Thread.sleep((long) remainingTime);
                    nextDrawTime += drawInterval;
                    if (System.nanoTime() - lastCheck >= 1_000_000_000) {
                        lastCheck = System.nanoTime();
                        System.out.println("FPS: " + frames + " Points: " + points + " Pacman lives: " + pacman.lives);
                        frames = 0;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public void update() {
        pacman.update();
        for (Ghost g : ghostArray) g.update();
        for (Edible e : edibleArray) e.update();

    }

    public void render() {
        maze.draw();
        pacman.draw();
        for (Ghost g : ghostArray) g.draw();



    }
}


