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

    /**
     * updates the entity with each step of the main game loop
     * @param delta ms since the last update
     */
    public abstract void update(int delta);

    /**
     * gets the current image of the entity
     * @return the entity's image
     */
    public abstract Image getImage();

    /**
     * gets the jbox body object of this entity
     * @return the body
     */
    public Body getBody()
    {
        return _body;
    }

    /**
     * gets the jbox body object of this entity
     * @param body the new body
     */
    public void setBody(Body body)
    {
        _body = body;
    }

    /**
     * @return the x position of the entity in it's jbox world
     */
    public float getX()
    {
        return _body.getPosition().x;
    }

    /**
     * @return the j position of the entity in it's jbox world
     */
    public float getY()
    {
        return _body.getPosition().y;
    }

    /**
     * @return get the rotation of the jbox body (in degrees)
     */
    public float getDegrees()
    {
        return _angle;
    }

    /**
     * @param angle the new angle of rotation for the jbox body (in degrees)
     */
    public void setDegrees(float angle)
    {
        _angle = angle;
    }
}
