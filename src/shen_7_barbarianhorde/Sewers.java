/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shen_7_barbarianhorde;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author ShadowX
 */
public class Sewers extends BasicGameState {
    static Player playerguy2;
    static Music music2;
    static Sound hurt;
    static FinalStairs finalstair1, finalstair2;
    int preHitTime = 0;
    int newHitTime = 0;
    
    public ArrayList<FinalStairs> stairs = new ArrayList();
    
    private static TiledMap sewerMap;
    private static AppGameContainer app;
    private static Camera camera;
    public static int counter = 0;

    private static final int SIZE = 32;

    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 750;
    public Sewers(int xSize, int ySize) {
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gc.setTargetFrameRate(60);
        gc.setShowFPS(false);
        
        playerguy2 = new Player(663,64);
	sewerMap = new TiledMap("res/sewers.tmx");
        music2 = new Music("res/Fade.ogg");
        hurt = new Sound("res/ouch.ogg");
	camera = new Camera(gc, sewerMap);

	Blocked2.blocked2 = new boolean[sewerMap.getWidth()][sewerMap.getHeight()];
	for (int xAxis = 0; xAxis < sewerMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < sewerMap.getHeight(); yAxis++) {
		int tileID = sewerMap.getTileId(xAxis, yAxis, 1);
		String value = sewerMap.getTileProperty(tileID,"blocked", "false");
		if ("true".equals(value)) {
			Blocked2.blocked2[xAxis][yAxis] = true;
		}
            }
	}
    	
        Trapped.trapped = new boolean[sewerMap.getWidth()][sewerMap.getHeight()];
	for (int xAxis = 0; xAxis < sewerMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < sewerMap.getHeight(); yAxis++) {
		int tileID = sewerMap.getTileId(xAxis, yAxis, 2);
		String value = sewerMap.getTileProperty(tileID,"trapped", "false");
		if ("true".equals(value)) {
			Trapped.trapped[xAxis][yAxis] = true;
		}
            }
	}
        
        music2.loop();
        
        finalstair1 = new FinalStairs(2976,3104);
        finalstair2 = new FinalStairs(2976,3136);
        stairs.add(finalstair1);
        stairs.add(finalstair2);
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	camera.centerOn((int) playerguy2.x, (int) playerguy2.y);
	camera.drawMap();
	camera.translateGraphics();

	playerguy2.sprite.draw((int) playerguy2.x, (int) playerguy2.y);
	g.drawString("Health: " + (int)(playerguy2.health), camera.cameraX + 10, camera.cameraY + 10);
        
        for (FinalStairs s : stairs) {
            if (s.isvisible) {
                s.currentImage.draw(s.x, s.y);
            }
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        counter += delta;
	Input input = gc.getInput();
	float fdelta = delta * playerguy2.speed;
	playerguy2.setpdelta(fdelta);

	double rightlimit = (sewerMap.getWidth() * SIZE) - (SIZE * 0.75);
	float projectedright = playerguy2.x + fdelta + SIZE;
	boolean cangoright = projectedright < rightlimit;

        if (input.isKeyPressed(Input.KEY_M)) {
            if (music2.playing()) {
                music2.pause();
            } else {
                music2.loop();
            }
        } else if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
            playerguy2.sprite = playerguy2.up;
            float fdsc = (float) (fdelta - (SIZE * .15));
            if (!(isBlocked(playerguy2.x, playerguy2.y - fdelta) || isBlocked((float) (playerguy2.x + SIZE + 1.5), playerguy2.y - fdelta))) {
                playerguy2.sprite.update(delta);
		playerguy2.y -= fdelta;
            } if (isTrapped(playerguy2.x, playerguy2.y - fdelta) || isTrapped((float) (playerguy2.x + SIZE + 1.5), playerguy2.y - fdelta)) {
		newHitTime = (int) System.currentTimeMillis();
                if (newHitTime - preHitTime >= 250) {
                    playerguy2.health -= 10;
                    preHitTime = newHitTime;
                    hurt.play();
                }
            }
	} else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
            playerguy2.sprite = playerguy2.down;
            if (!isBlocked(playerguy2.x, playerguy2.y + SIZE + fdelta) && !isBlocked(playerguy2.x + SIZE - 1, playerguy2.y + SIZE + fdelta)) {
		playerguy2.sprite.update(delta);
		playerguy2.y += fdelta;
            }if (isTrapped(playerguy2.x, playerguy2.y - fdelta) || isTrapped(playerguy2.x + SIZE - 1, playerguy2.y - fdelta)) {
		newHitTime = (int) System.currentTimeMillis();
                if (newHitTime - preHitTime >= 250) {
                    playerguy2.health -= 10;
                    preHitTime = newHitTime;
                    hurt.play();
                }
            }
        } else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
            playerguy2.sprite = playerguy2.left;
            if (!(isBlocked(playerguy2.x - fdelta, playerguy2.y) || isBlocked(playerguy2.x - fdelta, playerguy2.y + SIZE - 1))) {
		playerguy2.sprite.update(delta);
		playerguy2.x -= fdelta;
            } if (isTrapped(playerguy2.x - fdelta, playerguy2.y) || isTrapped(playerguy2.x - fdelta, playerguy2.y + SIZE - 1)) {
		newHitTime = (int) System.currentTimeMillis();
                if (newHitTime - preHitTime >= 250) {
                    playerguy2.health -= 10;
                    preHitTime = newHitTime;
                    hurt.play();
                }
            }
	}
        else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
            playerguy2.sprite = playerguy2.right;
            if (cangoright && (!(isBlocked(playerguy2.x + SIZE + fdelta, playerguy2.y) || isBlocked(playerguy2.x + SIZE + fdelta, playerguy2.y + SIZE - 1)))) {
		playerguy2.sprite.update(delta);
		playerguy2.x += fdelta;
            }if (isTrapped(playerguy2.x + SIZE + fdelta, playerguy2.y) || isTrapped(playerguy2.x + SIZE + fdelta, playerguy2.y + SIZE - 1)) {
		newHitTime = (int) System.currentTimeMillis();
                if (newHitTime - preHitTime >= 250) {
                    playerguy2.health -= 10;
                    preHitTime = newHitTime;
                    hurt.play();
                }
            }
        }
        
	playerguy2.rect.setLocation(playerguy2.getplayershitboxX(), playerguy2.getplayershitboxY());

        for (FinalStairs s : stairs) {
            if (playerguy2.rect.intersects(s.hitbox)) {
		if (s.isvisible) {
                    sbg.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
            }
	}
                
	playerguy2.time -= counter/1000;
	if(playerguy2.health <= 0){
            sbg.enterState(5, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	}
    }

    public int getID() {
    	return 4;
    }
    
    private boolean isBlocked(float tx, float ty) {
        int xBlock = (int) tx / SIZE;
    	int yBlock = (int) ty / SIZE;
	return Blocked2.blocked2[xBlock][yBlock];
    }
    
    private boolean isTrapped(float tx, float ty) {
        int xTrap = (int) tx / SIZE;
    	int yTrap = (int) ty / SIZE;
	return Trapped.trapped[xTrap][yTrap];
    }
    
    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_R:
                Sewers.playerguy2.x = 663f;
                Sewers.playerguy2.y = 64f;
                Sewers.playerguy2.health = 100;
                break;
            
            default:
                break;
        }
    }
}