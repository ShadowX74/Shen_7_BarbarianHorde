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

public class Win extends BasicGameState {
    private StateBasedGame game;
    public Image startimage;

    public Win(int xSize, int ySize) {
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        startimage = new Image("res/Win.png");
        this.game = game;
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    	startimage.draw();
        g.setColor(Color.white);
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    }

    public int getID() {
        return 3;
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_1:
                BarbarianHorde.playerguy.time  = 300000;
                BarbarianHorde.playerguy.speed = .4f;
                BarbarianHorde.playerguy.health = 100;
                BarbarianHorde.counter = 0;
                FinalRing.isvisible = true;
                BarbarianHorde.playerguy.x = 49f;
                BarbarianHorde.playerguy.y = 86f;
                BarbarianHorde.SandBoss.isVisible = true;
                BarbarianHorde.SandBoss.Bx = 1597;
                BarbarianHorde.SandBoss.By = 2199;
                BarbarianHorde.Boss.isVisible = true;
                BarbarianHorde.Boss.Bx = 2800;
                BarbarianHorde.Boss.By = 2135;
                BarbarianHorde.Boss2.isVisible = true;
                BarbarianHorde.Boss2.Bx = 2039;
                BarbarianHorde.Boss2.By = 1500;
                BarbarianHorde.MetalBoss.isVisible = true;
                BarbarianHorde.MetalBoss.Bx = 2944;
                BarbarianHorde.MetalBoss.By = 1400;
                BarbarianHorde.key1.isvisible = true;
                BarbarianHorde.gate1.isvisible = true;
                BarbarianHorde.gate2.isvisible = true;
                BarbarianHorde.descend1.isvisible = true;
                BarbarianHorde.descend2.isvisible = true;
                BarbarianHorde.playerguy.hasKey = false;
                BarbarianHorde.playerguy.hasRing = false;
                game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                break;
            
            default:
                break;
        }
    }
}
