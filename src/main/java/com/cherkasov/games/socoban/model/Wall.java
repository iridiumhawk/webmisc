package com.cherkasov.games.socoban.model;

import java.awt.*;

/**
 * Created by hawk on 19.02.2017.
 */
public class Wall extends CollisionObject {
    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.PINK);

        int leftX = getX() - getWidth() / 2;
        int leftY = getY() - getHeight() / 2;

        graphics.drawRect(leftX,leftY,getWidth(),getHeight());
        graphics.fillRect(leftX,leftY,getWidth(),getHeight());
    }

    public Wall(int x, int y) {

        super(x, y);
    }
}
