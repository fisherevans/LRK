package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/12/13
 * Time: 5:31 PM
 */
public class Player extends LRKEntity
{
    private Image _image;

    private float _speed = 5f;

    public Player(float x, float y)
    {
        super();
        setX(x);
        setY(y);
        _image = Resources.getImage("res/test/images/32x32char.png");
    }

    @Override
    public void update(int delta)
    {
        Vector2f v = new Vector2f(0, 0);

        if(Game.lrk.getInput().isKeyDown(Options.getControlUp()))
            v.y -= 1;
        if(Game.lrk.getInput().isKeyDown(Options.getControlDown()))
            v.y += 1;
        if(Game.lrk.getInput().isKeyDown(Options.getControlLeft()))
            v.x -= 1;
        if(Game.lrk.getInput().isKeyDown(Options.getControlRight()))
            v.x += 1;

        v.normalise();
        v.scale(_speed);
        setVel(v);

        stepPos(delta);

        if(getX()- getRadius() < 5)
            setX(5f+getRadius());
        if(getY()- getRadius() < 6)
            setY(6f + getRadius());
    }

    @Override
    public Image getImage()
    {
        return _image;
    }
}
