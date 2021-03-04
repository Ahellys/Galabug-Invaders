package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship extends GameObject {

    public boolean isAlive=true;
    private List<int[][]> frames;
    private int frameIndex;
    private boolean loopAnimation=false;


    public Ship(double x, double y) {
        super(x, y);
    }

    public void setStaticView(int[][] viewFrame){
        this.setMatrix(viewFrame);
        this.frames=new ArrayList<int[][]>();
        this.frames.add(viewFrame);
        this.frameIndex=0;
    }

    public void setAnimatedView(boolean isLoopAnimation ,int[][]... viewFrames){
         this.setMatrix(viewFrames[0]);
         this.frames= Arrays.asList(viewFrames);
         this.frameIndex=0;
         this.loopAnimation=isLoopAnimation;
    }

    public void nextFrame(){
        this.frameIndex++;
        if (this.frameIndex>=this.frames.size()){
            if (!this.loopAnimation)return;
            this.frameIndex=0;
        }
        //setMatrix(this.frames.get(this.frameIndex));
        this.matrix=this.frames.get(this.frameIndex);
    }

    public boolean isVisible(){
        return !(!this.isAlive && frameIndex>=frames.size());
    }

    @Override
    public void draw(Game game) {
        super.draw(game);
        nextFrame();
    }

    public Bullet fire(){
        return null;
    }


    public void kill(){
        this.isAlive=false;
    }


}
