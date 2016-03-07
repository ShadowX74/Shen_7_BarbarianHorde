/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shen_7_barbarianhorde;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Lose2 extends BasicGameState {
    private StateBasedGame game;
    public Image startimage;
    
    public Lose2(int xSize, int ySize) {
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	startimage = new Image("res/Lose2.png");
        this.game = game;
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    	startimage.draw();
        g.setColor(Color.white);
    }

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    }

    public int getID() {
        return 5;
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_1:
                Sewers.playerguy2.x = 663f;
                Sewers.playerguy2.y = 64f;
                Sewers.playerguy2.health = 100;
                game.enterState(4, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                break;
            
            default:
                break;
        }
    }
}