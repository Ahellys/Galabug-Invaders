package com.codegym.games.spaceinvaders;

import com.codegym.engine.cell.*;
import com.codegym.games.spaceinvaders.gameobjects.*;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersGame extends Game {
    public static final int WIDTH=64;
    public static final int HEIGHT=64;
    private List<Star> stars;
    private EnemyFleet enemyFleet;
    public static final int DIFFICULTY=5;
    private List<Bullet> enemyBullets;
    private PlayerShip playerShip;
    private boolean isGameStopped;
    private int animationsCount;
    private List<Bullet> playerBullets;
    private static final int PLAYER_BULLETS_MAX=1;
    private int score=0;

    @Override
    public void initialize() {
        setScreenSize(WIDTH,HEIGHT);
        createGame();
    }

    private void createGame() {
        createStars();
        score=0;
        enemyFleet=new EnemyFleet();
        enemyBullets=new ArrayList<Bullet>();
        playerShip=new PlayerShip();
        isGameStopped=false;
        animationsCount=0;
        playerBullets=new ArrayList<Bullet>();
        drawScene();
        setTurnTimer(40);
    }

    @Override
    public void onTurn(int step) {
        moveSpaceObjects();
        check();
        Bullet b =enemyFleet.fire(this);
        if (!(b==null)) enemyBullets.add(b);
        setScore(score);
        drawScene();
    }

    private void stopGameWithDelay(){
            animationsCount++;
            if (animationsCount>=10) stopGame(playerShip.isAlive);
    }

    private void stopGame(boolean isWin){
        isGameStopped=true;
        stopTurnTimer();
        if (isWin) showMessageDialog(Color.YELLOW,"GG",Color.GREEN,75);
        else showMessageDialog(Color.YELLOW,"You failed us...",Color.RED,75);
    }


    private void moveSpaceObjects(){
        enemyFleet.move();
        for (Bullet b:enemyBullets){
            b.move();
        }
        for (Bullet b:playerBullets){
            b.move();
        }
        playerShip.move();
    }

    private void drawScene() {
        drawField();
        enemyFleet.draw(this);
        playerShip.draw(this);
        for (Bullet b:enemyBullets){
            b.draw(this);
        }
        for (Bullet b:playerBullets){
            b.draw(this);
        }
    }

    private void drawField() {
        for (int y=0; y<HEIGHT;y++){
            for (int x=0;x<WIDTH;x++){
                setCellValueEx(x,y,Color.BLACK,"");
            }
        }
        for (Star s:stars){
            s.draw(this);
        }
    }

    private void check(){
        playerShip.checkHit(enemyBullets);
        score+=enemyFleet.checkHit(playerBullets);
        enemyFleet.deleteHiddenShips();
        if (enemyFleet.getBottomBorder()>=playerShip.y) playerShip.kill();
        if (enemyFleet.getShipCount()==0){
            playerShip.win();
            stopGameWithDelay();
        }
        removeDeadBullets();
        if (!playerShip.isAlive) stopGameWithDelay();
    }

    private void removeDeadBullets(){
        List<Bullet> temp=new ArrayList<>(enemyBullets);
        for (Bullet b:temp){
            if (!b.isAlive || b.y>=HEIGHT-1) enemyBullets.remove(b);
        }
        List<Bullet> copy= new ArrayList<>(playerBullets);
        for (Bullet b:copy){
            if (!b.isAlive || b.y+b.height<0) playerBullets.remove(b);
        }
    }

    @Override
    public void onKeyPress(Key key){
        if (isGameStopped && key==Key.SPACE){
            createGame();
            return;
        }
        switch (key){
            case LEFT:{
                playerShip.setDirection(Direction.LEFT);
                return;
            }
            case RIGHT:{
                playerShip.setDirection(Direction.RIGHT);
                return;
            }
            case SPACE:{
                Bullet pew=playerShip.fire();
                if (pew!=null && playerBullets.size()<PLAYER_BULLETS_MAX) playerBullets.add(pew) ;
            }
        }
    }

    @Override
    public void onKeyReleased(Key key){
       if (key==Key.LEFT && playerShip.getDirection()==Direction.LEFT) playerShip.setDirection(Direction.UP);
       else if (key==Key.RIGHT && playerShip.getDirection()==Direction.RIGHT) playerShip.setDirection(Direction.UP);
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value) {
        if (x>0 && x<WIDTH && y>0 && y<HEIGHT) super.setCellValueEx(x, y, cellColor, value);
    }

    private void createStars(){
        stars=new ArrayList<Star>();
        stars.add(new Star(12,21));
        stars.add(new Star(45,54));
        stars.add(new Star(35,53));
        stars.add(new Star(28,62));
        stars.add(new Star(19,19));
        stars.add(new Star(59,24));
        stars.add(new Star(3,30));
        stars.add(new Star(61,16));
    }
}
