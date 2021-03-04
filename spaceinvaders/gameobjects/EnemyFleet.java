package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.engine.cell.Game;
import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;
import com.codegym.games.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.List;

public class EnemyFleet {
    private static final int COLUMNS_COUNT=10;
    private static final int ROWS_COUNT=3;
    private static final int STEP= ShapeMatrix.ENEMY.length+1;
    private List<EnemyShip> ships;
    private Direction direction=Direction.RIGHT;

    public EnemyFleet(){
        createShips();
    }

    public void draw(Game game){
        for (EnemyShip s:ships){
            s.draw(game);
        }
    }

    private void createShips(){
        ships=new ArrayList<EnemyShip>();
        for (int x=0;x<COLUMNS_COUNT;x++){
            for (int y=0;y<ROWS_COUNT;y++){
                ships.add(new EnemyShip(x*STEP,y*STEP+12));
            }
        }
        EnemyShip Boss=new Boss(this.STEP*COLUMNS_COUNT/2-ShapeMatrix.BOSS_ANIMATION_FIRST.length/2-1,5);
        ships.add(Boss);
    }

    public void move(){
        if (ships.isEmpty())return;
        if (direction==Direction.LEFT && getLeftBorder()<0){
            direction=Direction.RIGHT;
            for (EnemyShip s:ships){
                s.move(Direction.DOWN,getSpeed());
            }
        }
        else if(direction==Direction.RIGHT && getRightBorder()>SpaceInvadersGame.WIDTH){
            direction=Direction.LEFT;
            for (EnemyShip s:ships){
                s.move(Direction.DOWN,getSpeed());
            }
        }
        else {
            for (EnemyShip s:ships){
                s.move(direction,getSpeed());
            }
        }

    }

    public double getBottomBorder(){
        double max=0;
        for (EnemyShip s:ships){
            if (s.y+s.height>max) max=s.y+s.height;
        }
        return max;
    }

    public int getShipCount(){
        return ships.size();
    }
    public void deleteHiddenShips(){
        List<EnemyShip> temp =new ArrayList<>(ships);
        for (EnemyShip s:temp){
            if (!s.isVisible()) ships.remove(s);
        }
    }

    public int checkHit(List<Bullet> bullets){
        if (bullets.isEmpty()) return 0;
        int scoreCount=0;
        for (EnemyShip s:ships){
            for (Bullet b:bullets){
                if (s.isCollision(b) && s.isAlive && b.isAlive){
                     s.kill();
                     b.kill();
                     scoreCount+=s.score;
                }
            }
        }
        return scoreCount;
    }

    private double getSpeed(){
        return (2.0<(3.0/ships.size())?2.0:(3.0/ships.size()));
    }

    private double getLeftBorder(){
        double min= SpaceInvadersGame.WIDTH;
        for (EnemyShip s:ships){
            if (s.x<min) min=s.x;
        }
        return min;
    }

    private double getRightBorder(){
        double max=0;
        for (EnemyShip s:ships){
            if (s.x+s.width>max) max=s.x+s.width;
        }
        return max;
    }

    public Bullet fire(Game game){
        if (ships.isEmpty()) return null;
        int rand = game.getRandomNumber(100/SpaceInvadersGame.DIFFICULTY);
        if (rand>0) return null;
        return ships.get(game.getRandomNumber(ships.size())).fire();
    }
}
