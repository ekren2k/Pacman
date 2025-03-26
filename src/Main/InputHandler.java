package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Entities.Direction;

public class InputHandler implements KeyListener {

    public Direction inputDirection = Direction.RIGHT;
    public Direction bufferedInputDirection = Direction.RIGHT;

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W -> {
                inputDirection = Direction.UP;
                bufferedInputDirection = Direction.UP;
            }

            case KeyEvent.VK_S -> {
                inputDirection = Direction.DOWN;
                bufferedInputDirection = Direction.DOWN;
            }

            case KeyEvent.VK_A -> {
                inputDirection = Direction.LEFT;
                bufferedInputDirection = Direction.LEFT;
            }
            case KeyEvent.VK_D -> {
                inputDirection = Direction.RIGHT;
                bufferedInputDirection = Direction.RIGHT;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}