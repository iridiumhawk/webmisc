package com.cherkasov.games.socoban.controller;


import com.cherkasov.games.socoban.model.Direction;

/**
 * Created by hawk on 21.02.2017.
 */
public interface EventListener {
    void move(Direction direction);

    void restart();

    void startNextLevel();

    void levelCompleted(int level);
}
