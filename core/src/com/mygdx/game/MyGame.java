package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class MyGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture block;
	private BitmapFont font;
	private Matrix4 fontMatrix;
	private int score;
	private OrthographicCamera camera;
	private float h, w;

	private Block[] blocks;
	private Block[] menuBlocks;
	private int blockSpeed;
	private int numberOfBlocks;
	private float blockWidth = 250, blockHeight = 350;
	private final float startBlockWidth = 250, startBlockHeight = 350;

	private boolean mainMenu, mainGame, leaderBoard, gameOver, gameOverAnimation;
	private String swap;

	private float textX, textY;

	private int animationTimer = 0;

	private String[] names;
	private int[] scores;
	private int leaderBoardPage;

	@Override
	public void create () {
		score = 0;
		leaderBoardPage = 0;

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		numberOfBlocks = 0;
		blocks = new Block[1000];
		menuBlocks = new Block[3];
		blockSpeed = 5;
		initializeGame();

		menuBlocks[0] = new Block(blockWidth + 150, blockHeight + 200, 0, 0);
		menuBlocks[0].blockHitBox.x = (Gdx.graphics.getWidth() / 2) - 400;
		menuBlocks[0].blockHitBox.y = (Gdx.graphics.getHeight() / 2) - 275;

		menuBlocks[1] = new Block(blockWidth + 150, blockHeight + 200, 0, 0);
		menuBlocks[1].blockHitBox.x = menuBlocks[0].blockHitBox.x + 400;
		menuBlocks[1].blockHitBox.y = menuBlocks[0].blockHitBox.y;

		batch = new SpriteBatch();

		block = new Texture(Block.BLOCK_IMG_PATH);
		font = new BitmapFont();
		font.setColor(0,0,0,1);
		font.getData().setScale(5);
		fontMatrix = new Matrix4();

		textX = 30;
		textY = -30;

		mainMenu = true;
		mainGame = false;
		leaderBoard = false;
		gameOver = false;
		gameOverAnimation = false;
		swap = "mm";

		names = new String[100];
		scores = new int[100];

		addDefaultScores();
	}

	private void addDefaultScores() {
		names[0] = "David";
		names[1] = "Joe";
		names[2] = "Kyle";
		names[3] = "John";
		names[4] = "Will";

		scores[0] = 40;
		scores[1] = 35;
		scores[2] = 30;
		scores[3] = 25;
		scores[4] = 20;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(mainMenu){
			resetCamera();
			batch.begin();
			System.out.println("block:" + blocks[0].blockHitBox.y + "\nh:" + h);
            fontMatrix.setToRotation(0,0,1, 90);
            batch.setTransformMatrix(fontMatrix);
            font.getData().setScale(8);
            font.draw(batch, "Stack the Blocks!", textX + 70, textY - 200);
			font.getData().setScale(6);
			font.draw(batch, "It's harder than you think", textX + 50, textY - 300);
            fontMatrix.setToRotation(0,0,0, 0);
            batch.setTransformMatrix(fontMatrix);

			batch.draw(block, blocks[0].blockHitBox.x, blocks[0].blockHitBox.y, blocks[0].blockHitBox.width, blocks[0].blockHitBox.height);
			batch.draw(block, menuBlocks[0].blockHitBox.x, menuBlocks[0].blockHitBox.y, menuBlocks[0].blockHitBox.width, menuBlocks[0].blockHitBox.height);
			batch.draw(block, menuBlocks[1].blockHitBox.x, menuBlocks[1].blockHitBox.y, menuBlocks[1].blockHitBox.width, menuBlocks[1].blockHitBox.height);

            fontMatrix.setToRotation(0,0,1, 90);
            batch.setTransformMatrix(fontMatrix);
			font.setColor(1,1,1,1);
			font.draw(batch, "Play!", 450, -775);
			font.getData().setScale(4.6f);
			font.draw(batch, "Leaderboard", 365, -1190);
            font.getData().setScale(5);
            fontMatrix.setToRotation(0,0,0, 0);
            batch.setTransformMatrix(fontMatrix);
			font.setColor(0,0,0,1);
			batch.end();

			blocks[0].blockHitBox.y += blocks[0].dy;
			if (blocks[0].blockHitBox.y > h - blocks[0].blockHitBox.height || blocks[0].blockHitBox.y < 0) {
				blocks[0].dy *= -1;
			}

			if(Gdx.input.justTouched()){
				//check play button pressed
			    if(Gdx.input.getX() >= menuBlocks[0].blockHitBox.x && Gdx.input.getX() <= menuBlocks[0].blockHitBox.x + menuBlocks[0].blockHitBox.width){
			        if(Gdx.input.getY() >= menuBlocks[0].blockHitBox.y && Gdx.input.getY() <= menuBlocks[0].blockHitBox.y + menuBlocks[0].blockHitBox.height){
			            swap = "mg";
                    }
                }
			    //check leaderboard button is pressed
				if(Gdx.input.getX() >= menuBlocks[1].blockHitBox.x && Gdx.input.getX() <= menuBlocks[1].blockHitBox.x + menuBlocks[1].blockHitBox.width){
					if(Gdx.input.getY() >= menuBlocks[1].blockHitBox.y && Gdx.input.getY() <= menuBlocks[1].blockHitBox.y + menuBlocks[1].blockHitBox.height){
						leaderBoardPage = 0;
						swap = "lb";
					}
				}
            }
		}
		if(mainGame) {
			batch.begin();
			for (int i = 0; i <= numberOfBlocks; i++) {
					batch.draw(block, blocks[i].blockHitBox.x, blocks[i].blockHitBox.y, blocks[i].blockHitBox.width, blocks[i].blockHitBox.height);
			}
			//print score (text must be rotated because game isn't landscape)
			fontMatrix.setToRotation(0,0,1, 90);
			batch.setTransformMatrix(fontMatrix);
			font.draw(batch, "Score: " + score, textX, textY);
			fontMatrix.setToRotation(0,0,0, 0);
			batch.setTransformMatrix(fontMatrix);

			if (Gdx.input.justTouched()) {
				blocks[numberOfBlocks].stop();
				if (numberOfBlocks != 0)
					checkAlignment(blocks[numberOfBlocks], blocks[numberOfBlocks - 1]);
				numberOfBlocks++;
				blocks[numberOfBlocks] = new Block(blockWidth, blockHeight, numberOfBlocks, blockSpeed);
				blocks[numberOfBlocks].blockHitBox.x = blocks[numberOfBlocks-1].blockHitBox.x - blockWidth + 50;
				blocks[numberOfBlocks].blockHitBox.y = (float)(Math.random() * (Gdx.graphics.getHeight()-blockHeight));
			}
			batch.end();
			blocks[numberOfBlocks].blockHitBox.y += blocks[numberOfBlocks].dy;
			if (blocks[numberOfBlocks].blockHitBox.y > h - blocks[numberOfBlocks].blockHitBox.height || blocks[numberOfBlocks].blockHitBox.y < 0) {
				blocks[numberOfBlocks].dy *= -1;
			}
		}
		if(leaderBoard){
			batch.begin();
			drawLeaderBoard(leaderBoardPage);
			if(Gdx.input.justTouched()){
				//Next page
				if(Gdx.input.getX() >= 1750 && Gdx.input.getX() <= 2000 && Gdx.input.getY() >= 0 && Gdx.input.getY() <= 375 && leaderBoardPage < 6){
					leaderBoardPage++;
				}
				//Previous Page
				if(Gdx.input.getX() >= 1750 && Gdx.input.getX() <= 2000 && Gdx.input.getY() >= 715 && Gdx.input.getY() <= 1090 && leaderBoardPage > 0){
					leaderBoardPage--;
				}
				//Back to Main Menu
				if(Gdx.input.getX() >= 0 && Gdx.input.getX() <= 200 && Gdx.input.getY() >= 790 && Gdx.input.getY() <= 1090){
				    initializeGame();
					swap = "mm";
				}
			}
			batch.end();
		}
		if(gameOverAnimation){
			batch.begin();
			for (int i = 0; i <= numberOfBlocks-1; i++) {
				batch.draw(block, blocks[i].blockHitBox.x, blocks[i].blockHitBox.y, blocks[i].blockHitBox.width, blocks[i].blockHitBox.height);
			}
			if(camera.position.x <= 1014 && animationTimer != 125 && score >= 9){
				camera.translate(8,0,0);
				camera.update();
				//move score to always stay in the corner of the screen
				textY -= 8;
			}
			else if(animationTimer != 125){
			    animationTimer++;
            }
			else{
				swap = "go";
				animationTimer = 0;
			}
			//print score (text must be rotated because game isn't landscape)
			fontMatrix.setToRotation(0,0,1, 90);
			batch.setTransformMatrix(fontMatrix);
			font.draw(batch, "Score: " + score, textX, textY);
			fontMatrix.setToRotation(0,0,0, 0);
			batch.setTransformMatrix(fontMatrix);
			batch.setProjectionMatrix(camera.combined);
			batch.end();
		}
		if(gameOver){
		    resetCamera();
		    batch.begin();
            fontMatrix.setToRotation(0,0,1, 90);
            batch.setTransformMatrix(fontMatrix);
            font.getData().setScale(8);
            font.draw(batch, "Game Over!", textX + 180, textY - 300);
            font.getData().setScale(6);
            font.draw(batch, "Your score is " + score, textX + 180, textY - 400);
            fontMatrix.setToRotation(0,0,0, 0);
            batch.setTransformMatrix(fontMatrix);

			batch.draw(block, menuBlocks[0].blockHitBox.x, menuBlocks[0].blockHitBox.y, menuBlocks[0].blockHitBox.width, menuBlocks[0].blockHitBox.height);
			batch.draw(block, menuBlocks[1].blockHitBox.x, menuBlocks[1].blockHitBox.y, menuBlocks[1].blockHitBox.width, menuBlocks[1].blockHitBox.height);

			fontMatrix.setToRotation(0,0,1, 90);
            batch.setTransformMatrix(fontMatrix);
            font.setColor(1,1,1,1);
            font.draw(batch, "Try Again!", 350, -775);
            font.getData().setScale(4.6f);
            font.draw(batch, "Leaderboard", 365, -1190);
            font.getData().setScale(5);
            fontMatrix.setToRotation(0,0,0, 0);
            batch.setTransformMatrix(fontMatrix);
            font.setColor(0,0,0,1);
            batch.end();

			if(Gdx.input.justTouched()) {
				//check play button pressed
				if (Gdx.input.getX() >= menuBlocks[0].blockHitBox.x && Gdx.input.getX() <= menuBlocks[0].blockHitBox.x + menuBlocks[0].blockHitBox.width) {
					if (Gdx.input.getY() >= menuBlocks[0].blockHitBox.y && Gdx.input.getY() <= menuBlocks[0].blockHitBox.y + menuBlocks[0].blockHitBox.height) {
						updateLeaderBoard(score, "Prof. Madden");
						initializeGame();
						swap = "mg";
					}
				}
				//check leaderboard button pressed
				if(Gdx.input.getX() >= menuBlocks[1].blockHitBox.x && Gdx.input.getX() <= menuBlocks[1].blockHitBox.x + menuBlocks[1].blockHitBox.width){
					if(Gdx.input.getY() >= menuBlocks[1].blockHitBox.y && Gdx.input.getY() <= menuBlocks[1].blockHitBox.y + menuBlocks[1].blockHitBox.height){
						updateLeaderBoard(score, "Prof. Madden");
						leaderBoardPage = 0;
						swap = "lb";
					}
				}
			}
		}
		swap(swap);
	}

	private void drawLeaderBoard(int page) {
		//Prev Page Button
		batch.draw(block, 1750, 0, 250, 375);
		//Next Page Button
		batch.draw(block, 1750, 715, 250, 375);
		//Back to Main Menu Button
		batch.draw(block, 0, 0, 200, 320);
		//rotate text to draw
		fontMatrix.setToRotation(0,0,1, 90);
		batch.setTransformMatrix(fontMatrix);
		//draw text on buttons
		font.setColor(1,1,1,1);
		font.getData().setScale(4.5f);
		font.draw(batch, "Previous", 75, -1850);
		font.draw(batch, "Back", 90, -70);
        font.getData().setScale(6f);
		font.draw(batch, "Next", 815, -1845);

		//draw page [page+1] of the leaderboard
		font.setColor(0,0,0,1);
		font.getData().setScale(8f);
		font.draw(batch, "Leader Board", 150, -200);
		font.getData().setScale(6f);
		font.draw(batch, "Name:", 100, -350);
		font.draw(batch, "Score:", 700, -350);
		font.draw(batch, "Page " + (page + 1), 400, -1850);

		int index = 0;
		if(page != 6) {
			for (int i = page * 15; i < (page * 15) + 15; i++) {
				if (names[i] != null) {
					font.draw(batch, names[i], 100, -380 - ((index + 1) * 80));
					font.draw(batch, Integer.toString(scores[i]), 700, -380 - ((index + 1) * 80));
				} else {
					font.draw(batch, "N/A", 100, -380 - ((index + 1) * 80));
					font.draw(batch, "N/A", 700, -380 - ((index + 1) * 80));
				}
				index++;
			}
		}
		else{
			for(int i = 90; i < 100; i++){
				if (names[i] != null) {
					font.draw(batch, names[i], 100, -380 - ((index + 1) * 80));
					font.draw(batch, Integer.toString(scores[i]), 700, -380 - ((index + 1) * 80));
				} else {
					font.draw(batch, "N/A", 100, -380 - ((index + 1) * 80));
					font.draw(batch, "N/A", 700, -380 - ((index + 1) * 80));
				}
				index++;
			}
		}

		//rotate back to normal
		fontMatrix.setToRotation(0,0,0, 0);
		batch.setTransformMatrix(fontMatrix);
	}

	private void updateLeaderBoard(int score, String text) {
		for(int i = 0; i < scores.length; i++){
			if(scores[i] <= score){
				shiftLeaderBoardDown(i);
				scores[i] = score;
				names[i] = text;
				return;
			}
		}
	}

	private void shiftLeaderBoardDown(int index) {
		for(int i = scores.length-1; i > index; i--){
			scores[i] = scores[i-1];
			names[i] = names[i-1];
		}
	}

	private void swap(String swap) {
		switch(swap){
			case "mg":
				mainGame = true;
				mainMenu = false;
				leaderBoard = false;
				gameOver = false;
				gameOverAnimation = false;
				break;
			case "mm":
				mainGame = false;
				mainMenu = true;
				leaderBoard = false;
				gameOver = false;
				gameOverAnimation = false;
				break;
			case "lb":
				mainGame = false;
				mainMenu = false;
				leaderBoard = true;
				gameOver = false;
				gameOverAnimation = false;
				break;
			case "go":
				mainGame = false;
				mainMenu = false;
				leaderBoard = false;
				gameOver = true;
				gameOverAnimation = false;
				break;
			case "goa":
				mainGame = false;
				mainMenu = false;
				leaderBoard = false;
				gameOver = false;
				gameOverAnimation = true;
				break;
			default:
				mainGame = false;
				mainMenu = false;
				leaderBoard = false;
				gameOver = false;
				gameOverAnimation = false;
				break;
		}
	}

	public void checkAlignment(Block topBlock, Block bottomBlock){
		if(topBlock.blockHitBox.y > bottomBlock.blockHitBox.y + bottomBlock.blockHitBox.height ||
			topBlock.blockHitBox.y + topBlock.blockHitBox.height < bottomBlock.blockHitBox.y) {
			swap = "goa";
			return;
		}

		if(topBlock.blockHitBox.y > bottomBlock.blockHitBox.y){
			topBlock.blockHitBox.height -= (topBlock.blockHitBox.y - bottomBlock.blockHitBox.y);
			blockHeight = topBlock.blockHitBox.height;
		}
		else{
			topBlock.blockHitBox.height -= (bottomBlock.blockHitBox.y - topBlock.blockHitBox.y);
			topBlock.blockHitBox.y = bottomBlock.blockHitBox.y;
			blockHeight = topBlock.blockHitBox.height;
		}
		score++;
		if(score%10 == 9){
			//move camera upwards
			camera.translate(-1950,0,0);
			camera.update();
			//move score to always stay in the corner of the screen
			textY += 1950;
			batch.setProjectionMatrix(camera.combined);
			blockSpeed += 5;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		block.dispose();
		font.dispose();
	}

	@Override
	public void resize(int w, int h){
	    camera.update();
		batch.setProjectionMatrix(camera.combined);
		this.w = w;
		this.h = h;
	}

	public void resetCamera(){
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
	}

	public void initializeGame(){
		score = 0;
		numberOfBlocks = 0;
		blockSpeed = 5;
		blockWidth = startBlockWidth;
		blockHeight = startBlockHeight;
		blocks[0] = new Block(blockWidth, blockHeight, 0, blockSpeed);
		blocks[0].blockHitBox.x = Gdx.graphics.getWidth() - blockWidth + 25;
		blocks[0].blockHitBox.y = (float)(Math.random() * (Gdx.graphics.getHeight()-blockHeight));
	}
}
