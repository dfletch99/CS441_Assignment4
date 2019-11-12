package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class Block {
    public static final String BLOCK_IMG_PATH = "block.png";

    float dx;
    Rectangle blockHitBox;
    boolean visible;
    int index;

    public Block(int width, int height, int i){
        blockHitBox = new Rectangle();
        blockHitBox.width = width;
        blockHitBox.height = height;
        visible = false;
        index = i;
        dx = 5;
    }

    public void stop(){
        dx = 0;
    }
}
