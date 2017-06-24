package com.cherkasov.games.socoban.controller;


import com.cherkasov.games.socoban.model.Direction;
import com.cherkasov.games.socoban.model.GameObjects;
import com.cherkasov.games.socoban.model.Model;
import com.cherkasov.games.socoban.view.View;

/**
 * Created by hawk on 06.02.2017.
 */
public class Controller implements EventListener {
    private View view;
    private Model model;

    public Controller() {

        model = new Model();
        model.restart();
        model.setEventListener(this);
        view = new View(this);
        view.init();
        view.setEventListener(this);
    }

    public static void main(String[] args) {

        Controller controller = new Controller();
    }

    public GameObjects getGameObjects() {

        return model.getGameObjects();
    }


    @Override
    public void move(Direction direction) {

        model.move(direction);
        view.update();
    }

    @Override
    public void restart() {

        model.restart();
        view.update();
    }

    @Override
    public void startNextLevel() {

        model.startNextLevel();
        view.update();
    }

    @Override
    public void levelCompleted(int level) {

        view.completed(level);
    }
}
