package shen_7_barbarianhorde;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Money {
    public int x;
    public int y;
    public boolean isvisible = true;
    Image currentImage;
    Shape hitbox;
    Image coin = new Image("res/crawl-tiles Oct-5-2010//item/misc/gold_pile.png");

    Money(int a, int b) throws SlickException {
        this.x = a;
        this.y = b;
        this.hitbox = new Rectangle(a, b, 32, 32);
        this.currentImage = coin;
    }
}
