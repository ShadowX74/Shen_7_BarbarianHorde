package shen_7_slickgame;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

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

    private static final int SIZE = 32;

    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 750;
    public BarbarianHorde(int xSize, int ySize) {
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gc.setTargetFrameRate(60);
        gc.setShowFPS(false);
        
        playerguy = new Player();
	grassMap = new TiledMap("res/mydungeon.tmx");
	camera = new Camera(gc, grassMap);

	Blocked.blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];
	for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
		int tileID = grassMap.getTileId(xAxis, yAxis, 1);
		String value = grassMap.getTileProperty(tileID,"blocked", "false");
		if ("true".equals(value)) {
			Blocked.blocked[xAxis][yAxis] = true;
		}
            }
	}

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

	playerguy.sprite.draw((int) playerguy.x, (int) playerguy.y);
	g.drawString("Time Left: " + playerguy.time/1000, camera.cameraX + 10, camera.cameraY + 10);
	g.drawString("Health: " + (int)(playerguy.health), camera.cameraX + 10, camera.cameraY + 25);

        for (Gate d : gates) {
            if (d.isvisible) {
                d.currentImage.draw(d.x, d.y);
            }
        }
        for (Keys k : keyz) {
            if (k.isvisible) {
                k.currentImage.draw(k.x, k.y);
            }
        }
        for (Enemy r : bosses) {
            r.move();
        }
	for (Enemy e : bosses) {
            if (e.isVisible) {
        	e.currentanime.draw(e.Bx, e.By);
            }
	}
	for (FinalRing w: stuffwin) {
            if (w.isvisible) {
            w.currentImage.draw(w.x, w.y);
            }
	}
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        counter += delta;
	Input input = gc.getInput();
	float fdelta = delta * playerguy.speed;
	playerguy.setpdelta(fdelta);

	double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);
	float projectedright = playerguy.x + fdelta + SIZE;
	boolean cangoright = projectedright < rightlimit;

	if (input.isKeyDown(Input.KEY_UP)) {
            playerguy.sprite = playerguy.up;
            float fdsc = (float) (fdelta - (SIZE * .15));
            if (!(isBlocked(playerguy.x, playerguy.y - fdelta) || isBlocked((float) (playerguy.x + SIZE + 1.5), playerguy.y - fdelta))) {
                playerguy.sprite.update(delta);
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
            if (cangoright && (!(isBlocked(playerguy.x + SIZE + fdelta, playerguy.y) || isBlocked(playerguy.x + SIZE + fdelta, playerguy.y + SIZE - 1)))) {
		playerguy.sprite.update(delta);
		playerguy.x += fdelta;
            }
	}
        
	playerguy.rect.setLocation(playerguy.getplayershitboxX(),
	playerguy.getplayershitboxY());
                
        for (Keys k : keyz) {
            if (playerguy.rect.intersects(k.hitbox)) {
		if (k.isvisible) {
                    playerguy.hasKey = true;
                    k.isvisible = false;
		}
            }
	}
        for (Gate d : gates) {
            if (playerguy.rect.intersects(d.hitbox)) {
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
                if (e.isVisible) {
                    playerguy.health -= 34;
                    e.isVisible = false;
                }
            }
	}
	for (FinalRing w : stuffwin) {
            if (playerguy.rect.intersects(w.hitbox)) {
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
    }
}