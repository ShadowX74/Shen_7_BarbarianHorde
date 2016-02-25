/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shen_7_barbarianhorde;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author ShadowX
 */
public class Stairs {
        public int x;
    public int y;
    public boolean isvisible = true;
    Image currentImage;
    Shape hitbox;
    Image stair = new Image("res/crawl-tiles Oct-5-2010/dc-dngn/gateways/stone_stairs_down.png");

    Stairs(int a, int b) throws SlickException {
        this.x = a;
	this.y = b;
	this.hitbox = new Rectangle(a, b, 32, 32);
        this.currentImage = stair;
    }
}
