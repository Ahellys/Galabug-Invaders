package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;
import com.codegym.games.spaceinvaders.SpaceInvadersGame;

import java.util.List;

public class PlayerShip extends Ship {
    private Direction direction=Direction.UP;


    public PlayerShip() {
        super(SpaceInvadersGame.WIDTH/2.0, SpaceInvadersGame.HEIGHT- ShapeMatrix.PLAYER.length-1);
        setStaticView(ShapeMatrix.PLAYER);
    }

    public void win(){
        this.setStaticView(ShapeMatrix.WIN_PLAYER);
    }

    @Override
    public void kill() {
        if (!this.isAlive)return;
        this.isAlive=false;
        this.setAnimatedView(false,ShapeMatrix.KILL_PLAYER_ANIMATION_FIRST,ShapeMatrix.KILL_PLAYER_ANIMATION_SECOND,ShapeMatrix.KILL_PLAYER_ANIMATION_THIRD, ShapeMatrix.DEAD_PLAYER);
    }

    public void checkHit(List<Bullet> bullets){
        if (this.isAlive) {
            for (Bullet b : bullets) {
                if (b.isAlive) {
                    if (this.isCollision(b)) {
                        this.kill();
                        b.kill();
                    }
                }
            }
        }
    }

    @Override
    public Bullet fire() {
        if (!this.isAlive)return null;
        return new Bullet(this.x+2,this.y-ShapeMatrix.BULLET.length,Direction.UP);
    }

    public void move(){
        if (!isAlive) return;
        if (direction==Direction.LEFT) this.x--;
        else if (direction==Direction.RIGHT) this.x++;
        if (this.x<0) this.x=0;
        else if (this.x+this.width>SpaceInvadersGame.WIDTH) this.x=SpaceInvadersGame.WIDTH-this.width;
    }

    public void setDirection(Direction newDirection){
        switch (newDirection) {
            case DOWN: return;
            case LEFT:{
                this.direction=Direction.LEFT;
                return;
            }
            case RIGHT:{
                this.direction=Direction.RIGHT;
                return;
            }
            case UP: this.direction=Direction.UP;
        }
    }

    public Direction getDirection(){
        return this.direction;
    }

}
