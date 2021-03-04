package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;

public class EnemyShip extends Ship {
    public int score=15;

    public EnemyShip(double x, double y) {
        super(x, y);
        setStaticView(ShapeMatrix.ENEMY);
    }

    public void move(Direction direction, double speed){
        switch (direction){
            case RIGHT:{
                this.x+=speed;
                break;
            }
            case LEFT:{
                this.x-=speed;
                break;
            }
            case DOWN:this.y+=2;
        }
    }

    @Override
    public void kill() {
        if (!this.isAlive)return;
            this.isAlive=false;
            this.setAnimatedView(false,ShapeMatrix.KILL_ENEMY_ANIMATION_FIRST,ShapeMatrix.KILL_ENEMY_ANIMATION_SECOND,ShapeMatrix.KILL_ENEMY_ANIMATION_THIRD);
    }

    @Override
    public Bullet fire() {
        return new Bullet(this.x+1,this.y+this.height,Direction.DOWN);
    }
}
