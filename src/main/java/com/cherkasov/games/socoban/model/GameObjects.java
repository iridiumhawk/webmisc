package com.cherkasov.games.socoban.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hawk on 20.02.2017.
 */
public class GameObjects {

    Set<Wall> walls;
    Set<Box> boxes;
    Set<Home> homes;
    Player player;

    public Set<Wall> getWalls() {

        return walls;
    }

    public Set<Box> getBoxes() {

        return boxes;
    }

    public Set<Home> getHomes() {

        return homes;
    }

    public Player getPlayer() {

        return player;
    }

    public GameObjects(Set<Wall> walls, Set<Box> boxes, Set<Home> homes, Player player) {

        this.walls = walls;
        this.boxes = boxes;
        this.homes = homes;
        this.player = player;
    }

    public Set<GameObject> getAll(){
       Set<GameObject> all = new HashSet<>();
       all.addAll(walls);
       all.addAll(boxes);
       all.addAll(homes);
       all.add(player);
       return all;
    }
}
