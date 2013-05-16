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
    public Wall(float x, float y, World world)
    {
        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyType.STATIC;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1f;
        fd.friction = 0.2f;
        fd.restitution = 0.0f;

        Body body = world.createBody(bd);
        body.createFixture(fd);

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
