package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;

public class Bullet extends GameObject{
    private int dy;
    public boolean isAlive=true;

    public Bullet(double x, double y, Direction direction) {
        super(x, y);
        setMatrix(ShapeMatrix.BULLET);
        if (direction==Direction.UP) dy=-1;
        else dy=1;
    }

    public void kill(){
        this.isAlive=false;
    }

    public void move(){
        this.y+=dy;
    }
}
