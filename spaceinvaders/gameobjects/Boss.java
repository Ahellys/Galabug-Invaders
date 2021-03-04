package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;

public class Boss extends EnemyShip{
    private int frameCount=0;

    public Boss(double x, double y) {
        super(x, y);
        this.setAnimatedView(true,ShapeMatrix.BOSS_ANIMATION_FIRST,ShapeMatrix.BOSS_ANIMATION_SECOND);
        this.score=100;
    }

    @Override
    public Bullet fire() {
        if (!this.isAlive) return null;
        if (this.matrix==ShapeMatrix.BOSS_ANIMATION_FIRST){
            return new Bullet(this.x+6,y+this.height, Direction.DOWN);
        }
        else return new Bullet(this.x,this.y+height,Direction.DOWN);

    }

    @Override
    public void kill() {
        if (!this.isAlive)return;
        this.isAlive=false;
        setAnimatedView(false,ShapeMatrix.KILL_BOSS_ANIMATION_FIRST,ShapeMatrix.KILL_BOSS_ANIMATION_SECOND,ShapeMatrix.KILL_BOSS_ANIMATION_THIRD);
    }

    @Override
    public void nextFrame() {
        frameCount++;
        if (frameCount%10==0 || !this.isAlive) super.nextFrame();
    }
}
