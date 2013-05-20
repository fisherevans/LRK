package com.fisherevans.lrk.launcher;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import org.newdawn.slick.*;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/16/13
 * Time: 9:35 PM
 */
public class CollisionVectorMaker extends BasicGame
{
    public static AppGameContainer app;
    private Image img;
    private int indexX = 0, indexY = 0;
    /**
     * Create a new basic game
     *
     * @param title The title for the game
     */
    public CollisionVectorMaker(String title)
    {
        super(title);
    }

    @Override
    public void init(GameContainer container) throws SlickException
    {
        img = new Image("res/tilesheets/triggers.png", false, Image.FILTER_NEAREST);
        System.out.print("case " + ((indexX+1) + (indexY)*8) + ": vs = new Vec2 [] { ");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {

        img.draw(-indexX*32*16, -indexY*32*16, 16);
        int x = app.getInput().getMouseX()/16;
        int y = app.getInput().getMouseY()/16;
        g.setColor(Color.green);
        g.fillRect(x*16, y*16, 16, 16);
    }

    public void keyPressed(int key, char c)
    {
        System.out.println(" }; break;");
        if(key == Input.KEY_Z)
        {
            System.out.print("case " + ((indexX+1) + (indexY)*8) + ": vs = new Vec2 [] { ");
            return;
        }

        indexX++;
        if(indexX >= 8)
        {
            indexX = 0;
            indexY++;
        }
        System.out.print("case " + ((indexX+1) + (indexY)*8) + ": vs = new Vec2 [] { ");
    }

    public void mousePressed(int button, int x, int y)
    {
        x /= 16;
        y /= 16;
        y = 32 - y;
        float px = ((float)x)/32f-0.5f;
        float py = ((float)y)/32f-0.5f;
        System.out.print("new Vec2(" + px + "f, " + -py + "f), ");
    }

    public static void main(String[] args) throws SlickException
    {
        System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl/native/windows").getAbsolutePath());
        app = new AppGameContainer(new CollisionVectorMaker("Make points"), 512+16, 512+16, false);
        app.setUpdateOnlyWhenVisible(false);
        app.setMouseGrabbed(true);
        app.start();
    }
}
