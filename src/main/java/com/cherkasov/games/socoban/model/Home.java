package com.cherkasov.games.socoban.model;

import java.awt.*;

/**
 * Created by hawk on 19.02.2017.
 */
public class Home extends GameObject{
    public Home(int x, int y) {

        super(x, y);
        this.width = 2;
        this.height = 2;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.RED);
        int leftX = getX() - getWidth() / 2;
        int leftY = getY() - getHeight() / 2;

        graphics.drawRect(leftX, leftY, getWidth(), getHeight());
    }
}
