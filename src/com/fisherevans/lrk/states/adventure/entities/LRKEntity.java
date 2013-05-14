package com.fisherevans.lrk.states.adventure.entities;

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
    private float _angle = 0, _radius = 0.5f;
    private Vector2f _pos, _vel;

    public LRKEntity()
    {
        _pos = new Vector2f(0, 0);
        _vel = new Vector2f(0, 0);
    }

    public abstract void update(int delta);

    public void stepPos(int delta)
    {
        _pos.add(_vel.copy().scale(delta/1000f));
    }

    public abstract Image getImage();


    public float getAngle()
    {
        return _angle;
    }

    public void setAngle(float angle)
    {
        _angle = angle;
    }

    public float getX()
    {
        return _pos.getX();
    }

    public void setX(float x)
    {
        _pos.x = x;
    }

    public float getY()
    {
        return _pos.getY();
    }

    public void setY(float y)
    {
        _pos.y = y;
    }

    public float getDx()
    {
        return _vel.getX();
    }

    public void setDx(float dx)
    {
        _vel.x = dx;
    }

    public float getDy()
    {
        return _vel.getY();
    }

    public void setDy(float dy)
    {
        _vel.y = dy;
    }

    public Vector2f getPos()
    {
        return _pos;
    }

    public void setPos(Vector2f pos)
    {
        _pos = pos;
    }

    public Vector2f getVel()
    {
        return _vel;
    }

    public void setVel(Vector2f vel)
    {
        _vel = vel;
    }

    public float getRadius()
    {
        return _radius;
    }

    public void setRadius(float radius)
    {
        _radius = radius;
    }
}
