package com.cherkasov.games.socoban.view;


import com.cherkasov.games.socoban.controller.EventListener;
import com.cherkasov.games.socoban.model.Direction;
import com.cherkasov.games.socoban.model.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;


/**
 * Created by hawk on 19.02.2017.
 */
public class Field extends JPanel {
    private View view;
    private EventListener eventListener;

    public Field(View view) {

        this.view = view;
        KeyHandler keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void paint(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 500, 500);

        Set<GameObject> gameObjectSet = view.getGameObjects().getAll();

        for (GameObject gameObject : gameObjectSet) {
            gameObject.draw(g);
        }

    }


    public void setEventListener(EventListener eventListener) {

        this.eventListener = eventListener;
    }

    public class KeyHandler extends KeyAdapter {
//        private Field field;

        @Override
        public void keyPressed(KeyEvent e) {

//            super.keyPressed(e);

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    eventListener.move(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    eventListener.move(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    eventListener.move(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    eventListener.move(Direction.DOWN);
                    break;
                case KeyEvent.VK_R:
                    eventListener.restart();
                    break;
            }

        }
    }
}
