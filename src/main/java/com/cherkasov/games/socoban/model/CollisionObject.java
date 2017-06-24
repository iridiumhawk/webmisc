package com.cherkasov.games.socoban.model;


import static com.cherkasov.games.socoban.model.Model.FIELD_SELL_SIZE;

/**
 * Created by hawk on 19.02.2017.
 */
public abstract class CollisionObject extends GameObject {
    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {

        switch (direction) {
            case UP:
                if ((this.getY() - FIELD_SELL_SIZE == gameObject.getY()) && (this.getX() == gameObject.getX())) {
                    return true;
                }
                break;
            case DOWN:
                if ((this.getY() + FIELD_SELL_SIZE == gameObject.getY()) && (this.getX() == gameObject.getX())) {
                    return true;
                }
                break;
            case LEFT:
                if ((this.getY()  == gameObject.getY()) && (this.getX() - FIELD_SELL_SIZE == gameObject.getX())) {
                    return true;
                }
                break;
            case RIGHT:
                if ((this.getY()  == gameObject.getY()) && (this.getX() + FIELD_SELL_SIZE == gameObject.getX())) {
                    return true;
                }
                break;
        }

        return false;
    }
}
