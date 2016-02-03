package shen_7_slickgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Lose extends BasicGameState {
    private StateBasedGame game;
    public Image startimage;
    public Lose(int xSize, int ySize) {
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	startimage = new Image("res/Lose.png");
        this.game = game;
        // TODO AutoÃ¢â‚¬Âgenerated method stub
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    	startimage.draw();
        // TODO AutoÃ¢â‚¬Âgenerated method stub
        g.setColor(Color.white);
        //g.drawString("You LOSE!", 450, 200);
        //g.drawString("press 1 to try again", 400, 320);
    }



    


    public void update(GameContainer container, StateBasedGame game, int delta)


            throws SlickException {


// TODO AutoÃ¢â‚¬Âgenerated method stub


    }



  


    public int getID() {


// TODO AutoÃ¢â‚¬Âgenerated method stub


        return 2;


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
                BarbarianHorde.playerguy.hasKey = false;
                //redo potions and reset cordinates of player
                game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));


                break;


            case Input.KEY_2:


// TODO: Implement later


                break;


            case Input.KEY_3:


// TODO: Implement later


                break;


            default:


                break;


        }


    }


}

