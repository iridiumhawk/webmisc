package com.cherkasov.games.socoban.view;
/*
*
1.	Использовать картинки для отображения объектов.
2.	Сделать редактор уровней.
3.	Добавить рейтинги
4.	Переделать игру под мобильную платформу, опубликовать в магазине
*/

import com.cherkasov.games.socoban.controller.Controller;
import com.cherkasov.games.socoban.controller.EventListener;
import com.cherkasov.games.socoban.model.GameObjects;

import javax.swing.*;

public class View extends JFrame {
    private Controller controller;
    private Field field;

    public View(Controller controller) {

        this.controller = controller;
    }

    public void init() {

        field = new Field(this);
        add(field);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Сокобан");
        setVisible(true);

    }

    public void setEventListener(EventListener eventListener) {

        field.setEventListener(eventListener);
    }

    public void update() {

        field.repaint();
    }

    public GameObjects getGameObjects() {

        return controller.getGameObjects();
    }

    public void completed(int level) {

        update();
        JOptionPane.showMessageDialog(null, level + "Completed", "Level", JOptionPane.INFORMATION_MESSAGE);
        controller.startNextLevel();
    }
}

