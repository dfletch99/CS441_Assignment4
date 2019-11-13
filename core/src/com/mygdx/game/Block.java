package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class Block {
    public static final String BLOCK_IMG_PATH = "block.png";

    float dy;
    Rectangle blockHitBox;
    boolean moving;
    int index;

    public Block(float width, float height, int i){
        blockHitBox = new Rectangle();
        blockHitBox.width = width;
        blockHitBox.height = height;
        moving = false;
        index = i;
        dy = 0;
    }

    public void stop(){
        dy = 0;
    }
    public void start(){
        dy = 5;
    }
    public void accelerate(){
        dy += 10;
    }
}
