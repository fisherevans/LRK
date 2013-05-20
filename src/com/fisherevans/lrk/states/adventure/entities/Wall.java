package com.fisherevans.lrk.states.adventure.entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/14/13
 * Time: 10:34 PM
 */
public class Wall extends LRKEntity
{
    public Wall(float x, float y, FixtureDef def, World world)
    {
        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyType.STATIC;

        Body body = world.createBody(bd);
        body.createFixture(def);

        setBody(body);
    }

    @Override
    public void update(int delta)
    {
    }

    @Override
    public Image getImage()
    {
        return null;
    }
}
