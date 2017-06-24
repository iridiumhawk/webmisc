package com.cherkasov.games.socoban.model;

import java.awt.*;

/**
 * Created by hawk on 19.02.2017.
 */
public class Box  extends CollisionObject implements Movable{
    @Override
    public void move(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        int leftX = getX() - getWidth() / 2;
        int leftY = getY() - getHeight() / 2;

        graphics.drawRect(leftX, leftY, getWidth(), getHeight());
        graphics.fillRect(leftX, leftY, getWidth(), getHeight());

    }

    public Box(int x, int y) {

        super(x, y);
    }
}
