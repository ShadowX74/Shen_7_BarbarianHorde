package shen_7_barbarianhorde;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class FinalRing {
    public int x;
    public int y;
    public boolean isvisible = true;
    Image currentImage;
    Shape hitbox;
    Image key = new Image("res/crawl-tiles Oct-5-2010/UNUSED/rings/ring_twisted.png");

    FinalRing(int a, int b) throws SlickException {
	this.x = a;
	this.y = b;
	this.hitbox = new Rectangle(a, b, 32, 32);
	this.currentImage = key;
    }
}
