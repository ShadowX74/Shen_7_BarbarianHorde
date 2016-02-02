package shen_7_slickgame;

import org.newdawn.slick.state.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.css.Rect;

public class BarbarianHorde extends BasicGameState {
    public FinalRing ring;
    static public Keys key1;
    static public Enemy MetalBoss, SandBoss, Boss, Boss2;
    static public Gate gate1, gate2;
    public static Player playerguy;

    public ArrayList<FinalRing> stuffwin = new ArrayList();
    public ArrayList<Gate> gates = new ArrayList();
    public ArrayList<Keys> keyz = new ArrayList();
    public ArrayList<Enemy> bosses = new ArrayList();
    
    private static TiledMap grassMap;
    private static AppGameContainer app;
    private static Camera camera;
    public static int counter = 0;

    /**
     * The collision map indicating which tiles block movement - generated based
     * on tile properties
     */

    // changed to match size of sprites & map
    private static final int SIZE = 32;

    // screen width and height won't change
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 750;
    public BarbarianHorde(int xSize, int ySize) {
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gc.setTargetFrameRate(60);
        gc.setShowFPS(false);
        playerguy = new Player();
	// *******************
	// Scenerey Stuff
	// ****************
	grassMap = new TiledMap("res/MyDungeon/mydungeon.tmx");

        // Ongoing checks are useful
//	System.out.println("Tile map is this wide: " + grassMap.getWidth());
	camera = new Camera(gc, grassMap);
	// *****************************************************************
	// Obstacles etc.
        // build a collision map based on tile properties in the TileD map
	Blocked.blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];
	// System.out.println("Map height:" + grassMap.getHeight());
	// System.out.println("Map width:" + grassMap.getWidth());
	// There can be more than 1 layer. You'll check whatever layer has the
	// obstacles.
	// You could also use this for planning traps, etc.
	// System.out.println("Number of tile layers: "
	// +grassMap.getLayerCount());
//	System.out.println("The grassmap is " + grassMap.getWidth() + "by "+ grassMap.getHeight());
	for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
		// int tileID = grassMap.getTileId(xAxis, yAxis, 0);
		// Why was this changed?
		// It's a Different Layer.
		// You should read the TMX file. It's xml, i.e.,human-readable
		// for a reason
		int tileID = grassMap.getTileId(xAxis, yAxis, 1);
		String value = grassMap.getTileProperty(tileID,"blocked", "false");
		if ("true".equals(value)) {
//              	System.out.println("The tile at x " + xAxis + " and y axis " + yAxis + " is blocked.");
			Blocked.blocked[xAxis][yAxis] = true;
		}
            }
	}
//	System.out.println("Array length" + Blocked.blocked[0].length);
        gate1 = new Gate(1600,1950);
        gate2 = new Gate(1632,1950);
        gates.add(gate1);
        gates.add(gate2);

        key1 = new Keys(3082,1489);
        keyz.add(key1);
                
        MetalBoss = new Enemy(2944,1400);
        SandBoss = new Enemy(1597,2199);
        Boss = new Enemy (2800,2135);
        Boss2 = new Enemy (2039, 1500);
        bosses.add(MetalBoss);
        bosses.add(SandBoss);
        bosses.add(Boss);
        bosses.add(Boss2);
        ring = new FinalRing(1615, 3133);
	stuffwin.add(ring);
    }
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	camera.centerOn((int) playerguy.x, (int) playerguy.y);
	camera.drawMap();
	camera.translateGraphics();
	// it helps to add status reports to see what's going on
	// but it gets old quickly
//	System.out.println("Current X: " + playerguy.x + " \n Current Y: "+ playerguy.y);
	playerguy.sprite.draw((int) playerguy.x, (int) playerguy.y);
//	g.drawString("x: " + (int)playerguy.x + "y: " +(int)playerguy.y , playerguy.x, playerguy.y - 10);
	g.drawString("Time Left: " + playerguy.time/1000, camera.cameraX + 10, camera.cameraY + 10);
	g.drawString("Health: " + (int)(playerguy.health), camera.cameraX + 10, camera.cameraY + 25);
	//g.draw(playerguy.rect);
//	g.drawString("time passed: " +counter/1000, camera.cameraX +600,camera.cameraY );
	// moveenemies();

        for (Gate d : gates) {
            if (d.isvisible) {
                d.currentImage.draw(d.x, d.y);
                // draw the hitbox
//              d.draw(d.hitbox);
            }
        }
                
        for (Keys k : keyz) {
            if (k.isvisible) {
                k.currentImage.draw(k.x, k.y);
		// draw the hitbox
		// d.draw(d.hitbox);
            }
        }
       
        for (Enemy r : bosses) {
            r.move();
        }
                
	for (Enemy e : bosses) {
            if (e.isVisible) {
        	e.currentanime.draw(e.Bx, e.By);
		// draw the hitbox
		//g.draw(e.hitbox);
            }
	}
                
	for (FinalRing w: stuffwin) {
            if (w.isvisible) {
            w.currentImage.draw(w.x, w.y);
            // draw the hitbox
            //g.draw(w.hitbox);
            }
	}
    }

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		counter += delta;
		Input input = gc.getInput();
		float fdelta = delta * playerguy.speed;
		playerguy.setpdelta(fdelta);
		double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);
		// System.out.println("Right limit: " + rightlimit);
		float projectedright = playerguy.x + fdelta + SIZE;
		boolean cangoright = projectedright < rightlimit;
		// there are two types of fixes. A kludge and a hack. This is a kludge.
                
		if (input.isKeyDown(Input.KEY_UP)) {
			playerguy.sprite = playerguy.up;
			float fdsc = (float) (fdelta - (SIZE * .15));
			if (!(isBlocked(playerguy.x, playerguy.y - fdelta) || isBlocked((float) (playerguy.x + SIZE + 1.5), playerguy.y - fdelta))) {
				playerguy.sprite.update(delta);
				// The lower the delta the slower the sprite will animate.
				playerguy.y -= fdelta;
			}
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			playerguy.sprite = playerguy.down;
			if (!isBlocked(playerguy.x, playerguy.y + SIZE + fdelta) && !isBlocked(playerguy.x + SIZE - 1, playerguy.y + SIZE + fdelta)) {
				playerguy.sprite.update(delta);
				playerguy.y += fdelta;
			}
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			playerguy.sprite = playerguy.left;
			if (!(isBlocked(playerguy.x - fdelta, playerguy.y) || isBlocked(playerguy.x - fdelta, playerguy.y + SIZE - 1))) {
				playerguy.sprite.update(delta);
				playerguy.x -= fdelta;
			}
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			playerguy.sprite = playerguy.right;
			// the boolean-kludge-implementation
			if (cangoright && (!(isBlocked(playerguy.x + SIZE + fdelta, playerguy.y) || isBlocked(playerguy.x + SIZE + fdelta, playerguy.y + SIZE - 1)))) {
				playerguy.sprite.update(delta);
				playerguy.x += fdelta;
			} // else { System.out.println("Right limit reached: " +
				// rightlimit);}
		}
                
		playerguy.rect.setLocation(playerguy.getplayershitboxX(),
		playerguy.getplayershitboxY());
                
                for (Keys k : keyz) {
			if (playerguy.rect.intersects(k.hitbox)) {
				//System.out.println("yay");
				if (k.isvisible) {
					playerguy.hasKey = true;
					k.isvisible = false;
				}
			}
		}
                
                for (Gate d : gates) {
			if (playerguy.rect.intersects(d.hitbox)) {
				//System.out.println("yay");
				if (d.isvisible && playerguy.hasKey == true) {
					d.isvisible = false;
				}
                                if (d.isvisible && playerguy.hasKey == false) {
                                    playerguy.y -= 10;
                                }
			}
		}
		
                for (Enemy e : bosses) {
			if (playerguy.rect.intersects(e.rect)) {
				//System.out.println("yay");
				if (e.isVisible) {
					playerguy.health -= 34;
					e.isVisible = false;
				}
			}
		}
                
		for (FinalRing w : stuffwin) {
			if (playerguy.rect.intersects(w.hitbox)) {
				//System.out.println("yay");
				if (w.isvisible) {
					w.isvisible = false;
					sbg.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
				}
			}
		}
		 
		playerguy.time -= counter/1000;
		if(playerguy.time <= 0 || playerguy.health <= 0){
			sbg.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}

	public int getID() {
		return 1;
	}
	
	private boolean isBlocked(float tx, float ty) {
		int xBlock = (int) tx / SIZE;
		int yBlock = (int) ty / SIZE;
		return Blocked.blocked[xBlock][yBlock];
		// this could make a better kludge
	}
}