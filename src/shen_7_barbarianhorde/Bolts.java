package shen_7_barbarianhorde;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author ShadowX
 */
public class Bolts {
        public int x, y, width, height, xmove, ymove; 
        private int dmg, hitboxX, hitboxY, timeExists;
        private boolean isVisible;
	Image boltpic;
        Shape hitbox;
        
        public Bolts(int a, int b) throws SlickException {
            this.x = a;
            this.y = b;
            this.isVisible = false;
            this.boltpic = new Image("res/crawl-tiles Oct-5-2010/effect/bolt01.png");
            this.hitbox = new Rectangle (a, b, 32, 32);
            this.timeExists = 35;
            this.xmove = 0;
            this.ymove = 0;
        }

    public int getTimeExists() {
        return timeExists;
    }

    public void setTimeExists(int t) {
        this.timeExists = t;
    }

    public void countdown() {
        this.timeExists--;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getHitboxX() {
        return hitboxX;
    }

    public void setHitboxX(int hitboxX) {
        this.hitboxX = hitboxX;
    }

    public int getHitboxY() {
        return hitboxY;
    }

    public void setHitboxY(int hitboxY) {
        this.hitboxY = hitboxY;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Image getBoltpic() {
        return boltpic;
    }

    public void setBoltpic(Image boltpic) {
        this.boltpic = boltpic;
    }

    public Shape getHitbox() {
        return hitbox;
    }

    public void setHitbox(Shape hitbox) {
        this.hitbox = hitbox;
    }

    public void setLocation(int a, int b) {
        this.setX((int) BarbarianHorde.playerguy.x);
        this.setY((int) BarbarianHorde.playerguy.y);
        this.setHitboxX((int) BarbarianHorde.playerguy.x + 5);
        this.setHitboxY((int) BarbarianHorde.playerguy.y - 10);
    }
    /**
         * Getters and setters are a common concept in Java.
         * A design guideline in Java, and object oriented programming in 
         * general, is to encapsulate/isolate values as much as possible.
         * Getters - are methods used to query the value of instance variables.
         * this.getLocationX();
         * Setters - methods that set values for instance variables
         * this.setLocation(BarbarianHorde.playerguy.x, BarbarianHorde.playerguy.y)
         */
}