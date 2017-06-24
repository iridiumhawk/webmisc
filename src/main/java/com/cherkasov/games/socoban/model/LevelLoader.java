package com.cherkasov.games.socoban.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hawk on 23.02.2017.
 */
public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels) {

        this.levels = levels;
    }

    public GameObjects getLevel(int level)  {

        if (level > 60) {
            level = level % 60;
        }


       Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player  = new Player(0,0);

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(levels.toString()));

        String inputLine;

            while(true) {
                if (reader.readLine().equals("Maze: " + level)) {
                    break;
                }
            }
            reader.readLine();

            int x = Integer.parseInt(reader.readLine().split(" ")[2]);
            int y = Integer.parseInt(reader.readLine().split(" ")[2]);

            int fieldSellSize = Model.FIELD_SELL_SIZE;

            for (int i = 0; i < 3; i++) {
                reader.readLine();
            }

            for (int i = 0; i < y; i++) {
                inputLine = reader.readLine();

                for (int j = 0; j < x; j++) {

                    switch (inputLine.charAt(j)) {
                        case 'X' :
                            walls.add(new Wall(j * fieldSellSize + fieldSellSize / 2, i * fieldSellSize + fieldSellSize / 2));
                            break;
                        case '*' :
                            boxes.add(new Box(j * fieldSellSize + fieldSellSize / 2, i * fieldSellSize + fieldSellSize / 2));
                            break;
                        case '.' :
                            homes.add(new Home(j * fieldSellSize + fieldSellSize / 2, i * fieldSellSize + fieldSellSize / 2));
                            break;
                        case '&' :
                            boxes.add(new Box(j * fieldSellSize + fieldSellSize / 2, i * fieldSellSize + fieldSellSize / 2));
                            homes.add(new Home(j * fieldSellSize + fieldSellSize / 2, i * fieldSellSize + fieldSellSize / 2));
                            break;
                        case '@' :
                            player = new Player(j * fieldSellSize + fieldSellSize / 2, i * fieldSellSize + fieldSellSize / 2);
                            break;
                    }
                }
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new GameObjects(walls, boxes, homes, player);
    }

}
/*
	Осталось дописать реализацию загрузчика уровней.
16.1.	Открой файл levels.txt и внимательно изучи структуру файла. Символ ‘X’ –
означает стену, ‘*’ - ящик, ‘.’ – дом, ‘&’ – ящик который стоит в доме, а ‘@’ - игрока.
Всего в файле 60 уровней.
16.2.	Реализуй метод GameObjects getLevel(int level). Он должен:
16.2.1.	Вычитывать из файла данные уровня level. Уровни должны повторяться
циклически, если пользователь прошел все 60 уровней и попал на 61 уровень, то
ему нужно вернуть 1, вместо 62 уровня вернуть 2 и т.д.
16.2.2.	Создать все игровые объекты, описанные в указанном уровне. Координаты
каждого игрового объекта должны быть рассчитаны согласно следующей логике:
16.2.2.1.	Самый верхний левый объект (если такой есть) должен иметь
координаты x = x0 = FIELD_SELL_SIZE / 2 и y = y0 = FIELD_SELL_SIZE / 2.
16.2.2.2.	Объект, который находится на одну позицию правее от него должен
иметь координаты x = x0 + FIELD_SELL_SIZE и y = y0.
16.2.2.3.	Объект, который находится на одну позицию ниже от самого верхнего
левого должен иметь координаты x = x0 и y = y0 + FIELD_SELL_SIZE.
16.2.2.4.	Аналогично должны рассчитываться координаты любого объекта на
поле.


16.2.3.	Создать новое хранилище объектов GameObjects и поместить в него все
объекты.
16.2.4.	Вернуть созданное хранилище.

Игра должна быть полностью рабочей. Проверь, если есть какие-то проблемы исправь их.*/
/* одного игрока,
одного дома, одного ящика и нескольких стен. Так будет проще отладить работу игры.
Координаты каждого объекта должны быть не нулевые и кратны половине
FIELD_SELL_SIZE (центр каждого объекта должен быть в середине ячейки поля).*/