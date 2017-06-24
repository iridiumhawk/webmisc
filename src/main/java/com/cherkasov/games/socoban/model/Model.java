package com.cherkasov.games.socoban.model;


import com.cherkasov.games.socoban.controller.EventListener;

import java.nio.file.Paths;

/**
 * Created by hawk on 06.02.2017.
 */
public class Model {
    public static int FIELD_SELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get("src\\com\\javarush\\test\\level34\\lesson15\\big01\\res\\levels.txt"));

    public void setEventListener(EventListener eventListener) {

        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {

        return gameObjects;
    }

    public void restartLevel(int level) {

        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {

        restartLevel(currentLevel);

    }

    public void startNextLevel() {

        currentLevel++;
        restart();
    }

    public void move(Direction direction) {

        Player player = gameObjects.getPlayer();

        if (checkWallCollision(player, direction)) {
            return;
        }

        if (checkBoxCollision(direction)) {
            return;
        }

        switch (direction) {
            case UP:
                player.move(0, -FIELD_SELL_SIZE);
                break;
            case DOWN:
                player.move(0, FIELD_SELL_SIZE);
                break;
            case LEFT:
                player.move(-FIELD_SELL_SIZE, 0);
                break;
            case RIGHT:
                player.move(FIELD_SELL_SIZE, 0);
                break;
        }

        checkCompletion();
    }
    /*15.4.	void move(Direction direction). Метод должен:
15.4.1.	Проверить столкновение со стеной (метод checkWallCollision()), если есть
столкновение – выйти из метода.
15.4.2.	Проверить столкновение с ящиками (метод checkBoxCollision()), если есть
столкновение – выйти из метода.
15.4.3.	Передвинуть игрока в направлении direction.
15.4.4.	Проверить завершен ли уровень.*/

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {

        for (Wall wall : gameObjects.getWalls()) {
            if (gameObject.isCollision(wall, direction)) {
                return true;
            }
        }
        return false;

    }

    public boolean checkBoxCollision(Direction direction) {

        Player player = gameObjects.getPlayer();

        for (Box box : gameObjects.getBoxes()) {
            if (player.isCollision(box, direction)) {
                if (checkWallCollision(box, direction)) {
                    return true;
                }
                for (Box boxSecond : gameObjects.getBoxes()) {
                    if (box.isCollision(boxSecond, direction)) {
                        return true;
                    }
                }

                switch (direction) {
                    case UP:
                        box.move(0, -FIELD_SELL_SIZE);
                        break;
                    case DOWN:
                        box.move(0, FIELD_SELL_SIZE);
                        break;
                    case LEFT:
                        box.move(-FIELD_SELL_SIZE, 0);
                        break;
                    case RIGHT:
                        box.move(FIELD_SELL_SIZE, 0);
                        break;
                }
            }
        }

        return false;

    }

    public void checkCompletion() {

        int counter = 0;
        for (Home home : gameObjects.getHomes()) {
            for (Box box : gameObjects.getBoxes()) {
                if (home.getX() == box.getX() && home.getY() == box.getY()) {
                    counter++;
                    break;
                }
            }
        }
        if (counter == gameObjects.getHomes().size()) {
            eventListener.levelCompleted(currentLevel);
        }

    }

}/*	Пришло время реализовать метод модели, отвечающий за движение move(), но для
начала реализуем вспомогательные методы. Добавь в класс модели методы:
15.1.	boolean checkWallCollision(CollisionObject gameObject, Direction direction). Этот
метод проверяет столкновение со стеной. Он должен вернуть true, если при движении
объекта gameObject в направлении direction произойдет столкновение со стеной,
иначе false. Для определения столкновения используй метод isCollision() у игрового
объекта.

15.2.	boolean checkBoxCollision(Direction direction). Этот метод проверяет
столкновение с ящиками. Метод должен:
15.2.1.	Вернуть true, если игрок не может быть сдвинут в направлении direction (там
находится: или ящик, за которым стена; или ящик за которым еще один ящик).
15.2.2.	Вернуть false, если игрок может быть сдвинут в направлении direction (там
находится: или свободная ячейка; или дом; или ящик, за которым свободная
ячейка или дом). При это, если на пути есть ящик, который может быть сдвинут, то
необходимо переместить этот ящик на новые координаты. Обрати внимание, что
все объекты перемещаются на фиксированное значение FIELD_SELL_SIZE, не
зависящее от размеров объекта, которые используются для его отрисовки.
Подсказка: для определения столкновений используй методы isCollision() игровых
объектов и метод checkWallCollision() модели.
15.3.	void checkCompletion(). Этот метод должен проверить пройден ли уровень (на
всех ли домах стоят ящики, их координаты должны совпадать). Если условие
выполнено, то проинформировать слушателя событий, что текущий уровень завершен.

Запусти программу и проверь, что игрока можно перемещать, он может перемещать
ящики, стены преграждают движение, а при перемещении всех ящиков в дома выводится
сообщение о прохождении уровня.*/

/*11.1.	Поле GameObjects gameObjects. Оно будет хранить наши игровые объекты.
11.2.	Поле отвечающее за текущий уровень int currentLevel. Проинициализируй его
значением 1.
11.3.	 Поле отвечающие за загрузчик уровней LevelLoader levelLoader.
Проинициализируй его с помощью файла levels.txt из папки res.
11.4.	Метод GameObjects getGameObjects(), он должен возвращать все игровые
объекты.
11.5.	Метод restartLevel(int level), он должен получать новые игровые объекты для
указанного уровня у загрузчика уровня levelLoader и сохранять их в поле gameObjects.
11.6.	Метод restart(), он должен перезапускать текущий уровнь, вызывая restartLevel
с нужным параметром.
11.7.	Метод startNextLevel(), он должен увеличивать значение переменной
currentLevel, хранящей номер текущего уровня и выполнять перезапуск нового уровня.
*/