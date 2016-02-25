package shen_7_barbarianhorde;

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
    public Projectile orb1;
    static public Keys key1;
    static public Enemy MetalBoss, SandBoss, Boss, Boss2;
    static public Gate gate1, gate2, descend1, descend2;
    static public Stairs stair1, stair2;
    public static Player playerguy;

    public ArrayList<FinalRing> stuffwin = new ArrayList();
    public ArrayList<Gate> gates = new ArrayList();
    public ArrayList<Gate> finalgates = new ArrayList();
    public ArrayList<Stairs> stair = new ArrayList();
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

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (yBlock % 40 == 0 && xBlock % 20 == 0) {
                        Enemy e = new Enemy(xAxis * SIZE, yAxis * SIZE);
                        bosses.add(e);
                    }
                }

            }
        }
        orb1 = new Projectile((int) BarbarianHorde.playerguy.x + 5, (int) BarbarianHorde.playerguy.y - 10);
        
        gate1 = new Gate(1600,1950, true);
        gate2 = new Gate(1632,1950, true);
        gates.add(gate1);
        gates.add(gate2);

        descend1 = new Gate(512,33, false);
        descend2 = new Gate(512,64, false);
        finalgates.add(descend1);
        finalgates.add(descend2);

        stair1 = new Stairs(1,1);
        stair2 = new Stairs(1,1);
        stair.add(stair1);
        stair.add(stair2);
        
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
	g.drawString("Orbs left: " + playerguy.orbs, camera.cameraX + 10, camera.cameraY + 25);
	g.drawString("Health: " + (int)(playerguy.health), camera.cameraX + 10, camera.cameraY + 10);
        
        g.drawString("x: " + (int)playerguy.x + "y: " +(int)playerguy.y ,playerguy.x, playerguy.y - 10);
        
        if (orb1.isIsVisible()) {
            orb1.orbpic.draw(orb1.getX(), orb1.getY());
        }
        for (Gate d : gates) {
            if (d.isvisible) {
                d.currentImage.draw(d.x, d.y);
            }
        }
        for (Gate d : finalgates) {
            if (d.isvisible) {
                d.currentImage.draw(d.x, d.y);
            }
        }
        for (Stairs s : stair) {
            if (s.isvisible) {
                s.currentImage.draw(s.x, s.y);
            }
        }
        for (Keys k : keyz) {
            if (k.isvisible) {
                k.currentImage.draw(k.x, k.y);
            }
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

        for (Enemy r : bosses) {
            r.move();
        }
	if (input.isKeyDown(Input.KEY_SPACE)) {
            orb1.setX((int) playerguy.x);
            orb1.setY((int) playerguy.y - 10);
            if (playerguy.orbs > 0 && orb1.isIsVisible() == false) {
                orb1.setIsVisible(true);
                orb1.setTimeExists(35);
            }
            if (playerguy.sprite == playerguy.right) {
                orb1.xmove = 10;
                orb1.ymove = 0;
            } else if (playerguy.sprite == playerguy.left) {
                orb1.xmove = -10;
                orb1.ymove = 0;
            } else if (playerguy.sprite == playerguy.up) {
                orb1.xmove = 0;
                orb1.ymove = -10;
            } else if (playerguy.sprite == playerguy.down) {
                orb1.xmove = 0;
                orb1.ymove = 10;
            }
        } else if (input.isKeyDown(Input.KEY_UP)) {
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
        if (orb1.isIsVisible()) {
            if (orb1.getTimeExists() > 0) {
                orb1.setX(orb1.x += orb1.xmove);
                orb1.setY(orb1.y += orb1.ymove);
                orb1.hitbox.setX(orb1.getX());
                orb1.hitbox.setY(orb1.getY());
                orb1.countdown();
            } else {
                orb1.setIsVisible(false);
                playerguy.orbs -= 1;
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
        
        for (Enemy r : bosses) {
            if (r.health <= 0) {
                r.isVisible = false;
                r.hitboxX = 0;
                r.hitboxY = 0;
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
       for (Gate d : finalgates) {
            if (playerguy.rect.intersects(d.hitbox)) {
                if (d.isvisible && playerguy.hasRing == true) {
                    d.isvisible = false;
		}
                if (d.isvisible && playerguy.hasRing == false) {
                    playerguy.x -= 10;
                }
            }
	}
        for (Enemy e : bosses) {
            if (orb1.hitbox.intersects(e.rect)) {
                orb1.setIsVisible(false);
                e.health -= 20;
                orb1.x = 0;
                orb1.y = 0;
            } else if (playerguy.rect.intersects(e.rect)) {
                if (e.isVisible) {
                    playerguy.health -= 25;
                    e.isVisible = false;
                }
            }
	}
	for (FinalRing w : stuffwin) {
            if (playerguy.rect.intersects(w.hitbox)) {
		if (w.isvisible) {
                    w.isvisible = false;
                    playerguy.hasRing = true;
		}
            }
	}
      	for (Stairs s : stair) {
            if (playerguy.rect.intersects(s.hitbox)) {
		if (s.isvisible) {
                    makeVisible();
                    sbg.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
            }
	}
	playerguy.time -= counter/1000;
	if(playerguy.health <= 0){
            makeVisible();
            sbg.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	}
    }

    public int getID() {
    	return 1;
    }

    public void makeVisible(){
        for (Enemy e : bosses) {
            e.isVisible = true;
        }
    }
    
    private boolean isBlocked(float tx, float ty) {
        int xBlock = (int) tx / SIZE;
    	int yBlock = (int) ty / SIZE;
	return Blocked.blocked[xBlock][yBlock];
    }
}