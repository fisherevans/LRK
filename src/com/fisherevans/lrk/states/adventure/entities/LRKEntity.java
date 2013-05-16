package com.fisherevans.lrk.states.adventure.entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/12/13
 * Time: 5:30 PM
 */
public abstract class LRKEntity
{
    private Body _body;

    private float _angle = 0f;

    public abstract void update(int delta);
    public abstract Image getImage();

    public Body getBody()
    {
        return _body;
    }

    public void setBody(Body body)
    {
        _body = body;
    }

    public float getX()
    {
        return _body.getPosition().x;
    }

    public float getY()
    {
        return _body.getPosition().y;
    }

    public float getDegrees()
    {
        return _angle;
    }

    public void setDegrees(float angle)
    {
        _angle = angle;
    }
}
