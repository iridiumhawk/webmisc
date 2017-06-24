package com.cherkasov.games.socoban.model;

import java.awt.*;

/**
 * Created by hawk on 19.02.2017.
 */
public class Player extends CollisionObject implements Movable {
    @Override
    public void move(int x, int y) {

        setX(getX() + x);
        setY(getY() + y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.YELLOW);

        int leftX = getX() - getWidth() / 2;
        int leftY = getY() - getHeight() / 2;

        graphics.drawOval(leftX,leftY,getWidth(),getHeight());
        graphics.fillOval(leftX,leftY,getWidth(),getHeight());

    }

    public Player(int x, int y) {

        super(x, y);
    }
}
